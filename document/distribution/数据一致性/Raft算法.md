## Raft算法

为了达到易于理解的目标，raft做了很多努力，其中最主要是两件事情：

1、问题分解
2、状态简化
   
问题分解是将"复制集中节点一致性"这个复杂的问题划分为数个可以被独立解释、理解、解决的子问题。
在raft，子问题包括，leader election， log replication，safety，membership changes。
而状态简化更好理解，就是对算法做出一些限制，减少需要考虑的状态数，使得算法更加清晰，更少的不确定性
（比如，保证新选举出来的leader会包含所有committed log entry）

raft协议中，一个节点任一时刻处于以下三个状态之一：
1、leader
2、follower
3、candidate


### 选举过程详解

term（任期）以选举（election）开始，然后就是一段或长或短的稳定工作期（normal Operation）。
任期是递增的，这就充当了逻辑时钟的作用.

如果follower在election timeout内没有收到来自leader的心跳，
（也许此时还没有选出leader，大家都在等；也许leader挂了；也许只是leader与该follower之间网络故障），
则会主动发起选举。步骤如下：

1、增加节点本地的 current term ，切换到candidate状态
2、投自己一票
3、并行给其他节点发送 RequestVote RPCs
4、等待其他节点的回复
   在这个过程中，根据来自其他节点的消息，可能出现三种结果：
	1）收到majority的投票（含自己的一票），则赢得选举，成为leader
	2）被告知别人已当选，那么自行切换到follower
	3）一段时间内没有收到majority投票，则保持candidate状态，重新发出选举

如果出现平票的情况，大家都在等啊等，直到超时后重新发起选举，
那么就延长了系统不可用的时间（没有leader是不能处理客户端写请求的），
因此raft引入了 randomized election timeouts（随机选举超时）来尽量避免平票情况。
同时，leader-based 共识算法中，节点的数目都是奇数个，尽量保证majority的出现。


### 日志复制 （客户端请求处理）

复制状态机：相同的初识状态 + 相同的输入 = 相同的结束状态

当系统（leader）收到一个来自客户端的写请求，到返回给客户端，整个过程从leader的视角来看会经历以下步骤：

1、leader append log entry
2、leader issue AppendEntries RPC in parallel
3、leader wait for majority response
4、leader apply entry to state machine
5、leader reply to client
6、leader notify follower apply log

leader只需要日志被复制到大多数节点即可向客户端返回

一个log被复制到大多数节点，就是committed，保证不会回滚
leader一定包含最新的committed log，因此leader只会追加日志，不会删除覆盖日志
不同节点，某个位置上日志相同，那么这个位置之前的所有日志一定是相同的
Raft永远不会通过计算副本数来提交以前条款的日志条目


### 安全性

#### 选举安全性
即任一任期内最多一个leader被选出。这一点非常重要，在一个复制集中任何时刻只能有一个leader。
系统中同时有多余一个leader，被称之为脑裂（brain split），这是非常严重的问题，会导致数据的覆盖丢失。
在raft中，两点保证了这个属性：

1、同一任期内最多只能投一票，先来先得；
2、只有获得majority投票的节点才会成为leader。

因此，某一任期内一定只有一个leader。

#### log匹配特性
如果两个节点上的某个log entry的log index相同且term相同，那么在该index之前的所有log entry应该都是相同的。

首先，leader在某一term的任一位置只会创建一个log entry，且log entry是append-only。
其次，consistency check（一致性检查）。
leader在AppendEntries中包含最新log entry之前的一个log 的term和index，
如果follower在对应的term index找不到日志，那么就会告知leader不一致。

当出现了leader与follower不一致的情况，leader强制follower复制自己的log。

#### leader完整性
如果一个log entry在某个任期被提交（committed），那么这条日志一定会出现在所有更高term的leader的日志里面。

1、一个日志被复制到majority节点才算committed
2、一个节点得到majority的投票才能成为leader，而节点A给节点B投票的其中一个前提是，
B的日志不能比A的日志旧（比较term，log index）。



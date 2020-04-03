## Paxos算法

算法有如下特性
1.基于消息传递，允许消息传输的丢失，重复，乱序，但是不允许消息被攥改。
2.在结点数少于半数失效的情况下仍然能正常的工作，结点失效可以在任何时候发生而不影响算法正常执行。

### Basic Paxos算法

Paxos算法包含三个角色 Proposer，Acceptor，Learner。

实现的时候采用一组固定数目Server，每个Server同时担任上述三个角色，
多个Client将自己的请求值Value_i随机发给一个Server处理，
然后这一组Server经过协商后得出统一的一个值Chosen_Value，
这个值必须被每个Server学习到，同时回复给所有发起请求的Client。



# 负载均衡

基于权重随机算法的 RandomLoadBalance、

基于最少活跃调用（+权重）数算法的 LeastActiveLoadBalance、

### 基于 hash 一致性的 ConsistentHashLoadBalance，


### 基于加权轮询算法的 RoundRobinLoadBalance （平滑加权轮询负载均衡）。
每个服务器对应两个权重，分别为 weight 和 currentWeight。
其中 weight 是固定的，currentWeight 会动态调整，初始值为0。
当有新的请求进来时，遍历服务器列表，让它的 currentWeight 加上自身权重。
遍历完成后，找到最大的 currentWeight，并将其减去权重总和，然后返回相应的服务器即可。

服务器 [A, B, C] 对应权重 [5, 1, 1] 的例子说明，现在有7个请求依次进入负载均衡逻辑，选择过程如下：

请求编号   currentWeight 数组   选择结果   减去权重总和后的 currentWeight 数组
1	[5, 1, 1]	  A	  [-2, 1, 1]
2	[3, 2, 2]	  A	  [-4, 2, 2]
3	[1, 3, 3]	  B	  [1, -4, 3]
4	[6, -3, 4]	  A	  [-1, -3, 4]
5	[4, -2, 5]	  C	  [4, -2, -2]
6	[9, -1, -1]	  A	  [2, -1, -1]
7	[7, 0, 0]	  A	  [0, 0, 0]



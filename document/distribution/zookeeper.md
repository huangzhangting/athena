### 


### 


### 应用
1、选主：
利用创建临时节点，创建成功的则为master，其他服务器利用watcher机制监听master

2、分布式锁
创建临时节点，成功了则获取锁，其他请求利用watcher机制等待释放；

共享锁、独占锁： 创建有序节点
读请求，如果没有节点，或者比自己小的节点都是读请求，则成功获取读锁；
若比自己小的节点有写请求，则获取锁失败

写请求，如果没有节点，则成功获取写锁

3、命名服务：例如分布式自增id



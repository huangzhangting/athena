### 非阻塞队列
1）LinkedList：双向链表，Deque的一种实现
2）PriorityQueue：优先级队列，有序数组，逻辑结构是一棵完全二叉树（有序堆）
3）ConcurrentLinkedQueue：
线程安全队列，利用cas操作


### 阻塞队列（线程安全）
(1) ArrayBlockingQueue 
一个由数组支持的有界队列。 

(2) LinkedBlockingQueue 
一个由链接节点支持的可选有界队列。 

(3) PriorityBlockingQueue 
一个由优先级堆支持的无界优先级队列。
放入元素时，因为无界，所以不会阻塞；获取时会阻塞；
数据存储在一个有序数据，逻辑结构是有序堆

(4) DelayQueue 
一个由优先级堆支持的，基于时间的调度队列。 
数据存储在一个PriorityQueue，元素本身需要实现Delayed接口

(5) SynchronousQueue 
同步队列，生产者、消费者一对一匹配，
有两种数据结构：公平队列、非公平栈

阻塞逻辑：如果没出现配对的线程之前，会先自旋，然后阻塞

？要先有线程take，然后其他线程put才能匹配上，如果先put会失败 ？


阻塞逻辑：基于锁机制实现
1）添加元素，如果队列满了，则在对应的 Condition notFull 上等待
2）获取元素，如果队列未空，则的对应的 Condition notEmpty 上等待


## HashMap

##### key hash值算法 ：
(key == null) ? 0 : (h = key.hashCode()) ^ (h >>> 16);

问题：为什么这么计算？


问题：容量为什么是2的n次幂？
因为计算节点在table位置是是用 （k的哈希 & 容量-1），低位全是1，直接取原数值


数据结构：
1、整体是一个节点数组
2、每个节点，是一个链表，或者红黑树

链表转红黑树：
1、链表长度>=8 && 节点数 >= 64

红黑树：




如何扩容？




## ConcurrentHashMap

##### 无锁读操作：
在1.8中ConcurrentHashMap的get操作全程不需要加锁
get操作全程不需要加锁是因为Node的成员val是用volatile修饰的和数组用volatile修饰没有关系
数组用volatile修饰主要是保证在数组扩容的时候保证可见性




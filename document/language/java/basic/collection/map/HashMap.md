## HashMap
键值对数组 + 链表（红黑树）实现

#### 链表转红黑树时机：
1、数组长度 >= 64
2、链表节点 >= 8

红黑树退化时机：
1、树节点数 <= 6

#### 扩容时机
1、链表转红黑树时，如果数组长度 < 64
2、元素个数 > 阈值（容量 * 负载因子）  容量是2的幂次方，如果指定初始值，那就是大于等于初始值的最小的一个值

#### 容量为什么是2的幂次方
因为计算元素在数组位置是用的与运算 (n - 1) & hash，容量n-1二进制相当于全是1，即去hash值本身数据。

#### 扩容时优化
1.8版本之前使用的是逐个元素头插法，并发扩容情况下会造成死循环

Entry<K,V> next = e.next;
int i = indexFor(e.hash, newCapacity);
e.next = newTable[i];
newTable[i] = e;
e = next;

假设：
老数据：A-B-null
新表位置：null

线程T1，T2，同时扩容 e->A next->B
T1执行完：B->A->null
T2开始执行，A->B->A
T2继续执行，e->B next->A  B<->A

优化：
区分低位链表，高位链表
(e.hash & oldCap) == 0：组成低位链表，还是放原来的位置
否则，组成高位链表，放到[i + oldCap]位置

因为计算下标位置时是：e.hash & (oldCap-1)
扩容时2倍增长，e.hash & oldCap 正好是取多出来的一位

newTab[j] = loHead;
newTab[j + oldCap] = hiHead;

高低链表组装完后，直接赋值，所以不会出现环，导致死锁


## ThreadLocal

#### ThreadLocal ，ThreadLocalMap 和 Thread 的关系
1、Thread里面维护了一个 ThreadLocalMap
2、ThreadLocalMap 是 ThreadLocal 的内部类
3、ThreadLocalMap 里面存的键值对的 键 是 ThreadLocal
键值对是弱引用（WeakReference）

#### ThreadLocalMap
1、ThreadLocalMap的数据结构仅仅是数组
2、ThreadLocalMap 是通过开放地址法来解决hash 冲突的问题
3、ThreadLocalMap里面的Entry 内部类中的key 是弱引用，value 是强引用

#### 为什么选择开放地址法
特点：
一旦发生了冲突，就去寻找下一个空的散列地址
容易产生堆积问题，不适于大规模的数据存储。
散列函数的设计对冲突会有很大的影响，插入时可能会出现多次冲突的现象。
删除的元素是多个冲突元素中的一个，需要对后面的元素作处理，实现较复杂。

1、ThreadLocal 中看到一个属性 HASH_INCREMENT = 0x61c88647 ，
0x61c88647 是一个神奇的数字，让哈希码能均匀的分布在2的N次方的数组里, 
即 Entry[] table，关于这个神奇的数字google 有很多解析，这里就不重复说了

2、ThreadLocal 往往存放的数据量不会特别大（而且key 是弱引用又会被垃圾回收，及时让数据量更小），
这个时候开放地址法简单的结构会显得更省空间，
同时数组的查询效率也是非常高，
加上第一点的保障，冲突概率也低


#### 清理方法 expungeStaleEntry 
remove 、get 和set 方法都可能触发清理方法expungeStaleEntry()
清理key已经被回收的value，因为是开放地址法，所以清理完后还要将后面的元素交换过来，
不然下次存入数据时，前面的位置已经为null了，可以直接存入数据，这样就会出现重复的key，


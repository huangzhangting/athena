## LinkedHashMap

1、extends HashMap 定制化功能通过，父类的扩展点方法，以及重写父类部分方法来实现
例如
重写get方法实现访问顺序：if (accessOrder)afterNodeAccess(e);
重写afterNodeInsertion，并提供扩展点removeEldestEntry，来实现移除头结点（移除最老的数据）
重写newNode，实现插入顺序：会调用linkNodeLast(p)，把新节点加入到链表

2、相比于 HashMap 多维护了一套插入节点的链表结构，用来实现顺序输出

3、支持插入顺序、访问顺序（被访问的数据移动到尾部）

4、特殊使用：
利用访问顺序，可以实现LRU（Least Recently Used 最近最少使用）算法: 
指定accessOrder=true，重写removeEldestEntry方法
例子：org.tin.athena.test.LRUCache


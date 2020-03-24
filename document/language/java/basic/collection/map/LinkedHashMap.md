## LinkedHashMap

1、extends HashMap 定制化功能通过，父类的扩展点方法，以及重写父类部分方法来实现

2、相比于 HashMap 多维护了一套插入节点的链表结构，用来实现顺序输出

3、支持插入顺序、访问顺序

4、特殊使用：
利用访问顺序，可以实现LRU算法: 指定accessOrder=true，重写removeEldestEntry方法
例子：org.tin.athena.test.LRUCache


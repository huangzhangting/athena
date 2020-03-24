## TreeMap

1、使用红黑树，存储节点

2、节点根据key排序，可以自定义比较器，没有定义比较器的情况下，使用key.compareTo()方法

3、特殊使用：
实现一致性hash：利用 tailMap(fromKey)方法，返回key>=fromKey的子map
例子：org.tin.athena.test.ConsistentHashingWithVirtualNode


#### 弱引用 WeakReference
要搞清楚 弱引用对象 和 被弱引用指向的对象
使用如下：
WeakReference<TestObject> weakReference = new WeakReference<>(new TestObject());
弱引用对象weakReference 指向了 new TestObject()
可以通过 weakReference.get() 获得指向的对象

高级应用：WeakReference(T referent, ReferenceQueue<? super T> q)
使用如下：
ReferenceQueue<Object> queue = new ReferenceQueue<>();
WeakReference<TestObject> weakReference = new WeakReference<>(new TestObject(), queue);

引用队列ReferenceQueue的作用：
当referent（TestObject）被GC回收时，jvm会把弱引用对象（weakReference）加入到该队列


## WeakHashMap
键值对数组 + 链表 实现

#### 定义了一个引用队列：
private final ReferenceQueue<Object> queue = new ReferenceQueue<>();

#### 键值对类继承弱引用：
Entry<K,V> extends WeakReference<Object>
Entry的构造方法，调用了super(key, queue)
也就是说：
new Entry() 弱引用对象 指向了 key 对象，还绑定了 ReferenceQueue

#### 引用队列 ReferenceQueue 的作用：
当key被GC回收时，jvm会把弱引用对象（Entry对象）加入到绑定的队列

把弱引用对象维护起来的作用：expungeStaleEntries() 方法：删除陈旧的条目
get、put、remove、size等操作都会触发清理方法

从引用队列取出元素（从队列删除），然后从键值对数组中删除该数据，并将该键值对的value=null

作用：
1）清理弱引用对象（Entry对象）
2）清理value强引用的对象，帮助GC


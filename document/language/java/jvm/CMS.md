### CMS
CMS全称 ConcurrentMarkSweep，是一款并发的、使用标记-清除算法的垃圾回收器， 
如果老年代使用CMS垃圾回收器，需要添加虚拟机参数-"XX:+UseConcMarkSweepGC"。

### GC过程
1、InitialMarking（初始化标记，整个过程STW）
该阶段单线程执行，主要分分为两步：
 1）标记GC Roots可达的老年代对象；
 2）遍历新生代对象，标记可达的老年代对象；

2、Marking（并发标记）
该阶段GC线程和应用线程并发执行，遍历InitialMarking阶段标记出来的存活对象，
然后继续递归标记这些对象可达的对象。

因为该阶段并发执行的，在运行期间可能发生新生代的对象晋升到老年代、或者是直接在老年代分配对象、或者更新老年代对象的引用关系等等，
对于这些对象，都是需要进行重新标记的，否则有些对象就会被遗漏，发生漏标的情况。

为了提高重新标记的效率，该阶段会把上述对象所在的Card标识为Dirty，后续只需扫描这些Dirty Card的对象，避免扫描整个老年代。

2.1 预清理阶段
扫描处理Dirty Card中的对象

3、重标记(STW)  
这个阶段是多线程的, 暂停所有用户线程。

遍历新生代对象，重新标记
根据GC Roots，重新标记
遍历老年代的Dirty Card，重新标记，这里的Dirty Card大部分已经在clean阶段处理过

减少remark阶段停顿
一般CMS的GC耗时80%都在remark阶段，如果发现remark阶段停顿时间很长，可以尝试添加该参数：
-XX:+CMSScavengeBeforeRemark。
在执行remark操作之前先做一次Young GC，目的在于减少年轻代对老年代的无效引用，降低remark时的开销。


4、并发清理。用户线程被重新激活，同时清理那些无效的对象。

5、重置。 CMS清除内部状态，为下次回收做准备。


#### 相关参数
-XX:CMSInitiatingOccupancyFraction=70 是指设定CMS在对内存占用率达到70%的时候开始GC

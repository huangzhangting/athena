# Spring

### IOC: 控制反转（依赖注入）
好处：
IoC是一种可以帮助我们解耦各业务对象间依赖关系的对象绑定方式！

1、实现方式：
通过构造方法注入、通过setter方法注入

自动绑定模式：autowire属性：
no：
byName：根据名称，即id属性
byType：根据类型
constructor：针对构造方法的自动绑定
autodetect：byType和constructor模式的结合体


2、实现原理

3、常用注解，注解实现原理

4、循环依赖怎么解决

### IOC容器的职责：
1、业务对象的构建管理
2、业务对象之间的依赖绑定


### bean的scope类型：
singleton ：
1、singleton类型的bean定义，在一个容器中只存在一个共享实例，
（而Singleton模式则是保证在同一个Classloader中只存在一个这种类型的实例）
2、从容器启动，到它第一次被请求而实例化开始，只要容器不销毁或者退出，该类型bean的单一实例就会一直存活

prototype ：
拥有prototype scope的bean定义，容器在接到该类型对象的请求的时候，会每次都重新生成一个新的对象实例给请求方

自定义scope:

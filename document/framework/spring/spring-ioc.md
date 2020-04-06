# Spring - IOC

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
spring针对Bean之间的循环依赖，有自己的处理方案。关键点就是三级缓存。
当然这种方案不能解决所有的问题，他只能解决Bean单例模式下非构造函数的循环依赖。

scope：配置singleton；通过setter方法注入依赖关系

实现原理：
DefaultSingletonBeanRegistry#getSingleton
第一级缓存singletonObjects里面放置的是实例化好的单例对象。
第二级earlySingletonObjects里面存放的是提前曝光的单例对象（没有完全装配好）。
第三级singletonFactories里面存放的是要被实例化的对象的对象工厂。

例子：A->B->C->A
1、先创建A，然后将没有完全装配的A对象，放入缓存，然后进行初始化、成员属性赋值操作
2、发现需要依赖B，然后创建B，流程跟A一样
3、然后创建C，流程跟A一样
4、C发现依赖A，然后从缓存中得到未完全初始化的A
5、C初始化完成后，返回到B的流程，然后返回到A的流程


### IOC容器的职责：
1、业务对象的构建管理
2、业务对象之间的依赖绑定

Spring的IoC容器主要有两种，即 BeanFactory 和 ApplicationContext。
ApplicationContext构建于BeanFactory之上，提供了许多BeanFactory之外的特性：
1）支持国际化
2）应用事件发布
3）资源加载
4）父子容器
5）环境配置项信息：EnvironmentCapable # Environment
6）支持查看更多信息：ListableBeanFactory


### bean的 scope 类型：
singleton ：
1、singleton类型的bean定义，在一个容器中只存在一个共享实例，
（而Singleton模式则是保证在同一个Classloader中只存在一个这种类型的实例）
2、从容器启动，到它第一次被请求而实例化开始，只要容器不销毁或者退出，该类型bean的单一实例就会一直存活

prototype ：
拥有prototype scope的bean定义，容器在接到该类型对象的请求的时候，会每次都重新生成一个新的对象实例给请求方

自定义scope:


### FactoryBean 的作用
在spring中分为简单bean，和复杂bean，复杂bean可以使用FactoryBean来创建

### bean实例化策略
1、通过无惨构造方法，反射创建；如果存在存在方法替换，则使用cglib生成子类
2、通过指定构造方法，反射创建；如果存在存在方法替换，则使用cglib生成子类
3、通过工厂方法创建

### lookup-method
主要解决是 singleton 依赖 prototype 场景

<bean id="myCommand" class="fiona.apple.AsyncCommand" scope="prototype"></bean>
<bean id="commandManager" class="fiona.apple.CommandManager">
    <lookup-method name="createCommand" bean="myCommand"/>
</bean>

public abstract class CommandManager {
    public Object process(Object commandState) {
        Command command = createCommand();
        command.setState(commandState);
        return command.execute();
    }
    // okay... but where is the implementation of this method?
    protected abstract Command createCommand();
}

### replaced-method
方法替换

<bean id="myBean" class="xxx.MyBean">
    <replaced-method name="display" replacer="replacer"/>
</bean>
<bean id="replacer" class="xxx.MyBeanReplacer"/>

public class MyBeanReplacer implements MethodReplacer{
    @Override
    public Object reimplement(Object obj, Method method, Object[] args) throws Throwable {
        System.out.println("我替换了原来的方法");
        return null;
    }
}


#### BeanFactoryPostProcessor 扩展机制：
该机制允许我们在容器实例化相应对象之前，对注册到容器的BeanDefinition所保存的信息做相应的修改。
常用的PostProcessor：
1、PropertyPlaceholderConfigurer
2、PropertyOverrideConfigurer
3、CustomEditorConfigurer：使用CustomEditorConfigurer注册自定义PropertyEditor到容器

PropertyEditor 功能：
来帮助进行String类型到其他类型的转换工作

自定义PropertyEditor

对于BeanFactory来说，对象实例化默认采用延迟初始化;
ApplicationContext启动之后会实例化所有的bean定义;

#### BeanPostProcessor 扩展机制：
1、BeanPostProcessor是存在于对象实例化阶段，而BeanFactoryPostProcessor则是存在于容器启动阶段
2、会处理容器内所有符合条件的实例化后的对象实例


### @Autowired 注解实现原理
1、通过bean后置处理器实现：AutowiredAnnotationBeanPostProcessor 
2、调用的地方 AbstractBeanFactory.createBean -> 子类doCreateBean
 -> populateBean() 填充bean成员属性
3、通过调用InstantiationAwareBeanPostProcessor 的 postProcessPropertyValues
实现成员属性注入

优先按照类型匹配，
如果存在类型的多个实例就尝试使用类型匹配，如果确定不了，
可以通过@Primary和@Priority注解来确定，
如果也确定不了，最后通过名称筛选一个。

如果和 @Qualifier 结合使用时，自动注入的策略就根据名称匹配了。



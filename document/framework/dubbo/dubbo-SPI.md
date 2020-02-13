# Dubbo SPI

SPI 全称为 Service Provider Interface，是一种服务发现机制。
SPI 的本质是将接口实现类的全限定名配置在文件中，并由服务加载器读取配置文件，加载实现类。
这样可以在运行时，动态为接口替换实现类。

### getExtension(name) 获取扩展类原理
先从缓存获取
Map<String, Holder<Object>> cachedInstances：

如果实例缓存没有，则创建扩展类

#### loadExtensionClasses过程，以及重要成员属性：

##### 解析 SPI 注解，获取默认名称
String cachedDefaultName：

##### 加载指定文件夹下的配置文件
通过类加载器加载配置文件中的实现类配置。

##### 1、检测目标类上是否有 Adaptive 注解
Class<?> cachedAdaptiveClass：

##### 2、检测 clazz 是否是 Wrapper 类型
Set<Class<?>> cachedWrapperClasses：

##### 3、clazz 是一个普通的拓展类
Map<String, Activate> cachedActivates：如果类上有 Activate 注解，存储 name 到 Activate 注解对象的映射关系
Map<Class<?>, String> cachedNames：存储 Class 到名称的映射关系
Map<String, Class<?>> extensionClasses：存储名称到 Class 的映射关系


#### 相关注解：
@SPI：声明在接口上，可以设置默认名称

@Adaptive：声明在接口方法上，或者实现类上，用于 getAdaptiveExtension(); 优先获取实现类 
1. 在类上加上@Adaptive注解的类，是最为明确的创建对应类型Adaptive类。所以他优先级最高。
2. @SPI注解中的value是默认值，如果通过URL获取不到关于取哪个类作为Adaptive类的话，就使用这个默认值，当然如果URL中可以获取到，就用URL中的。
3. 可以再方法上增加@Adaptive注解，注解中的value与链接中的参数的key一致，链接中的key对应的value就是spi中的name,获取相应的实现类。


@Activate：声明在实现类上，作用是什么 ？


## dubbo IOC
Dubbo IOC 是通过 setter 方法注入依赖。
通过 ObjectFactory 获取依赖对象：Dubbo 目前提供了两种 ExtensionFactory，
分别是 SpiExtensionFactory 和 SpringExtensionFactory。
前者用于创建自适应的拓展，后者是用于从 Spring 的 IOC 容器中获取所需的拓展。


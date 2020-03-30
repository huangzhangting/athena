# Spring MVC

### 父子容器
1、spring ioc容器
2、spring MVC 容器（子容器）

父容器通过 ContextLoaderListener 创建，实际类：XmlWebApplicationContext
创建成功后会设置到 ServletContext
servletContext.setAttribute(WebApplicationContext.ROOT_WEB_APPLICATION_CONTEXT_ATTRIBUTE, context);

初始化：configureAndRefreshWebApplicationContext() -> AbstractApplicationContext 的 refresh()


MVC容器通过 DispatcherServlet 
1、initServletBean() -> initWebApplicationContext()
2、从ServletContext获取ROOT_WEB_APPLICATION_CONTEXT_ATTRIBUTE，作为父级容器
3、创建MVC容器，默认类：XmlWebApplicationContext
4、configureAndRefreshWebApplicationContext() -> AbstractApplicationContext 的 refresh()
5、FrameworkServlet 定义的 onRefresh() -> initStrategies(context) 初始化mvc相关的资源


当然，在根容器创建与子容器创建之间，还会创建监听器、过滤器等，完整的加载顺序为：
（web.xml加载顺序）
ServletContext -> context-param -> listener -> filter -> servlet


getBean逻辑：
1、单例bean从单例缓存获取
2、如果有父级容器，并且bean没有在子容器定义，则从父容器获取
3、如果在子容器定义了bean，则从子容器获取


### 容器初始化过程 AbstractApplicationContext 的 refresh()
1、准备容器环境：
Initialize any placeholder property sources in the context environment
等
2、准备bean工厂
3、注册bean工厂后置处理器
4、调用bean工厂后置处理器
5、注册bean后置处理器，例如：AutowiredAnnotationBeanPostProcessor 等等
6、初始化国际化资源
7、初始化事件广播器
8、扩展点onRefresh()，提供给子类用的
9、注册事件监听器
10、初始化剩余的单例bean（非延迟的）
11、完成刷新：初始化生命周期处理器、发布容器刷新事件


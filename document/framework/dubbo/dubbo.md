### 特点
0、服务注册、发现
1、集群
2、服务降级
3、服务路由
4、负载均衡
5、有现成的管理控制台
6、成熟的生产环境应用：其他公司
7、


### 优雅停机

AbstractConfig 里面： 添加了 shutdown hook

static {
        Runtime.getRuntime().addShutdownHook(new Thread(new Runnable() {
            public void run() {
                if (logger.isInfoEnabled()) {
                    logger.info("Run shutdown hook now.");
                }
                ProtocolConfig.destroyAll();
            }
        }, "DubboShutdownHook"));
    }
    

一、AbstractRegistryFactory.destroyAll()
销毁注册信息：registry.destroy();
1）注销对外提供的服务
2）取消订阅
3）取消失败重试任务
4）关闭注册客户端，例如zk客户端

二、销毁协议：protocol.destroy();
1）关闭 ExchangeServer
2）关闭 ExchangeClient
3）销毁 invokers 
4）撤销 exporter 
        

### 线程池策略和线程模型（不同的版本，不太一样）
线程模型：用于处理如下请求：链接事件、断开事件、普通请求、异常事件、发送
1、all：所有请求都分发到线程池，不包含发送
2、message：只有message receive使用线程池.

3、connection：
新建一个线程池（核心线程数1）处理链接、断开事件，connect disconnect 保证顺序
普通请求、异常事件，分发到线程池

4、direct：所有消息都不派发到业务线程池，全部在IO线程上直接执行
5、execution：除发送全部使用线程池处理

dubbo线程分派策略（不同版本的源码不太一样）
all	所有消息都派发到线程池，包括请求，响应，连接事件，断开事件等
direct	所有消息都不派发到线程池，全部在 IO 线程上直接执行
message	只有请求和响应消息派发到线程池，其它消息均在 IO 线程上执行
execution	只有请求消息派发到线程池，不含响应。其它消息均在 IO 线程上执行
connection	在 IO 线程上，将连接断开事件放入队列，有序逐个执行，其它消息派发到线程池


### 请求过程
客户端：
1、调用代理类
2、服务降级 MockCluster
3、集群容错 例如：FailoverClusterInvoker
4、路由过滤
5、负载均衡
6、过滤器处理
7、协议层 例如：DubboInvoker
8、交换层客户端 例如：Netty client
9、编码、序列化，发送数据

请求调用路径：
proxy0#sayHello(String)
  —> InvokerInvocationHandler#invoke(Object, Method, Object[])
    —> MockClusterInvoker#invoke(Invocation)
      —> AbstractClusterInvoker#invoke(Invocation)
        —> FailoverClusterInvoker#doInvoke(Invocation, List<Invoker<T>>, LoadBalance)
          —> Filter#invoke(Invoker, Invocation)  // 包含多个 Filter 调用
            —> ListenerInvokerWrapper#invoke(Invocation) 
              —> AbstractInvoker#invoke(Invocation) 
                —> DubboInvoker#doInvoke(Invocation)
                  —> ReferenceCountExchangeClient#request(Object, int)
                    —> HeaderExchangeClient#request(Object, int)
                      —> HeaderExchangeChannel#request(Object, int)
                        —> AbstractPeer#send(Object)
                          —> AbstractClient#send(Object, boolean)
                            —> NettyChannel#send(Object, boolean)
                              —> NioClientSocketChannel#write(Object)
                              

服务端：
1、收到消息后，先解码，例如：DubboCodec
2、


解码后，服务调用链：
NettyHandler#messageReceived(ChannelHandlerContext, MessageEvent)
  —> AbstractPeer#received(Channel, Object)
    —> MultiMessageHandler#received(Channel, Object)
      —> HeartbeatHandler#received(Channel, Object)
        —> AllChannelHandler#received(Channel, Object) //只是一种线程分派策略
          —> ExecutorService#execute(Runnable)    // 由线程池执行后续的调用逻辑

ChannelEventRunnable#run()
  —> DecodeHandler#received(Channel, Object)
    —> HeaderExchangeHandler#received(Channel, Object)
      —> HeaderExchangeHandler#handleRequest(ExchangeChannel, Request)
        —> DubboProtocol.requestHandler#reply(ExchangeChannel, Object)
          —> Filter#invoke(Invoker, Invocation)
            —> AbstractProxyInvoker#invoke(Invocation)
              —> Wrapper0#invokeMethod(Object, String, Class[], Object[])
                —> DemoServiceImpl#sayHello(String)

服务端响应：
1、封装Response
2、channel.send(res) 例如：NettyChannel


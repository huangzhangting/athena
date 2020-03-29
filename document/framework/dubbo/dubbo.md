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
        

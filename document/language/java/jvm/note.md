### 线上jvm配置例子

-server 

-XX:+DisableExplicitGC 
-XX:+UseConcMarkSweepGC 
-XX:+CMSParallelRemarkEnabled 
-XX:+UseCMSCompactAtFullCollection 

-Xms1024m -Xmx1024m -XX:PermSize=64m -XX:MaxPermSize=256m 

-Dfile.encoding=UTF-8 
-Dcom.sun.management.jmxremote 
-Dcom.sun.management.jmxremote.port=000 
-Dcom.sun.management.jmxremote.ssl=false 
-Dcom.sun.management.jmxremote.authenticate=false 
-Djava.rmi.server.hostname=xx.xx.xx
-Duser.dir=/data/xxx

-XX:+HeapDumpOnOutOfMemoryError 
-XX:HeapDumpPath=/data/logs/xxx/dump/ 

-jar /data/xxx/xxx.jar


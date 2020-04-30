### mysql zip安装
1、下载zip文件，解压后：例如：D:\hzt\tool\mysql-8.0.19-winx64

2、在mysql-8.0.19-winx64下创建文件夹data、创建my.ini配置文件
配置如下：
[client]
port=3306
default-character-set=utf8

[mysql] 
default-character-set=utf8 

[mysqld] 
port = 3306 
basedir=D:/hzt/tool/mysql-8.0.19-winx64
datadir=D:/hzt/tool/mysql-8.0.19-winx64/data  
collation-server = utf8_unicode_ci
init-connect='SET NAMES utf8'
character-set-server = utf8
max_connections=200 
default-storage-engine=INNODB 
sql_mode=NO_ENGINE_SUBSTITUTION,STRICT_TRANS_TABLES 

3、以管理员身份打开cmd命令窗口，将目录切换到MySQL的安装目录的 bin目录 下

4、安装mysql： mysqld -install
执行命令后提示：Service successfully installed. 表示安装成功
可能出现缺少dll文件，例如：可以去下载安装VC_redist.x64

5、初始化mysql：mysqld --initialize-insecure --user=mysql 

6、启动mysql服务：net start mysql

7、启动MySQL之后，root用户的密码为空，设置密码：
mysqladmin -u root -p password 新密码 
Enter password: 旧密码

由于旧密码为空，所以直接回车即可。


##### 使用navicat链接mysql时会出现问题
例如：Navicat连接MySQL Server8.0版本时出现Client does not support authentication protocol requested  by server

登录mysql： mysql -h主机地址 -u用户名 －p用户密码 （注:u与root可以不用加空格，其它也一样）

use mysql;

alter user 'root'@'localhost' identified with mysql_native_password by 'root';

flush privileges;

然后再用navicat就可以链接了
原因：

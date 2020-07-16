## tomcat安装使用

1. 下载
```shell
wget https://mirrors.tuna.tsinghua.edu.cn/apache/tomcat/tomcat-8/v8.5.57/bin/apache-tomcat-8.5.57.tar.gz
```
2. 解压

  ```shell
  tar -xf apache-tomcat-8.5.57.tar.gz
  ```

  

3. 配置jdk环境
- 将jdk配置到环境变量，tomcat启动时若jdk未配置将自动寻找环境变量
```shell
vim /etc/profile
export JAVA_HOME=/usr/local/jdk1.8
export PATH=$PATH:$JAVA_HOME/bin
:wq
#更新
source /ect/profile
```
- 在tomcat启动脚本中配置
4. 将web服务与tomcat分离
```shell
mkdir test-web
cp -r /usr/local/tomcat/conf /usr/local/test-web/
cd test-web
mkdir logs webapps

#编写启动脚本
vim tomcat.sh
#!/bin/bash 
export JAVA_OPTS="-Xms100m -Xmx200m"
export JAVA_HOME=/usr/local/jdk1.8/
export CATALINA_HOME=/usr/local/tomcat
cd `dirname $0`
export CATALINA_BASE="`pwd`"

case $1 in
        start)
        $CATALINA_HOME/bin/catalina.sh start
                echo start success!!
        ;;
        stop)
                $CATALINA_HOME/bin/catalina.sh stop
                echo stop success!!
        ;;
        restart)
        $CATALINA_HOME/bin/catalina.sh stop
                echo stop success!!
                sleep 3
        $CATALINA_HOME/bin/catalina.sh start
        echo start success!!
        ;;
        version)
        $CATALINA_HOME/bin/catalina.sh version
        ;;
        configtest)
        $CATALINA_HOME/bin/catalina.sh configtest
        ;;
        esac
exit 0
:wq
#赋予执行权限
chmod +x tomcat.sh
```
5. 在webapps目录下建立服务
```shell
cd webapps
mkdir ROOT
echo 'hello tomcat!' > ROOT/index.html
```
6.启动并访问
```shell
/usr/local/test-web/tomcat.sh start
```

## tomcat 源码构建

- **下载源码:** https://mirrors.tuna.tsinghua.edu.cn/apache/tomcat/tomcat-8/v8.5.57/src/apache-tomcat-8.5.57-src.zip

- **解压**

-  `apache-tomcat-8.5.57-src`目录下添加`pom.xml`文件 

  ```xml
  <?xml version="1.0" encoding="UTF-8"?>
  <project xmlns="http://maven.apache.org/POM/4.0.0"
           xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance"
           xsi:schemaLocation="http://maven.apache.org/POM/4.0.0 http://maven.apache.org/xsd/maven-4.0.0.xsd">
  
      <modelVersion>4.0.0</modelVersion>
      <groupId>org.apache.tomcat</groupId>
      <artifactId>Tomcat8.0</artifactId>
      <name>Tomcat8.0</name>
      <version>8.0</version>
  
      <build>
          <finalName>Tomcat8.0</finalName>
          <sourceDirectory>java</sourceDirectory>
          <testSourceDirectory>test</testSourceDirectory>
          <resources>
              <resource>
                  <directory>java</directory>
              </resource>
          </resources>
          <testResources>
              <testResource>
                  <directory>test</directory>
              </testResource>
          </testResources>
          <plugins>
              <plugin>
                  <groupId>org.apache.maven.plugins</groupId>
                  <artifactId>maven-compiler-plugin</artifactId>
                  <version>2.3</version>
                  <configuration>
                      <encoding>UTF-8</encoding>
                      <source>1.8</source>
                      <target>1.8</target>
                  </configuration>
              </plugin>
          </plugins>
      </build>
  
      <dependencies>
          <dependency>
              <groupId>junit</groupId>
              <artifactId>junit</artifactId>
              <version>4.12</version>
              <scope>test</scope>
          </dependency>
          <dependency>
              <groupId>org.easymock</groupId>
              <artifactId>easymock</artifactId>
              <version>3.4</version>
          </dependency>
          <dependency>
              <groupId>ant</groupId>
              <artifactId>ant</artifactId>
              <version>1.7.0</version>
          </dependency>
          <dependency>
              <groupId>wsdl4j</groupId>
              <artifactId>wsdl4j</artifactId>
              <version>1.6.2</version>
          </dependency>
          <dependency>
              <groupId>javax.xml</groupId>
              <artifactId>jaxrpc</artifactId>
              <version>1.1</version>
          </dependency>
          <dependency>
              <groupId>org.eclipse.jdt.core.compiler</groupId>
              <artifactId>ecj</artifactId>
              <version>4.5.1</version>
          </dependency>
  
      </dependencies>
  </project>
  ```

-  在`apache-tomcat-8.5.57-src` 同级目录新建`catalina-home`并保证目录下面文件如下 
  - bin  从`apache-tomcat-8.5.57-src`拷入
  - conf  从`apache-tomcat-8.5.57-src`拷入
  - lib  自建空文件夹
  - logs  自建空文件夹
  - temp  自建空文件夹
  - webapps  从`apache-tomcat-8.5.57-src`拷入
  - work  自建空文件夹
- 在`idea`中打开，`open  apache-tomcat-8.5.57-src`
- 配置启动(`Edit Configurations`)，选择一个`Application`启动程序
  -  MainClass: org.apache.catalina.startup.Bootstrap 
  -  Vm Options: -Dcatalina.home= 你存放tomcat的目录\apache-tomcat-8.5.57-src\catalina-home 
- 启动 Tomcat

### 问题解决

- 启动报错 TestCookieFilter 报错找不到这个类CookieFilter

  - 解决方法：删除TestCookieFilter类

-  启动后，访问localhost:8080 报错 org.apache.jasper.JasperException: java.lang.NullPointerException 

  - 解决方案：
    org.apache.catalina.startup.Bootstrap 静态代码块中添加

  ```java
  {
  	JasperInitializer initializer = new JasperInitializer();
  }
  ```

- 启动乱码

  - VM Options 添加参数:`-Duser.language=en` `-Duser.region=US`


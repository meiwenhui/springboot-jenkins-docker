# springboot-jenkins-docker

pom.xml 文件添加插件配置
```xml
<plugin>
    <groupId>com.spotify</groupId>
    <artifactId>docker-maven-plugin</artifactId>
    <version>1.0.0</version>
    <configuration>
        <imageName>${docker.image.prefix}/${project.artifactId}</imageName>
        <dockerDirectory>src/main/docker</dockerDirectory>
        <resources>
            <resource>
                <targetPath>/</targetPath>
                <directory>${project.build.directory}</directory>
                <include>${project.build.finalName}.jar</include>
            </resource>
        </resources>
    </configuration>
</plugin>
```
在`src/main/docker`目录下新建Dockerfile文件，内容如下
```
FROM openjdk:8-jdk-alpine
VOLUME /tmp
ADD springboot-jenkins-docker-0.0.1-SNAPSHOT app.jar
ENTRYPOINT ["java","-Djava.security.egd=file:/dev/./urandom","-jar","/app.jar"]
```


在jenkins构建步骤中添加2个步骤

 1. 顶级maven，选择maven版本（上面的全局配置中配的maven），添加maven打包命令
   `clean install -Dmaven.test.skip=true`
 2. 执行shell 添加shell添加执行shell步骤
    ```
    # 先删除之前的容器
    echo "remobe old container"
    docker ps -a | grep springboot-jenkins-docker | awk '{print $1}'| xargs docker rm -f
    # 删除之前的镜像
    echo "romove old image"
    docker rmi springboot-jenkins-docker
    # 构建镜像
    mvn docker:build
    # 打印当前镜像
    echo "current docker images"
    docker images | grep springboot-jenkins-docker
    # 启动容器
    echo "start container"
    docker run -p 9001:9000 -d springboot-jenkins-docker
    # 打印当前容器
    echo "current container"
    docker ps -a | grep springboot-jenkins-docker
    echo "star service success!"
    ```
    
    
    
[使用Jenkins+docker 部署springboot项目](https://my.oschina.net/yimingkeji/blog/2878371)  
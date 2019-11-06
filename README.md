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

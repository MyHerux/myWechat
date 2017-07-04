# Docker化-构建Springboot的镜像
## 准备SpringBoot项目
- 略

## Dockerfile常用命令
- FROM — 指定基础镜像
```
FROM <image>
FROM <image>:<tag>
FROM <image>@<digest>
```
- MAINTAINER — 指定作者
```
MAINTAINER <author>
```
- RUN — 运行指定的命令
```
RUN cmd arg1 arg2 --- 以/bin/sh -c cmd 方式运行命令(shell)
RUN ["cmd", "arg1", "arg2"] --- 直接运行命令(exec)
```
- CMD — 容器启动时要运行的命令，可显式指定命令覆盖，只能使用一次
```
CMD ["cmd", "arg1", "arg2"] (exec)
CMD cmd arg1 arg2 (shell)
CMD ["arg1", "arg2"] --- 做为ENTRYPOINT的参数运行，需配合ENTRYPOINT使用
```
- ENTRYPOINT —启动时的默认命令，如设置，则CMD为其参数
```
ENTRYPOINT ["cmd", "arg1", "arg2"] (exec)
ENTRYPOINT cmd arg1 arg2 (shell)
```
- VOLUME — 向容器添加卷
```
VOLUME ["dir1", "dir2",...]
VOLUME dir1 dir2 ...
```
- ADD — 复制文件到镜像中
```
ADD &ltsrc> ... <dest>
ADD ["<src>", ... "<dest>"]
```
- COPY — 复制文件到镜像中
```
COPY <src> ... <dest>
COPY ["<src>", ... "<dest>"]
```
- USER — 指定用户
```
USER username:group
USER uid:gid
```
- WORKDIR — 指定工作目录
```
WOKRDIR dir
```
- EXPOSE — 暴露接口给外部
```
EXPOSE <port> [<port>...]
```
- LABEL — 为镜像指定标签
```
LABEL <key>=<value> <key>=<value> <key>=<value> ...
```
- ENV — 设置环境变量
```
ENV <key> <value>
ENV <key>=<value>... --- 可以一次指定多个变量
```
- ONBUILD — 添加触发器
```
ONBUILD [instruction]
```   
## SpringBoot的Dockerfile
- Dockerfile
```
  #指定springboot需要的基础镜像
  FROM maven:3.3.3
  #复制pom文件到maven
  ADD pom.xml /tmp/build/
  #运行指定命令
  RUN cd /tmp/build && mvn -q dependency:resolve
  #复制项目文件
  ADD src /tmp/build/src
          #构建应用
  RUN cd /tmp/build && mvn -q -DskipTests=true package \
          #拷贝编译结果到指定目录
          && mv target/*.jar /app.jar \
          #清理编译痕迹
          && cd / && rm -rf /tmp/build

  VOLUME /tmp
  EXPOSE 8080
  ENTRYPOINT ["java","-Djava.security.egd=file:/dev/./urandom","-jar","/app.jar"]
```
## 构建Docker镜像
- 进入应用根目录，构建Docker镜像
```
docker build -t [项目名] .
```
- 从镜像启动容器
```
docker run -d -p 8080:8080
```
- 访问
```
curl http://127.0.0.1:8080
```

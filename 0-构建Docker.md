## 在 CentOS 7 上安装 Docker
- 使用 yum 从软件仓库安装 Docker：
```
yum install docker
```
- 首先启动 Docker 的守护进程：
```
service docker start
```
- 如果想要 Docker 在系统启动时运行，执行：
```
chkconfig docker on
```

## Docker仓库
- 配置 Docker 加速器
使用daocloud的加速器加速下载
```
curl -sSL https://get.daocloud.io/daotools/set_mirror.sh | sh -s http://b0703481.m.daocloud.io
```
- 拉取镜像
```
docker pull ubuntu
```

## 运行一个容器
使用 docker create 命令创建容器，或者使用 docker run 命令运行一个新容器
- 用刚拉取的镜像启动一个新容器
```
docker run -it ubuntu:latest sh -c '/bin/bash'
```
这时候我们成功创建了一个 Ubuntu 的容器，并将当前终端连接为这个 Ubuntu 的 bash shell。这时候就可以愉快地使用 Ubuntu 的相关命令了。
参数 -i 表示这是一个交互容器，会把当前标准输入重定向到容器的标准输入中，而不是终止程序运行，-t 指为这个容器分配一个终端。
好了，按 `Ctrl+D` 可以退出这个容器了。

- 查看当前运行的容器
```
docker ps -a
```
每个容器都有一个唯一的 ID 标识，通过 ID 可以对这个容器进行管理和操作。在创建容器时，我们可以通过 --name 参数指定一个容器名称，如果没有指定系统将会分配一个.
- 重新启动容器
当我们按 `Ctrl+D` 退出容器时，命令执行完了，所以容器也就退出了。要重新启动这个容器，可以使用 `docker start` 命令.
```
docker start -i [容器name]
```

## 删除一个容器
- 删除容器
```
docker rm /[容器name]
```

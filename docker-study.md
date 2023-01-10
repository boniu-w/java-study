# 1. 命令

1. 删除容器

   1. 停止容器: docker stop 容器id

      停止所有容器: docker stop $(docker ps -a -q)

   2. 删除容器: docker rm 容器id

2. 删除镜像(image) 

   docker image rm 镜像id





 

| command                                         | description            | example |
| ----------------------------------------------- | ---------------------- | ------- |
| docker stop  容器id                             | 停止某个容器           |         |
| docker stop  $(docker ps -a -q)                 | 停止所有容器           |         |
| docker rm 容器id                                | 删除某个容器           |         |
| docker rm -f 容器id                             | 强制删除某个容器       |         |
| docker image rm 镜像id<br />docker  rmi  镜像id | 删除某个镜像           |         |
| docker  ps  -a                                  | 查询所有已经创建的容器 |         |
| docker   ps                                     | 查询所有正在运行的容器 |         |
| docker  exec  -it  容器id  /bin/bash            | 进入容器的控制台       |         |
| docker  login                                   | 登录 dockerhub         |         |
| docker  logout                                  | 退出 dockerhub         |         |
|                                                 |                        |         |
|                                                 |                        |         |
|                                                 |                        |         |
|                                                 |                        |         |
|                                                 |                        |         |
|                                                 |                        |         |
|                                                 |                        |         |
|                                                 |                        |         |
|                                                 |                        |         |
|                                                 |                        |         |
|                                                 |                        |         |
|                                                 |                        |         |
|                                                 |                        |         |
|                                                 |                        |         |




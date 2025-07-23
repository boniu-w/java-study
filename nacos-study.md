1. ubuntu 启动命令

   cd 到 bin目录下, 注意最后有一个 "\."

   ```sh
   bash  startup.sh  -m  standalone  .
   ```

2. 集群

   ```
   cd  /usr/nacos/bin
   bash  startup.sh -p 3333 -m standalone .
   ```

   nacos  ->  conf  ->   cluster.conf

   ```
   ${host}:${port}
   127.0.0.1:3333
   127.0.0.1:3334
   127.0.0.1:3335
   ```

   

3. 关闭

   ```
   cd  /usr/nacos/bin
   bash  shutdown.sh
   ```

4. 与 nginx 配合 配置集群 , 编辑 startup.sh 这个启动脚本

   nacos  ->  bin  ->  startup.sh

   

   ![](.\img\nacos-nginx-集群.png)

   ![](.\img\nginx-dserverport.png)

5. 




# 2. 配置问题

1. 优先级问题

 ```
1. extension 的优先级 高于 shared , 也就是 extension 加载晚于 shared 
2. 若同为 shared 或 extension 配置, 则位于下面的优先级高, 也就是, 下面的晚加载, 会覆盖上面的配置
3. ${spring.application.name} - ${spring.profies.active} 优先级最高, 也就是最后加载, 会覆盖前面的配置

 ```

​	

# windows

## 1. 启动

```shell
startup.cmd  -m  standalone  .
```


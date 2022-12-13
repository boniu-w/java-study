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

   
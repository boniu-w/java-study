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





# 3. 读取配置的顺序

在 Nacos 与 Spring Cloud 环境下，配置文件的读取（加载）顺序遵循“从通用到具体”的原则。对于最新版本的 Nacos（2.x 系列），加载顺序与优先级紧密相关，遵循**后加载的配置覆盖先加载的配置**（即后加载的优先级更高）。

以下是完整的读取顺序及优先级说明：

### 1. 配置文件加载顺序（由先到后）

在项目启动时，Spring Cloud Nacos 会按照以下顺序依次读取配置：

1. **共享配置 (Shared Configs)**：
   - 通过 `spring.cloud.nacos.config.shared-configs` 引入。
   - 通常用于存放多个微服务共用的基础配置（如数据库地址、日志级别）。
2. **扩展配置 (Extension Configs)**：
   - 通过 `spring.cloud.nacos.config.extension-configs` 引入。
   - 用于特定业务模块的通用配置。
3. **应用自身配置 (Data ID)**：
   - 按照以下细分顺序加载：
     - `${spring.application.name}`（不带后缀，优先级最低）
     - `${spring.application.name}.yaml`（或 `.properties`，带后缀）
     - `${spring.application.name}-${spring.profiles.active}.yaml`（带环境后缀，**优先级最高**）

------

### 2. 优先级汇总表

**规则：** 优先级数值越大（或加载越晚），其配置项会覆盖之前的同名配置。

| **优先级**   | **配置来源**           | **常见文件名示例**       | **说明**                                     |
| ------------ | ---------------------- | ------------------------ | -------------------------------------------- |
| **1 (最高)** | **带环境的远程配置**   | `order-service-dev.yaml` | 针对特定环境的最具体配置                     |
| **2**        | **不带环境的远程配置** | `order-service.yaml`     | 应用默认的远程配置                           |
| **3**        | **扩展配置**           | `common-redis.yaml`      | `extension-configs` 数组中索引越大优先级越高 |
| **4**        | **共享配置**           | `common-base.yaml`       | `shared-configs` 数组中索引越大优先级越高    |
| **5 (最低)** | **本地配置文件**       | `application.yaml`       | 本地配置通常会被 Nacos 远程配置完全覆盖      |

------

### 3. 特殊规则与注意事项

- 本地优先设置：

  如果你希望本地配置（如 application.yaml 中的端口号）能覆盖远程 Nacos 配置，需要在 Nacos 远程配置或本地 bootstrap.yaml 中设置：

  YAML

  

  ```
  spring:
    cloud:
      config:
        override-none: true  # 远程配置优先级降至最低
  ```

- 文件后缀匹配：

  Nacos 默认根据 spring.cloud.nacos.config.file-extension 来匹配 Data ID（默认为 properties）。如果远程是 .yaml，必须显式指定后缀。

- 数组内的顺序：

  在 extension-configs 或 shared-configs 的列表中，下标越大，优先级越高。例如：

  YAML

  

  ```
  extension-configs:
    - data-id: config-A.yaml # 优先级较低
    - data-id: config-B.yaml # 优先级较高，可覆盖 A
  ```



```yaml
# bootstrap.yml
spring:
  application:
    name: order-service
  profiles:
    active: dev
  cloud:
    nacos:
      config:
        server-addr: 127.0.0.1:8848
        file-extension: yaml
        group: DEFAULT_GROUP
        # 1. 共享配置 (优先级最低)
        shared-configs:
          - data-id: common-logging.yaml
            group: COMMON_GROUP
            refresh: true
        # 2. 扩展配置 (优先级中等)
        extension-configs:
          - data-id: common-redis.yaml
            group: COMMON_GROUP
            refresh: true
          - data-id: common-mybatis.yaml
            group: COMMON_GROUP
            refresh: true
```

### 💡 核心要点总结：

* **加载顺序**：`shared-configs` -> `extension-configs` -> `${application.name}` -> `${application.name}-${profile}`。
* **冲突处理**：如果同一个配置项（如 `server.port`）在所有文件中都出现了，系统会采用**最后加载**的那一个（即带 `-dev` 后缀的文件）。
* **Group 隔离**：注意 `shared-configs` 经常存放在不同的 Group（如 `COMMON_GROUP`）以便管理。


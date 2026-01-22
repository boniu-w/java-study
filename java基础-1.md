# 1. java 命名规范



| 类型   | 约束                                                         | 例                                             |
| ------ | ------------------------------------------------------------ | ---------------------------------------------- |
| 项目名 | 全部小写，多个单词用中划线分隔‘-’                            | spring-cloud                                   |
| 包名   | 全部小写                                                     | com.alibaba.fastjson                           |
| 类名   | 单词首字母大写                                               | Feature, ParserConfig,DefaultFieldDeserializer |
| 变量名 | 首字母小写，多个单词组成时，除首个单词，其他单词首字母都要大写 | password, userName                             |
| 常量名 | 全部大写，多个单词，用'_'分隔                                | CACHE_EXPIRED_TIME                             |
| 方法   | 同变量                                                       | read(), readObject(), getById()                |



1. 包命名

   | 前缀名          | 例                             | 含义                                                         |
   | --------------- | ------------------------------ | ------------------------------------------------------------ |
   | indi（或onem ） | indi.发起者名.项目名.模块名.…… | 个体项目，指个人发起，但非自己独自完成的项目，可公开或私有项目，copyright主要属于发起者。 |
   | pers            | pers.个人名.项目名.模块名.……   | 个人项目，指个人发起，独自完成，可分享的项目，copyright主要属于个人 |
   | priv            | priv.个人名.项目名.模块名.……   | 私有项目，指个人发起，独自完成，非公开的私人使用的项目，copyright属于个人。 |
   | team            | team.团队名.项目名.模块名.……   | 团队项目，指由团队发起，并由该团队开发的项目，copyright属于该团队所有 |
   | 顶级域名        | com.公司名.项目名.模块名.……    | 公司项目，copyright由项目发起的公司所有                      |

2. 类命名

   | 属性           | 约束                                      | 例                                                           |
   | -------------- | ----------------------------------------- | ------------------------------------------------------------ |
   | 抽象类         | Abstract 或者 Base 开头                   | BaseUserService                                              |
   | 枚举类         | Enum 作为后缀                             | GenderEnum                                                   |
   | 工具类         | Util作为后缀                              | StringUtil                                                   |
   | 异常类         | Exception结尾                             | RuntimeException                                             |
   | 接口实现类     | 接口名+ Impl                              | UserServiceImpl                                              |
   | 领域模型相关   | /DO/DTO/VO/DAO                            | 正例：UserDAO 反例： UserDo， UserDao                        |
   | 设计模式相关类 | Builder，Factory等                        | 当使用到设计模式时，需要使用对应的设计模式作为后缀，如ThreadFactory |
   | 处理特定功能的 | Handler，Predicate, Validator             | 表示处理器，校验器，断言，这些类工厂还有配套的方法名如handle，predicate，validate |
   | 测试类         | Test结尾                                  | UserServiceTest， 表示用来测试UserService类的                |
   | MVC分层        | Controller，Service，ServiceImpl，DAO后缀 | UserManageController，UserManageDAO                          |





# 2. get 和 post

```
curl --request GET \
  --url http://localhost:9967/dentImportedFrom/list \
  --header 'content-type: multipart/form-data' \
  --form dentAssessmentHistoryId=12 \
  --form kp=122

  和 

  curl --request GET \
  --url 'http://localhost:9967/dentImportedFrom/list?dentAssessmentHistoryId=12&kp=33'

  有什么区别
```



这两个 `curl` 命令虽然目标 URL 路径相同，但它们传递参数的方式完全不同。这会导致服务端接收和处理数据的方式存在本质区别。

以下是核心区别的详细对比：

### 1. 参数传递位置不同 (核心区别)

- **第二个命令 (推荐用于 GET):**
  - **方式:** Query String (查询字符串)。
  - **位置:** 参数直接附加在 URL 后面（`?` 之后）。
  - **表现:** 数据是 URL 的一部分。这是 **HTTP GET 请求的标准传参方式**。
  - **服务端获取:** 后端通常通过 "Query Parameters" 获取 (例如 Java Spring 的 `@RequestParam`, Node Express 的 `req.query`)。
- **第一个命令 (通常是错误的用法):**
  - **方式:** Request Body (请求体)。
  - **位置:** 参数被封装在 HTTP 请求的 Body 中，格式为 `multipart/form-data`。
  - **表现:** 即使你强制指定了 `GET` 方法，`--form` 也会让 curl 构建一个带有 Body 的请求。
  - **服务端获取:** 后端通常预期 GET 请求没有 Body，因此**极大概率会忽略这些参数**，导致服务端认为你没有传任何参数。

### 2. HTTP 语义与标准兼容性

| **特性**         | **命令 2 (URL Query)**                               | **命令 1 (Multipart Form)**                                  |
| ---------------- | ---------------------------------------------------- | ------------------------------------------------------------ |
| **HTTP 方法**    | `GET`                                                | `GET` (强制指定)                                             |
| **参数位置**     | URL                                                  | **Body**                                                     |
| **Content-Type** | 无 (通常不适用)                                      | `multipart/form-data`                                        |
| **标准合规性**   | **完全合规**。GET 请求用于获取资源，参数应标识资源。 | **非常规/不合规**。虽然 HTTP 协议未明确禁止 GET 带 Body，但 RFC 建议 GET 请求不应包含 Body。 |
| **结果**         | 服务端能正常读取参数。                               | **服务端通常读不到参数** (参数为 null)。                     |

### 3. 为什么第一个命令可能会失败？

大多数后端框架（如 Spring Boot, Django, Express.js）和中间件（如 Nginx, Varnish）在处理 `GET` 请求时，**默认只会解析 URL 上的参数，而完全忽略 Request Body**。

如果你运行第一个命令，服务端大概率会报 "Missing parameter" 错误，或者返回查询结果为空（因为它以为你没传 ID）。

### 总结

- **如果你想发起 GET 请求查询数据：** 请使用 **第二个命令**（URL 参数）。
- **如果你想发起 POST 请求提交数据：** 应该把第一个命令中的 `--request GET` 改为 `POST`（此时使用 `multipart/form-data` 才是合理的）。

------

建议的下一步

如果您是在测试接口，建议您确认该接口的定义。如果是查询接口（GET），请坚持使用 URL 传参；如果是提交表单（POST），请去掉 --request GET 让 curl 默认使用 POST。


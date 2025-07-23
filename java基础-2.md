# 1. Math 

## 1. exp() , expm1() 

在Java的Math类中，`exp()`和`expm1()`方法都是用来计算指数函数的，但它们之间存在细微的区别：

1. **exp()方法**: 这个方法计算的是e（自然对数的底，约等于2.71828）的指数次幂。即，`exp(x)`返回`e`的`x`次方。例如，`Math.exp(1)`返回`e`的值，大约是2.718281828459045。
2. **expm1()方法**: 这个方法计算的是`e^x - 1`，即自然对数底e的x次方减去1的值。这个函数在x接近0的时候特别有用，因为它提供了比直接计算`exp(x) - 1`更高的精度，避免了当结果接近0时因浮点数运算导致的精度损失。例如，`Math.expm1(0.001)`会给出一个非常接近0.001的结果，因为`e^0.001`大约是1.00050016670838, 减去1后得到的值非常接近原输入0.001，而直接计算`Math.exp(0.001) - 1`可能会因为浮点数运算的误差而损失精度。

总结来说，`exp()`直接计算自然指数函数的值，而`expm1()`提供了一个在x接近0时更为精确的`e^x - 1`的计算方法，提高了数值计算的准确性



## 2.  log() ,  log10() 

在Java的Math类中，`log()`和`log10()`方法分别代表两种不同的对数计算：

1. **log()方法**: 这个方法计算的是自然对数，即以e（欧拉数，约等于2.71828）为底数的对数。数学上表示为`ln(x)`。例如，`Math.log(10)`将返回大约2.302585092994046的值，因为`ln(10)`就是这个数值。
2. **log10()方法**: 此方法计算的是以10为底数的常用对数。换句话说，它计算的是一个数是10的几次幂。数学上表示为`log10(x)`。例如，`Math.log10(100)`将返回2，因为10的2次方等于100。

简单来说，`log()`适用于计算自然对数，而`log10()`则用于计算以10为底的对数。在实际应用中，选择哪个函数取决于你的具体数学运算或公式要求





# 2. mybatis 

## 1. 缓存

MyBatis 是一个持久层框架，提供了对数据库访问的简单而强大的工具。它的缓存机制旨在提高数据库访问的效率，减少不必要的数据库查询。MyBatis 提供了一级缓存和二级缓存两种缓存机制。

### 一级缓存

一级缓存是 SqlSession 级别的缓存，也就是说，同一个 SqlSession 在其生命周期内，相同的查询会被缓存，后续相同的查询将从缓存中获取结果，而不需要再次访问数据库。一级缓存的特点如下：

1. **默认开启**：一级缓存是默认开启的，不需要额外配置。

2. **缓存范围**：一级缓存的作用范围是 SqlSession。在同一个 SqlSession 对象中进行的查询会被缓存，而在不同的 SqlSession 中不会共享缓存。

3. 失效情况

   ：

   - 执行更新、插入、删除操作后，一级缓存会被清空。
   - 关闭 SqlSession 时，一级缓存也会被清空。
   - 手动清除缓存可以调用 `sqlSession.clearCache()` 方法。

### 二级缓存

二级缓存是 Mapper 级别的缓存，不同的 SqlSession 也可以共享。二级缓存需要手动开启和配置。其特点如下：

1. **需手动开启**：二级缓存默认是关闭的，需要在 MyBatis 配置文件中手动开启。

2. 配置方式

   ：

   - 在 MyBatis 全局配置文件（mybatis-config.xml）中开启二级缓存：

     ```
     mybatis:
       configuration:
         cache-enabled: true
     ```

   - 在对应的 Mapper XML 文件中配置缓存：

     ```
     xml
     复制代码
     <cache/>
     ```

3. **缓存范围**：二级缓存的作用范围是 Mapper。相同的 Mapper 对应的查询可以共享缓存。

4. 失效情况

   ：

   - 执行更新、插入、删除操作后，相关的缓存会被清空。
   - 配置缓存策略时，可以设置缓存的有效时间、大小等参数。
   - 可以通过配置来控制缓存的行为，比如 LRU（Least Recently Used）策略、FIFO（First In First Out）策略、缓存大小等。

### 配置示例

以下是一个简单的配置示例：

1. **mybatis-config.xml**：

   ```
   mybatis:
     configuration:
       cache-enabled: true
   ```

2. **UserMapper.xml**：

   ```xml
   <?xml version="1.0" encoding="UTF-8" ?>
   <!DOCTYPE mapper
       PUBLIC "-//mybatis.org//DTD Mapper 3.0//EN"
       "http://mybatis.org/dtd/mybatis-3-mapper.dtd">
   <mapper namespace="com.example.mapper.UserMapper">
       <cache/>
       
       <select id="selectUser" parameterType="int" resultType="com.example.model.User">
           SELECT * FROM user WHERE id = #{id}
       </select>
   </mapper>
   ```

通过上述配置，MyBatis 将会启用二级缓存，并将 `UserMapper` 的查询结果缓存起来，以提高查询效率。

### 总结

MyBatis 的缓存机制通过一级缓存和二级缓存来减少数据库查询次数，从而提高性能。一级缓存是默认开启的，作用范围是 SqlSession，二级缓存需要手动开启，作用范围是 Mapper。正确配置和使用缓存，可以显著提高应用程序的性能。

MyBatis 的一级缓存是基于 `SqlSession` 的缓存，它存储在内存中。在 MyBatis 的设计中，一级缓存并没有明确的大小限制，因为它的生命周期是与 `SqlSession` 绑定的，一旦 `SqlSession` 关闭或者被清空，缓存的数据就会被释放。

### 一级缓存的特点

1. **默认开启**：一级缓存是默认开启的，不需要额外配置。
2. **缓存范围**：缓存的数据只在当前 `SqlSession` 内有效，不同的 `SqlSession` 之间不共享缓存。
3. **缓存内容**：一级缓存会缓存查询语句的结果，以键值对的形式存储，键是查询的 SQL 语句和参数，值是查询结果。
4. **生命周期**：一级缓存的生命周期与 `SqlSession` 一致，`SqlSession` 关闭或调用 `clearCache()` 方法时，缓存会被清空。

### 一级缓存的失效情况

以下几种情况会导致一级缓存失效：

1. **执行更新、插入、删除操作**：在 `SqlSession` 中执行这些操作后，一级缓存会被清空。
2. **SqlSession 关闭**：关闭 `SqlSession` 时，一级缓存会被清空。
3. **手动清除缓存**：调用 `sqlSession.clearCache()` 方法时，一级缓存会被清空。
4. **不同的查询条件**：相同的 SQL 查询语句但不同的查询参数会被视为不同的缓存条目，不会命中缓存。

### 一级缓存的大小控制

虽然 MyBatis 没有为一级缓存设置明确的大小限制，但缓存的数据大小实际受以下因素影响：

1. **SqlSession 的生命周期**：`SqlSession` 的生命周期决定了缓存的数据量。在长生命周期的 `SqlSession` 中，缓存的数据量可能会增大。
2. **内存限制**：因为一级缓存存储在内存中，受 JVM 可用内存的限制。在内存不足的情况下，可能会导致内存溢出。
3. **查询的频率和数据量**：频繁的查询和大量的数据会增加缓存的占用空间。

### 优化建议

1. **控制 SqlSession 的生命周期**：避免长生命周期的 `SqlSession`，确保在适当的时候关闭 `SqlSession` 以释放缓存。
2. **合理使用缓存**：对于频繁更新的数据，合理使用一级缓存，避免缓存失效带来的性能开销。
3. **监控内存使用**：在高并发或大数据量的应用中，监控 JVM 内存使用情况，确保内存资源充足。

### 总结

MyBatis 的一级缓存是基于 `SqlSession` 的缓存，没有明确的大小限制，缓存的数据量受 `SqlSession` 生命周期和 JVM 可用内存的影响。在实际应用中，需要合理控制 `SqlSession` 的生命周期，并监控内存使用情况，以确保系统的稳定性和性能。



### 1. 一级缓存的生命周期如此之短, 那他存在的意义是什么呢

一级缓存在 MyBatis 中的存在是为了提升查询性能和减少数据库访问次数。虽然一级缓存的生命周期较短，但它在某些情况下仍然能够带来显著的性能提升和优势：

### 意义和优势

1. **减少数据库访问**：
   - 在同一个 `SqlSession` 内，如果多次查询相同的数据，MyBatis 会将第一次查询的结果放入一级缓存中，后续的查询可以直接从缓存中获取，避免了多次数据库的查询操作，从而提升了查询效率。
2. **提升性能**：
   - 相对于每次查询都访问数据库，使用一级缓存可以大幅减少数据库的访问次数，特别是在复杂的查询场景下，可以显著提升系统的响应速度和吞吐量。
3. **维护数据一致性**：
   - 一级缓存的生命周期与 `SqlSession` 相同，当事务提交或回滚时，缓存也会被正确地清理，保证了缓存数据与数据库数据的一致性。
4. **简化代码逻辑**：
   - 开发者可以借助一级缓存，不必手动实现重复查询的缓存逻辑，简化了代码的复杂度和维护成本。

### 使用场景和限制

虽然一级缓存有上述优势，但在以下情况下可能会遇到其限制：

- **多线程环境**：`SqlSession` 不是线程安全的，因此一级缓存只能在单个线程内有效，多个线程无法共享缓存数据。
- **大量数据或复杂查询**：对于大量数据或者复杂查询，一级缓存的效果可能有限，甚至会因为缓存占用内存过多而造成性能问题。
- **事务边界**：跨事务的查询无法共享一级缓存，每个事务拥有自己独立的一级缓存。

### 总结

一级缓存在 MyBatis 中的存在，主要是为了优化短期内的数据库访问性能，减少重复查询带来的性能损耗。尽管它的生命周期短暂，但在正确使用的情况下，能够有效地提升系统的性能和响应速度，同时确保数据的一致性和可靠性。



### 2. 在同一个 SqlSession 内，如果多次查询相同的数据 是什么意思, 举个例子

在同一个 `SqlSession` 内，如果多次查询相同的数据指的是在相同的 `SqlSession` 实例中执行多次相同的查询操作，MyBatis 会利用一级缓存来避免重复向数据库发出相同的查询请求，而是直接从缓存中获取结果。这种机制可以有效地减少数据库的访问次数，提升查询性能。

### 示例说明

假设有一个简单的 `User` 实体类和对应的 `UserMapper`，以及一个包含多个用户记录的数据库表。

#### User 实体类

```
java
复制代码
public class User {
    private Long id;
    private String username;
    private String email;
    // 省略 getter 和 setter 方法
}
```

#### UserMapper 接口

```
java
复制代码
public interface UserMapper {
    User selectUserById(Long id);
}
```

#### 示例场景

假设我们有一个服务类 `UserService`，其中使用了 `UserMapper` 来查询用户信息：

```java
@Service
public class UserService {

    @Autowired
    private SqlSessionFactory sqlSessionFactory;

    public void getUserByIdTwice() {
        try (SqlSession sqlSession = sqlSessionFactory.openSession()) {
            UserMapper userMapper = sqlSession.getMapper(UserMapper.class);

            // 第一次查询，从数据库获取数据
            User user1 = userMapper.selectUserById(1L);
            System.out.println("First Query: " + user1);

            // 第二次查询，相同的查询操作，会使用一级缓存
            User user2 = userMapper.selectUserById(1L);
            System.out.println("Second Query: " + user2);

            // 第一次和第二次查询的对象是相同的，因此结果应该相同
            System.out.println("Are they the same object? " + (user1 == user2)); // 输出 true
        }
    }
}
```

#### 解释

在上述示例中：

1. **SqlSession 的使用**：通过 `SqlSessionFactory` 创建一个新的 `SqlSession` 实例。
2. **查询操作**：使用 `UserMapper` 执行两次相同的查询操作 `selectUserById(1L)`。
3. **一级缓存的作用**：
   - 第一次查询时，MyBatis 会将查询结果存储在 `SqlSession` 的一级缓存中。
   - 第二次查询时，MyBatis 首先检查一级缓存中是否已经存在相同查询的结果（根据 `selectUserById(1L)` 的参数），如果存在，则直接返回缓存中的对象，而不会再次向数据库发出查询请求。
4. **对象的比较**：
   - `user1` 和 `user2` 是相同的 Java 对象引用，即它们指向的是同一个对象。
   - 因此，输出的比较结果为 `true`，表明第二次查询返回的是第一次查询的结果，且使用了一级缓存。

### 注意事项

- 一级缓存的作用域是在同一个 `SqlSession` 内部。如果在同一个 `SqlSession` 实例中执行相同的查询操作，MyBatis 将会使用一级缓存来优化重复查询。
- 一级缓存在 `SqlSession` 关闭之后会失效，它的生命周期与 `SqlSession` 一致。

通过这种机制，MyBatis 能够有效地减少重复的数据库访问，提升查询性能，特别是在需要频繁执行相同查询的情况下



### 示例2:

```java
import org.apache.ibatis.session.SqlSession;
import com.example.mapper.UserMapper;

public class FirstLevelCacheDemo {
    public static void main(String[] args) {
        // 假设 `sqlSessionFactory` 是已经初始化好的 SqlSessionFactory 对象
        try (SqlSession sqlSession = sqlSessionFactory.openSession()) {
            UserMapper mapper = sqlSession.getMapper(UserMapper.class);
            
            // 第一次查询用户信息
            User user1 = mapper.getUserById(1);
            System.out.println("第一次查询: " + user1);

            // 第二次查询用户信息，这里会命中一级缓存
            User user2 = mapper.getUserById(1);
            System.out.println("第二次查询: " + user2);

            // 修改用户信息
            user1.setName("Updated Name");
            mapper.updateUser(user1);
            sqlSession.commit();

            // 再次查询，这次应该从缓存中获取旧的数据
            User user3 = mapper.getUserById(1);
            System.out.println("第三次查询: " + user3); // 注意这里会输出修改前的数据

            // 清除缓存
            sqlSession.clearCache();

            // 清除缓存后再次查询
            User user4 = mapper.getUserById(1);
            System.out.println("第四次查询: " + user4); // 这次会查询到最新的数据
        }
    }
}

在这个例子中：

第一次查询 getUserById(1) 会从数据库中获取数据并将其放入缓存。
第二次查询 getUserById(1) 会直接从缓存中读取数据，而不是查询数据库。
更新用户信息后提交事务，此时缓存中的数据仍然是旧的。
第三次查询由于缓存仍然有效，会返回旧的数据。
调用 sqlSession.clearCache() 清除缓存后，第四次查询会重新查询数据库，获取最新的数据。

缓存失效的情况
提交或回滚事务后，SqlSession 的缓存会被清空。
执行任何增删改（DML）操作后，受影响的数据会被从缓存中移除。
关闭 SqlSession 时，缓存会被清空
```





# 3. openfeign



## 1. @FeignClient 注解解释

在 Spring Cloud 中，`@FeignClient` 注解用于声明一个 Feign 客户端，用来调用远程的 HTTP 服务。它是一个功能强大的工具，可以帮助你更方便地进行微服务间的通信。下面是对注解各个属性的详细解释：

```java
@FeignClient(
    contextId = "historyAssessmentSpIntInspectDt",
    name = "ceet-ufps-pl-pia",
    path = "/svc/org",
    fallbackFactory = HistoryAssessmentSpIntInspectDtFallbackFactory.class
)
```

### 1. `contextId`

- **含义**：`contextId` 用于标识 Feign 客户端的唯一 ID，通常用于区分不同的 Feign 客户端。
- **用途**：它可以帮助你在应用程序中使用相同的 `name` 来定义多个 Feign 客户端时，避免冲突和混淆。每个 Feign 客户端可以通过 `contextId` 唯一标识。

### 2. `name`

- **含义**：`name` 指定了 Feign 客户端的名称，通常与被调用服务的名称一致。
- **用途**：在服务注册中心（如 Eureka）中，Feign 客户端会使用这个名称来查找并调用远程服务。如果你使用 Spring Cloud Ribbon 进行客户端负载均衡，`name` 也会用作负载均衡的标识。

### 3. `path`

- **含义**：`path` 指定了所有请求的基础 URL 路径。
- **用途**：这个路径会被添加到所有由 Feign 客户端发出的请求的 URL 之前。例如，如果 `path` 是 `/svc/org`，则所有请求都会以这个路径开头。

### 4. `fallbackFactory`

- **含义**：`fallbackFactory` 指定了一个回退工厂类，当 Feign 调用失败时，会使用这个工厂类来创建一个回退实例。
- **用途**：回退工厂可以提供更灵活的错误处理机制，允许你根据具体的错误原因提供不同的回退实现。`HistoryAssessmentSpIntInspectDtFallbackFactory` 是一个实现 `FallbackFactory` 接口的类，用于生成回退实例。

### 综合示例

假设有一个远程服务 `ceet-ufps-pl-pia` 提供了一组与组织相关的 API，你希望通过 Feign 客户端来访问这些 API。以下是一个完整的示例：

#### Feign 客户端接口

```java
@FeignClient(
    contextId = "historyAssessmentSpIntInspectDt",
    name = "ceet-ufps-pl-pia",
    path = "/svc/org",
    fallbackFactory = HistoryAssessmentSpIntInspectDtFallbackFactory.class
)
public interface HistoryAssessmentSpIntInspectDtClient {

    @GetMapping("/someEndpoint")
    SomeResponse getSomeData(@RequestParam("param") String param);
}
```

#### 回退工厂类

```java
import feign.hystrix.FallbackFactory;
import org.springframework.stereotype.Component;

@Component
public class HistoryAssessmentSpIntInspectDtFallbackFactory implements FallbackFactory<HistoryAssessmentSpIntInspectDtClient> {
    @Override
    public HistoryAssessmentSpIntInspectDtClient create(Throwable cause) {
        return new HistoryAssessmentSpIntInspectDtClient() {
            @Override
            public SomeResponse getSomeData(String param) {
                // 提供回退逻辑，比如返回一个默认的响应或抛出自定义异常
                return new SomeResponse("Default data due to fallback");
            }
        };
    }
}
```

### 总结

`@FeignClient` 注解配置了一个 Feign 客户端，用于访问远程服务 `ceet-ufps-pl-pia`。这个客户端将所有请求的基础路径设置为 `/svc/org`，并在调用失败时通过 `HistoryAssessmentSpIntInspectDtFallbackFactory` 提供回退逻辑。`contextId` 用于唯一标识这个客户端，确保在应用中没有冲突。





# 4. maven 构建插件 加与不加 的区别

```xml
<plugins> 
    <plugin>
        <groupId>org.springframework.boot</groupId>
        <artifactId>spring-boot-maven-plugin</artifactId>
        <version>3.1.3</version>
```



在Maven构建的Spring Boot项目中，加入`spring-boot-maven-plugin`与不加入，主要区别在于以下几个方面：

### 加入`spring-boot-maven-plugin`时：

1. **可执行jar**：最显著的区别是它可以将你的项目打包成一个可直接执行的jar文件，包含了所有依赖、类和资源。这意味着你可以直接通过`java -jar your-app.jar`命令来运行应用，无需手动处理类路径或依赖。
2. **重打包功能**：该插件提供了一个`repackage`目标，它会在普通jar包的基础上，重新打包生成一个包含`BOOT-INF`目录的可执行jar。这个过程会把应用的依赖、配置以及其他资源一起打包进去，非常适合快速部署和微服务架构。
3. **自动配置Manifest文件**：插件会自动配置MANIFEST.MF文件，设置Main-Class为Spring Boot的启动类加载器，确保应用能够正确启动。
4. **优化**：它还可以执行一些优化操作，比如去除未使用的类和资源，减小最终生成的jar文件大小。
5. **运行时属性支持**：允许你在打包时嵌入或外部化运行时配置，如指定JVM参数、活跃配置文件等。

### 不加入`spring-boot-maven-plugin`时：

1. **标准jar**：项目会被打包成一个标准的jar文件，不包含任何依赖。这意味着这个jar不能直接通过`java -jar`命令执行，而需要手动设置类路径或在应用服务器上部署时管理依赖。
2. **依赖管理**：你需要手动或通过其他方式管理项目的依赖关系，确保在运行或部署应用时，所有必要的库都可用。
3. **适用场景**：如果你的项目不是作为独立应用运行，而是作为库被其他项目依赖，或者打算部署到传统Servlet容器（如Tomcat）中作为war包，那么可能不需要这个插件。

综上所述，是否加入`spring-boot-maven-plugin`取决于你的项目需求和部署策略。对于希望快速构建独立运行微服务的应用来说，加入该插件是非常便利的。



# 5. java14 的 record

## 1. java record 的用法, 它解决是什么难题

Java `record` 是在 Java 14 中引入的一种简洁的语法，用于定义不可变的数据持有类。它主要解决了样板代码过多的问题，使得编写和维护简单的数据类更加方便和高效。下面具体讲解 `record` 的用法及其解决的问题。

### `record` 的基本用法

定义一个 `record` 非常简单，只需要声明它的名称和包含的字段：

```java
public record Point(int x, int y) {}
```

上面的 `Point` 记录类会自动生成以下内容：

- 私有的、不可变的字段 `x` 和 `y`。
- 公共的构造函数 `Point(int x, int y)`。
- `x()` 和 `y()` 的 getter 方法。
- `toString()`、`equals()` 和 `hashCode()` 方法。

### 使用示例

```java
public class Main {
    public static void main(String[] args) {
        Point point = new Point(5, 10);

        System.out.println(point.x()); // 输出: 5
        System.out.println(point.y()); // 输出: 10
        System.out.println(point);     // 输出: Point[x=5, y=10]
    }
}

public record Point(int x, int y) {}
```

### 详细说明

1. **字段声明和构造函数**: `record` 自动生成构造函数和字段的访问方法。

   ```java
   Point point = new Point(5, 10);
   System.out.println(point.x()); // 5
   System.out.println(point.y()); // 10
   ```

2. **不可变性**: `record` 的字段是不可变的，即一旦初始化，不能再改变。

   ```java
   // point.x = 20; // 编译错误，无法修改字段值
   ```

3. **自动生成的标准方法**: 自动生成的 `toString()`、`equals()` 和 `hashCode()` 方法。

   ```java
   Point point1 = new Point(5, 10);
   Point point2 = new Point(5, 10);
   
   System.out.println(point1.equals(point2)); // true
   System.out.println(point1.hashCode() == point2.hashCode()); // true
   ```

4. **自定义构造函数和方法**: 可以自定义构造函数和添加方法，但要遵守不可变性的原则。

   ```java
   public record Point(int x, int y) {
       public Point {
           if (x < 0 || y < 0) {
               throw new IllegalArgumentException("Coordinates must be non-negative");
           }
       }
   
       public int sum() {
           return x + y;
       }
   }
   ```

### 解决的问题

1. **样板代码减少**: `record` 减少了定义简单数据持有类时的样板代码，如手动编写构造函数、`toString()`、`equals()` 和 `hashCode()` 方法。

   ```java
   // 使用 record
   public record Person(String name, int age) {}
   
   // 手动编写的等效类
   public class Person {
       private final String name;
       private final int age;
   
       public Person(String name, int age) {
           this.name = name;
           this.age = age;
       }
   
       public String getName() {
           return name;
       }
   
       public int getAge() {
           return age;
       }
   
       @Override
       public boolean equals(Object o) {
           if (this == o) return true;
           if (o == null || getClass() != o.getClass()) return false;
           Person person = (Person) o;
           return age == person.age && Objects.equals(name, person.name);
       }
   
       @Override
       public int hashCode() {
           return Objects.hash(name, age);
       }
   
       @Override
       public String toString() {
           return "Person{" +
                   "name='" + name + '\'' +
                   ", age=" + age +
                   '}';
       }
   }
   ```

2. **不可变性**: `record` 类的字段是不可变的，默认保证了数据的不可变性，有助于编写线程安全的代码。

3. **简洁的语法**: 使用 `record` 语法更加简洁，便于理解和维护。

### 何时使用 `record`

- **数据传输对象（DTO）**: 用于封装和传输数据的对象。
- **值对象**: 包含数据但没有独立身份的对象，如坐标、颜色等。
- **不可变对象**: 需要保证对象创建后状态不可变的对象。

### 总结

Java `record` 提供了一种简洁的方式来定义不可变的数据持有类，自动生成构造函数、`toString()`、`equals()` 和 `hashCode()` 方法，减少样板代码，提高开发效率。适用于数据传输对象和值对象等需要不可变性的场景



例子:

```java
public class RecordTest {
    private record KpPair<A, B>(A sectionStartingKp, B sectionEndingKp) {
    }

    private record ThicknessPair<A, B>(A kpPair, B sectionThickness) {
    }

    public static void main(String[] args) {
        KpPair<Double, Double> p = new KpPair<Double, Double>(1d, 2d);
        ThicknessPair<KpPair<Double, Double>, Double> tp = new ThicknessPair<KpPair<Double, Double>, Double>(p, 1d);
        System.out.println("tp = " + tp); // ThicknessPair[kpPair=KpPair[sectionStartingKp=1.0, sectionEndingKp=2.0], sectionThickness=1.0]
    }

}
```





# 6. 内部类

在 Java 中，内部类的创建和引用方式取决于内部类的类型。以下是对成员内部类、静态内部类、局部内部类和匿名内部类的创建和引用方式的详细介绍：

### 1. 成员内部类（Member Inner Class）

成员内部类是定义在另一个类的内部，并且不包含 `static` 关键字。成员内部类可以访问外部类的所有成员变量和方法，包括私有的。

**创建和引用成员内部类的示例：**

```java
public class OuterClass {
    private String outerField = "Outer field";

    public class InnerClass {
        public void display() {
            System.out.println("Outer field: " + outerField);
        }
    }

    public static void main(String[] args) {
        OuterClass outer = new OuterClass();
        OuterClass.InnerClass inner = outer.new InnerClass();
        inner.display();  // 输出：Outer field: Outer field
    }
}
```

### 2. 静态内部类（Static Nested Class）

静态内部类使用 `static` 关键字修饰，不能直接访问外部类的非静态成员变量和方法。

**创建和引用静态内部类的示例：**

```java
public class OuterClass {
    private static String staticOuterField = "Static Outer field";

    public static class StaticInnerClass {
        public void display() {
            System.out.println("Static Outer field: " + staticOuterField);
        }
    }

    public static void main(String[] args) {
        OuterClass.StaticInnerClass inner = new OuterClass.StaticInnerClass();
        inner.display();  // 输出：Static Outer field: Static Outer field
    }
}
```

### 3. 局部内部类（Local Inner Class）(方法里面的类)

局部内部类是在方法内部定义的类。它只能在定义它的方法内部使用。

**创建和引用局部内部类的示例：**

```java
public class OuterClass {
    private String outerField = "Outer field";

    public void methodWithInnerClass() {
        class LocalInnerClass {
            public void display() {
                System.out.println("Outer field: " + outerField);
            }
        }
        LocalInnerClass inner = new LocalInnerClass();
        inner.display();  // 输出：Outer field: Outer field
    }

    public static void main(String[] args) {
        OuterClass outer = new OuterClass();
        outer.methodWithInnerClass();
    }
}
```

### 4. 匿名内部类（Anonymous Inner Class）

匿名内部类是没有名字的内部类，通常在需要简化代码时使用。它们一般用于实现接口或者继承类，并且只能实例化一次。

**创建和引用匿名内部类的示例：**

```java
public class OuterClass {
    private String outerField = "Outer field";

    public void createAnonymousClass() {
        new Thread(new Runnable() {
            @Override
            public void run() {
                System.out.println("Outer field: " + outerField);
            }
        }).start();
    }

    public static void main(String[] args) {
        OuterClass outer = new OuterClass();
        outer.createAnonymousClass();  // 启动线程，输出：Outer field: Outer field
    }
}
```

通过这些示例，你可以看到不同类型的内部类在创建和引用时的具体用法。每种类型的内部类都有其特定的应用场景和优缺点，选择适当的内部类类型可以使代码更加清晰和易于维护





# 7. java引用传递的 例子



## 1.  list.stream() 例子

```
 List<SpRiDtDetailDTO> list = spRiDtDetailDTOList.stream()
.filter(e -> e.getKpValue() != null && e.getKpValue().doubleValue() >= partitionInfo.getSectionStartingKp() && e.getKpValue().doubleValue() <= partitionInfo.getSectionEndingKp())
.sorted(Comparator.comparing(SpRiDtDetailDTO::getKpValue))
.toList();
我修改了 list 里面的对象的值, 那么 spRiDtDetailDTOList 里面的对象的值 也变化了吗
```



在 Java 中，`List<SpRiDtDetailDTO> list` 是通过流操作从 `spRiDtDetailDTOList` 过滤和排序得到的一个新列表。这种情况下，`list` 中的元素是对 `spRiDtDetailDTOList` 中相应元素的引用。因此，如果你修改了 `list` 中某个对象的值，那么 `spRiDtDetailDTOList` 中相应的对象的值也会发生变化。

这是因为 Java 中的对象是通过引用传递的，而不是通过值传递的。流操作生成的新列表 `list` 只是原列表 `spRiDtDetailDTOList` 中对象的引用，因此它们实际上指向的是同一组对象。

举个例子来说明：

```
java
复制代码
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

class SpRiDtDetailDTO {
    private Double kpValue;

    public SpRiDtDetailDTO(Double kpValue) {
        this.kpValue = kpValue;
    }

    public Double getKpValue() {
        return kpValue;
    }

    public void setKpValue(Double kpValue) {
        this.kpValue = kpValue;
    }

    @Override
    public String toString() {
        return "SpRiDtDetailDTO{" +
                "kpValue=" + kpValue +
                '}';
    }
}

public class Main {
    public static void main(String[] args) {
        List<SpRiDtDetailDTO> spRiDtDetailDTOList = new ArrayList<>();
        spRiDtDetailDTOList.add(new SpRiDtDetailDTO(1.0));
        spRiDtDetailDTOList.add(new SpRiDtDetailDTO(2.0));
        spRiDtDetailDTOList.add(new SpRiDtDetailDTO(3.0));

        // Simulating partitionInfo.getSectionStartingKp() and partitionInfo.getSectionEndingKp()
        double sectionStartingKp = 1.0;
        double sectionEndingKp = 3.0;

        List<SpRiDtDetailDTO> list = spRiDtDetailDTOList.stream()
                .filter(e -> e.getKpValue() != null && e.getKpValue() >= sectionStartingKp && e.getKpValue() <= sectionEndingKp)
                .sorted(Comparator.comparing(SpRiDtDetailDTO::getKpValue))
                .collect(Collectors.toList());

        // Modify an object in the new list
        list.get(0).setKpValue(10.0);

        // Print both lists to see the effect
        System.out.println("Original List: " + spRiDtDetailDTOList);
        System.out.println("New List: " + list);
    }
}
```

运行此代码时，你会发现 `spRiDtDetailDTOList` 和 `list` 中的第一个对象的 `kpValue` 都变成了 `10.0`：

```
css
复制代码
Original List: [SpRiDtDetailDTO{kpValue=10.0}, SpRiDtDetailDTO{kpValue=2.0}, SpRiDtDetailDTO{kpValue=3.0}]
New List: [SpRiDtDetailDTO{kpValue=10.0}, SpRiDtDetailDTO{kpValue=2.0}, SpRiDtDetailDTO{kpValue=3.0}]
```

这说明在 `list` 中修改对象的值会影响到 `spRiDtDetailDTOList` 中对应的对象



# 8. default 限定符

在Java中，“default”关键字主要用于以下两个场景：

1. **默认方法（Default Methods）**：

   - Java 8引入了接口中的默认方法概念。在接口中声明的方法可以使用`default`关键字来定义一个实现。这样，实现了该接口的类会自动获得这些方法的默认实现，除非它们已经覆盖了这些方法。
   - 这个特性允许你向现有的接口添加新方法而不破坏已有的实现。

   **示例代码**：

   <svg width="12" height="12" viewBox="0 0 11.199999809265137 11.199999809265137" class="cursor-pointer flex items-center tongyi-ui-highlighter-copy-btn"><g><path d="M11.2,1.6C11.2,0.716344,10.4837,0,9.6,0L4.8,0C3.91634,0,3.2,0.716344,3.2,1.6L4.16,1.6Q4.16,1.3349,4.34745,1.14745Q4.5349,0.96,4.8,0.96L9.6,0.96Q9.8651,0.96,10.0525,1.14745Q10.24,1.3349,10.24,1.6L10.24,6.4Q10.24,6.6651,10.0525,6.85255Q9.8651,7.04,9.6,7.04L9.6,8C10.4837,8,11.2,7.28366,11.2,6.4L11.2,1.6ZM0,4L0,9.6C0,10.4837,0.716344,11.2,1.6,11.2L7.2,11.2C8.08366,11.2,8.8,10.4837,8.8,9.6L8.8,4C8.8,3.11634,8.08366,2.4,7.2,2.4L1.6,2.4C0.716344,2.4,0,3.11634,0,4ZM1.14745,10.0525Q0.96,9.8651,0.96,9.6L0.96,4Q0.96,3.7349,1.14745,3.54745Q1.3349,3.36,1.6,3.36L7.2,3.36Q7.4651,3.36,7.65255,3.54745Q7.84,3.7349,7.84,4L7.84,9.6Q7.84,9.8651,7.65255,10.0525Q7.4651,10.24,7.2,10.24L1.6,10.24Q1.3349,10.24,1.14745,10.0525Z"></path></g></svg>

   ```java
   public interface MyInterface {
       default void myMethod() {
           System.out.println("This is a default method.");
       }
   }
   
   public class MyClass implements MyInterface {
       // MyClass 不需要实现 myMethod 方法，因为它已经有了一个默认实现。
   }
   
   public class Main {
       public static void main(String[] args) {
           MyClass myClass = new MyClass();
           myClass.myMethod(); // 输出: This is a default method.
       }
   }
   ```

2. **默认值（Default Values）**：

   - 在Java中，`default`也可以用来指代枚举类型的默认实例。当创建枚举类型时，如果没有明确指定某个常量作为默认值，那么第一个定义的枚举常量将被默认为默认值。
   - 这个用途相对较少见，并且通常是在特定框架或库中为了配置目的而使用。

需要注意的是，`default`关键字不应该与类、变量或常规方法的定义混淆，它只适用于上述两种情况。此外，在Java 9及更高版本中，`default`关键字还可以用于模块定义文件（module-info.java）中的模块声明中，用来指定模块是开放给所有其他模块还是只对特定模块开放。

如果你有更具体的关于`default`关键字的问题或者需要更详细的解释，请告诉我！



在Java中，如果你在一个类内部定义了一个字段（成员变量）但没有显式地指定访问控制修饰符（即访问限定符），那么它的默认访问级别是**包私有**（package-private）。这意味着这个字段只能被同一个包内的类所访问。

下面是几种不同的访问级别及其含义：

- **public**：公开的，可以被任何类访问。
- **protected**：受保护的，可以被同一包内以及子类访问，无论子类是否位于同一个包内。
- **private**：私有的，只能被定义该字段的类本身访问。
- **无访问控制修饰符**（默认）：也称为包私有（package-private），只能被同一个包内的类访问。



# 9. 反斜杠

在 Java 字符串中，反斜杠 `\` 是一个特殊字符，用作转义字符。这意味着反斜杠后面的字符会被解释为其特定的含义，例如 `\n` 表示换行，`\t` 表示制表符，`\\"` 表示一个字面上的双引号，等等。

### 解释 `\\\\`

1. **单个反斜杠 \**：
   - 在字符串中，单个反斜杠是一个转义字符。如果你想在字符串中表示一个反斜杠，你需要用两个反斜杠 `\\`。
2. **两个反斜杠 \\**：
   - 表示一个字面上的反斜杠字符。即，`"\\u"` 中的 `\\` 将被解释为一个实际的 `\` 字符。
3. **四个反斜杠 \\\\**：
   - 由于第一个两个反斜杠 `\\` 表示一个反斜杠，第二个两个反斜杠 `\\` 也同样表示一个反斜杠，所以 `\\\\` 最终表示两个字面上的反斜杠字符。即，`"\\\\"` 这个字符串实际包含两个反斜杠。

### 例子

以下是一些字符串示例及其解释：

```
java


复制代码
String singleBackslash = "\\"; // 表示一个反斜杠
String twoBackslashes = "\\\\"; // 表示两个反斜杠
String unicodeExample = "\\u4F60"; // 表示Unicode转义字符（如：你）
```

- `singleBackslash` 的值为 `\`
- `twoBackslashes` 的值为 `\\`
- `unicodeExample` 的值为 `\u4F60`，在处理时需要将其转义解析为对应字符。

### 使用场景

当你需要在正则表达式中使用反斜杠，或在文件路径、字符串处理等场合时，通常会遇到这样的转义需求。



# 10. 怎么理解 list本身不可变

怎么理解 list本身不可变



###### ChatGPT 说：

### **✅ 如何理解 List 本身不可变？**

**⚡ 关键点：**

- **不可变 List**：指的是 **不能修改** 这个 `List` 的结构（即不能 `add()`、`remove()`、`set()` 等）。
- **但 List 内部的对象仍然可变**，除非它们本身也是不可变的。

------

## **1️⃣ List 本身不可变 ≠ List 里的对象不可变**

**📌 示例：创建不可变 List，但内部对象仍然可修改**

```
java


复制编辑
import java.util.List;

public class Main {
    public static void main(String[] args) {
        Person person1 = new Person("Alice", 25);
        Person person2 = new Person("Bob", 30);

        List<Person> people = List.of(person1, person2); // 创建不可变 List

        // 尝试修改 List 本身（会报错）
        // people.add(new Person("Charlie", 40)); // ❌ UnsupportedOperationException
        // people.remove(0); // ❌ UnsupportedOperationException
        // people.set(0, new Person("David", 35)); // ❌ UnsupportedOperationException

        // 但可以修改 List 里的对象
        person2.setAge(35); // ✅ 允许修改
        System.out.println(people);
        // 输出: [Person{name='Alice', age=25}, Person{name='Bob', age=35}]
    }
}
```

**🔎 结论**

- **List.of(person1, person2) 让 people 变成了不可变 List**
- **但 person2 是可变的，所以 person2.setAge(35) 依然有效！**

------

## **2️⃣ List 本身不可变：不能增删改**

当 `List` **本身不可变** 时，下面这些操作都会抛出异常：

```
java


复制编辑
List<String> names = List.of("Alice", "Bob", "Charlie");

names.add("David");   // ❌ UnsupportedOperationException
names.remove(0);      // ❌ UnsupportedOperationException
names.set(1, "Eve");  // ❌ UnsupportedOperationException
```

**📌 结果：**

```
cpp


复制编辑
Exception in thread "main" java.lang.UnsupportedOperationException
```

### **🚀 为什么 List.of() 让 List 变不可变？**

- `List.of(...)` **返回的是 Java 内部的 ImmutableCollections.ListN**
- 这个 `ListN` 没有 `add()`、`remove()`、`set()` 的实现，调用时会直接抛 `UnsupportedOperationException`。

------

## **3️⃣ 如何让 List 里的对象也不可变？**

如果你想让 `List` **本身** 和 **其中的对象** 都不可变，可以让 `Person` 变成不可变对象：

### **✅ 方案 1：用 final + 无 setter**

```
java


复制编辑
public final class Person {
    private final String name;
    private final int age;

    public Person(String name, int age) {
        this.name = name;
        this.age = age;
    }

    public String getName() { return name; }
    public int getAge() { return age; }
}
```

### **✅ 方案 2：使用 record（Java 14+）**

```
java


复制编辑
public record Person(String name, int age) {}
```

**🔎 这样，即使 List 里的对象，也不能修改 age 了！**

------

## **🎯 4️⃣ 总结**

| 操作                        | `List.of(...)` 是否允许        |
| --------------------------- | ------------------------------ |
| `list.add(element)`         | ❌ 不允许                       |
| `list.remove(index)`        | ❌ 不允许                       |
| `list.set(index, newValue)` | ❌ 不允许                       |
| 修改 `List` 里的对象        | ✅ 允许，**除非对象本身不可变** |

------

**🔥 结论：**

- **List.of() 让 List 本身不可变**，但**不影响其中的对象**。
- **要让 List 里的对象也不可变，必须让对象本身（如 Person）不可变**。
- **如果 Person 也是不可变的，那么 List.of() 里的所有元素都是只读的！**



# 11. 怎么理解对象不可变

### **如何理解对象不可变（Immutable Object）？**

**不可变对象（Immutable Object）** 指的是 **一旦创建，就不能被修改** 的对象。它的状态在整个生命周期中保持不变，任何修改都会返回一个新的对象，而不是修改原对象。

------

## **1. 不可变对象的特点**

### **✅ 主要特性**

1. **状态不可变**：对象的字段（字段值）在对象创建后 **不能被修改**。
2. **线程安全**：多个线程可以安全地访问不可变对象，无需同步。
3. **无副作用**：由于不会修改对象本身，调用方法不会影响外部状态。
4. **通常是 final**：类通常被声明为 `final`，确保子类不能修改行为。

------

## **2. 如何创建一个不可变对象？**

以 **Person** 类为例，演示如何定义不可变对象：

```
java


复制编辑
public final class Person {
    private final String name;
    private final int age;

    public Person(String name, int age) {
        this.name = name;
        this.age = age;
    }

    public String getName() {
        return name;
    }

    public int getAge() {
        return age;
    }

    // 不提供 setter 方法，确保不可修改

    @Override
    public String toString() {
        return "Person{name='" + name + "', age=" + age + "}";
    }
}
```

### **🚀 关键点**

1. **final 类**：`Person` 使用 `final` 修饰，防止子类修改不可变性。
2. **所有字段 final**：确保对象创建后字段值不可更改。
3. **没有 setter 方法**：不提供任何修改字段的方法。
4. **构造方法初始化所有字段**：只能在创建时赋值，之后不能更改。

------

## **3. 不可变对象的优势**

### **✅ 线程安全**

多个线程可以安全访问 `Person`，不需要额外的同步：

```
java


复制编辑
Person p1 = new Person("Alice", 25);
Person p2 = new Person("Alice", 25);

System.out.println(p1 == p2);  // false，不是同一个对象
System.out.println(p1.equals(p2));  // true，值相等
```

**由于 Person 不可变，它可以安全地被多个线程共享**，而不需要同步。

### **✅ 适用于缓存和哈希**

不可变对象在 **哈希表（HashMap、HashSet）** 和 **缓存** 里表现更好，因为它们不会改变，哈希值也不会变化。

------

## **4. 不可变对象 VS 可变对象**

| 对比项       | 不可变对象（Immutable）          | 可变对象（Mutable）          |
| ------------ | -------------------------------- | ---------------------------- |
| **状态修改** | 不能修改，创建后固定             | 可以随时修改                 |
| **线程安全** | 天然线程安全                     | 可能需要同步                 |
| **哈希值**   | 固定不变                         | 可能变化                     |
| **示例**     | `String`、`Integer`、`LocalDate` | `StringBuilder`、`ArrayList` |

### **示例：String 是不可变对象**

```
java


复制编辑
String s1 = "Hello";
String s2 = s1.concat(" World");
System.out.println(s1);  // 仍然是 "Hello"
System.out.println(s2);  // "Hello World"
```

**s1.concat(" World") 并不会修改 s1，而是返回一个新字符串 s2**。

------

## **5. 需要修改怎么办？（返回新对象）**

如果需要修改不可变对象的数据，**创建新对象** 而不是修改原对象。

### **示例：返回新对象**

```
java


复制编辑
public Person withAge(int newAge) {
    return new Person(this.name, newAge);
}
```

### **调用示例**

```
java


复制编辑
Person p1 = new Person("Alice", 25);
Person p2 = p1.withAge(30);

System.out.println(p1); // Person{name='Alice', age=25}
System.out.println(p2); // Person{name='Alice', age=30}
```

**p1 依然是 25 岁，p2 是新对象，30 岁！**

------

## **6. 什么时候使用不可变对象？**

✅ 适用于：

- **多线程环境**，如 `String`、`Integer`、`LocalDate`
- **缓存场景**，如 `String` 被存入字符串池
- **数据建模**，如 Java 记录 (`record`)、DTO（数据传输对象）

------

## **7. 结论**

- **不可变对象的核心：不能修改，只能创建新对象**
- **Java 里的 String、Integer、LocalDate 等类都是不可变的**
- **不可变对象更安全、线程安全、适用于哈希表**
- **如果需要修改数据，返回新对象，而不是修改原对象**

------

**你是想实现自己的不可变对象，还是想更深入了解 Java 的不可变类？** 😊



##### 您说：

public class Person {     private String name;     private int age;} 那如果是这样, 能实现不可变吗



###### ChatGPT 说：

你的 `Person` 类 **不能算是不可变对象**，因为它没有采取必要的保护措施，使其状态在创建后仍然可以被修改。下面我们来分析为什么它 **不是不可变的**，以及如何改进它。

------

## **1. 你的 Person 类的问题**

```
java


复制编辑
public class Person {
    private String name;
    private int age;
}
```

### **❌ 为什么它不是不可变的？**

1. **字段不是 final**
   - `private String name;` 和 `private int age;` 没有 `final` 修饰，意味着可以在构造方法之外被修改。
2. **没有构造方法**
   - 没有构造方法的话，默认会有无参构造器，允许在对象创建后赋值。
3. **没有限制修改的方法**
   - 如果添加 `setter` 方法（如 `setName()` 和 `setAge()`），那么对象可以随时被修改，不符合“不可变”的概念。

------

## **2. 如何修改成真正的不可变对象？**

我们需要 **确保对象创建后不可修改**，可以按照以下方式实现：

### **✅ 方案 1：使用 final 关键字**

```
java


复制编辑
public final class Person {
    private final String name;
    private final int age;

    public Person(String name, int age) {
        this.name = name;
        this.age = age;
    }

    public String getName() {
        return name;
    }

    public int getAge() {
        return age;
    }

    @Override
    public String toString() {
        return "Person{name='" + name + "', age=" + age + "}";
    }
}
```

### **✅ 关键点**

1. **类用 final 修饰**，防止子类篡改不可变性。
2. **所有字段 final**，保证创建后不能被修改。
3. **没有 setter 方法**，不能修改 `name` 和 `age`。
4. **在构造方法里初始化字段**，避免后续修改。

------

## **3. 这样真的不可变了吗？**

对于 **基本类型（如 int）和不可变对象（如 String）**，这个方案已经是 **100% 不可变** 了！🎯

但是，如果 `Person` 类中有 **可变对象（如 List、Date）**，我们需要额外的保护。

------

## **4. 如果 Person 里有 List 怎么办？**

如果 `Person` 有一个 **List<String>** 类型的 `hobbies`（爱好），默认情况下 `List` 是 **可变的**，别人可以修改它：

```
java


复制编辑
import java.util.List;

public final class Person {
    private final String name;
    private final int age;
    private final List<String> hobbies; // List 是可变的！

    public Person(String name, int age, List<String> hobbies) {
        this.name = name;
        this.age = age;
        this.hobbies = hobbies; // ❌ 直接赋值，外部仍然可以修改
    }

    public List<String> getHobbies() {
        return hobbies; // ❌ 返回的是可变对象的引用
    }
}
```

### **❌ 这里的问题**

即使 `Person` 是 `final`，但是 `hobbies` 仍然可以通过 **外部修改**：

```
java


复制编辑
List<String> hobbies = new ArrayList<>();
hobbies.add("Reading");

Person p = new Person("Alice", 25, hobbies);
hobbies.add("Swimming"); // 修改了外部 list，影响了 p

System.out.println(p.getHobbies()); // [Reading, Swimming] (意外修改)
```

**💥 这样 Person 仍然不是不可变的！**

------

## **5. 解决方案：深拷贝 List**

### **✅ 方案 1：使用 Collections.unmodifiableList()**

```
java


复制编辑
import java.util.Collections;
import java.util.List;

public final class Person {
    private final String name;
    private final int age;
    private final List<String> hobbies;

    public Person(String name, int age, List<String> hobbies) {
        this.name = name;
        this.age = age;
        // 创建不可变 List，防止外部修改
        this.hobbies = Collections.unmodifiableList(hobbies);
    }

    public List<String> getHobbies() {
        return hobbies; // 直接返回不可变 List
    }
}
```

### **✅ 方案 2：深拷贝 List**

```
java


复制编辑
import java.util.ArrayList;
import java.util.List;

public final class Person {
    private final String name;
    private final int age;
    private final List<String> hobbies;

    public Person(String name, int age, List<String> hobbies) {
        this.name = name;
        this.age = age;
        // 复制 List，防止外部修改
        this.hobbies = new ArrayList<>(hobbies);
    }

    public List<String> getHobbies() {
        return new ArrayList<>(hobbies); // 返回副本，防止修改原对象
    }
}
```

**✅ 这样，外部的 List 无法影响 Person！**

------

## **6. Java 记录（record）可以更简单**

如果你在 Java 14+，可以直接用 `record` 创建不可变对象：

```
java


复制编辑
public record Person(String name, int age) {}
```

**record 天然不可变**：

- **字段自动 final**
- **没有 setter**
- **toString()、equals()、hashCode() 自动生成**

使用：

```
java


复制编辑
Person p = new Person("Alice", 25);
System.out.println(p.name()); // Alice
// p.name = "Bob"; // ❌ 编译错误，不能修改
```

------

## **7. 结论**

| 方式                             | 是否不可变 | 备注                      |
| -------------------------------- | ---------- | ------------------------- |
| 你的 `Person` 类                 | ❌ 否       | 字段可以修改              |
| `final` + `final` 字段           | ✅ 是       | 适用于基本类型和 `String` |
| `List` 直接赋值                  | ❌ 否       | `List` 仍然可变           |
| `Collections.unmodifiableList()` | ✅ 是       | 让 `List` 变为只读        |
| 深拷贝 `List`                    | ✅ 是       | 彻底防止修改              |
| `record` (Java 14+)              | ✅ 是       | Java 提供的最佳方案       |

### **🔥 最佳做法**

如果你用 Java 14+，`record` 是最好的方案。否则：

- **基本类型 & String** 用 `final`
- **集合 & 可变对象** 复制数据或使用 `Collections.unmodifiableList()`



# 12. 怎么理解string 的不可变

### **✅ 如何理解 String a = "hello"; a = "world";**

在 Java 中，`String` 是 **不可变对象（immutable）**，但 `a = "world";` 仍然是合法的。这是因为**`a` 不是 `String` 本身，而是一个引用**，可以指向不同的 `String` 对象。

------

## **1️⃣ 代码分析**

```
java


复制编辑
String a = "hello";  // a 指向 "hello"
a = "world";         // a 现在指向 "world"
```

### **🛠 背后的内存变化**

1. ```
   String a = "hello";
   ```

   - 在字符串常量池（String Pool）中创建 `"hello"`，`a` 指向它。

2. ```
   a = "world";
   ```

   - 在字符串常量池中创建 `"world"`（如果不存在）。
   - `a` 现在指向 `"world"`，但 `"hello"` 仍然在内存中，不会被改变。

------

## **2️⃣ 为什么 String 是不可变的？**

**不可变** 的意思是，**字符串对象的值一旦创建就不能修改**，但变量 `a` 只是引用，可以指向别的 `String`。

### **❌ 错误理解：修改了 "hello"**

**❌ a = "world"; 修改了 "hello"**

> ❌ **错误！** `"hello"` **并没有变**，只是 `a` 变了。

### **✅ 正确理解**

```
java


复制编辑
String a = "hello";
a = "world";
```

**✅ "hello" 仍然存在，但 a 不再指向它。**

------

## **3️⃣ 证明 String 是不可变的**

如果 `String` **是可变的**，那么下面的代码：

```
java


复制编辑
String s1 = "hello";
String s2 = s1;  // s1 和 s2 指向同一个 "hello"
s1 = "world";    // s1 变了，s2 还是 "hello"

System.out.println(s2); // "hello"
```

**如果 String 可变，s2 也会变成 "world"，但它仍然是 "hello"！**

**✅ 说明 String 是不可变的，修改 s1 只是让它指向新字符串。**

------

## **4️⃣ String 的真正不可变性**

真正体现 `String` **不可变** 的例子：

```
java


复制编辑
String str = "hello";
str.toUpperCase(); // ❌ 这个操作不会修改 str
System.out.println(str); // 仍然是 "hello"

String newStr = str.toUpperCase();
System.out.println(newStr); // "HELLO"
```

**📌 toUpperCase() 没有修改 "hello"，而是创建了一个新字符串 "HELLO"**。

------

## **🎯 总结**

| 操作                                   | 影响                                              |
| -------------------------------------- | ------------------------------------------------- |
| `a = "hello"; a = "world";`            | `a` 的指向变了，**但 "hello" 没有被修改**         |
| `s1 = "hello"; s2 = s1; s1 = "world";` | `s2` 仍然是 `"hello"`，**说明 String 是不可变的** |
| `str.toUpperCase();`                   | **不会修改 str，会生成一个新字符串**              |

**🚀 重点：**

- `String` 是不可变的，**修改字符串实际上是创建新对象**。
- `a = "hello"; a = "world";` **只是改变 a 的指向，不是修改 hello**。
- **如果你想要真正修改字符串的内容，应该使用 StringBuilder 或 StringBuffer。**

**💡 你是想理解 String 的不可变性，还是遇到了 String 相关的代码问题？😊**



##### 您说：

"hello" 仍然在内存中 啥意思, 我能访问到它吗



###### ChatGPT 说：

### **✅ "hello" 仍然在内存中，但你不能直接访问它！**

当你执行：

```
java


复制编辑
String a = "hello";
a = "world";
```

**📌 "hello" 仍然可能存在于内存中，但变量 a 已经不指向它了，你无法再通过 a 访问它。**

------

## **1️⃣ "hello" 真的还在内存中吗？**

取决于具体情况：

1. **如果 "hello" 还被其他变量引用**，它仍然可以被访问：

   ```
   java
   
   
   复制编辑
   String a = "hello";
   String b = a;  // b 也指向 "hello"
   a = "world";   // a 变了，但 b 仍然是 "hello"
   System.out.println(b); // 仍然能访问 "hello"
   ```

   **📌 这里 "hello" 还在，因为 b 仍然引用它。**

2. **如果 "hello" 没有被任何变量引用**，它最终会被 GC（垃圾回收）清理掉：

   ```
   java
   
   
   复制编辑
   String a = "hello";
   a = "world"; // "hello" 现在没有任何变量引用
   ```

   **📌 "hello" 可能还暂时在内存里，但 Java 的垃圾回收器（GC）最终会清理掉它**。

------

## **2️⃣ 我还能访问 "hello" 吗？**

### **❌ 不能通过 a 访问**

```
java


复制编辑
String a = "hello";
a = "world";
System.out.println(a); // "world"，无法再通过 `a` 访问 "hello"
```

### **✅ 但如果 "hello" 在字符串常量池中，仍然可以通过 intern() 访问**

```
java


复制编辑
String x = "hello";
x = "world";

String y = "hello"; // 由于字符串池的特性，y 可能指向同一个 "hello"
System.out.println(y); // 仍然是 "hello"
```

**📌 "hello" 可能仍然在字符串常量池（String Pool）里，所以 y 还能访问到它。**

------

## **3️⃣ 如何证明 "hello" 还在？**

你可以用 **弱引用（WeakReference）** 观察垃圾回收：

```
java


复制编辑
import java.lang.ref.WeakReference;

public class Main {
    public static void main(String[] args) {
        String str = new String("hello"); // 强制创建一个新的字符串
        WeakReference<String> weakRef = new WeakReference<>(str);

        System.out.println("Before GC: " + weakRef.get()); // "hello"

        str = null; // 解除引用
        System.gc(); // 手动触发垃圾回收（不保证立即生效）

        System.out.println("After GC: " + weakRef.get()); // 可能是 null（如果 GC 发生了）
    }
}
```

**📌 如果 After GC 输出 null，说明 "hello" 被垃圾回收了。**

------

## **🎯 结论**

| **情况**                                      | **"hello" 还在内存中吗？** | **能访问到吗？** |
| --------------------------------------------- | -------------------------- | ---------------- |
| 变量仍然引用它 (`String b = a;`)              | ✅ 还在                     | ✅ 还能访问       |
| `"hello"` 在字符串池（`String x = "hello";`） | ✅ 可能还在                 | ✅ 还能访问       |
| 没有变量引用它                                | ❌ 可能被 GC 清理           | ❌ 无法访问       |

**🚀 重点**

- `"hello"` **可能** 还在内存里，但如果没有引用，**你无法再访问它**。
- **如果 "hello" 在字符串池（String Pool）里，它不会被 GC，仍然可以访问**。
- **真正的删除依赖于 Java 的垃圾回收机制（GC）**





# 13. System.*getProperties*() 里 都有什么

```tex
java.specification.version = 17
sun.cpu.isalist = amd64
sun.jnu.encoding = GBK
java.class.path = H:\java-project\wg\springboot-3.1.0-java17\target\classes;C:\Users\wg\.m2\repository\org\luaj\luaj-jse\3.0.1\luaj-jse-3.0.1.jar;C:\Users\wg\.m2\repository\org\apache\httpcomponents\httpclient\4.5.14\httpclient-4.5.14.jar;C:\Users\wg\.m2\repository\org\apache\httpcomponents\httpcore\4.4.16\httpcore-4.4.16.jar;C:\Users\wg\.m2\repository\commons-logging\commons-logging\1.2\commons-logging-1.2.jar;C:\Users\wg\.m2\repository\commons-codec\commons-codec\1.11\commons-codec-1.11.jar;C:\Users\wg\.m2\repository\org\mybatis\spring\boot\mybatis-spring-boot-starter\3.0.2\mybatis-spring-boot-starter-3.0.2.jar;C:\Users\wg\.m2\repository\org\springframework\boot\spring-boot-starter-jdbc\3.1.0\spring-boot-starter-jdbc-3.1.0.jar;C:\Users\wg\.m2\repository\com\zaxxer\HikariCP\5.0.1\HikariCP-5.0.1.jar;C:\Users\wg\.m2\repository\org\springframework\spring-jdbc\6.0.9\spring-jdbc-6.0.9.jar;C:\Users\wg\.m2\repository\org\mybatis\spring\boot\mybatis-spring-boot-autoconfigure\3.0.2\mybatis-spring-boot-autoconfigure-3.0.2.jar;C:\Users\wg\.m2\repository\org\mybatis\mybatis\3.5.13\mybatis-3.5.13.jar;C:\Users\wg\.m2\repository\org\mybatis\mybatis-spring\3.0.2\mybatis-spring-3.0.2.jar;C:\Users\wg\.m2\repository\org\springframework\boot\spring-boot-starter\3.1.0\spring-boot-starter-3.1.0.jar;C:\Users\wg\.m2\repository\org\springframework\boot\spring-boot\3.1.0\spring-boot-3.1.0.jar;C:\Users\wg\.m2\repository\org\springframework\boot\spring-boot-autoconfigure\3.1.0\spring-boot-autoconfigure-3.1.0.jar;C:\Users\wg\.m2\repository\org\springframework\boot\spring-boot-starter-logging\3.1.0\spring-boot-starter-logging-3.1.0.jar;C:\Users\wg\.m2\repository\ch\qos\logback\logback-classic\1.4.7\logback-classic-1.4.7.jar;C:\Users\wg\.m2\repository\ch\qos\logback\logback-core\1.4.7\logback-core-1.4.7.jar;C:\Users\wg\.m2\repository\org\apache\logging\log4j\log4j-to-slf4j\2.20.0\log4j-to-slf4j-2.20.0.jar;C:\Users\wg\.m2\repository\org\slf4j\jul-to-slf4j\2.0.7\jul-to-slf4j-2.0.7.jar;C:\Users\wg\.m2\repository\jakarta\annotation\jakarta.annotation-api\2.1.1\jakarta.annotation-api-2.1.1.jar;C:\Users\wg\.m2\repository\org\springframework\spring-core\6.0.9\spring-core-6.0.9.jar;C:\Users\wg\.m2\repository\org\springframework\spring-jcl\6.0.9\spring-jcl-6.0.9.jar;C:\Users\wg\.m2\repository\org\yaml\snakeyaml\1.33\snakeyaml-1.33.jar;C:\Users\wg\.m2\repository\org\springframework\boot\spring-boot-starter-web\3.1.0\spring-boot-starter-web-3.1.0.jar;C:\Users\wg\.m2\repository\org\springframework\boot\spring-boot-starter-json\3.1.0\spring-boot-starter-json-3.1.0.jar;C:\Users\wg\.m2\repository\com\fasterxml\jackson\datatype\jackson-datatype-jdk8\2.15.0\jackson-datatype-jdk8-2.15.0.jar;C:\Users\wg\.m2\repository\com\fasterxml\jackson\datatype\jackson-datatype-jsr310\2.15.0\jackson-datatype-jsr310-2.15.0.jar;C:\Users\wg\.m2\repository\com\fasterxml\jackson\module\jackson-module-parameter-names\2.15.0\jackson-module-parameter-names-2.15.0.jar;C:\Users\wg\.m2\repository\org\springframework\spring-web\6.0.9\spring-web-6.0.9.jar;C:\Users\wg\.m2\repository\org\springframework\spring-beans\6.0.9\spring-beans-6.0.9.jar;C:\Users\wg\.m2\repository\io\micrometer\micrometer-observation\1.10.7\micrometer-observation-1.10.7.jar;C:\Users\wg\.m2\repository\io\micrometer\micrometer-commons\1.10.7\micrometer-commons-1.10.7.jar;C:\Users\wg\.m2\repository\org\springframework\spring-webmvc\6.0.9\spring-webmvc-6.0.9.jar;C:\Users\wg\.m2\repository\org\springframework\spring-aop\6.0.9\spring-aop-6.0.9.jar;C:\Users\wg\.m2\repository\org\springframework\spring-expression\6.0.9\spring-expression-6.0.9.jar;C:\Users\wg\.m2\repository\org\springframework\boot\spring-boot-starter-undertow\3.1.0\spring-boot-starter-undertow-3.1.0.jar;C:\Users\wg\.m2\repository\io\undertow\undertow-core\2.3.6.Final\undertow-core-2.3.6.Final.jar;C:\Users\wg\.m2\repository\org\jboss\logging\jboss-logging\3.4.3.Final\jboss-logging-3.4.3.Final.jar;C:\Users\wg\.m2\repository\org\jboss\xnio\xnio-api\3.8.8.Final\xnio-api-3.8.8.Final.jar;C:\Users\wg\.m2\repository\org\wildfly\common\wildfly-common\1.5.4.Final\wildfly-common-1.5.4.Final.jar;C:\Users\wg\.m2\repository\org\wildfly\client\wildfly-client-config\1.0.1.Final\wildfly-client-config-1.0.1.Final.jar;C:\Users\wg\.m2\repository\org\jboss\xnio\xnio-nio\3.8.8.Final\xnio-nio-3.8.8.Final.jar;C:\Users\wg\.m2\repository\org\jboss\threads\jboss-threads\3.5.0.Final\jboss-threads-3.5.0.Final.jar;C:\Users\wg\.m2\repository\io\undertow\undertow-servlet\2.3.6.Final\undertow-servlet-2.3.6.Final.jar;C:\Users\wg\.m2\repository\jakarta\servlet\jakarta.servlet-api\6.0.0\jakarta.servlet-api-6.0.0.jar;C:\Users\wg\.m2\repository\io\undertow\undertow-websockets-jsr\2.3.6.Final\undertow-websockets-jsr-2.3.6.Final.jar;C:\Users\wg\.m2\repository\jakarta\websocket\jakarta.websocket-api\2.1.0\jakarta.websocket-api-2.1.0.jar;C:\Users\wg\.m2\repository\jakarta\websocket\jakarta.websocket-client-api\2.1.0\jakarta.websocket-client-api-2.1.0.jar;C:\Users\wg\.m2\repository\org\apache\tomcat\embed\tomcat-embed-el\10.1.8\tomcat-embed-el-10.1.8.jar;C:\Users\wg\.m2\repository\org\springframework\boot\spring-boot-starter-data-jpa\3.1.0\spring-boot-starter-data-jpa-3.1.0.jar;C:\Users\wg\.m2\repository\org\springframework\boot\spring-boot-starter-aop\3.1.0\spring-boot-starter-aop-3.1.0.jar;C:\Users\wg\.m2\repository\org\aspectj\aspectjweaver\1.9.19\aspectjweaver-1.9.19.jar;C:\Users\wg\.m2\repository\org\hibernate\orm\hibernate-core\6.2.2.Final\hibernate-core-6.2.2.Final.jar;C:\Users\wg\.m2\repository\jakarta\persistence\jakarta.persistence-api\3.1.0\jakarta.persistence-api-3.1.0.jar;C:\Users\wg\.m2\repository\jakarta\transaction\jakarta.transaction-api\2.0.1\jakarta.transaction-api-2.0.1.jar;C:\Users\wg\.m2\repository\org\hibernate\common\hibernate-commons-annotations\6.0.6.Final\hibernate-commons-annotations-6.0.6.Final.jar;C:\Users\wg\.m2\repository\io\smallrye\jandex\3.0.5\jandex-3.0.5.jar;C:\Users\wg\.m2\repository\com\fasterxml\classmate\1.5.1\classmate-1.5.1.jar;C:\Users\wg\.m2\repository\net\bytebuddy\byte-buddy\1.12.18\byte-buddy-1.12.18.jar;C:\Users\wg\.m2\repository\jakarta\xml\bind\jakarta.xml.bind-api\4.0.0\jakarta.xml.bind-api-4.0.0.jar;C:\Users\wg\.m2\repository\jakarta\activation\jakarta.activation-api\2.1.0\jakarta.activation-api-2.1.0.jar;C:\Users\wg\.m2\repository\org\glassfish\jaxb\jaxb-runtime\4.0.2\jaxb-runtime-4.0.2.jar;C:\Users\wg\.m2\repository\org\glassfish\jaxb\jaxb-core\4.0.2\jaxb-core-4.0.2.jar;C:\Users\wg\.m2\repository\org\eclipse\angus\angus-activation\2.0.0\angus-activation-2.0.0.jar;C:\Users\wg\.m2\repository\org\glassfish\jaxb\txw2\4.0.2\txw2-4.0.2.jar;C:\Users\wg\.m2\repository\com\sun\istack\istack-commons-runtime\4.1.1\istack-commons-runtime-4.1.1.jar;C:\Users\wg\.m2\repository\jakarta\inject\jakarta.inject-api\2.0.1\jakarta.inject-api-2.0.1.jar;C:\Users\wg\.m2\repository\org\antlr\antlr4-runtime\4.10.1\antlr4-runtime-4.10.1.jar;C:\Users\wg\.m2\repository\org\springframework\data\spring-data-jpa\3.1.0\spring-data-jpa-3.1.0.jar;C:\Users\wg\.m2\repository\org\springframework\data\spring-data-commons\3.1.0\spring-data-commons-3.1.0.jar;C:\Users\wg\.m2\repository\org\springframework\spring-orm\6.0.9\spring-orm-6.0.9.jar;C:\Users\wg\.m2\repository\org\springframework\spring-tx\6.0.9\spring-tx-6.0.9.jar;C:\Users\wg\.m2\repository\org\springframework\spring-aspects\6.0.9\spring-aspects-6.0.9.jar;C:\Users\wg\.m2\repository\com\mysql\mysql-connector-j\8.0.33\mysql-connector-j-8.0.33.jar;C:\Users\wg\.m2\repository\com\google\protobuf\protobuf-java\3.21.9\protobuf-java-3.21.9.jar;C:\Users\wg\.m2\repository\com\alibaba\druid\1.2.18\druid-1.2.18.jar;C:\Users\wg\.m2\repository\cn\hutool\hutool-all\5.8.18\hutool-all-5.8.18.jar;C:\Users\wg\.m2\repository\org\slf4j\slf4j-api\2.0.7\slf4j-api-2.0.7.jar;C:\Users\wg\.m2\repository\org\apache\poi\poi\5.2.3\poi-5.2.3.jar;C:\Users\wg\.m2\repository\org\apache\commons\commons-collections4\4.4\commons-collections4-4.4.jar;C:\Users\wg\.m2\repository\org\apache\commons\commons-math3\3.6.1\commons-math3-3.6.1.jar;C:\Users\wg\.m2\repository\commons-io\commons-io\2.11.0\commons-io-2.11.0.jar;C:\Users\wg\.m2\repository\com\zaxxer\SparseBitSet\1.2\SparseBitSet-1.2.jar;C:\Users\wg\.m2\repository\org\apache\logging\log4j\log4j-api\2.18.0\log4j-api-2.18.0.jar;C:\Users\wg\.m2\repository\org\apache\poi\poi-ooxml\5.2.3\poi-ooxml-5.2.3.jar;C:\Users\wg\.m2\repository\org\apache\poi\poi-ooxml-lite\5.2.3\poi-ooxml-lite-5.2.3.jar;C:\Users\wg\.m2\repository\org\apache\xmlbeans\xmlbeans\5.1.1\xmlbeans-5.1.1.jar;C:\Users\wg\.m2\repository\org\apache\commons\commons-compress\1.21\commons-compress-1.21.jar;C:\Users\wg\.m2\repository\com\github\virtuald\curvesapi\1.07\curvesapi-1.07.jar;C:\Users\wg\.m2\repository\io\github\boniu-w\common-boniu\1.0-SNAPSHOT\common-boniu-1.0-SNAPSHOT.jar;C:\Users\wg\.m2\repository\com\fasterxml\jackson\core\jackson-annotations\2.14.2\jackson-annotations-2.14.2.jar;C:\Users\wg\.m2\repository\com\fasterxml\jackson\core\jackson-databind\2.14.2\jackson-databind-2.14.2.jar;C:\Users\wg\.m2\repository\com\fasterxml\jackson\core\jackson-core\2.14.2\jackson-core-2.14.2.jar;C:\Users\wg\.m2\repository\org\springframework\spring-context\6.0.9\spring-context-6.0.9.jar;C:\Users\wg\.m2\repository\org\apache\commons\commons-lang3\3.12.0\commons-lang3-3.12.0.jar;C:\Users\wg\.m2\repository\com\sevenme\remainingLifeTool\20240612\remainingLifeTool-20240612.jar
java.vm.vendor = Oracle Corporation
sun.arch.data.model = 64
user.variant = 
java.vendor.url = https://java.oracle.com/
java.vm.specification.version = 17
os.name = Windows 10
sun.java.launcher = SUN_STANDARD
user.country = CN
sun.boot.library.path = C:\Program Files\Java\jdk-17.0.1\bin
sun.java.command = io.github.boniu.test.lua.LuaExample
jdk.debug = release
sun.cpu.endian = little
user.home = C:\Users\wg
user.language = zh
java.specification.vendor = Oracle Corporation
java.version.date = 2021-10-19
java.home = C:\Program Files\Java\jdk-17.0.1
file.separator = \
java.vm.compressedOopsMode = Zero based
line.separator = 

java.vm.specification.vendor = Oracle Corporation
java.specification.name = Java Platform API Specification
user.script = 
sun.management.compiler = HotSpot 64-Bit Tiered Compilers
java.runtime.version = 17.0.1+12-LTS-39
user.name = wg
path.separator = ;
os.version = 10.0
java.runtime.name = Java(TM) SE Runtime Environment
file.encoding = UTF-8
java.vm.name = Java HotSpot(TM) 64-Bit Server VM
java.vendor.url.bug = https://bugreport.java.com/bugreport/
java.io.tmpdir = C:\Users\wg\AppData\Local\Temp\
java.version = 17.0.1
user.dir = H:\java-project\wg\springboot-3.1.0-java17
os.arch = amd64
java.vm.specification.name = Java Virtual Machine Specification
sun.os.patch.level = 
native.encoding = GBK
java.library.path = C:\Program Files\Java\jdk-17.0.1\bin;C:\WINDOWS\Sun\Java\bin;C:\WINDOWS\system32;C:\WINDOWS;E:\VMware\VMware Workstation\bin\;e:\Kingbase\ES\V8\KESRealPro\V008R006C008B0014\Interface\compress\v8r6_compress_win\kb_x64\release;C:\Program Files\Common Files\Siemens\Automation\Simatic OAM\bin;C:\Program Files\nodejs\node_global\cnpm;C:\Windows\System32;C:\Windows;C:\Windows\System32\wbem;C:\Windows\System32\WindowsPowerShell\v1.0\;C:\Windows\System32\OpenSSH\;C:\Program Files\MySQL\MySQL Server 8.0\bin;E:\apache-maven-3.6.3\bin;E:\flutter\flutter_windows_3.0.5-stable\flutter\bin;C:\Program Files\Git\cmd;C:\Program Files\nodejs\node_global\node_modules\yarn\bin;D:\Programs\Python\Python37\Lib\site-packages\jep;E:\gradle-7.3.2\bin;C:\Program Files\nodejs\;C:\Program Files (x86)\GnuPG\bin;C:\Program Files (x86)\NetSarang\Xftp 7\;C:\Program Files (x86)\NetSarang\Xshell 7\;C:\Program Files\Docker\Docker\resources\bin;C:\Program Files\Cloudflare\Cloudflare WARP\;E:\lua-5.4.2_Win64_bi;C:\MinGW\bin;E:\Python38;E:\Python38\Scripts;C:\Program Files\PuTTY\;C:\WINDOWS\system32;C:\WINDOWS;C:\WINDOWS\System32\Wbem;C:\WINDOWS\System32\WindowsPowerShell\v1.0\;C:\WINDOWS\System32\OpenSSH\;C:\Program Files\dotnet;C:\Program Files\Java\jdk-17.0.1\bin;C:\Program Files\Java\jdk-17.0.1\jre\bin;C:\MinGW\bin;C:\Program Files\JetBrains\IntelliJ IDEA Community Edition 2023.2\bin;;C:\Users\wg\AppData\Local\Programs\Microsoft VS Code\bin;C:\Users\wg\AppData\Roaming\npm;e:\webStorm 2020\bin;;e:\Program Files\JetBrains\PyCharm Community Edition 2022.1.3\bin;;E:\apache-jmeter-5.5\lib\ext\ApacheJMeter_core.jar;E:\apache-jmeter-5.5\lib\jorphan.jar;C:\Users\wg\.dotnet\tools;e:\JetBrains Rider 2023.3.4\bin;;e:\JetBrains\CLion 2024.1.4\bin;;d:\DataGrip 2024.2.2\bin;;;.
java.vm.info = mixed mode, sharing
java.vendor = Oracle Corporation
java.vm.version = 17.0.1+12-LTS-39
sun.io.unicode.encoding = UnicodeLittle
java.class.version = 61.0
```





下面是 `System.getProperties()` 返回的一些 **常见键值对**，以及它们的含义：

### **1️⃣ Java 运行环境相关**

| 键（Key）            | 说明                | 示例                                     |
| -------------------- | ------------------- | ---------------------------------------- |
| `java.version`       | Java 运行时环境版本 | `17.0.2`                                 |
| `java.vendor`        | Java 供应商         | `Oracle Corporation`                     |
| `java.vendor.url`    | Java 供应商官网     | `https://java.oracle.com/`               |
| `java.home`          | JDK/JRE 安装目录    | `C:\Program Files\Java\jdk-17`           |
| `java.class.version` | Java 类文件格式版本 | `61.0`（Java 17）                        |
| `java.class.path`    | 类路径（Classpath） | `C:\myproject\classes;C:\libs\mylib.jar` |

------

### **2️⃣ JVM 相关**

| 键（Key）                       | 说明           | 示例                       |
| ------------------------------- | -------------- | -------------------------- |
| `java.vm.version`               | JVM 版本号     | `17.0.2+8`                 |
| `java.vm.vendor`                | JVM 供应商     | `Oracle Corporation`       |
| `java.vm.name`                  | JVM 名称       | `OpenJDK 64-Bit Server VM` |
| `java.vm.specification.version` | JVM 规范版本   | `17`                       |
| `java.vm.specification.vendor`  | JVM 规范提供者 | `Oracle Corporation`       |

------

### **3️⃣ Java 规范**

| 键（Key）                    | 说明            | 示例                              |
| ---------------------------- | --------------- | --------------------------------- |
| `java.specification.version` | Java 规范版本   | `17`                              |
| `java.specification.vendor`  | Java 规范供应商 | `Oracle Corporation`              |
| `java.specification.name`    | Java 规范名称   | `Java Platform API Specification` |

------

### **4️⃣ 操作系统相关**

| 键（Key）    | 说明         | 示例                              |
| ------------ | ------------ | --------------------------------- |
| `os.name`    | 操作系统名称 | `Windows 10`                      |
| `os.arch`    | 操作系统架构 | `amd64`（64 位）或 `x86`（32 位） |
| `os.version` | 操作系统版本 | `10.0`                            |

------

### **5️⃣ 用户环境**

| 键（Key）   | 说明                     | 示例             |
| ----------- | ------------------------ | ---------------- |
| `user.name` | 当前用户的用户名         | `Administrator`  |
| `user.home` | 当前用户的主目录         | `C:\Users\Admin` |
| `user.dir`  | 当前 Java 进程的工作目录 | `C:\myproject`   |

------

### **6️⃣ 文件和路径**

| 键（Key）        | 说明           | Windows 示例 | Linux/macOS 示例 |
| ---------------- | -------------- | ------------ | ---------------- |
| `file.separator` | 文件路径分隔符 | `\`          | `/`              |
| `path.separator` | 路径分隔符     | `;`          | `:`              |
| `line.separator` | 换行符         | `\r\n`       | `\n`             |

**📌 说明：**

- **Windows 文件路径分隔符** 是 `\`，Linux/macOS 是 `/`。
- **Windows path.separator** 是 `;`，Linux/macOS 是 `:`（用于 `CLASSPATH` 和 `PATH` 变量）。
- **Windows line.separator** 是 `\r\n`（回车+换行），而 Linux/macOS 仅 `\n`。



# 14. static 关键字

在 Java 里，`static` 关键字的用途广泛，它能修饰类、方法、变量和代码块，下面分别介绍其具体作用：

### 静态变量（类变量）

- **定义与特点**：当使用 `static` 修饰变量时，该变量就成为静态变量，也被叫做类变量。静态变量为类的所有实例所共享，也就是说，无论创建了多少个该类的对象，静态变量都只有一份拷贝，存储于类的内存区域中。



### 静态方法

- **定义与特点**：使用 `static` 修饰的方法是静态方法。静态方法属于类，而非类的某个实例。调用静态方法时，无需创建类的对象，可以直接通过类名调用。静态方法只能访问静态变量和其他静态方法，不能访问实例变量和实例方法，因为实例变量和实例方法依赖于具体的对象。



### 静态内部类

- **定义与特点**：使用 `static` 修饰的内部类为静态内部类。静态内部类不依赖于外部类的实例，可以直接创建对象。静态内部类只能访问外部类的静态成员。



### 静态代码块

- **定义与特点**：静态代码块是用 `static` 关键字修饰的代码块，它在类加载时执行，且仅执行一次。静态代码块常被用于对静态变量进行初始化操作





# 15. 设计模式

Java 中常用的设计模式按照《GoF（Gang of Four）》分类，主要分为三大类：**创建型、结构型、行为型**。以下是各类中最常见、最实用的设计模式及说明：

------

## ✅ 一、创建型模式（5 种）

创建对象的方式，关注“如何实例化对象”。

| 模式名                               | 说明                           | 使用场景举例                                            |
| ------------------------------------ | ------------------------------ | ------------------------------------------------------- |
| **单例模式（Singleton）**            | 保证类只有一个实例             | 配置类、线程池、数据库连接池                            |
| **工厂方法模式（Factory Method）**   | 延迟到子类来决定要实例化哪个类 | 日志框架、数据库驱动                                    |
| **抽象工厂模式（Abstract Factory）** | 提供一组相关产品的创建方法     | UI 组件库切换主题                                       |
| **建造者模式（Builder）**            | 复杂对象的分步骤构建           | 创建配置复杂的对象（如 JSON 构造器、Lombok 的 builder） |
| **原型模式（Prototype）**            | 通过克隆创建新对象             | 创建对象成本高，复制更快（如 Excel 模板复制）           |



------

## ✅ 二、结构型模式（7 种）

描述类和对象如何组合在一起形成更大的结构。

| 模式名                      | 说明                             | 使用场景举例                         |
| --------------------------- | -------------------------------- | ------------------------------------ |
| **适配器模式（Adapter）**   | 将一个接口转化为客户端期望的接口 | 数据转换、旧系统兼容                 |
| **装饰器模式（Decorator）** | 动态添加对象功能，保持类不变     | Java IO 流（如 BufferedInputStream） |
| **代理模式（Proxy）**       | 控制对对象的访问                 | AOP、RPC、权限控制、缓存             |
| **外观模式（Facade）**      | 提供统一接口，隐藏子系统复杂性   | Controller 层调用多个 Service        |
| **桥接模式（Bridge）**      | 分离抽象和实现，使其独立变化     | 图形系统中形状和颜色组合             |
| **组合模式（Composite）**   | 树形结构处理整体和部分           | 文件系统、菜单结构                   |
| **享元模式（Flyweight）**   | 大量小对象共享内存               | 字符缓存、池技术（Integer.valueOf）  |



------

## ✅ 三、行为型模式（11 种）

关注对象之间的通信与职责分配。

| 模式名                                    | 说明                       | 使用场景举例             |
| ----------------------------------------- | -------------------------- | ------------------------ |
| **策略模式（Strategy）**                  | 定义一系列算法，可互换     | 支付方式选择、排序方式   |
| **模板方法模式（Template Method）**       | 定义算法骨架，子类实现细节 | 抽象类的标准流程定义     |
| **观察者模式（Observer）**                | 一方变更通知多方           | 事件监听、消息订阅       |
| **责任链模式（Chain of Responsibility）** | 请求链传递直到处理         | Servlet Filter、日志处理 |
| **命令模式（Command）**                   | 将请求封装成对象           | 操作撤销、事务处理       |
| **状态模式（State）**                     | 状态切换封装行为           | 状态机、流程审批         |
| **备忘录模式（Memento）**                 | 保存状态以便恢复           | 编辑器撤销               |
| **访问者模式（Visitor）**                 | 访问不同类的结构统一操作   | 编译器语法树处理         |
| **中介者模式（Mediator）**                | 用中介封装对象交互         | GUI 控件交互             |
| **解释器模式（Interpreter）**             | 自定义语言解释             | EL 表达式、正则解析      |
| **迭代器模式（Iterator）**                | 顺序访问集合               | `Iterator` 接口          |



------

## 🔥 最常见的实际用法（建议重点掌握）

| 模式         | Java 实际应用                       |
| ------------ | ----------------------------------- |
| **单例**     | `Runtime.getRuntime()`、Spring Bean |
| **工厂**     | `Calendar.getInstance()`、JDBC驱动  |
| **建造者**   | `StringBuilder`、Lombok `@Builder`  |
| **策略**     | 业务分支逻辑、if/else 优化          |
| **模板方法** | `HttpServlet.service()`             |
| **代理**     | JDK 动态代理、CGLIB、Spring AOP     |
| **观察者**   | Swing事件、Spring事件               |
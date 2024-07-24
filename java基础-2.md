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

```
java
复制代码
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





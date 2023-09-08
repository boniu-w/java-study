# 1. spring解决了哪些问题,如果不用spring,会有什么情况,

如果现在的开发使用了纯粹的JSP+Servlet+JDBC开发，代码的确是最简单的，但是带来的后续结果就是维护麻烦，而且不停的自己处理所有的操作关系，写出的反射应用，开发与维护成本太高了

随着技术的发展，有了更好的替代产品：

1.显示层：Jquery+JSON，简化数据传输，同时隐藏DOM操作的细节，或者使用bootstrap前台框架就行页面修饰（简化DIV+CSS修饰）；

 2.控制层：利用Struts2.x完成，提供了拦截器就行数据验证、提供了自动的数据类型的转换（VO的处理操作），方便用户就行数据上传操作、所有资源配置容易、调整路径配置容易。

3.数据层：使用Hibernate代替了已有的JDBC处理，利用了Hibernate提供的单表数据、数据查询、数据关联以及缓存机制等概念提升数据的操作性能与代码的开发简化；



但是随着这些问题的解决，发现业务层的问题越来越越多了，如果说要使用Struts2.x+Hibernate开发，虽然再控制层与数据层的操作上得到了大规模的改进，但是依然不可避免的要处理如下情况：

1.数据层依然需要定义操作接口标准，依然需要定义子类，依然要准备DAOFactory;

2.业务层依然需要负责关闭数据库，手工处理事务，利用工厂类调用数据层，定义自己的ServiceFactory;

3.在控制层里面依然需要业务层工厂类取得相应的对象信息。



spring 框架都做了哪些事?

1. 统一了应用程序的编程模型

Spring框架提供了一系列的组件和工具，用于统一应用程序的编程模型。这些组件包括：

- 核心容器：提供了依赖注入和控制反转功能，管理应用程序中的对象。
- 数据访问/集成层：提供了对JDBC、ORM框架、NoSQL数据库等多种数据访问技术的支持。
- Web应用开发框架：提供了一系列的组件和工具，用于开发Web应用程序，包括Web MVC框架、Web安全框架、RESTful Web服务框架等。
- AOP框架：实现了面向切面编程，将应用程序的横切逻辑（如日志记录、事务管理等）与业务逻辑分离开来，提高了应用程序的可维护性和可扩展性。
- 消息传递：提供了一系列的组件和工具，用于实现消息传递功能，包括JMS、AMQP等。

这些组件和工具都是为了简化应用程序的开发和维护，提高开发人员的效率。

2. 实现了面向切面编程

Spring框架通过AOP技术实现了面向切面编程。面向切面编程将应用程序的横切逻辑（如日志记录、事务管理等）与业务逻辑分离开来，从而提高了应用程序的可维护性和可扩展性。

Spring框架提供了一系列的AOP组件和工具，包括切面、切点、通知、连接点等。开发人员可以使用这些组件和工具来实现各种横切逻辑。

3. 提供了依赖注入和控制反转功能

Spring框架通过依赖注入和控制反转功能，解决了应用程序中对象之间的耦合关系，使得开发人员可以更加灵活地组装和管理应用程序中的对象。

依赖注入指的是将一个对象所依赖的其他对象通过构造函数、setter方法或者字段注入到该对象中。控制反转指的是将应用程序的对象的创建和管理交给Spring框架，而不是由开发人员手动创建和管理对象。

Spring框架提供了一系列的依赖注入和控制反转的方式，包括构造函数注入、setter注入、自动装配等。开发人员可以根据实际情况选择不同的方式来管理应用程序中的对象。

4. 支持多种数据访问技术

Spring框架提供了对多种数据访问技术的支持，包括JDBC、ORM框架、NoSQL数据库等。通过使用Spring框架，开发人员可以很方便地访问和操作不同类型的数据源，提高了开发效率。

5. 提供了Web应用开发框架

Spring框架提供了一系列的组件和工具，用于开发Web应用程序。这些组件和工具包括Web MVC框架、Web安全框架、RESTful Web服务框架等。

Web MVC框架是Spring框架中最核心的组件之一，用于开发基于MVC模式的Web应用程序。Web安全框架提供了一系列的组件和工具，用于实现Web应用程序的安全功能。RESTful Web服务框架提供了一种简单、轻量级、灵活的Web服务开发方式，非常适合于构建移动应用程序或者前端应用程序的后端服务。

6. 简化了测试

Spring框架提供了一系列的组件和工具，用于简化应用程序的测试工作。这些组件和工具包括Spring Test框架、Mock对象、JUnit、TestNG等。

Spring Test框架提供了一系列的注解和工具，用于简化测试代码的编写。Mock对象可以用来模拟应用程序中的对象，从而简化测试工作。JUnit和TestNG是两种常用的测试框架，Spring框架与这两种框架的集成非常紧密。

7. 提供了面向切面编程和声明式事务管理

Spring框架提供了面向切面编程和声明式事务管理功能，这两个功能可以有效地提高应用程序的可维护性和可扩展性。

面向切面编程将应用程序的横切逻辑（如日志记录、事务管理等）与业务逻辑分离开来，从而提高了应用程序的可维护性和可扩展性。声明式事务管理允许开发人员将事务管理逻辑与业务逻辑分离开来，从而提高了应用程序的可维护性和可扩展性。

总之，Spring框架是一个非常强大的开发框架，它可以帮助开发人员简化应用程序的开发和维护工作，提高开发人员的效率，同时也可以提高应用程序的可维护性和可扩展性

# 2. 怎么理解spring 容器 这个概念

它解决了横切面编程以及注入编程的窘境,各个框架之间的整合操作是一个的问题，而Spring解决了这个问题,也就是说最早的Spring提供的是一个工厂设计模式，同时也提供了不同开发框架的资源整合。

首先需要明确的是，spring本身提供的是自己的容器，如果是容器，那么容器可以负责维持对象的状态，或者是某一个操作的横切。而在整个spring里面提供了如下核心组件：

**1.容器核心组件：**

Beans :表示的是spring对所有Bean对象的管理，主要是包含了对象间的关系配置以及一些对象的实例化操作。

Core：包含了最底层的开发支持，例如：依赖的注入关系、资源文件的访问，数据类型的转换；

Context:提供的是一个完整的容器上下文，在这个上下文可以处理对象生命周期或者是事务；

SPEL：表达式语言模块，利用SPEL实现表达式语言的操作，以增强String的功能；

**2.切面编程：**

AOP：是整个spring的灵魂所在，利用切面编程来辅助性操作，例如：数据库关闭、事务处理；

Aspect: 表示的是切面编程的语法支持，该语法形式最早起源于Apache中的AspectJ组件；

Instrumetation: 是在JDK1.5之后增加的一个组件，主要用于检测JVM在运行中代码的动态处理过程；

Messaging：信息体系结构和协议支持；

**3.数据访问模块：**

JDBC:在整个Java之中，对于数据库的操作只有JDBC一种操作形式，所以在spring也提供了一种ORMapping框架，这个框架利用JDBC半原生完成；

ORM：ORMapping框架的处理操作，可以方便的整合JDO,Hibernate,IBatis,MyBatis等常见组件；

OXM：提供了一个对象与XML 文件之间的互相转换；

JMS:提供消息服务的支持；

Transactions : 表示在数据访问模块支持了事务的操作处理

**4.WEB支持模块：**

MVC：spring提供了自己的MVC实现（是目前最好的一种）；

Struts: spring 方便的支持Struts的管理（方便的是Struts2.x）;

Servlet: 自己负责处理MVC 的Servlet程序类；

websocket： sockjs WebSocket的实现,包括对 STOMP的支持



现在可以发现，整个Spring完全承办了一个项目能够独立开发，并且可以容纳其他框架同时存在的综合性框架，

不像struts只能负责控制层的，而hibernate只能负责数据层，spring全都可以就行负责。



**总结：**

spring中避免了关键字new造成耦合的问题；

spring本身就是一个工厂，不需要在编写工厂类了；

spring不在需要就行明确的引用关系传递，直接通过配置完成；

所有框架几乎都可以在spring中整合在一起使用；

spring编程 = Factory设计模式 + Proxy设计模式。



# 3. spring bean

## 1. Spring在创建Bean的过程中分为三步

1. 实例化，对应方法：`AbstractAutowireCapableBeanFactory`中的`createBeanInstance`方法
2. 属性注入，对应方法：`AbstractAutowireCapableBeanFactory`的`populateBean`方法
3. 初始化，对应方法：`AbstractAutowireCapableBeanFactory`的`initializeBean`

这些方法在之前源码分析的文章中都做过详细的解读了，如果你之前没看过我的文章，那么你只需要知道

1. 实例化，简单理解就是new了一个对象
2. 属性注入，为实例化中new出来的对象填充属性
3. 初始化，执行aware接口中的方法，初始化方法，完成`AOP`代理



![](.\img\create-bean.png)



## 2. bean 的生命周期

1. spring扫描scan指定的包下添加了Component等注解的类
2. 为这些类创建一个BeanDefinition的子类对象，这个子类对象包含这个类的一些基本信息如类名称、描述、构造器参数、是否需要延迟加载等等
3. 将这个子类对象放到Spring的单例池（是一个map）中，
4. 通过for循环获取BeanDefinition子类对象的构成的map中的子类对象，查看这个类的基本信息，进行验证，如果需要创建对象，就调用prestantiateSingletons将此类new出来，放到Spring单例池中。

## 3. 静态代码块的加载时机

静态代码块是类中一种特殊的代码块，它会在类被加载时执行，且只会执行一次。静态代码块可以用于进行类的初始化工作，例如初始化类的static成员变量。

静态代码块的加载时机可以分为以下几种情况：

1. 在类被主动引用时，如果还没有被初始化，则会先触发类的初始化工作，静态代码块也会被执行。
2. 当一个子类在初始化时，发现父类还没有被初始化，则会触发父类的初始化工作，静态代码块也会被执行。
3. Java虚拟机启动时，如果定义了main函数所在的类，则会触发这个类的初始化工作，静态代码块也会被执行。

需要注意的是，如果一个类的某个静态代码块抛出了异常，那么这个类的初始化工作就会中止，同时也会导致整个程序的崩溃。因此，在编写静态代码块时，应该确保代码的正确性并处理好可能出现的异常情况。

## 4. @PostConstruct 的加载时机

@PostConstruct方法的作用是在对象依赖注入完成后执行一些初始化工作，这个过程发生在对象初始化完成之后、初始化方法执行之前。

# 4. spring 配置



# 5. spring 问题

1. add springbootapplication to classpath



# 6. spring 替代

除了 Spring 框架，还有很多其他的 Java 框架可以用来构建 Web 应用程序或者企业级应用程序，其中一些比较流行的框架如下：

Apache Struts：Struts 是一个基于 MVC 模式的 Web 框架，它使用 JSP 和标签库作为视图层，以及 Action 类作为控制器层，可以方便地实现表单处理、验证和数据绑定等功能。

Hibernate：Hibernate 是一个 ORM 框架，它可以将 Java 对象映射到关系数据库中的表结构，从而方便地进行数据操作和管理，同时也支持缓存、事务管理等功能。

Apache Wicket：Wicket 是一个基于组件化编程的 Web 框架，它使用面向对象的编程风格，将 HTML 页面和 Java 代码完全分离，从而实现了良好的可维护性和可测试性。

Apache Tapestry：Tapestry 是一个基于组件化编程的 Web 框架，它使用类似于 HTML 的模板语言和 Java 代码实现了良好的视图层和控制器层的分离，同时还支持依赖注入、事件驱动等功能。

Play Framework：Play 是一个基于 Akka 和 Scala 语言的 Web 框架，它使用响应式编程的思想，实现了高并发、高可伸缩性和低延迟的 Web 应用程序。

这些框架各有特点和适用场景，可以根据具体的需求和技术栈进行选择和使用。





# 7. 注解分析

## 1. @Primary

   @Primary注解用于在Spring框架中标识默认的Bean。例如，在一个注入多个相同类型Bean的场景下，通过@Primary注解标识默认Bean可以方便地进行自动装配。

   举个例子，假设我们有两个实现了同一接口的类A和B，我们在另一个类C中注入这两个类：

   ```java
   @Component
   public class A implements MyInterface {
       // implementation
   }
   
   @Component
   @Primary
   public class B implements MyInterface {
       // implementation
   }
   
   @Component
   public class C {
       @Autowired
       private MyInterface myInterface;
   }
   ```

   在这个例子中，B被标识为默认的Bean，因此在C中自动注入的是B而不是A。如果没有@Primary注解，Spring会抛出一个NoUniqueBeanDefinitionException异常，因为Spring无法决定使用哪个Bean。

   总之，@Primary注解用于解决在同一类型Bean的多个实现中选择默认Bean的问题。

## 2. @Qualifier

   如果在配置文件中指定了多个数据源，那么在调用 `schedulerFactoryBean()` 方法并注入数据源参数时，Spring IoC 容器会根据参数类型和具体注入场景来自动选择匹配的数据源，并将它作为参数传递给方法。

   假设你在配置文件中同时配置了两个数据源，如下：

   ```
   # 第一个数据源
   spring.datasource.url=jdbc:mysql://localhost:3306/quartz1?useUnicode=true&characterEncoding=utf8&useSSL=false
   spring.datasource.driver-class-name=com.mysql.jdbc.Driver
   spring.datasource.username=root
   spring.datasource.password=root
   
   # 第二个数据源
   wg.datasource.url=jdbc:mysql://localhost:3306/quartz2?useUnicode=true&characterEncoding=utf8&useSSL=false
   wg.datasource.driver-class-name=com.mysql.jdbc.Driver
   wg.datasource.username=root
   wg.datasource.password=root
   ```

   你在调用 `schedulerFactoryBean()` 方法时，可以指定数据源参数的具体注入方式。比如，使用 `@Qualifier` 注解来指定要注入的数据源，如下：

   ```
   @Bean
   public SchedulerFactoryBean schedulerFactoryBean(@Qualifier("wgDataSource") DataSource dataSource) {
       // your configuration code
       return schedulerFactoryBean;
   }
   ```

   上述 `schedulerFactoryBean()` 方法中使用了 `@Qualifier("wgDataSource")` 注解来指定要使用名为 `wgDataSource` 的数据源。这就意味着，Spring IoC 容器会根据 `wgDataSource` 的具体配置，将其作为 `DataSource` 参数的具体实现注入到方法中。

   如果不使用 `@Qualifier` 注解指定数据源，那么Spring IoC 容器会默认选择 `@Primary` 注解标记的数据源。如果没有 `@Primary` 注解，那么容器会选择配置文件中排在前面的数据源作为默认数据源。

## 3. @PostConstruct

@PostConstruct 是Java EE中一种常用的注解，它用于标注一个方法，该方法会在依赖注入完成后执行。在对象生命周期中，@PostConstruct方法会在构造函数执行后、依赖注入完成后、初始化方法执行之前被调用。

具体来说，对象创建的过程可以分为以下几个阶段：

1. 加载类的过程：当Java虚拟机启动时，会通过类加载器加载应用程序中的类，在加载过程中，JVM会执行类的静态初始化块。

2. 创建对象的过程：在加载类之后，JVM会创建对象实例，也就是对象的初始化。在这个过程中，JVM会执行对象的构造函数、非静态初始化块以及实例变量的初始化。

3. 依赖注入的过程：在对象实例化完成之后，如果这个对象需要依赖其他对象，则会通过依赖注入的方式将它所依赖的其他对象注入到它自己中。

4. 初始化方法的调用：在依赖注入完成之后，如果对象中有@PostConstruct方法，则会调用这个方法进行对象的初始化工作。

5. 对象的使用：在初始化完成之后，对象可以被使用，在使用过程中，对象可能会被多次调用其方法，直到不再被需要，随后进入垃圾回收。

总之，@PostConstruct方法的作用是在对象依赖注入完成后执行一些初始化工作，这个过程发生在对象初始化完成之后、初始化方法执行之前。



```java
@Override
@Nullable
public Object postProcessBeforeInitialization(Object bean, String beanName) throws BeansException {
    if (!StringUtils.hasLength(beanName)) {
        return bean;
    }
    //调用@PostConstruct注解标注的方法
    invokeInitMethods(bean, beanName);
    return bean;
}
具体执行方法的代码：

public static void invokeInitMethods(Object target, String beanName) throws Throwable {
    if (target instanceof InitializingBean) {
        ((InitializingBean) target).afterPropertiesSet();
    }
    if (target instanceof SmartInitializingSingleton) {
        earlySingletonReferences.add(target);
        ((SmartInitializingSingleton) target).afterSingletonsInstantiated();
        earlySingletonReferences.remove(target);
    }
    String initMethodName = getInitMethodName(target);
    if (StringUtils.hasLength(initMethodName) &&
            !(target instanceof SmartInitializingSingleton && ((SmartInitializingSingleton) target).isEagerInitCalled())) {
        Method initMethod = ClassUtils.getMethodIfAvailable(target.getClass(), initMethodName);
        if (initMethod != null) {
            if (logger.isTraceEnabled()) {
                logger.trace("Invoking init method '" + initMethodName + "' on bean with name '" + beanName +
                        "'");
            }
            if (System.getSecurityManager() != null) {
                AccessController.doPrivileged((PrivilegedAction<Object>) () -> {
                    ReflectionUtils.makeAccessible(initMethod);
                    return null;
                }, getAccessControlContext());
            }
            else {
                ReflectionUtils.makeAccessible(initMethod);
            }
            ReflectionUtils.invokeMethod(initMethod, target);
        }
    }
}
```



# 8. 代码分析

## 1. quartz 关于定时任务

```java
        //quartz参数
        Properties prop = new Properties();
        prop.put("org.quartz.scheduler.instanceName", "Scheduler");
        prop.put("org.quartz.scheduler.instanceId", "AUTO");
        //线程池配置
        prop.put("org.quartz.threadPool.class", "org.quartz.simpl.SimpleThreadPool");
        prop.put("org.quartz.threadPool.threadCount", "20");
        prop.put("org.quartz.threadPool.threadPriority", "5");
        //JobStore配置
        prop.put("org.quartz.jobStore.class", "org.springframework.scheduling.quartz.LocalDataSourceJobStore");
        //集群配置
        prop.put("org.quartz.jobStore.isClustered", "true");
        prop.put("org.quartz.jobStore.clusterCheckinInterval", "15000");
        prop.put("org.quartz.jobStore.maxMisfiresToHandleAtATime", "1");
        
        prop.put("org.quartz.jobStore.misfireThreshold", "12000");
        prop.put("org.quartz.jobStore.tablePrefix", "QRTZ_");
        prop.put("org.quartz.jobStore.selectWithLockSQL", "SELECT * FROM {0}LOCKS UPDLOCK WHERE LOCK_NAME = ?");


这段代码用于配置 Quartz 调度器的属性，包括 org.quartz.scheduler、org.quartz.threadPool、org.quartz.jobStore 等。

org.quartz.scheduler.instanceName：指定 Scheduler 实例的名称。
org.quartz.scheduler.instanceId：指定 Scheduler 实例的唯一标识符，可以为 AUTO，这样 Quartz 就会自动生成一个唯一的标识符。
org.quartz.threadPool.class：指定线程池类名，这里指定的是 org.quartz.simpl.SimpleThreadPool。
org.quartz.threadPool.threadCount：指定线程池最大线程数。
org.quartz.threadPool.threadPriority：指定线程池中线程的优先级。
org.quartz.jobStore.class：指定 JobStore 实现类，这里指定为 org.springframework.scheduling.quartz.LocalDataSourceJobStore。LocalDataSourceJobStore 是 Quartz 提供的一个用于存储任务的实现，它将任务存储在数据库中。在 Spring 项目中使用时，可以利用 Spring 提供的 DataSource。
org.quartz.jobStore.isClustered：设置为 true，启用集群模式。
org.quartz.jobStore.clusterCheckinInterval：指定节点之间的检查间隔时间（毫秒）。
org.quartz.jobStore.maxMisfiresToHandleAtATime：指定每次检查的最大错过触发数量。
org.quartz.jobStore.misfireThreshold：指定允许的错过触发时间（毫秒）。
org.quartz.jobStore.tablePrefix：指定表前缀，这里指定为 QRTZ_。
org.quartz.jobStore.selectWithLockSQL：指定 SQL 查询语句，用于从数据库中检索一个已经被锁定的任务。
总体来说，这段代码就是配置 Quartz 调度器的各种属性，这些属性决定了调度器的行为和性能。
```





# 9. serverlet

## 1. severlet 作用: 

```
动态内容生成：Servlet是根据请求-响应模型工作的，它可以接收HTTP请求并且根据请求动态生成HTML或其他格式的数据来构建网页或者动态获取资源。Servlet通过执行Java代码来加工处理数据并生成动态内容，实现与客户端的交互。动态生成的内容可根据请求参数的不同进行细节上的区分，并能够支持多语言页面、国际化的知识库内容等。

并发处理：Servlet是运行在Web服务器中的Java程序，也是一个多线程应用程序。Servlet容器会为每个请求创建一个Servlet实例，每个实例都会有专属的线程执行它的服务方法。当有许多并发请求时，Servlet容器会根据最大线程数和线程池大小进行处理，同时还可以使用一些缓存机制来提高性能。

生命周期管理：Servlet的生命周期包括初始化、服务、和销毁三个阶段。初始化阶段可以用于创建对象、连接数据库和读取配置文件等操作；服务阶段就是处理具体的业务逻辑，比如查询、修改、删除等操作；销毁阶段用于清理Servlet的内存，关闭数据库等操作。开发者可以重写Servlet生命周期方法以执行自定义的操作，并可以通过注解替代web.xml配置方式来简化配置。

会话管理：在HTTP协议中，每个请求之间是相互独立的。为了维护用户的状态，Servlet容器引入了会话机制。当一个客户端发起请求时，如果没有会话，则Servlet容器会创建一个新的会话对象，并将其与一个唯一的sessionId（会话ID）关联起来。之后可以在整个会话周期中保存或获取需要存储或检索的信息，比如用户是否登录、用户角色等。开发者可以使用HttpSession接口来访问当前会话和当前会话中存储和检索数据。

综上所述，Servlet作为Java Web应用程序的核心组件，可以实现动态内容生成、处理并发请求、生命周期管理以及会话管理等任务。它提供了强大而灵活的功能，能够帮助开发者更加高效地构建Web应用程序。
```



## 2. 没有serverlet 会怎样

```
没有Servlet会对Java Web应用程序的开发和运行产生以下影响：

缺乏统一的组件模型：服务器端应用程序需要接收来自客户端的请求，并处理这些请求并返回响应结果。缺乏类似于Servlet的组件模型，将导致无法快捷地解析请求、生成响应数据，也无法动态地生成HTML页面。此外，每次写服务端程序都要先底层处理HTTP协议，这将是非常麻烦而且极其消耗时间。

处理并发请求麻烦：如果没有Servlet提供的并发处理能力，那么处理许多并发的请求将十分复杂和困难。并发管理涉及到线程池的配置，这个不仅很重要，而且很容易出错。

生命周期管理更加复杂：Web应用程序中，组件的创建和销毁是经过完善的生命周期管理过程的。但如果没有Servlet概念，则需要进行手动管理组件的生命周期，比如说从创建、初始化、运行到销毁环节必须都由编码人员去控制，否则就容易造成内存泄漏问题。

会话管理需要手动实现：如果没有Servlet容器HttpSession的管理，我们就需要手动在Http Session里面维持用户状态，保存用户信息，这不仅繁琐，而且易出错。

总之，没有Servlet将使得Java Web应用程序的开发和运行变得复杂和困难。因为Servlet为Web应用程序提供了规范化的基础架构和标准模型，便于开发人员在其基础上实现更加灵活、高效、安全、可维护的Web应用程序。同时，Servlet还提供了许多帮助开发人员快速开发Web应用程序的工具，包括数据缓存、调度管理、安全认证、分布式处理等，这些都是无法替代的特性。
```



# 10. Spring Boot 项目包内的 bean 注册到 Spring 容器中，项目依赖包中的 bean 不会被扫描和注册, 这句话什么意思
这句话指的是 Spring Boot 项目中，只有被包含在该项目源代码树下（即 `src/main/java` 或子目录下）的 Java 类才会自动扫描并注册为 Spring 容器中的 bean。而对于位于其他依赖库中的 Java 类（例如，Maven 依赖的 JAR 包、Project Gradle 依赖的模块等）则不会被自动扫描和注册。

这是因为 Maven 和 Gradle 采用了一种“隔离级别”的概念，即将代码分为编译级别和运行时级别。只有被编译成 Class 文件的代码才能够被打包进最终的生成文件中，运行时级别的代码则需要在类路径上进行配置。

因此，在 Spring Boot 项目中，只有通过 Gradle 或 Maven 将依赖库的代码 “提升” 到编译级别后，才能够让其成为 Spring 容器中的 bean。

当然，如果我们希望将依赖库中的类作为 Spring Bean 注册到容器中，也可以手动使用 `@Bean` 或者其他相应注解将其注册到 Spring 容器中；或者借助 Spring 的扩展组件，实现自定义的 Bean 自动装配逻辑。



# 11. autowired  和 resource 区别



@Resouce 不支持 

```
@nullable
```

不支持 required

```
required=false
```



不支持 optional 

```
private Optional<AliyunOssProperties> ossProperties;
```



# 12. 注解 



@AutoConfiguration @RequiredArgsConstructor @Import @EnableConfigurationProperties 详解 这几个注解

下面是对这几个注解的详细解释：

1. `@AutoConfiguration`：`@AutoConfiguration`是Spring Boot中的注解，用于自动配置应用程序的各个组件和功能。通过使用`@AutoConfiguration`注解，Spring Boot可以根据应用程序的依赖和配置来自动配置相应的Bean、组件和其他功能。它基于条件化配置和约定优于配置的原则，简化了应用程序的配置过程。
2. `@RequiredArgsConstructor`：`@RequiredArgsConstructor`是Lombok库提供的注解，用于自动生成带有`final`修饰的成员变量的构造函数。当在类上使用`@RequiredArgsConstructor`注解时，Lombok会根据类中的`final`成员变量生成一个包含这些成员变量的构造函数。这样可以简化手动编写构造函数的过程。
3. `@Import`：`@Import`是Spring框架中的注解，用于导入其他配置类或组件类到当前配置类中。通过使用`@Import`注解，可以将其他配置类或组件类添加到当前配置类的Bean定义中，使它们可以在容器中进行管理和使用。这在模块化开发和配置组合中非常有用。
4. `@EnableConfigurationProperties`：`@EnableConfigurationProperties`是Spring Boot中的注解，用于启用特定的配置属性类。配置属性类通常用于封装应用程序的配置信息，例如`application.properties`或`application.yml`文件中的属性。通过使用`@EnableConfigurationProperties`注解，可以将配置属性类与当前配置类关联起来，使得配置属性类的属性可以通过`@ConfigurationProperties`注解进行注入和使用。

请注意，以上解释是基于常见的用法和约定，具体的用法可能因具体的应用程序和上下文而有所不同。建议在实际开发中查阅相关文档和资料，以了解每个注解的具体行为和使用方式。



# 13. private static final long serialVersionUID = 1L; 具体作用是啥, 能不能不写


`serialVersionUID` 是 Java 中用于控制序列化版本的一个标识符。它的作用如下：

1. **版本控制：** 当您对一个类进行序列化（将对象转换为字节流以便存储或传输）时，Java 会自动生成一个 serialVersionUID。当您反序列化（将字节流还原为对象）时，Java 会检查被序列化的类的 serialVersionUID 是否与反序列化的类的 serialVersionUID 匹配。如果匹配，Java 认为类版本一致，可以成功反序列化。如果不匹配，就会抛出序列化版本不一致的异常（`InvalidClassException`）。
2. **防止反序列化问题：** 如果不显式声明 `serialVersionUID`，Java 会根据类的结构生成一个默认的 `serialVersionUID`。问题是，如果类的结构发生了变化（例如，添加、删除或修改了字段或方法），默认生成的 `serialVersionUID` 也会发生变化，导致无法正确反序列化以前版本的对象。

因此，为了控制类的序列化版本，以确保后续的类结构变化不会导致反序列化问题，通常会在类中显式声明一个 `serialVersionUID`。这个值可以是固定的，也可以是根据一定规则生成的。



如果您不写 `serialVersionUID`，Java 将为您生成一个默认值，但这样可能会导致潜在的反序列化问题。因此，建议在需要序列化的类中显式声明 `serialVersionUID`。如果您确定不会对类结构进行更改，并且不担心版本控制问题，那么可以选择不写 `serialVersionUID`，但这不是推荐的做法。
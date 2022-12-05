#### 1. spring解决了哪些问题,如果不用spring,会有什么情况,

如果现在的开发使用了纯粹的JSP+Servlet+JDBC开发，代码的确是最简单的，但是带来的后续结果就是维护麻烦，而且不停的自己处理所有的操作关系，写出的反射应用，开发与维护成本太高了

随着技术的发展，有了更好的替代产品：

1.显示层：Jquery+JSON，简化数据传输，同时隐藏DOM操作的细节，或者使用bootstrap前台框架就行页面修饰（简化DIV+CSS修饰）；

 2.控制层：利用Struts2.x完成，提供了拦截器就行数据验证、提供了自动的数据类型的转换（VO的处理操作），方便用户就行数据上传操作、所有资源配置容易、调整路径配置容易。

3.数据层：使用Hibernate代替了已有的JDBC处理，利用了Hibernate提供的单表数据、数据查询、数据关联以及缓存机制等概念提升数据的操作性能与代码的开发简化；



但是随着这些问题的解决，发现业务层的问题越来越越多了，如果说要使用Struts2.x+Hibernate开发，虽然再控制层与数据层的操作上得到了大规模的改进，但是依然不可避免的要处理如下情况：

1.数据层依然需要定义操作接口标准，依然需要定义子类，依然要准备DAOFactory;

2.业务层依然需要负责关闭数据库，手工处理事务，利用工厂类调用数据层，定义自己的ServiceFactory;

3.在控制层里面依然需要业务层工厂类取得相应的对象信息。

#### 2. 怎么理解spring 容器 这个概念

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



# 1. spring bean

Spring在创建Bean的过程中分为三步

1. 实例化，对应方法：`AbstractAutowireCapableBeanFactory`中的`createBeanInstance`方法
2. 属性注入，对应方法：`AbstractAutowireCapableBeanFactory`的`populateBean`方法
3. 初始化，对应方法：`AbstractAutowireCapableBeanFactory`的`initializeBean`

这些方法在之前源码分析的文章中都做过详细的解读了，如果你之前没看过我的文章，那么你只需要知道

1. 实例化，简单理解就是new了一个对象
2. 属性注入，为实例化中new出来的对象填充属性
3. 初始化，执行aware接口中的方法，初始化方法，完成`AOP`代理



![](.\img\create-bean.png)





# 2. spring 配置



# 3. spring 问题

1. add springbootapplication to classpath
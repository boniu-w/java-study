1. java -jar 包
2. localhost:8080 
3. 账户 密码 都是 sentinel



# 1. Sentinel: 分布式系统的流量防卫兵

## Sentinel 是什么？

随着微服务的流行，服务和服务之间的稳定性变得越来越重要。[Sentinel](https://sentinelguard.io/) 以流量为切入点，从流量控制、流量路由、熔断降级、系统自适应过载保护、热点流量防护等多个维度保护服务的稳定性



场景: 实时监控, 热点限流, 异常熔断, 线程数隔离, 速率控制, 流量控制, 来源访问控制

![](.\img\sentinel使用场景.png)

实时监控功能: 以在控制台中看到接入应用的单台机器秒级数据，甚至 500 台以下规模的集群的汇总运行情况。
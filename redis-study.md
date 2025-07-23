# 1. 命令



 

| command                            | description  |      |
| ---------------------------------- | ------------ | ---- |
| redis-server start                 |              |      |
| .\redis-benchmark.exe -n 100000 -q | 测试电脑性能 |      |
| sudo systemctl status redis        | 查询服务状态 |      |



# 2. 基本

Redis 只能支持六种数据类型 [string, hash, list, set, zset, hyperloglog] 的操作



1. redis 如果要存储对象的话, 对象必须  序列化 `implements Serializable`
2. 
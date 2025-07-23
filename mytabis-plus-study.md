# 1. 大坑

1. 大坑, mybatis plus, 查询时, 如果集合为 empty 则不会进行 sql 拼接, 会进行全表查询,

   而我的期望是, 当 ids 为空时, 不查询, 所以 这种写法

   ```java
   resultQueryWrapper.select("id")
                   .in(historyIdsForDelete.size() > 0, "history_inspection_id", historyIdsForDelete);
   ```

   应改为: 

   ```java
   if (historyIdsForDelete.size() > 0) {
               QueryWrapper<R> resultQueryWrapper = new QueryWrapper<>();
               resultQueryWrapper.select("id")
                       .in("history_inspection_id", historyIdsForDelete);
   }  
   ```

   



# 2. or 的用法

```java
QueryWrapper<MedicamentAssessJobCompletionReportEntity> wrapper = new QueryWrapper<>();
                wrapper.lambda()
                        .eq(operationZone != null, MedicamentAssessJobCompletionReportEntity::getOperationCompany, operationZone)
                        .and(e -> e.eq(MedicamentAssessJobCompletionReportEntity::getStatus, 6).or().eq(MedicamentAssessJobCompletionReportEntity::getStatus, 7))
                ;
```


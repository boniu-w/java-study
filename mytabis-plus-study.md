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





# 3. 对有空值 得排序

```java
private Page<SpMflDataTblDTO> getSpMflDataTblDTOPage(Integer pageNum, Integer size, String spInCompleteDataId) {
        QueryWrapper<SpMflDataTblEntity> queryWrapper = new QueryWrapper<>();
        queryWrapper.lambda().eq(SpMflDataTblEntity::getSpInCompleteDataId, spInCompleteDataId)
                .ge(SpMflDataTblEntity::getDefectLength, BigDecimal.valueOf(10d));
    	// 第一步排序：表达式结果为 0 的记录（非空值）会排在 1（空值）前面（因为 ASC 升序，0 < 1）；
		// 第二步排序：对非空值的记录，再按 MAXIMUM_DEFECT_DEPTH 本身降序排列。
        // queryWrapper.orderBy(true, true, "CASE WHEN MAXIMUM_DEFECT_DEPTH IS NULL THEN 1 ELSE 0 END"); // 将空值放后面, 且要放排序时第一位
        queryWrapper.lambda().orderByDesc(SpMflDataTblEntity::getMaximumDefectDepth);

        Page<SpMflDataTblEntity> page = new Page<>(pageNum, size);
        page = spMflDataTblDao.selectPage(page, queryWrapper);
        List<SpMflDataTblDTO> dataTblDTOS = spMflDataTblMapping.toDTOs(page.getRecords());
        Page<SpMflDataTblDTO> dtoPage = new Page<>(pageNum, size, page.getTotal());
        dtoPage.setRecords(dataTblDTOS);
        return dtoPage;
    }
```


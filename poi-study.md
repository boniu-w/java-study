# params

属性	类型	默认值	功能
titleRows	int	0	表格标题行数,默认0
headRows	int	1	表头行数,默认1
startRows	int	0	字段真正值和列标题之间的距离 默认0
startSheetIndex	int	0	开始读取的sheet位置,默认为0
needVerfiy	boolean	false	是否需要校验上传的Excel
needSave	boolean	false	是否需要保存上传的Excel
saveUrl	String	“upload/excelUpload”	保存上传的Excel目录,默认是 如 TestEntity这个类保存路径就是upload/excelUpload/Test/yyyyMMddHHmss* 保存名称上传时间五位随机数
importFields	String[]	null	导入时校验数据模板,是不是正确的Excel
verifyHanlder	IExcelVerifyHandler	null	校验处理接口,自定义校验
dataHanlder	IExcelDataHandler	null	数据处理接口,以此为主,replace,format都在这后面



# 对于 Boolean 类型的字段

> @Excel(name = "是否保温", replace = {"/`_`null", "是`_`true", "否_false"})
> private Boolean isInsulated;



无法 替换 为 null, 只有true 和 false 两个选项, 因此 只能用 string 类型



# 对于 bigdecimal 类型的字段

> @Excel(name = "管线工作压力[MPa]", replace = {"/_null"})
> private BigDecimal pipeOperatingPressure;



替换为null 时, 会发生异常

```
13:47:06.359 [main] ERROR c.a.e.excel.imports.CellValueService - Character n is neither a decimal digit number, decimal point, nor "e" notation exponential mark.
java.lang.NumberFormatException: Character n is neither a decimal digit number, decimal point, nor "e" notation exponential mark.
```

说明 代码解析 replace 时 解析的时 字符串'null' 的 首字母 'n', 

但 如果这么写 

> @Excel(name = "管线工作压力[MPa]", replace = {"/_-99999.99"})
> private BigDecimal pipeOperatingPressure;

就不报异常, 且可以正常转换


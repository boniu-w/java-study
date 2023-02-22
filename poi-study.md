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


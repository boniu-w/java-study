## 1. isInstance 与 instanceof

	@org.junit.Test
	public void test5(){
	
	    String s="123";
	    if(s.getClass().isInstance(String.class)){  // false
	        System.out.println("...........");
	    }
	
	    if(s instanceof String){  // true
	        System.out.println("<<<<<<<<<<<<<<<<<");
	    }
	}

### 2. java日期 转 sql日期

	Date date = new Date();
	Timestamp sqlDate = new Timestamp(date.getTime())  // 带 时分秒的
	
	java.sql.Date sqlDate1 = new java.sql.Date(date.getTime());  // 不带时分秒 只有年月日

#### 3. session 与  request
	1. session.setAttribute()和session.getAttribute()配对使用，作用域是整个会话期间，在所有的页面都使用这些数据的时候使用。
 	2. request.setAttribute()和request.getAttribute()配对使用，作用域是请求和被请求页面之间。request.setAttribute()是只在此action的
     下一个forward需要使用的时候使用；request.getAttribute()表示从request范围取得设置的属性，必须要先setAttribute设置属性，才能通过getAttribute来取得，
     设置与取得的为Object对象类型。其实表单控件中的Object的 name与value是存放在一个哈希表中的，所以在这里给出Object的name会到哈希表中找出对应它的value。
     setAttribute()的参数是String和Object。
 	3. request.getParameter()表示接收参数，参数为页面提交的参数。包括：表单提交的参数、URL重写(就是xxx?id=1中的id)传的参数等，因此这个并没有设置参数的
     方法(没有setParameter())，而且接收参数返回的不是Object，而是String类型。

### 4. 定义数组

	int[] array = {1,2,3};  // 用大括号 而不是 中括号

#### 5. 关于 除法 的一些

```java
@org.junit.Test
public void test2() {
	long round = Math.round((2 + 3) / 2);
	System.out.println(round);  // 2  为什么是2

	long l = Math.round(2.5);
	System.out.println(l); // 3
	double ceil = Math.ceil(2.5);
	System.out.println("ceil : " + ceil); // 3

	double b = (2d + 3d) / 2d;
	System.out.println("b : " + b);  // 2.5
    
}
```

#### 6. 所有整型包装类对象之间值的比较，全部使用 equals 方法比较。
​	说明：对于 Integer var = ? 在-128 至 127 范围内的赋值，Integer 对象是在 IntegerCache.cache 产
​	生，会复用已有对象，这个区间内的 Integer 值可以直接使用==进行判断，但是这个区间之外的所有数
​	据，都会在堆上产生，并不会复用已有对象，这是一个大坑，推荐使用 equals 方法进行判断。
​	
  -----------------------------浮点数之间的等值判断，基本数据类型不能用==来比较，包装数据类型不能用equals 来判断-----------------------------
​	反例：
​	 float a = 1.0f - 0.9f;
​	 float b = 0.9f - 0.8f;
​	 if (a == b) {
​	 // 预期进入此代码快，执行其它业务逻辑
​	 // 但事实上 a==b 的结果为 false
​	 }
​	 Float x = Float.valueOf(a);
​	 Float y = Float.valueOf(b);
​	 if (x.equals(y)) {
​	 // 预期进入此代码快，执行其它业务逻辑
​	 // 但事实上 equals 的结果为 false
​	 }
​	 
#### 7. 循环体内，字符串的连接方式，使用 StringBuilder 的 append 方法进行扩展
​	说明：下例中，反编译出的字节码文件显示每次循环都会 new 出一个 StringBuilder 对象，然后进行
​	append 操作，最后通过 toString 方法返回 String 对象，造成内存资源浪费。
​	

	反例：
	String str = "start"; 
	for (int i = 0; i < 100; i++) { 
	str = str + "hello"; 
	}

#### 8.  科学计数法

3.2e11  等同于 3.2 × 10¹¹

#### 9. 两个list 合并去重

1. 用set

2. java8以后可通过stream来完成

   ```java
   
   List<String> collect = Stream.of(listA, listB)
                         .flatMap(Collection::stream)
                         .distinct()
                         .collect(Collectors.toList());
   ```

   如果合并的是对象,注意重写equals和hashcode方法;

#### 10. 将map 转换成实体类

阿里的json包

```java
// bankFlowMap 转换 成BankFlow
BankFlow bankFlow = JSON.parseObject(JSON.toJSONString(bankFlowMap), BankFlow.class);
System.out.println("bankFlow : " + bankFlow);
```

#### 11. 转驼峰

```java
public String getHumpString(String string) {

        String[] s = string.split("_");
        StringBuilder stringBuilder = new StringBuilder(s[0]);

        for (int k = 0; k < s.length - 1; k++) {
            stringBuilder.append(s[k + 1].substring(0, 1).toUpperCase() + s[k + 1].substring(1));
        }

        return stringBuilder.toString();
    }
```



#### 12. 复制数组

```java
excelTitles = Arrays.copyOf(titles, titles.length);
```



#### 13. if else 与 if if 的区别

在if-else if分支结构中，如果前面有一个条件成立，则下面的分支结构将不会被执行；

而if-if分支结构则不然——只要条件成立就都会执行。

​	

#### 14. 实体类转map

<u>**JSONObject.toJavaObject(JSON.parseObject(string), Map.class);**</u>

```java
Iterator<BankFlow> bankFlowIterator = bankFlowList.iterator();
    while (bankFlowIterator.hasNext()){
        BankFlow next = bankFlowIterator.next();
        String s = JSON.toJSONString(next);
        Map map = JSONObject.toJavaObject(JSON.parseObject(s), Map.class);
        map.put("tick","resultData");

    }
```

#### 15. 枚举的应用

```java
String[] titles = {
          Title.TRANSACTION_SUBJECT.getFileName(),  // 交易主体
          Title.ACCOUNT_SUBJECT.getFileName(),  // 交易主体账号
          Title.CARD_ENTITY.getFileName(),  // 交易主体
          Title.RECOVERY_MARK.getFileName(),  // 收付标志
          Title.TRANSACTION_AMOUNT.getFileName(),  // 交易金额
          Title.TRANSACTION_DATE.getFileName(),  // 交易日期
          Title.COUNTER_PARTY.getFileName(),  // 交易对手
          Title.BALANCE_TRANSACTION.getFileName(),  // 交易后余额
          Title.TRANSACTION_BANK.getFileName(),  // 交易主体归属行
          Title.COUNTERPARTY_BANK.getFileName(),  // 交易对手归属行
          Title.PLACE_TRANSACTION.getFileName(),
          Title.TRADING_PLACE.getFileName(),
          Title.TRANSACTION_NUMBER.getFileName(),
          Title.CURRENCY.getFileName()
        };
```



#### 16. 自定义注解的应用

1. <u>**field.isAnnotationPresent(Excel.class)**</u>

````java
	// 获取所有带@Excel注解的成员属性字段
	Field[] fields = bankFlow.getClass().getDeclaredFields();
	ArrayList<Field> fieldArrayList = new ArrayList<>();
    for (Field field : fields) {
        if (field.isAnnotationPresent(Excel.class)) {
            fieldArrayList.add(field);
        }
    }

````

2. <u>**field.getAnnotation(Excel.class).name();**</u>

````java
    // 获取自定义注解@Excel 的 name 即@Excel(name = "交易主体")
	row = sheet.createRow(0);
    for (int i = 0; i < fieldArrayList.size(); i++) {
        cell = row.createCell(i);
        Field field = fieldArrayList.get(i);
        Excel annotation = field.getAnnotation(Excel.class);
        cell.setCellType(Cell.CELL_TYPE_STRING);
        cell.setCellValue(annotation.name());
    }
````

3. <u>**field.get(flow)**</u>

```java
    for (int i = 0; i < bankFlowList.size(); i++) {
        row = sheet.createRow(i + 1);
        BankFlow flow = bankFlowList.get(i);
        for (int j = 0; j < fieldArrayList.size(); j++) {
            cell = row.createCell(j);
            Field field = fieldArrayList.get(j);
            field.setAccessible(true);
            if (field.get(flow) instanceof String) {
                cell.setCellValue((String) field.get(flow));
            }
        }
    }
```

#### 17. 增强for 与 for 的区别

1. 增强for循环和iterator遍历的效果是一样的，**增强for循环的内部也就是调用iteratoer实现的(可以查看编译后的文件)**。增强for循环有些缺点，例如不能在增强循环里动态的删除集合内容，不能获取下标等。
2. ArrayList由于使用数组实现，**因此下标明确，最好使用普通循环**。
3. 而对于 LinkedList 由于获取一个元素，要从头开始向后找，**因此建议使用增强for循环，也就是iterator**。

### 18. 关于数据库的日期 类型 和 java的 数据类型的对应

oracle 的时间戳 和 java 的 String 也可以对应



#### 19. 格式字符

1. ```tex
    %d  表示按整型数据的实际长度输出数据。
    %c  用来输出一个字符。
    %s  用来输出一个字符串。
    %x  表示以十六进制数形式输出整数。
   
   
   
   ```

   




# 1. isInstance 与 instanceof

	@org.junit.Test
	public void test5(){
	
	    String s="123";
	    if(s.getClass().isInstance(String.class)){  // false
	        System.out.println("...........");
	    }
	    
		if (String.class.isInstance(s)){  // true
	            System.out.println(true);
	    }
	    
	    if(s instanceof String){  // true
	        System.out.println("<<<<<<<<<<<<<<<<<");
	    }
	}

# 2. java日期 转 sql日期

	Date date = new Date();
	Timestamp sqlDate = new Timestamp(date.getTime())  // 带 时分秒的
	
	java.sql.Date sqlDate1 = new java.sql.Date(date.getTime());  // 不带时分秒 只有年月日

# 3. session 与  request

	1. session.setAttribute()和session.getAttribute()配对使用，作用域是整个会话期间，在所有的页面都使用这些数据的时候使用。
	2. request.setAttribute()和request.getAttribute()配对使用，作用域是请求和被请求页面之间。request.setAttribute()是只在此action的
	 下一个forward需要使用的时候使用；request.getAttribute()表示从request范围取得设置的属性，必须要先setAttribute设置属性，才能通过getAttribute来取得，
	 设置与取得的为Object对象类型。其实表单控件中的Object的 name与value是存放在一个哈希表中的，所以在这里给出Object的name会到哈希表中找出对应它的value。
	 setAttribute()的参数是String和Object。
	4. request.getParameter()表示接收参数，参数为页面提交的参数。包括：表单提交的参数、URL重写(就是xxx?id=1中的id)传的参数等，因此这个并没有设置参数的
	 方法(没有setParameter())，而且接收参数返回的不是Object，而是String类型。

# 4. 数组

## 1. 定义数组

	int[] array = {1,2,3};  // 用大括号 而不是 中括号
	String[] command = new String[10];




## 2. 



# 5. 关于 除法 的一些

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

# 6. 所有整型包装类对象之间值的比较，全部使用 equals 方法比较。

	说明：对于 Integer var = ? 在-128 至 127 范围内的赋值，Integer 对象是在 IntegerCache.cache 产生，会复用已有对象，这个区间内的 Integer 值可以直接使用==进行判断，但是这个区间之外的所有数据，都会在堆上产生，并不会复用已有对象，这是一个大坑，推荐使用 equals 方法进行判断。

--**浮点数之间的等值判断，基本数据类型不能用==来比较，包装数据类型不能用equals 来判断**--
**Java中的解决方法，是通过设立一个阈值来消除计算机计算所带来的误差引起的误差**

反例：
	 float a = 1.0f - 0.9f;
	 float b = 0.9f - 0.8f;
	 if (a == b) {
	 // 预期进入此代码快，执行其它业务逻辑
	 // 但事实上 a==b 的结果为 false
	 }
	 Float x = Float.valueOf(a);
	 Float y = Float.valueOf(b);
	 if (x.equals(y)) {
	 // 预期进入此代码快，执行其它业务逻辑
	 // 但事实上 equals 的结果为 false
	 }



```java
public void floatJudge() {
        float a = 1.0f - 0.9f;
        float b = 0.9f - 0.8f;

        System.out.println(a);
        System.out.println(b);

        if (a == b) {
            System.out.println("a == b");
        } else {
            System.out.println("a != b");
        }

        final double e = 1E-14;
        BigDecimal bigDecimal=new BigDecimal(e);
        System.out.println("bigDecimal -> "+bigDecimal);

        float abs = Math.abs(a - b);
        System.out.println("abs -> "+abs);


        final double e2 = 1E-7;

        if (Math.abs(a - b) < e2) {
            System.out.println("a 终于 == b");
        } else {
            System.out.println("a 还是 != b");
        }


    }
```







​	 

# 7. 循环体内，字符串的连接方式，使用 StringBuilder 的 append 方法进行扩展

	说明：下例中，反编译出的字节码文件显示每次循环都会 new 出一个 StringBuilder 对象，然后进行
	append 操作，最后通过 toString 方法返回 String 对象，造成内存资源浪费。


	反例：
	String str = "start"; 
	for (int i = 0; i < 100; i++) { 
	str = str + "hello"; 
	}

# 8.  科学计数法

1. 3.2e11  等同于 3.2 × 10¹¹

2. excel里,导入的数据到excel里显示这样 6.22848E+18 , 但它并不等同于java 的科学计数法,

在excel里 6.22848E+18 = 6228480028317730000



# 9. List

## 1. 两个list 合并去重

1. 用set

2. java8以后可通过stream来完成

   ```java
   List<String> collect = Stream.of(listA, listB)
                         .flatMap(Collection::stream)
                         .distinct()
                         .collect(Collectors.toList());
   ```

   如果合并的是对象,注意重写equals和hashcode方法;

## 2. 找出 两个list 相同 元素



## 3. 存储null

treeset 不能存储null, 会出现空指针

# 10. 将map 转换成实体类

阿里的json包

```java
// bankFlowMap 转换 成BankFlow
BankFlow bankFlow = JSON.parseObject(JSON.toJSONString(bankFlowMap), BankFlow.class);
System.out.println("bankFlow : " + bankFlow);
```

# 11. 转驼峰

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



# 12. 复制数组

```java
excelTitles = Arrays.copyOf(titles, titles.length);
```



# 13. if else 与 if if 的区别

在if-else if分支结构中，如果前面有一个条件成立，则下面的分支结构将不会被执行；

而if-if分支结构则不然——只要条件成立就都会执行。

​	

# 14. 实体类转map

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

# 15. 枚举的应用

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



# 16. 自定义注解的应用

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

# 17. 增强for 与 for 的区别

1. 增强for循环和iterator遍历的效果是一样的，**增强for循环的内部也就是调用iteratoer实现的(可以查看编译后的文件)**。增强for循环有些缺点，例如不能在增强循环里动态的删除集合内容，不能获取下标等。
2. ArrayList由于使用数组实现，**因此下标明确，最好使用普通循环**。
3. 而对于 LinkedList 由于获取一个元素，要从头开始向后找，**因此建议使用增强for循环，也就是iterator**。

# 18. 关于数据库的日期 类型 和 java的 数据类型的对应

oracle 的时间戳 和 java 的 String 也可以对应



# 19. 格式字符, 符号

1. ```tex
   %d  表示按整型数据的实际长度输出数据。
   %c  用来输出一个字符。
   %s  用来输出一个字符串。
   %x  表示以十六进制数形式输出整数。
   ```





| 字符         | 16进制  | unicode | 作用                                                         |
| ------------ | ------- | ------- | ------------------------------------------------------------ |
| \n (newline) | \\u000A |         | 换行,光标往下一行                                            |
| \t           |         |         | 补全当前字符串长度到8的整数倍，最少1个最多8个空格,补多少要看你\t前字符串长度,比如当前字符串长度10，那么\t后长度是16，也就是补6个空格,如果当前字符串长度12，此时\t后长度是16，补4个空格 |
| \r (return)  | \\u000D |         | 回车,光标回到本行开头                                        |
| \f           |         |         | 换页                                                         |
|              |         | \\u0020 | 半角空格                                                     |
|              |         | \\u3000 | 全角空格                                                     |



# 20. continue

continue: 中断本次循环,而不是break 中断循环;

```java
public void continueTest() {
        int s = 0;

        for (int i = 0; i < 10; i++) {
            if (i == 2) {
                s -= 10;
                continue;
            }

            if (i == 3) {
                System.out.println("true");
                continue;
            }

            s += 10;
        }

        System.out.println(s);

    }
```

如果if 成立 则 中断本次循环,进入下一次循环;

执行结果:

```java
true
70
```



# 21. 读取json文件

把.json文件 放到 static下面 , 建个文件放进去也可以

```java
	@RequestMapping(value = "/testJson")
    @ResponseBody
    public void testJson() {
        ClassPathResource resource = new    ClassPathResource("static/json/nongHangTitles.json");
        try {
            if (resource.exists()) {

                File file = resource.getFile();
                String s = FileUtils.readFileToString(file);

                System.out.println(s);


                JSONArray jsonArray = JSON.parseArray(s);
                System.out.println(jsonArray.toString());



            }

        } catch (IOException e) {
            e.printStackTrace();
        }
    }
```



# 22. 配置

1. 要想application.properties 文件里有mybatis-plus 的提示 需要这个

```xml
<!--mybatis-plus自动的维护了mybatis以及mybatis-spring的依赖，在springboot中这三者不能同时的出现，避免版本的冲突，表示：跳进过这个坑-->
        <dependency>
            <groupId>com.baomidou</groupId>
            <artifactId>mybatis-plus-boot-starter</artifactId>
            <version>3.1.1</version>
        </dependency>
```



<div style="page-break-after:always"></div>



# 23. 乘法 除法 的移位操作

\>> 是带符号右移，若操作数是正数，则高位补“0”，若操作数是负数，则高位补“1”.

<< 将操作数向左边移动，并且在低位补0.

\>>> 是无符号右移，无论操作数是正数还是负数，在高位都补“0”



乘法: 左移

除法: 右移

```java
/****************************************************************
     * 乘法 除法 的移位
     * @author: wg
     * @time: 2020/6/24 10:36
     ****************************************************************/
    @RequestMapping(value = "/transpose")
    @ResponseBody
    public void transpose() {

      int a = 10;  // 二进制 1010

        int i = a << 1;
        System.out.println(i);  // 20 = a * 2的1次方
        System.out.println("20 的二进制表示 : " + Integer.toBinaryString(i));


        int i1 = a << 2;
        System.out.println(i1);  // 40 = a * 2的2次方


        int i2 = a << 3;
        System.out.println(i2);  // 80 = a * 2的3次方


        int i3 = a * (a ^ 3);  // ^  二进制的异或符
        System.out.println(i3);

        // a*3;
        int i4 = (a << 1) + a;
        System.out.println(i4);


        /* 除法 */
        int i5 = a >> 3;
        System.out.println(i5); // 1

        int i6 = (a >> 1) - a;
        System.out.println(i6);


        /*  与运算符用符号 "&"  */
        int i7 = a & 2;
        int i9 = a % 4;
        System.out.println("i7 ->= " + i7 + "   #  i9 ->= " + i9);  // 2

        int i8 = a & 1;
        int i10 = a % 2;
        System.out.println("i8 ->= " + i8 + "   #  i10->  " + i10);  // 0
        System.out.println();

        int n = 1;
        int m = (n << 3) + 2;
        System.out.println("m - > " + m);
        System.out.println(Integer.toBinaryString(m));
        System.out.println();
        int l = Integer.parseInt("10", 3);
        System.out.println("三进制数 l 转成 10进制 -> " + l);  // 3
        int p = Integer.parseInt("1010", 2);
        System.out.println("二进制数 p 转成 10进制 -> " + Integer.parseInt("1010", 2));  //  10 

        // 负数的 左右 移位
        int k = -1;
        System.out.println("k 的二进制表示 : " + Integer.toBinaryString(k));
        int h = k << 1;
        System.out.println(h);
        System.out.println("h 的二进制表示 : " + Integer.toBinaryString(h));

    }
```



另:

符号 " ^ " 是 二进制 的异或 操作



负数的左移,右移:

1. 负数的左移：和整数左移一样，在负数的二进制位右边补0，一个数在左移的过程中会有正有负的情况，所以切记负数左移不会特殊处理符号位。如果一直左移，最终会变成0
2. 负数的右移：需要保持数为负数，所以操作是对负数的二进制位左边补1。如果一直右移，最终会变成-1，即(-1)>>1是-1。





java 转进制 源码

```java
public static int parseInt(String s, int radix)
                throws NumberFormatException{
        /*
         * WARNING: This method may be invoked early during VM initialization
         * before IntegerCache is initialized. Care must be taken to not use
         * the valueOf method.
         */

        if (s == null) {
            throw new NumberFormatException("null");
        }

        if (radix < Character.MIN_RADIX) {
            throw new NumberFormatException("radix " + radix +
                                            " less than Character.MIN_RADIX");
        }

        if (radix > Character.MAX_RADIX) {
            throw new NumberFormatException("radix " + radix +
                                            " greater than Character.MAX_RADIX");
        }

        int result = 0;
        boolean negative = false;
        int i = 0, len = s.length();
        int limit = -Integer.MAX_VALUE;
        int multmin;
        int digit;

        if (len > 0) {
            char firstChar = s.charAt(0);
            if (firstChar < '0') { // Possible leading "+" or "-"
                if (firstChar == '-') {
                    negative = true;
                    limit = Integer.MIN_VALUE;
                } else if (firstChar != '+')
                    throw NumberFormatException.forInputString(s);

                if (len == 1) // Cannot have lone "+" or "-"
                    throw NumberFormatException.forInputString(s);
                i++;
            }
            multmin = limit / radix;
            while (i < len) {
                // Accumulating negatively avoids surprises near MAX_VALUE
                digit = Character.digit(s.charAt(i++),radix);
                if (digit < 0) {
                    throw NumberFormatException.forInputString(s);
                }
                if (result < multmin) {
                    throw NumberFormatException.forInputString(s);
                }
                result *= radix;
                if (result < limit + digit) {
                    throw NumberFormatException.forInputString(s);
                }
                result -= digit;
            }
        } else {
            throw NumberFormatException.forInputString(s);
        }
        return negative ? result : -result;
    }
```



# 24. 与运算符  " & " : 两个操作数中位都为1，结果才为1，否则结果为0，

两个操作数中位都为1，结果才为1，否则结果为0，例如下面的程序段

```java
/****************************************************************
     * 运算符 测试
     * @author: wg
     * @time: 2020/6/28 11:52
     ****************************************************************/
    public void operatorTest() {

        /* 与运算符 "&" */
        int a = 129;
        int b = 128;
        int i = a & b;
        System.out.println(i); // 128


    }
```

分析: 

a 的二进制 10000001, b 的二进制 10000000,根据运算规律,相同为 1 不同为 0 ,所以结果是 128;

```
对两个表达式执行按位“与”。

result = expression1 & expression2

参数
result

任何变量。

expression1

任何表达式。

expression2

任何表达式。

说明
& 运算符查看两个表达式的二进制表示法的值，并执行按位“与”操作。该操作的结果如下所示：

0101   (expression1)
1100   (expression2)
----
0100   (result)
任何时候，只要两个表达式的某位都为 1，则结果的该位为 1。否则，结果的该位为 0。
```



2. 或运算符 " | "

两个只要有一个为1,那么结果就为1 , 否则为0 ;

3. 非运算符 " ~ "

如果位为0，结果是1，如果位为1，结果是0，

4. 异或运算符 " ^ "

   两个操作数的位中，相同则结果为0，不同则结果为1

   

另: 在if 条件中时 , 或运算符 " | ", 

	当第一个条件为真,后面的条件依然会执行,而短路或 " || ",只要第一个条件为真,则后面的条件就不再执行;



# 25. get 与 post

Http定义了与服务器交互的不同方法，最基本的方法有4种，分别是GET，POST，PUT，DELETE。URL全称是资源描述符，我们可以这样认为：一个URL地址，它用于描述一个网络上的资源，而HTTP中的GET，POST，PUT，DELETE就对应着对这个资源的查，改，增，删4个操作。到这里，大家应该有个大概的了解了，GET一般用于获取/查询资源信息，而POST一般用于更新资源信息。

　　**1.根据HTTP规范，GET用于信息获取，而且应该是安全的和幂等的**。

　　(1).所谓安全的意味着该操作用于获取信息而非修改信息。换句话说，GET 请求一般不应产生副作用。就是说，它仅仅是获取资源信息，就像数据库查询一样，不会修改，增加数据，不会影响资源的状态。

  * 注意：这里安全的含义仅仅是指是非修改信息。

　　(2).**<u>幂等的意味着对同一URL的多个请求应该返回同样的结果</u>**。这里我再解释一下**幂等**这个概念：

　　**幂等**（idempotent、idempotence）是一个数学或计算机学概念，常见于抽象代数中。
　　幂等有一下几种定义：
　　对于单目运算，如果一个运算对于在范围内的所有的一个数多次进行该运算所得的结果和进行一次该运算所得的结果是一样的，那么我们就称该运算是幂等的。比如绝对值运算就是一个例子，在实数集中，有abs(a)=abs(abs(a))。
　　对于双目运算，则要求当参与运算的两个值是等值的情况下，如果满足运算结果与参与运算的两个值相等，则称该运算幂等，如求两个数的最大值的函数，有在在实数集中幂等，即max(x,x) = x。

看完上述解释后，应该可以理解GET幂等的含义了。

　　但在实际应用中，以上2条规定并没有这么严格。引用别人文章的例子：比如，新闻站点的头版不断更新。虽然第二次请求会返回不同的一批新闻，该操作仍然被认为是安全的和幂等的，因为它总是返回当前的新闻。从根本上说，如果目标是当用户打开一个链接时，他可以确信从自身的角度来看没有改变资源即可。

　　**2**.根据HTTP规范，POST表示可能修改变服务器上的资源的请求。继续引用上面的例子：还是新闻以网站为例，读者对新闻发表自己的评论应该通过POST实现，因为在评论提交后站点的资源已经不同了，或者说资源被修改了。

 

　　上面大概说了一下HTTP规范中GET和POST的一些原理性的问题。但在实际的做的时候，很多人却没有按照HTTP规范去做，导致这个问题的原因有很多，比如说：

　　**1**.很多人贪方便，更新资源时用了GET，因为用POST必须要到FORM（表单），这样会麻烦一点。

　　**2**.**对资源的增，删，改，查操作，其实都可以通过GET/POST完成，不需要用到PUT和DELETE**。

　　**3**.另外一个是，早期的Web MVC框架设计者们并没有有意识地将URL当作抽象的资源来看待和设计，所以导致一个比较严重的问题是**传统的Web MVC框架基本上都只支持GET和POST两种HTTP方法，而不支持PUT和DELETE方法**。

  * 简单解释一下MVC：MVC本来是存在于Desktop程序中的，M是指数据模型，V是指用户界面，C则是控制器。使用MVC的目的是将M和V的实现代码分离，从而使同一个程序可以使用不同的表现形式。

　　以上3点典型地描述了老一套的风格（没有严格遵守HTTP规范），随着架构的发展，现在出现REST(Representational State Transfer)，一套支持HTTP规范的新风格，这里不多说了，可以参考《RESTful Web Services》。

 

　　说完原理性的问题，我们再从表面现像上面看看GET和POST的区别：

　　**1**.GET请求的数据会附在URL之后（就是把数据放置在HTTP协议头中），以?分割URL和传输数据，参数之间以&相连，如：login.action?name=hyddd&password=idontknow&verify=%E4%BD%A0%E5%A5%BD。如果数据是英文字母/数字，原样发送，如果是空格，转换为+，**如果是中文/其他字符，则直接把字符串用BASE64加密**，得出如：%E4%BD%A0%E5%A5%BD，其中％XX中的XX为该符号以**16进制表示的ASCII**。

　　**POST把提交的数据则放置在是HTTP包的包体中。**

　　**2**."GET方式提交的数据最多只能是1024字节，理论上POST没有限制，可传较大量的数据，IIS4中最大为80KB，IIS5中为100KB"？？！

　　以上这句是我从其他文章转过来的，其实这样说是错误的，不准确的：

　　(1).首先是"GET方式提交的数据最多只能是1024字节"，因为GET是通过URL提交数据，那么GET可提交的数据量就跟URL的长度有直接关系了。**而实际上，URL不存在参数上限的问题**，HTTP协议规范没有对URL长度进行限制。这个限制是特定的浏览器及服务器对它的限制。IE对URL长度的限制是2083字节(2K+35)。对于其他浏览器，如Netscape、FireFox等，理论上没有长度限制，其限制取决于操作系统的支持。

　　注意这是限制是整个URL长度，而不仅仅是你的参数值数据长度。[见参考资料5]

Http get方法提交的数据大小长度并没有限制，Http协议规范没有对URL长度进行限制。 目前说的get长度有限制，是特定的浏览器及服务器对它的限制。

	IE：对URL的最大限制为2083个字符，若超出这个数字，提交按钮没有任何反应。
	
	Firefox：对Firefox浏览器URL的长度限制为：65536个字符。
	
	Safari：URL最大长度限制为80000个字符。
	
	Opera：URL最大长度限制为190000个字符。
	
	Google(chrome)：URL最大长度限制为8182个字符。
	
	Apache(Server)：能接受的最大url长度为8192个字符（这个准确度待定？？？）
	
	Microsoft Internet Information Server(IIS)：n能接受最大url的长度为16384个字符

　　(2).**理论上讲，POST是没有大小限制的**，HTTP协议规范也没有进行大小限制，说“POST数据量存在80K/100K的大小限制”是不准确的，POST数据是没有限制的，起限制作用的是服务器的处理程序的处理能力。

　　对于ASP程序，Request对象处理每个表单域时存在100K的数据长度限制。但如果使用Request.BinaryRead则没有这个限制。

　　由这个延伸出去，对于IIS 6.0，微软出于安全考虑，加大了限制。我们还需要注意：

　　　　 1).IIS 6.0默认ASP POST数据量最大为200KB，每个表单域限制是100KB。
　　　　 2).IIS 6.0默认上传文件的最大大小是4MB。
　　　　 3).IIS 6.0默认最大请求头是16KB。
　　IIS 6.0之前没有这些限制。[见参考资料5]

　　所以上面的80K，100K可能只是默认值而已(注：关于IIS4和IIS5的参数，我还没有确认)，但肯定是可以自己设置的。由于每个版本的IIS对这些参数的默认值都不一样，具体请参考相关的IIS配置文档。

　　**3**.在ASP中，服务端获取GET请求参数用Request.QueryString，获取POST请求参数用Request.Form。在JSP中，用request.getParameter(\"XXXX\")来获取，虽然jsp中也有request.getQueryString()方法，但使用起来比较麻烦，比如：传一个test.jsp?name=hyddd&password=hyddd，用request.getQueryString()得到的是：name=hyddd&password=hyddd。在PHP中，可以用$_GET和$_POST分别获取GET和POST中的数据，而$_REQUEST则可以获取GET和POST两种请求中的数据。值得注意的是，JSP中使用request和PHP中使用$_REQUEST都会有隐患，这个下次再写个文章总结。

　　**4**.POST的安全性要比GET的安全性高。注意：这里所说的安全性和上面GET提到的“安全”不是同个概念。上面“安全”的含义仅仅是不作数据修改，而这里安全的含义是真正的Security的含义，比如：通过GET提交数据，用户名和密码将明文出现在URL上，因为(1)登录页面有可能被浏览器缓存，(2)其他人查看浏览器的历史纪录，那么别人就可以拿到你的账号和密码了，除此之外，使用GET提交数据还可能会造成Cross-site request forgery攻击。

　　总结一下，Get是向服务器发索取数据的一种请求，而Post是向服务器提交数据的一种请求，在FORM（表单）中，Method默认为"GET"，实质上，GET和POST只是发送机制不同，并不是一个取一个发！

　　

GET VS POST扩展：

1、多数浏览器对于POST采用两阶段发送数据的，先发送请求头，再发送请求体，即使参数再少再短，也会被分成两个步骤来发送（相对于GET）,也就是第一步发送header数据，第二部再发送body部分。Http是应用层的协议，而再传输层有些情况TCP会出现两次连结的过程，http协议本身不保存状态信息，一次请求一次响应。对于TCP而言，通信次数越多反而可靠性越低，能在一次连结中传输完需要的信息是最可靠的，所以尽量使用GET请求来减少网络耗时。如果通信时间增加，这段时间客户端于服务器端一直保持连接状态，在服务器侧负载可能会增加，可靠性会下降。

2、GET请求能够被cache，GET请求能够被保存在浏览器的浏览历史里面（密码等重要数据GET提交，别人查看历史记录，就可以直接看到这些私密数据）POST不进行缓存。

3、GET参数是带在URL后面，传统IE中URL的最大可用长度为2048字符，其他浏览器对URL长度限制实现上有所不同。POST请求无长度限制（目前理论上是这样）。

4、GET提交的数据大小，不同浏览器的限制不同，一般在2k-8k之间，POST提交数据比较大，大小靠服务器的设定值限制，而且某些数据只能用POST方法【携带】，比如file。

5、全部用POST不是十分合理，最好先把请求按功能和场景分下类，对数据请求频繁，数据不敏感且数据量在普通浏览器最小限定的2k范围内，这种情况使用GET。其他地方使用POST。

6、GET的本质是【得】，而POST的本质是【给】。而且，GET是【幂等】的，在这一点上，GET被认为是【安全的】。实际上server端也可以用作资源更新，但是这种用法违反了约定，容易造成CSRF（跨站请求伪造）。

# 26. getbytes()的意义,

	有时候,为了让中文字符适应某些特殊要求(**如http header要求其内容必须为iso8859-1编码**),可能会通过将中文字符按照字节方式来编码的情况,如:
String s_iso88591 = new String("中".getBytes("UTF-8"),"ISO8859-1"),

这样得到的s_iso8859-1字符串实际是三个在ISO8859-1中的字符,在将这些字符传递到目的地后,目的地程序再通过相反的方式

String s_utf8 = new String(s_iso88591.getBytes("ISO8859-1"),"UTF-8")

来得到正确的中文汉字"中"，**这样就既保证了遵守协议规定、也支持中文**





# 27. for 循环 与 iterator 迭代 循环 的 区别

 HashMap hashMap = new HashMap<>();

 List<Wordbook> wordbooks = wordbookInterface.examineWordbookAll();

 List<MatchingToWordbook> matchingToWordbooks = wordbookInterface.examineMatchingToWordbook();

 Iterator<MatchingToWordbook> iterator = matchingToWordbooks.iterator();

        for (int i = 1; i < wordbooks.size() + 1; i++) {
            ArrayList<String> arrayList = new ArrayList<>();
    		
            while (iterator.hasNext()) {
                MatchingToWordbook matchingToWordbook = iterator.next();
    			
                String fieldName = matchingToWordbook.getFieldName();
                Integer type = matchingToWordbook.getType();
    			
                if (type == i) {
                    arrayList.add(fieldName);
                }
            }
            hashMap.put(i, arrayList);
        }


​		

		for (int i = 1; i < wordbooks.size() + 1; i++) {
	        ArrayList<String> arrayList = new ArrayList<>();
	
	        for (MatchingToWordbook matchingToWordbook : matchingToWordbooks) {
	            Integer type = matchingToWordbook.getType();
	            String fieldName = matchingToWordbook.getFieldName();
	
	            if (type == i) {
	                arrayList.add(fieldName);
	            }
	        }
	        hashMap.put(i, arrayList);
	
	    }



1. iterator遍历集合时不能同时**对集合**做修改或增加之类，否则会报ConcurrentModificationException（并发修改异常）

2. 使用for循环动态遍历**有序集合**，边遍历边对有序集合操作，是可以的，不会抛出异常
3. 使用增强for循环时，对数组操作也会报ConcurrentModificationException（并发修改异常）





# 28. 查端口号



```
netstat -ano
netstat -aon|findstr "8080"  // 根据端口号查pid
# tasklist|findstr "2722"  // 根据pid 查 程序
# taskkill /f /t /im 程序名;
taskkill /f /pid pid号

```



# 29. 方法定义 为 final

不可重写,不可动态绑定





# 30. 迭代器的结构

一般情况下链表的遍历是通过指针的移动来寻找下一个结点，并输出数据的。

但是在数据结构的设定中，结点都是被设为private的，也就是说我们无法在链表以外对链表元素进行逐个处理的，也就是说在对图中链表元素进行分类处理是很不方便的，所以为了比面这种情况，我们将为链表构造迭代器，用来处理链表元素。

实现：

链表迭代器的实现，便是用指针模拟正常的循环，所以我们需要：

1.构造一个迭代器的类

2.重载循环中用到的==、！=、*、++等运算符

3.为链表创建首迭代器，和尾迭代器函数





# 31. 为什么要有抽象类



	取决于 抽象方法,这个抽象方法 类似与接口中的方法, 没有方法体,是抽象的,

接口中的方法都是 public abstract,只是平时不写,

	抽象类区别于接口,抽象类中 有 其他不是abstract 的方法,只是类中的某个方法刚好需要抽象,所以就决定了类是抽象的.



# 32. 函数式编程思维

函数式编程的思想: 希望可以允许程序员用计算来表示程序,用计算的组合来表达程序的组合;

非函数式编程: 用命令来表示程序,用命令的顺序执行来表达程序的组合;



函数式编程: 关心数据的映射,强调函数的计算比指令的执行重要

命令式编程: 关心解决问题的步骤



**函数式编程的好处**

由于命令式编程语言也可以通过类似函数指针的方式来实现高阶函数，函数式的最主要的好处主要是不可变性带来的。没有可变的状态，函数就是**引用透明（Referential transparency）**的和**没有副作用（No Side Effect）**。

一个好处是，函数即不依赖外部的状态也不修改外部的状态，函数调用的结果不依赖调用的时间和位置，这样写的代码容易进行推理，不容易出错。这使得单元测试和调试都更容易。

不变性带来的另一个好处是：由于（多个线程之间）不共享状态，不会造成**资源争用(Race condition)**，也就不需要用**锁**来保护可变状态，也就不会出现**死锁**，这样可以更好地并发起来，尤其是在**对称多处理器**（SMP）架构下能够更好地利用多个处理器（核）提供的并行处理能力



# 33.  @DateTimeFormat @JsonFormat

@DateTimeFormat : 前端传入的参数是String , 而后端接收的字段是Date , 此情况用这个注解,

这两个注解组合使用

```java

@JsonFormat(timezone = "GMT+8", pattern = "yyyy-MM-dd HH:mm:ss")
@DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
```



@JsonFormat 当写成json字符串时, 会变成 yyyy-MM-dd HH:mm:ss 这样形式, 例: user 的 birthday 字段加这个注解

```java
    public void test1() {
        User user = new User();
        user.setName("123");
        user.setBirthday(new Date());

        ObjectMapper objectMapper = new ObjectMapper();
        try {
            String userJsonString = objectMapper.writeValueAsString(user);
            System.out.println(userJsonString);
        } catch (JsonProcessingException e) {
            e.printStackTrace();
        }
    }
```





# 34. 前台请求有数据,后台接收不到

后台加注解

@ResquestBody



不加这个注解 axios 在post 请求时,默认是把参数放到url后面的,

axios 有三种请求, 默认的

- Content-Type: application/json;charset=utf-8

还有就是

- Content-Type: multipart/form-data

- Content-Type: application/x-www-form-urlencoded



1. 从jquery转到axios 容易忘记设置Content-Type
2. 后台的接收方式也不一样, 注意加 @RequestBody



还有一种解决方式: 

 引入qs

```js
import Qs from 'qs'
let data = {
	"username": "admin",
	"pwd": "admin"
}

axios({
	headers: {
		'deviceCode': 'A95ZEF1-47B5-AC90BF3'
	},
	method: 'post',
	url: '/api/lockServer/search',
	data: Qs.stringify(data)
})

```



# 35. asp, jsp

ASP 脚本由 <% 和 %> 包围。向浏览器写输出：

<html>
<body>
<% response.write("Hello World!") %>
</body>
</html>

ASP 中的默认语言是 VBScript。如需使用其他脚本语言，请在 ASP 页面顶部插入一段语言说明：

<%@ language="javascript" %>
<html>
<body>

<%
....
%>



Request.QueryString 用于收集 method="get" 的表单中的值。使用 GET 方法从表单传送的信息对所有的用户都是可见的（出现在浏览器的地址栏）,

Request.Form 用于收集使用 method="post" 的表单中的值。使用 POST 方法从表单传送的信息对用户是不可见的



# 36. 正则

密码:

```java
var reg= /^(?=.*[a-zA-Z])(?=.*\d)(?=.*[~!@#$%^&*()_+`\-={}:";'<>?,./]).{8,}$/;
                              
"密码由8位数字、大小写字母和特殊符号组成!"                              
```





非打印字符: 

| 字符 | 描述                                                         |
| :--- | :----------------------------------------------------------- |
| \cx  | 匹配由x指明的控制字符。例如， \cM 匹配一个 Control-M 或回车符。x 的值必须为 A-Z 或 a-z 之一。否则，将 c 视为一个原义的 'c' 字符。 |
| \f   | 匹配一个换页符。等价于 \x0c 和 \cL。                         |
| \n   | 匹配一个换行符。等价于 \x0a 和 \cJ。                         |
| \r   | 匹配一个回车符。等价于 \x0d 和 \cM。                         |
| \s   | 匹配任何空白字符，包括空格、制表符、换页符等等。等价于 [ \f\n\r\t\v]。注意 Unicode 正则表达式会匹配全角空格符。 |
| \S   | 匹配任何非空白字符。等价于 [^ \f\n\r\t\v]。                  |
| \t   | 匹配一个制表符。等价于 \x09 和 \cI。                         |
| \v   | 匹配一个垂直制表符。等价于 \x0b 和 \cK。                     |



特殊字符: 

| 特别字符 | 描述                                                         |
| :------- | :----------------------------------------------------------- |
| $        | 匹配输入字符串的结尾位置。如果设置了 RegExp 对象的 Multiline 属性，则 $ 也匹配 '\n' 或 '\r'。要匹配 $ 字符本身，请使用 \$。 |
| ( )      | 标记一个子表达式的开始和结束位置。子表达式可以获取供以后使用。要匹配这些字符，请使用 \( 和 \)。 |
| *        | 匹配前面的子表达式零次或多次。要匹配 * 字符，请使用 \*。     |
| +        | 匹配前面的子表达式一次或多次。要匹配 + 字符，请使用 \+。     |
| .        | 匹配除换行符 \n 之外的任何单字符。要匹配 . ，请使用 \. 。    |
| [        | 标记一个中括号表达式的开始。要匹配 [，请使用 \[。            |
| ?        | 匹配前面的子表达式零次或一次，或指明一个非贪婪限定符。要匹配 ? 字符，请使用 \?。 |
| \        | 将下一个字符标记为或特殊字符、或原义字符、或向后引用、或八进制转义符。例如， 'n' 匹配字符 'n'。'\n' 匹配换行符。序列 '\\' 匹配 "\"，而 '\(' 则匹配 "("。 |
| ^        | 匹配输入字符串的开始位置，除非在方括号表达式中使用，当该符号在方括号表达式中使用时，表示不接受该方括号表达式中的字符集合。要匹配 ^ 字符本身，请使用 \^。 |
| {        | 标记限定符表达式的开始。要匹配 {，请使用 \{。                |
| \|       | 指明两项之间的一个选择。要匹配 \|，请使用 \|。               |



限定符: 

| 字符  | 描述                                                         |
| :---- | :----------------------------------------------------------- |
| *     | 匹配前面的子表达式零次或多次。例如，zo* 能匹配 "z" 以及 "zoo"。* 等价于{0,}。 |
| +     | 匹配前面的子表达式一次或多次。例如，'zo+' 能匹配 "zo" 以及 "zoo"，但不能匹配 "z"。+ 等价于 {1,}。 |
| ?     | 匹配前面的子表达式零次或一次。例如，"do(es)?" 可以匹配 "do" 、 "does" 中的 "does" 、 "doxy" 中的 "do" 。? 等价于 {0,1}。 |
| {n}   | n 是一个非负整数。匹配确定的 n 次。例如，'o{2}' 不能匹配 "Bob" 中的 'o'，但是能匹配 "food" 中的两个 o。 |
| {n,}  | n 是一个非负整数。至少匹配n 次。例如，'o{2,}' 不能匹配 "Bob" 中的 'o'，但能匹配 "foooood" 中的所有 o。'o{1,}' 等价于 'o+'。'o{0,}' 则等价于 'o*'。 |
| {n,m} | m 和 n 均为非负整数，其中n <= m。最少匹配 n 次且最多匹配 m 次。例如，"o{1,3}" 将匹配 "fooooood" 中的前三个 o。'o{0,1}' 等价于 'o?'。请注意在逗号和两个数之间不能有空格。 |











# 37. mybatis plus and or

````java
        queryWrapper.and(wrapper -> wrapper.eq("delete_identity",0));

````



# 38. mybatis plus 的一个注解, 忽略字段 

实体类上查数据库是忽略字段

```java
com.baomidou.mybatisplus.annotation.TableField;

/* 忽略字段 */
@TableField(exist = false)
private Integer count;
```



```java
 @TableField(value = "`assessment_staff`", updateStrategy = FieldStrategy.IGNORED)
```

其中  updateStrategy = FieldStrategy.IGNORED 的意思是 可以把这个字段更新为null, 

另外, 更新时 是 全字段更新, 即 假如前端没有 传这个 字段 , 更新时, 就会把这个字段更新为 null 





# 39. aop

```java
package com.Gzs.demo.SpringSecurityDemo.aop;

import com.Gzs.demo.SpringSecurityDemo.Entity.Role;
import com.Gzs.demo.SpringSecurityDemo.Service.RoleService;
import com.alibaba.fastjson.JSONObject;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.Collection;

/*************************************************************
 * @Package com.Gzs.demo.SpringSecurityDemo.aop
 * @author wg
 * @date 2020/7/29 16:19
 * @version
 * @Copyright
 *************************************************************/
@Aspect
@Component
public class Required {
    @Autowired
    RoleInterface roleInterface;

    @Pointcut("@annotation(wg.application.annotation.RequiredRole)")
    public void requiredRole() {
    }

    /****************************************************************
     * 如果不抛出异常, 无法阻止controller 执行方法内部的逻辑,也就是,这个注解的本意没有达成
     * 这个aop就没有意义
     * @author: wg
     * @time: 2020/8/7 15:32
     ****************************************************************/
    @Around("requiredRole()")
    public Object around(ProceedingJoinPoint joinPoint) throws Throwable {
        boolean b = false;
        String value = "";
        Signature signature = joinPoint.getSignature();
        MethodSignature methodSignature = (MethodSignature) signature;
        Method method = methodSignature.getMethod();
        RequiredRole requiredRole = method.getAnnotation(RequiredRole.class);
        String[] values = requiredRole.value();
        for (int i = 0; i < values.length; i++) {

            // 注解的value 值
            value = values[i];
            // 根据value 查询数据库中角色
            String[] roleCodes = roleInterface.getRoleCode();
            for (int j = 0; j < roleCodes.length; j++) {
                if (roleCodes[j].equals(value)) {
                    b = true;
                }

            }

        }

        if (b == true) {
            System.out.println("????   " + joinPoint.proceed().toString());
            return joinPoint.proceed();
        } else {
            throw new Exception("required admin ");
        }

    }
}
```



注意点 :

1. 

 @Pointcut("execution(public * com.Gzs.demo.SpringSecurityDemo.Controller.PermissionController.saveRolePermission(..))")

最后的 saveRolePermission(..)  方法括号里面 有 " .. " 号,表示 任意参数, 如果不加 " .. "号, 表示 没有参数

2. @Before 目前不知道怎么 改变参数值;





# 40. http 缓存

1. HTTP 服务器响应返回状态码 304，304 代表表示告诉浏览器，本地有缓存数据，可直接从本地获取，无需从服务器获取浪费时间

2. 浏览器在加载资源时，根据请求头的Expires 和 Cache-control 判断是否命中强缓存，是则直接从缓存读取资源，不会发请求到服务器。如果没有命中强缓存，浏览器一定会发送一个请求到服务器，通过 Last-Modified 和 Etag 验证资源是否命中协商缓存，如果命中，服务器会将这个请求返回，但是不会返回这个资源的数据，依然是从缓存中读取资源。如果前面两者都没有命中，直接从服务器加载资源。



![image text](https://github.com/boniu-w/img/blob/master/%E8%AF%B7%E6%B1%82%E6%95%B0%E6%8D%AE.gif?raw=true)













# 41. 一些注解



| 注解                                                         | 解释                              | 所在包                                                       |
| ------------------------------------------------------------ | --------------------------------- | ------------------------------------------------------------ |
| @Scope("prototype")                                          | 被注解的bean变成多例              | org.springframework.context.annotation.Scope;                |
| @Configuration                                               | 使之成为配置类容器, 相当与<beans> | org.springframework.context.annotation.Configuration;        |
| @Transient                                                   | 忽略字段,非数据库字段             | javax.persistence.Transient;                                 |
| @TableField                                                  | 忽略字段,非数据库字段             | mybatisplus 的注解 com.baomidou.mybatisplus.annotation.TableField; |
| @JsonFormat(timezone = "GMT+8",pattern = "yyyy-MM-dd HH:mm:ss") | 日期处理注解,一般结合着写         | com.fasterxml.jackson.annotation;                            |
| @DateTimeFormat(pattern="yyyy-MM-dd HH:mm:ss")               | 日期处理注解,一般结合着写         | org.springframework.format.annotation;                       |
| @Mappings({         @Mapping(source = "entity.id", target = "id") }) | 把两个实体类融合成一个            | org.mapstruct.Mappings;                                      |





## 1. @Mappings 例子



```java
@Mappings({
        @Mapping(source = "entity.id", target = "id")
})
public abstract ConstructionDataDTO toDTO(ConstructionDataEntity entity, MaterialTypeEntity materialTypeEntity);
```





# 42. 注解是怎么起作用的, 例子参见[39]aop

当写自定义注解的时候`J2SE 5.0`在`java.lang.annotation`包中提供了四种注解可以被使用：

- `@Documented`:是否将注解放在`Javadocs`
- `@Retention`:当需要注解的时候
- `@Target`:注解作用的位置
- `@Inherited`: 子类是否获得注解

`@Documented`：一个简单的市场注解，告诉您是否在Java文档中添加注解。

`@Retention` ：定义注解应保留多长时间。

- `RetentionPolicy.SOURCE`: 在编译期间丢弃。编译完成后，这些注解没有任何意义，因此它们不会写入字节码。示例：`@Override`，`@ SuppressWarnings`
- `RetentionPolicy.CLASS`: 在类加载期间丢弃。在进行字节码级后处理时很有用。这是默认值。
- `RetentionPolicy.RUNTIME`: 不会丢弃。注解应该可以在运行时进行反射。这是我们通常用于自定义注解的内容。

`@Target`: 可以放置注解的位置。如果不指定，则可以将注解放在任何位置。以下是有效值。
这里的一个重点是它只是包容性，这意味着如果你想要对7个属性进行注解并且只想要只排除一个属性，则需要在定义目标时包括所有7个。

- `ElementType.TYPE (class, interface, enum)`
- `ElementType.FIELD (instance variable)`
- `ElementType.METHOD`
- `ElementType.PARAMETER`
- `ElementType.CONSTRUCTOR`
- `ElementType.LOCAL_VARIABLE`
- `ElementType.ANNOTATION_TYPE (on another annotation)`
- `ElementType.PACKAGE (remember package-info.java)`

`@Inherited`: 控制注解是否应该影响子类。

现在，注解定义中的内容是什么？注解仅支持基本数据类型，字符串,数组和枚举。注解的所有属性都定义为方法，也可以提供默认值



如果注释中只有一个属性，则应将其命名为`value`，并且在使用时可以在没有属性名称的情况下使用它。

到现在为止还挺好。我们定义了自定义注解并将其应用于某些业务逻辑方法。现在，是时候写一个消费者了。为此，我们需要使用反射。如果您熟悉反射代码，您就知道反射提供了`Class`，`Method`和`Field`对象。所有这些都有一个`getAnnotation()`方法，它返回注解对象。我们需要将此对象转换为自定义注解（在使用`instanceOf()`检查之后），然后，我们可以调用自定义注解中定义的方法。
让我们看一下使用上面注解的示例代码：



```java
Class businessLogicClass = BusinessLogic.class;
for(Method method : businessLogicClass.getMethods()) {
    Todo todoAnnotation = (Todo)method.getAnnotation(Todo.class);
    if(todoAnnotation != null) {
        System.out.println(" Method Name : " + method.getName());
        System.out.println(" Author : " + todoAnnotation.author());
        System.out.println(" Priority : " + todoAnnotation.priority());
        System.out.println(" Status : " + todoAnnotation.status());
    }
}

```



# 43. Date 与 LocalDate 

1. Date 转 LocalDate

```java
date.toInstant().atZone(ZoneId.systemDefault()).toLocalDate();
```



2. LocalDate 转 Date

```java
 ZonedDateTime zonedDateTime = localDate.atStartOfDay(ZoneId.systemDefault());
 Date.from(zonedDateTime.toInstant());
```

3. LocaDateTime 转 Date

```java

   Date.from(localDateTime.atZone(ZoneId.systemDefault()).toInstant());
  

```

4. LocalDate 格式化 转字符串

```java
public static String formatDate(Date date) {
        LocalDate localDate = date.toInstant().atZone(ZoneId.systemDefault()).toLocalDate();
        return localDate.format(DateTimeFormatter.ofPattern("yyyy-MM-dd"));
    }

```



5. localdatetime

   ```java
    LocalDateTime localDateTime=  LocalDateTime.now();
   
   
   
   ```

6. localDateTime 与 字符串

   ```java
   // 字符串 转 LocalDateTime
   DateTimeFormatter fmt = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
   LocalDateTime parse = LocalDateTime.parse("2017-09-28 17:07:05",fmt);
   
   ```

// localdatetime 转 字符串
    DateTimeFormatter fmt = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
    LocalDateTime now = LocalDateTime.now();
    fmt.format(now);

   ```
   



# 44. ENUM 枚举



​```java
package wg.application.enumeration;

/*************************************************************
 * @Package wg.application.enumeration
 * @author wg
 * @date 2020/8/24 9:55
 * @version
 * @Copyright
 *************************************************************/
public enum EnumTest {

    MAX_INT {
        public int getMaxInt() {
            return Integer.MAX_VALUE;
        }
    };

    public int getMaxInt() {
        throw new AbstractMethodError();
    }

}
   ```





```java
package wg.application.enumeration;

import org.springframework.util.StringUtils;

/**
 * Query 规则 常量
 * @Author Scott
 * @Date 2019年02月14日
 */
public enum QueryRuleEnum {

    GT(">","gt","大于"),
    GE(">=","ge","大于等于"),
    LT("<","lt","小于"),
    LE("<=","le","小于等于"),
    EQ("=","eq","等于"),
    NE("!=","ne","不等于"),
    IN("IN","in","包含"),
    LIKE("LIKE","like","全模糊"),
    LEFT_LIKE("LEFT_LIKE","left_like","左模糊"),
    RIGHT_LIKE("RIGHT_LIKE","right_like","右模糊"),
    SQL_RULES("USE_SQL_RULES","ext","自定义SQL片段");

    private String value;
    
    private String condition; 

    private String msg;

    QueryRuleEnum(String value, String condition, String msg){
        this.value = value;
        this.condition = condition;
        this.msg = msg;
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public String getCondition() {
      return condition;
   }

   public void setCondition(String condition) {
      this.condition = condition;
   }

   public static QueryRuleEnum getByValue(String value){
       if(StringUtils.isEmpty(value)) {
          return null;
       }
        for(QueryRuleEnum val :values()){
            if (val.getValue().equals(value) || val.getCondition().equals(value)){
                return val;
            }
        }
        return  null;
    }
}
```





# 45.HTML 中有用的字符实体

**注释：**实体名称对大小写敏感！

| 显示结果 | 描述              | 实体名称           | 实体编号 |
| :------- | :---------------- | :----------------- | :------- |
|          | 空格              | \&nbsp;            | \&#160;  |
| <        | 小于号            | \&lt;              | \&#60;   |
| >        | 大于号            | \&gt;              | \&#62;   |
| &        | 和号              | \&amp;             | \&#38;   |
| "        | 引号              | \&quot;            | \&#34;   |
| '        | 撇号              | \&apos; (IE不支持) | \&#39;   |
| ￠       | 分（cent）        | \&cent;            | \&#162;  |
| £        | 镑（pound）       | \&pound;           | \&#163;  |
| ¥        | 元（yen）         | \&yen;             | \&#165;  |
| €        | 欧元（euro）      | \&euro;            | \&#8364; |
| §        | 小节              | \&sect;            | \&#167;  |
| ©        | 版权（copyright） | \&copy;            | \&#169;  |
| ®        | 注册商标          | \&reg;             | \&#174;  |
| ™        | 商标              | \&trade;           | \&#8482; |
| ×        | 乘号              | \&times;           | \&#215;  |
| ÷        | 除号              | \&divide;          | \&#247;  |
| /        | 斜线              | `&#47;`            |          |



# 46. mybatis plus 

1. apply 用法

   ```java
   queryWrapper
             .apply(
               StringUtils.isNotBlank(standingBook.getHandleCasePerson()),
               "(handle_case_person_one like CONCAT('%',{0},'%') or handle_case_person_two like CONCAT('%',{0},'%'))",
               standingBook.getHandleCasePerson()
             );
   ```

   



# 47. 泛型 <?> 和 \<T> 的区别 

```java
@RequestMapping(value = "/test2")
    public Result test2(){
        List<Student> list1 = new ArrayList<>();
        list1.add(new Student("zhangsan",18,0));
        list1.add(new Student("lisi",28,0));
        //list1.add(new Teacher("wangwu",24,1));
        //这里如果add(new Teacher(...));就会报错，因为我们已经给List指定了数据类型为Student
        show1(list1);

        System.out.println("************分割线**************");

        //这里我们并没有给List指定具体的数据类型，可以存放多种类型数据
        List list2 = new ArrayList<>();
        list2.add(new Student("zhaoliu",22,1));
        list2.add(new Teacher("sunba",30,0));
        show2(list2);

        return Result.ok();
    }

    public static <T> void show1(List<T> list){
        for (Object object : list) {
            System.out.println(object.toString());
        }
    }

    public static void show2(List<?> list) {
        for (Object object : list) {
            System.out.println(object);
        }
    }
```





Class<T> 的用法 示例

```java
	Class<?> aClass = Class.forName("wg.application.entity.DutyEntity");
    String[] strings = ExcelUtil.readExcelTitle(0, (Class<T>) aClass);

```



常用的 T，E，K，V，？

本质上这些个都是通配符，没啥区别，只不过是编码时的一种约定俗成的东西。比如上述代码中的 T ，我们可以换成 A-Z 之间的任何一个 字母都可以，并不会影响程序的正常运行，但是如果换成其他的字母代替 T ，在可读性上可能会弱一些。**通常情况下，T，E，K，V，？是这样约定的：**

- ？表示不确定的 java 类型
- T (type) 表示具体的一个java类型
- K V (key value) 分别代表java键值中的Key Value
- E (element) 代表Element



？**无界通配符**





# 48. requestbody

 @RequestBody接收的是一个Json对象的字符串，而不是一个Json对象, 然而在ajax请求往往传的都是Json对象, 用 JSON.stringify(data)的方式就能将对象变成字符串。同时ajax请求的时候也要指定dataType: "json",contentType:"application/json" 这样就可以轻易的将一个对象或者List传到Java端，使用@RequestBody即可绑定对象或者List



<div style="page-break-after:always"></div>



# 49. 数组: 前端数组转 字符串, 将两个字符串数组 合并为一个 对象

1. 将两个字符串数组 合并为一个 对象  数组的reduce()方法

```js
                    var values = new Array();
                    $("#mail-content input").each(function () {
                        values.push($(this).val())
                    })

                    console.log(values, typeof values);

                    var str = ["name", "company", "phone", "content"];

                    var obj= values.reduce(function (result, value, index) {
                        result[str[index]] = value;
                        return result;
                    },{})

                    console.log("*****  ",obj)
```

2. 数组转 字符串

   ```js
   						var postcodeValues=new Array();
                           $("#postcode input").each(function () {
                               postcodeValues.push($(this).val())
                           })
   
                           var postcode= postcodeValues.join("");
   
                           console.log(postcode)
   ```

   



# 50. mybatis配置详解



# 51. formData 详解

FormData 对象 数据  , 后台用 request.getparameter 接收 也可以用 实体类接收或 map

但是 , 用ajax 会报一个错误, 用xmlhttprequest 不报错



```js
function submit3() {
            var name = $("#name").val();
            let age = $("#age").val();

            let obj = {};
            obj = Object.assign({
                name: name,
                age: age
            }, obj)

            console.log(obj)

            let request = new XMLHttpRequest();

            request.open("post", "http://127.0.0.1:33333/validateTestController/validatePost", false);
            request.setRequestHeader('Content-Type', 'application/x-www-form-urlencoded');
            request.send(obj)         // 后台接收不到 数据

            console.log("request  ", request)
            if ((request.status >= 200 && request.status < 300) || request.status == 304) {

                console.log("200", request.status)
            } else {
                console.log("xmlHttpRequest 的状态为: ", request.status)
            }

            /////////////////////////////////////////////////////////////////////////////
            var formData = new FormData();

            formData.append('name', name);
            formData.append('age', age);
            formData.append('birthDate', 1940);

            var xhr = new XMLHttpRequest();
            xhr.open('POST', 'http://127.0.0.1:33333/validateTestController/validatePost');
            xhr.send(formData);    // 后台可以接收到 数据

            console.log(formData)
            if ((request.status >= 200 && request.status < 300) || request.status == 304) {

                console.log("formData 200", request.status)
            } else {
                console.log("formData xmlHttpRequest 的状态为: ", request.status)
            }

        }
```



```js
        function submit4() {
            var name = $("#name").val();
            let age = $("#age").val();

            let formData = new FormData();
            formData.append('name', name)
            formData.append('age', age)

            console.log(formData)
            $.ajax({
                url: "http://127.0.0.1:33333/validateTestController/validate",
                data: formData,  // 后台接收不到数据, 而且前台就报错: Uncaught TypeError: Illegal invocation
                type: "get",

                // cache: false,
                // processData: false,  // 告诉jQuery不要去处理发送的数据 processData 可不得了,
                // contentType: false,  // 告诉jQuery不要去设置Content-Type请求头
                success: function (res) {
                    console.log(res)
                },
                error: function () {
                    console.log("error")
                }
            })

        }
```



```js
function submit1() {
    var name = $("#name").val();
    let age = $("#age").val();

    let obj = {};
    obj["name"] = name;
    obj["age"] = age;

    console.log(obj)
    $.ajax({
        url: "http://127.0.0.1:33333/validateTestController/validate",
        data: obj,  // 后台可以接收数据 用实体类
        type: "get",

        cache: false,
        // processData: false,  // 告诉jQuery不要去处理发送的数据 processData 可不得了,
        // contentType: false,  // 告诉jQuery不要去设置Content-Type请求头
        success: function (res) {
            console.log(res)
        },
        error: function () {
            console.log("error")
        }
    })

}
```





由此可见, 用ajax 就不要使用 formdata 对象提交数据,  要提交formdata 对象数据 使用xmlhttprequest, ajax 提交数据用object

总结: ajax -> object

xmlhttprequest -> formdata



**注意: 只有false request.status 才 >=200**

使用formdata 封装表单数据

```js
<div id="div3">

    <form id="form3">
        <input type="text" name="name" value="">
        <input type="number" name="age">
        <input type="button" value="sendForm" onclick="sendForm()">
    </form>

    <script>

        var selectors = "#form3";
        var form = document.querySelector(selectors)

        function sendForm() {
           var formData = new FormData(form);

            formData.append("sex","1")

            var request = new XMLHttpRequest();
            request.open('POST', 'http://127.0.0.1:33333/validateTestController/getFormData',false);
            request.send(formData);    // 后台可以接收到 数据

            console.log(formData)
            if ((request.status >= 200 && request.status < 300) || request.status == 304) {
                console.log("formData 200", request.status)
            } else {
                console.log("formData xmlHttpRequest 的状态为: ", request.status)
            }

        }
    </script>

</div>
```





# 52. \* 和** 的作用

\* 表示所有的文件, 但是并不包括子目录下的文件

\** 匹配包含任意级子目录中所有的文件





# 53. 转全角, 转半角

```java
/**
     转半角的函数(DBC case)<br/><br/>
     全角空格为12288，半角空格为32
     其他字符半角(33-126)与全角(65281-65374)的对应关系是：均相差65248
     * @param input 任意字符串
     * @return 半角字符串
     *
     */
    public static String ToDBC(String input) {
        char[] c = input.toCharArray();
        for (int i = 0; i < c.length; i++) {
            if (c[i] == 12288) {
                //全角空格为12288，半角空格为32
                c[i] = (char) 32;
                continue;
            }
            if (c[i] > 65280 && c[i] < 65375)
                //其他字符半角(33-126)与全角(65281-65374)的对应关系是：均相差65248
                c[i] = (char) (c[i] - 65248);
        }
        return new String(c);
    }
```





```java
/**
     转全角的方法(SBC case)<br/><br/>
         全角空格为12288，半角空格为32
         其他字符半角(33-126)与全角(65281-65374)的对应关系是：均相差65248
     * @param input 任意字符串
     * @return 半角字符串
     *
     */
    public static String ToSBC(String input)
    {
        //半角转全角：
        char[] c=input.toCharArray();
        for (int i = 0; i < c.length; i++)
        {
            if (c[i]==32)
            {
                c[i]=(char)12288;
                continue;
            }
            if (c[i]<127)
                c[i]=(char)(c[i]+65248);
        }
        return new String(c);
    }
```





# 54. mybatis

```xml
<?xml version="1.0" encoding="UTF-8" ?>
<!DOCTYPE mapper PUBLIC "-//mybatis.org//DTD Mapper 3.0//EN" "http://mybatis.org/dtd/mybatis-3-mapper.dtd" >
<mapper namespace="net.mingsoft.cms.dao.ICategoryDao">

	<resultMap id="resultMap" type="net.mingsoft.cms.entity.CategoryEntity">
		<id column="id" property="id" /><!--编号 -->
		<result column="category_title" property="categoryTitle" /><!--栏目管理名称 -->
		<result column="category_id" property="categoryId" /><!--所属栏目 -->
		<result column="category_type" property="categoryType" /><!--栏目管理属性 -->
		<result column="category_sort" property="categorySort" /><!--自定义顺序 -->
		<result column="category_list_url" property="categoryListUrl" /><!--列表模板 -->
		<result column="category_url" property="categoryUrl" /><!--内容模板 -->
		<result column="category_keyword" property="categoryKeyword" /><!--栏目管理关键字 -->
		<result column="category_descrip" property="categoryDescrip" /><!--栏目管理描述 -->
		<result column="category_img" property="categoryImg" /><!--缩略图 -->
		<result column="category_diy_url" property="categoryDiyUrl" /><!--自定义链接 -->
		<result column="mdiy_model_id" property="mdiyModelId" /><!--栏目管理的内容模型id -->
		<result column="category_datetime" property="categoryDatetime" /><!--类别发布时间 -->
		<result column="category_manager_id" property="categoryManagerId" /><!--发布用户id -->
		<result column="app_id" property="appId" /><!--应用编号 -->
		<result column="dict_id" property="dictId" /><!--字典对应编号 -->
		<result column="category_flag" property="categoryFlag" /><!--栏目属性 -->
		<result column="category_path" property="categoryPath" /><!--栏目路径 -->
		<result column="category_parent_id" property="categoryParentId" /><!--父类型编号 -->
		<result column="create_by" property="createBy" /><!--创建人 -->
		<result column="create_date" property="createDate" /><!--创建时间 -->
		<result column="update_by" property="updateBy" /><!--修改人 -->
		<result column="update_date" property="updateDate" /><!--修改时间 -->
		<result column="del" property="del" /><!--删除标记 -->
	</resultMap>

	<!--保存-->
	<insert id="saveEntity" useGeneratedKeys="true" keyProperty="id"
			parameterType="net.mingsoft.cms.entity.CategoryEntity" >
		insert into cms_category
		<trim prefix="(" suffix=")" suffixOverrides=",">
			<if test="categoryTitle != null and categoryTitle != ''">category_title,</if>
			<if test="categoryId != null and categoryId != ''">category_id,</if>
			<if test="categoryType != null and categoryType != ''">category_type,</if>
			<if test="categorySort != null">category_sort,</if>
			<if test="categoryListUrl != null and categoryListUrl != ''">category_list_url,</if>
			<if test="categoryUrl != null and categoryUrl != ''">category_url,</if>
			<if test="categoryKeyword != null and categoryKeyword != ''">category_keyword,</if>
			<if test="categoryDescrip != null and categoryDescrip != ''">category_descrip,</if>
			<if test="categoryImg != null and categoryImg != ''">category_img,</if>
			<if test="categoryDiyUrl != null and categoryDiyUrl != ''">category_diy_url,</if>
			<if test="mdiyModelId != null and mdiyModelId != ''">mdiy_model_id,</if>
			<if test="categoryDatetime != null">category_datetime,</if>
			<if test="categoryManagerId != null">category_manager_id,</if>
			<if test="appId != null">app_id,</if>
			<if test="dictId != null">dict_id,</if>
			<if test="categoryFlag != null">category_flag,</if>
			<if test="categoryPath != null and categoryPath != ''">category_path,</if>
			<if test="categoryParentId != null and categoryParentId != ''">category_parent_id,</if>
			<if test="createBy &gt; 0">create_by,</if>
			<if test="createDate != null">create_date,</if>
			<if test="updateBy &gt; 0">update_by,</if>
			<if test="updateDate != null">update_date,</if>
			<if test="del != null">del,</if>
		</trim>
		<trim prefix="values (" suffix=")" suffixOverrides=",">
			<if test="categoryTitle != null and categoryTitle != ''">#{categoryTitle},</if>
			<if test="categoryId != null and categoryId != ''">#{categoryId},</if>
			<if test="categoryType != null and categoryType != ''">#{categoryType},</if>
			<if test="categorySort != null">#{categorySort},</if>
			<if test="categoryListUrl != null and categoryListUrl != ''">#{categoryListUrl},</if>
			<if test="categoryUrl != null and categoryUrl != ''">#{categoryUrl},</if>
			<if test="categoryKeyword != null and categoryKeyword != ''">#{categoryKeyword},</if>
			<if test="categoryDescrip != null and categoryDescrip != ''">#{categoryDescrip},</if>
			<if test="categoryImg != null and categoryImg != ''">#{categoryImg},</if>
			<if test="categoryDiyUrl != null and categoryDiyUrl != ''">#{categoryDiyUrl},</if>
			<if test="mdiyModelId != null and mdiyModelId != ''">#{mdiyModelId},</if>
			<if test="categoryDatetime != null">#{categoryDatetime},</if>
			<if test="categoryManagerId != null">#{categoryManagerId},</if>
			<if test="appId != null">#{appId},</if>
			<if test="dictId != null">#{dictId},</if>
			<if test="categoryFlag != null ">#{categoryFlag},</if>
			<if test="categoryPath != null and categoryPath != ''">#{categoryPath},</if>
			<if test="categoryParentId != null and categoryParentId != ''">#{categoryParentId},</if>
			<if test="createBy &gt; 0">#{createBy},</if>
			<if test="createDate != null">#{createDate},</if>
			<if test="updateBy &gt; 0">#{updateBy},</if>
			<if test="updateDate != null">#{updateDate},</if>
			<if test="del != null">#{del},</if>
		</trim>
	</insert>

	<!--更新-->
	<update id="updateEntity" parameterType="net.mingsoft.cms.entity.CategoryEntity">
		update cms_category
		<set>
			<if test="categoryTitle != null and categoryTitle != ''">category_title=#{categoryTitle},</if>
			category_id=#{categoryId},
			category_parent_id=#{categoryParentId},
			<if test="categoryType != null and categoryType != ''">category_type=#{categoryType},</if>
			<if test="categorySort != null">category_sort=#{categorySort},</if>
			<if test="categoryListUrl != null and categoryListUrl != ''">category_list_url=#{categoryListUrl},</if>
			<if test="categoryUrl != null and categoryUrl != ''">category_url=#{categoryUrl},</if>
			<if test="categoryKeyword != null ">category_keyword=#{categoryKeyword},</if>
			<if test="categoryDescrip != null ">category_descrip=#{categoryDescrip},</if>
			<if test="categoryImg != null and categoryImg != ''">category_img=#{categoryImg},</if>
			<if test="categoryDiyUrl != null">category_diy_url=#{categoryDiyUrl},</if>
			<if test="mdiyModelId != null and mdiyModelId != ''">mdiy_model_id=#{mdiyModelId},</if>
			<if test="categoryDatetime != null">category_datetime=#{categoryDatetime},</if>
			<if test="categoryManagerId != null">category_manager_id=#{categoryManagerId},</if>
			<if test="appId != null">app_id=#{appId},</if>
			<if test="dictId != null">dict_id=#{dictId},</if>
			<if test="categoryFlag != null ">category_flag=#{categoryFlag},</if>
			<if test="categoryPath != null and categoryPath != ''">category_path=#{categoryPath},</if>
			<if test="createBy &gt; 0">create_by=#{createBy},</if>
			<if test="createDate != null">create_date=#{createDate},</if>
			<if test="updateBy &gt; 0">update_by=#{updateBy},</if>
			<if test="updateDate != null">update_date=#{updateDate},</if>
			<if test="del != null">del=#{del},</if>
		</set>
		where id = #{id}
	</update>

	<!--根据id获取-->
	<select id="getEntity" resultMap="resultMap" parameterType="int">
			select * from cms_category where id=#{id}
		</select>

	<!--根据实体获取-->
	<select id="getByEntity" resultMap="resultMap" parameterType="net.mingsoft.cms.entity.CategoryEntity">
		select * from cms_category
		<where>
			<if test="categoryTitle != null and categoryTitle != ''">and category_title=#{categoryTitle}</if>
			<if test="categoryId != null and categoryId != ''">and category_id=#{categoryId}</if>
			<if test="categoryType != null and categoryType != ''">and category_type=#{categoryType}</if>
			<if test="categorySort != null"> and category_sort=#{categorySort} </if>
			<if test="categoryListUrl != null and categoryListUrl != ''">and category_list_url=#{categoryListUrl}</if>
			<if test="categoryUrl != null and categoryUrl != ''">and category_url=#{categoryUrl}</if>
			<if test="categoryKeyword != null and categoryKeyword != ''">and category_keyword=#{categoryKeyword}</if>
			<if test="categoryDescrip != null and categoryDescrip != ''">and category_descrip=#{categoryDescrip}</if>
			<if test="categoryImg != null and categoryImg != ''">and category_img=#{categoryImg}</if>
			<if test="categoryDiyUrl != null and categoryDiyUrl != ''">and category_diy_url=#{categoryDiyUrl}</if>
			<if test="mdiyModelId != null and mdiyModelId != ''">and mdiy_model_id=#{mdiyModelId}</if>
			<if test="categoryDatetime != null"> and category_datetime=#{categoryDatetime} </if>
			<if test="categoryManagerId != null"> and category_manager_id=#{categoryManagerId} </if>
			<if test="appId != null"> and app_id=#{appId} </if>
			<if test="dictId != null"> and dict_id=#{dictId} </if>
			<if test="categoryFlag != null and categoryFlag != ''">and category_flag=#{categoryFlag}</if>
			<if test="categoryPath != null and categoryPath != ''">and category_path=#{categoryPath}</if>
			<if test="categoryParentId != null and categoryParentId != ''">and category_parent_id=#{categoryParentId}</if>
			<if test="createBy &gt; 0"> and create_by=#{createBy} </if>
			<if test="createDate != null"> and create_date=#{createDate} </if>
			<if test="updateBy &gt; 0"> and update_by=#{updateBy} </if>
			<if test="updateDate != null"> and update_date=#{updateDate} </if>
			<if test="del != null"> and del=#{del} </if>
		</where>
		limit 0,1
	</select>
	<sql id="queryWhereCategoryId" databaseId="mysql">
		find_in_set('${id}',CATEGORY_PARENT_ID)
	</sql>
	<sql id="queryWhereCategoryId" databaseId="oracle" >
		instr(','||'${id}'||',', ','||CATEGORY_PARENT_ID||',')>0
	</sql>
	<sql id="queryWhereCategoryId" databaseId="sqlServer">
		CHARINDEX(','+'${id}'+',' , ','+CATEGORY_PARENT_ID +',')>0
	</sql>

	<!-- 模糊查询开始 -->
	<select id="queryChildren" resultMap="resultMap">
		select * from cms_category
		<where>
			<if test="appId &gt; 0">
				and app_id=#{appId}
			</if>
			<if test="dictId &gt; 0">
				and dict_id=#{dictId}
			</if>
			and
			(
			<if test="categoryParentId != null and categoryParentId!=''">
				find_in_set(#{categoryParentId},CATEGORY_PARENT_ID)
			</if>
			<if test="categoryParentId == null or  categoryParentId ==''">
				<include refid="queryWhereCategoryId"></include>
			</if>
			<if test="id != null">
				or id=#{id}
			</if>
			)
			and del=0
		</where>
	</select>


	<!--删除-->
	<delete id="deleteEntity" parameterType="int">
			delete from cms_category  where id=#{id}
		</delete>

	<!--批量删除-->
	<delete id="delete" >
		delete from cms_category
		<where>
			id  in <foreach collection="ids" item="item" index="index"
							open="(" separator="," close=")">#{item}</foreach>
		</where>
	</delete>
	<!--查询全部-->
	<select id="queryAll" resultMap="resultMap">
			select * from cms_category order by id desc
		</select>
	<!--条件查询-->
	<select id="query" resultMap="resultMap">
		select * from cms_category
		<where>
			<if test="categoryTitle != null and categoryTitle != ''"> and category_title=#{categoryTitle}</if>
			<if test="categoryId != null and categoryId != ''"> and category_id=#{categoryId}</if>
			<if test="categoryType != null and categoryType != ''"> and category_type=#{categoryType}</if>
			<if test="categorySort != null"> and category_sort=#{categorySort} </if>
			<if test="categoryListUrl != null and categoryListUrl != ''"> and category_list_url=#{categoryListUrl}</if>
			<if test="categoryUrl != null and categoryUrl != ''"> and category_url=#{categoryUrl}</if>
			<if test="categoryKeyword != null and categoryKeyword != ''"> and category_keyword=#{categoryKeyword}</if>
			<if test="categoryDescrip != null and categoryDescrip != ''"> and category_descrip=#{categoryDescrip}</if>
			<if test="categoryImg != null and categoryImg != ''"> and category_img=#{categoryImg}</if>
			<if test="categoryDiyUrl != null and categoryDiyUrl != ''"> and category_diy_url=#{categoryDiyUrl}</if>
			<if test="mdiyModelId != null and mdiyModelId != ''"> and mdiy_model_id=#{mdiyModelId}</if>
			<if test="categoryDatetime != null"> and category_datetime=#{categoryDatetime} </if>
			<if test="categoryManagerId != null"> and category_manager_id=#{categoryManagerId} </if>
			<if test="appId != null"> and app_id=#{appId} </if>
			<if test="dictId != null"> and dict_id=#{dictId} </if>
			<if test="categoryFlag != null and categoryFlag != ''"> and category_flag=#{categoryFlag}</if>
			<if test="categoryPath != null and categoryPath != ''"> and category_path=#{categoryPath}</if>
			<if test="categoryParentId != null and categoryParentId != ''"> and category_parent_id=#{categoryParentId}</if>
			<if test="createBy &gt; 0"> and create_by=#{createBy} </if>
			<if test="createDate != null"> and create_date=#{createDate} </if>
			<if test="updateBy &gt; 0"> and update_by=#{updateBy} </if>
			<if test="updateDate != null"> and update_date=#{updateDate} </if>
			<if test="del != null"> and del=#{del} </if>
			<include refid="net.mingsoft.base.dao.IBaseDao.sqlWhere"></include>
		</where>
		order by id desc
	</select>
	    <select id="sortBankFlow" resultType="org.jeecg.modules.htjd.newBankFlow.entity.NewBankFlow">
        select
        a.id,
        a.sjy,
        a.jyzh,
        a.jykh,
        a.jyhm,
        a.sf,
        a.jyrq,
        a.jysj,
        a.sfbz,
        a.jyje,
        a.jyye,
        a.jydszkh,
        a.dshm,
        a.pp,
        a.dskhyh,
        a.zysm,
        a.bz,
        a.xjbz,
        a.jybz,
        a.jywdmc,
        a.ip,
        a.mac
        from
        (select
        id,
        sjy,
        jyzh,
        jykh,
        jyhm,
        sf,
        jyrq,
        jysj,
        sfbz,
        jyje,
        jyye,
        jydszkh,
        dshm,
        pp,
        dskhyh,
        zysm,
        bz,
        xjbz,
        jybz,
        jywdmc,
        ip,
        mac
        from new_bankFlow
        <where>
            <if test="jykh != null and jykh != '' ">
                and jykh=#{jykh}
            </if>
            <if test="jyzh != null and jyzh != '' ">
                and jyzh=#{jyzh}
            </if>
            <if test="jydszkh != null and jydszkh != '' ">
                and jydszkh=#{jydszkh}
            </if>
            <if test="dshm != null and dshm != '' ">
                and dshm=#{dshm}
            </if>
            <if test="sf != null and sf != '' ">
                and sf=#{sf}
            </if>
            <if test="sfbz != null and sfbz != '' ">
                and sfbz=#{sfbz}
            </if>
            <if test="jyhm != null and jyhm != '' ">
                and jyhm=#{jyhm}
            </if>
            <if test="startTime != null and startTime != '' and endTime != null and endTime != '' ">
                and date(jyrq) between #{startTime} and #{endTime}
            </if>
        </where>
        ) as a
        <if test="sortMap != null and sortMap != '' ">
        order by
            <foreach collection="sortMap.entrySet()" index="key" item="value" separator=",">
                (a.${key}+0) ${value}
            </foreach>
        </if>

    </select>
</mapper>

```





mybatis @Param

如果传入的参数是基本类型参数和实体类对象

```xml
public List<student> selectuser(@Param(value = "page")int pn ,@Param(value = "st")student student);
<select id="selectuser" resultType="com.user.entity.student">
    SELECT * FROM student
    where sname like concat(concat("%",#{st.sname}),"%")
    LIMIT #{page} ,5
</select>
```



## 2. mybatis的2个内置参数

> _parameter
>
> _databaseId



```xml
<?xml version="1.0" encoding="UTF-8"?>
<!DOCTYPE mapper PUBLIC "-//mybatis.org//DTD Mapper 3.0//EN" "http://mybatis.org/dtd/mybatis-3-mapper.dtd">
<mapper namespace="com.sevenme.pipelinemanager.offshorepipeline.mapper.RiskAssessmentHistoryMapper">
  <resultMap id="BaseResultMap" type="com.sevenme.pipelinemanager.offshorepipeline.model.RiskAssessmentHistory">
    <id column="id" jdbcType="CHAR" property="id" />
    <result column="design_id" jdbcType="CHAR" property="designId" />
    <result column="pipeline_number" jdbcType="VARCHAR" property="pipelineNumber" />
    <result column="pipeline_name" jdbcType="VARCHAR" property="pipelineName" />
    <result column="section_history_id" jdbcType="CHAR" property="sectionHistoryId" />
    <result column="assessment_date" jdbcType="TIMESTAMP" property="assessmentDate" />
    <result column="assessment_staff" jdbcType="VARCHAR" property="assessmentStaff" />
    <result column="max_risk_level" jdbcType="VARCHAR" property="maxRiskLevel" />
    <result column="create_time" jdbcType="TIMESTAMP" property="createTime" />
    <result column="update_time" jdbcType="TIMESTAMP" property="updateTime" />
  </resultMap>
  <sql id="Example_Where_Clause">
    <where>
      <foreach collection="oredCriteria" item="criteria" separator="or">
        <if test="criteria.valid">
          <trim prefix="(" prefixOverrides="and" suffix=")">
            <foreach collection="criteria.criteria" item="criterion">
              <choose>
                <when test="criterion.noValue">
                  and ${criterion.condition}
                </when>
                <when test="criterion.singleValue">
                  and ${criterion.condition} #{criterion.value}
                </when>
                <when test="criterion.betweenValue">
                  and ${criterion.condition} #{criterion.value} and #{criterion.secondValue}
                </when>
                <when test="criterion.listValue">
                  and ${criterion.condition}
                  <foreach close=")" collection="criterion.value" item="listItem" open="(" separator=",">
                    #{listItem}
                  </foreach>
                </when>
              </choose>
            </foreach>
          </trim>
        </if>
      </foreach>
    </where>
  </sql>
  <sql id="Update_By_Example_Where_Clause">
    <where>
      <foreach collection="example.oredCriteria" item="criteria" separator="or">
        <if test="criteria.valid">
          <trim prefix="(" prefixOverrides="and" suffix=")">
            <foreach collection="criteria.criteria" item="criterion">
              <choose>
                <when test="criterion.noValue">
                  and ${criterion.condition}
                </when>
                <when test="criterion.singleValue">
                  and ${criterion.condition} #{criterion.value}
                </when>
                <when test="criterion.betweenValue">
                  and ${criterion.condition} #{criterion.value} and #{criterion.secondValue}
                </when>
                <when test="criterion.listValue">
                  and ${criterion.condition}
                  <foreach close=")" collection="criterion.value" item="listItem" open="(" separator=",">
                    #{listItem}
                  </foreach>
                </when>
              </choose>
            </foreach>
          </trim>
        </if>
      </foreach>
    </where>
  </sql>
  <sql id="Base_Column_List">
    id, design_id, pipeline_number, pipeline_name, section_history_id, assessment_date, 
    assessment_staff, max_risk_level, create_time, update_time
  </sql>
  <select id="selectByExample" parameterType="com.sevenme.pipelinemanager.offshorepipeline.model.RiskAssessmentHistoryExample" resultMap="BaseResultMap">
    select
    <if test="distinct">
      distinct
    </if>
    <include refid="Base_Column_List" />
    from risk_assessment_history
    <if test="_parameter != null">
      <include refid="Example_Where_Clause" />
    </if>
    <if test="orderByClause != null">
      order by ${orderByClause}
    </if>
  </select>
  <select id="selectByPrimaryKey" parameterType="java.lang.String" resultMap="BaseResultMap">
    select 
    <include refid="Base_Column_List" />
    from risk_assessment_history
    where id = #{id,jdbcType=CHAR}
  </select>
  <delete id="deleteByPrimaryKey" parameterType="java.lang.String">
    delete from risk_assessment_history
    where id = #{id,jdbcType=CHAR}
  </delete>
  <delete id="deleteByExample" parameterType="com.sevenme.pipelinemanager.offshorepipeline.model.RiskAssessmentHistoryExample">
    delete from risk_assessment_history
    <if test="_parameter != null">
      <include refid="Example_Where_Clause" />
    </if>
  </delete>
  <insert id="insert" parameterType="com.sevenme.pipelinemanager.offshorepipeline.model.RiskAssessmentHistory">
    insert into risk_assessment_history (id, design_id, pipeline_number, 
      pipeline_name, section_history_id, assessment_date, 
      assessment_staff, max_risk_level, create_time, 
      update_time)
    values (#{id,jdbcType=CHAR}, #{designId,jdbcType=CHAR}, #{pipelineNumber,jdbcType=VARCHAR}, 
      #{pipelineName,jdbcType=VARCHAR}, #{sectionHistoryId,jdbcType=CHAR}, #{assessmentDate,jdbcType=TIMESTAMP}, 
      #{assessmentStaff,jdbcType=VARCHAR}, #{maxRiskLevel,jdbcType=VARCHAR}, #{createTime,jdbcType=TIMESTAMP}, 
      #{updateTime,jdbcType=TIMESTAMP})
  </insert>
  <insert id="insertSelective" parameterType="com.sevenme.pipelinemanager.offshorepipeline.model.RiskAssessmentHistory">
    insert into risk_assessment_history
    <trim prefix="(" suffix=")" suffixOverrides=",">
      <if test="id != null">
        id,
      </if>
      <if test="designId != null">
        design_id,
      </if>
      <if test="pipelineNumber != null">
        pipeline_number,
      </if>
      <if test="pipelineName != null">
        pipeline_name,
      </if>
      <if test="sectionHistoryId != null">
        section_history_id,
      </if>
      <if test="assessmentDate != null">
        assessment_date,
      </if>
      <if test="assessmentStaff != null">
        assessment_staff,
      </if>
      <if test="maxRiskLevel != null">
        max_risk_level,
      </if>
      <if test="createTime != null">
        create_time,
      </if>
      <if test="updateTime != null">
        update_time,
      </if>
    </trim>
    <trim prefix="values (" suffix=")" suffixOverrides=",">
      <if test="id != null">
        #{id,jdbcType=CHAR},
      </if>
      <if test="designId != null">
        #{designId,jdbcType=CHAR},
      </if>
      <if test="pipelineNumber != null">
        #{pipelineNumber,jdbcType=VARCHAR},
      </if>
      <if test="pipelineName != null">
        #{pipelineName,jdbcType=VARCHAR},
      </if>
      <if test="sectionHistoryId != null">
        #{sectionHistoryId,jdbcType=CHAR},
      </if>
      <if test="assessmentDate != null">
        #{assessmentDate,jdbcType=TIMESTAMP},
      </if>
      <if test="assessmentStaff != null">
        #{assessmentStaff,jdbcType=VARCHAR},
      </if>
      <if test="maxRiskLevel != null">
        #{maxRiskLevel,jdbcType=VARCHAR},
      </if>
      <if test="createTime != null">
        #{createTime,jdbcType=TIMESTAMP},
      </if>
      <if test="updateTime != null">
        #{updateTime,jdbcType=TIMESTAMP},
      </if>
    </trim>
  </insert>
  <select id="countByExample" parameterType="com.sevenme.pipelinemanager.offshorepipeline.model.RiskAssessmentHistoryExample" resultType="java.lang.Long">
    select count(*) from risk_assessment_history
    <if test="_parameter != null">
      <include refid="Example_Where_Clause" />
    </if>
  </select>
  <update id="updateByExampleSelective" parameterType="map">
    update risk_assessment_history
    <set>
      <if test="record.id != null">
        id = #{record.id,jdbcType=CHAR},
      </if>
      <if test="record.designId != null">
        design_id = #{record.designId,jdbcType=CHAR},
      </if>
      <if test="record.pipelineNumber != null">
        pipeline_number = #{record.pipelineNumber,jdbcType=VARCHAR},
      </if>
      <if test="record.pipelineName != null">
        pipeline_name = #{record.pipelineName,jdbcType=VARCHAR},
      </if>
      <if test="record.sectionHistoryId != null">
        section_history_id = #{record.sectionHistoryId,jdbcType=CHAR},
      </if>
      <if test="record.assessmentDate != null">
        assessment_date = #{record.assessmentDate,jdbcType=TIMESTAMP},
      </if>
      <if test="record.assessmentStaff != null">
        assessment_staff = #{record.assessmentStaff,jdbcType=VARCHAR},
      </if>
      <if test="record.maxRiskLevel != null">
        max_risk_level = #{record.maxRiskLevel,jdbcType=VARCHAR},
      </if>
      <if test="record.createTime != null">
        create_time = #{record.createTime,jdbcType=TIMESTAMP},
      </if>
      <if test="record.updateTime != null">
        update_time = #{record.updateTime,jdbcType=TIMESTAMP},
      </if>
    </set>
    <if test="_parameter != null">
      <include refid="Update_By_Example_Where_Clause" />
    </if>
  </update>
  <update id="updateByExample" parameterType="map">
    update risk_assessment_history
    set id = #{record.id,jdbcType=CHAR},
      design_id = #{record.designId,jdbcType=CHAR},
      pipeline_number = #{record.pipelineNumber,jdbcType=VARCHAR},
      pipeline_name = #{record.pipelineName,jdbcType=VARCHAR},
      section_history_id = #{record.sectionHistoryId,jdbcType=CHAR},
      assessment_date = #{record.assessmentDate,jdbcType=TIMESTAMP},
      assessment_staff = #{record.assessmentStaff,jdbcType=VARCHAR},
      max_risk_level = #{record.maxRiskLevel,jdbcType=VARCHAR},
      create_time = #{record.createTime,jdbcType=TIMESTAMP},
      update_time = #{record.updateTime,jdbcType=TIMESTAMP}
    <if test="_parameter != null">
      <include refid="Update_By_Example_Where_Clause" />
    </if>
  </update>
  <update id="updateByPrimaryKeySelective" parameterType="com.sevenme.pipelinemanager.offshorepipeline.model.RiskAssessmentHistory">
    update risk_assessment_history
    <set>
      <if test="designId != null">
        design_id = #{designId,jdbcType=CHAR},
      </if>
      <if test="pipelineNumber != null">
        pipeline_number = #{pipelineNumber,jdbcType=VARCHAR},
      </if>
      <if test="pipelineName != null">
        pipeline_name = #{pipelineName,jdbcType=VARCHAR},
      </if>
      <if test="sectionHistoryId != null">
        section_history_id = #{sectionHistoryId,jdbcType=CHAR},
      </if>
      <if test="assessmentDate != null">
        assessment_date = #{assessmentDate,jdbcType=TIMESTAMP},
      </if>
      <if test="assessmentStaff != null">
        assessment_staff = #{assessmentStaff,jdbcType=VARCHAR},
      </if>
      <if test="maxRiskLevel != null">
        max_risk_level = #{maxRiskLevel,jdbcType=VARCHAR},
      </if>
      <if test="createTime != null">
        create_time = #{createTime,jdbcType=TIMESTAMP},
      </if>
      <if test="updateTime != null">
        update_time = #{updateTime,jdbcType=TIMESTAMP},
      </if>
    </set>
    where id = #{id,jdbcType=CHAR}
  </update>
  <update id="updateByPrimaryKey" parameterType="com.sevenme.pipelinemanager.offshorepipeline.model.RiskAssessmentHistory">
    update risk_assessment_history
    set design_id = #{designId,jdbcType=CHAR},
      pipeline_number = #{pipelineNumber,jdbcType=VARCHAR},
      pipeline_name = #{pipelineName,jdbcType=VARCHAR},
      section_history_id = #{sectionHistoryId,jdbcType=CHAR},
      assessment_date = #{assessmentDate,jdbcType=TIMESTAMP},
      assessment_staff = #{assessmentStaff,jdbcType=VARCHAR},
      max_risk_level = #{maxRiskLevel,jdbcType=VARCHAR},
      create_time = #{createTime,jdbcType=TIMESTAMP},
      update_time = #{updateTime,jdbcType=TIMESTAMP}
    where id = #{id,jdbcType=CHAR}
  </update>

</mapper>
```





# 55. 开发英语

property, attribute 都是 属性

property是 物体本身自带属性，不能改变的（一旦改了就是另外一个东西了）
attribute，由于 attribute还可以做动词，表示赋予。。。特性，属于人为赋予的可改变的属性。





# 56. mybatis plus

## 1. 部分代码演示

```java
    @AutoLog(value = "流水表-分页列表查询")
    @ApiOperation(value = "流水表-分页列表查询", notes = "流水表-分页列表查询")
    @PostMapping(value = "/list")
    public Result<?> queryPageList(@RequestBody(required = false) JSONObject json,
                                   @RequestParam(name = "pageNo", defaultValue = "1") Integer pageNo,
                                   @RequestParam(name = "pageSize", defaultValue = "10") Integer pageSize,
                                   HttpServletRequest req) {
        System.out.println(json);

        // 如果 json 里没有k 或 v, 则 移除 ss ; 如果 ss 为空 也移除 ss
        checkSS(json);

        JSONArray ss = json.getJSONArray("ss");

        if (!ObjectUtils.isEmpty(json.get("pageNo"))) {
            pageNo = (Integer) json.get("pageNo");
        } else {
            pageNo = 1;
        }

        if (!ObjectUtils.isEmpty(json.get("pageSize"))) {
            pageSize = (Integer) json.get("pageSize");
        } else {
            pageSize = 10;
        }

        String jyrq = json.getString("jyrq");
        if (!StringUtils.isEmpty(jyrq)) {
            String[] split = jyrq.split(":");
            if (split.length == 2 && !StringUtils.isEmpty(split[0])) {
                json.put("startTime", split[0]);
                json.put("endTime", split[1]);
            } else {
                json.remove("jyrq");
            }
        }

        NewBankFlow newBankFlow = JSON.toJavaObject(json, NewBankFlow.class);

        if (ss != null) {
            HashMap<String, String> sortMap = new HashMap<>();
            for (int i = 0; i < ss.size(); i++) {
                JSONObject jsonObject = ss.getJSONObject(i);
                String k = jsonObject.getString("k");
                String v = jsonObject.getString("v");
                if (!StringUtils.isEmpty(k) && !StringUtils.isEmpty(v)) {
                    sortMap.put(k, v);
                }
            }
            newBankFlow.setSortMap(sortMap);

            //System.out.println("newBankFlow -> " + newBankFlow);

            //System.out.println("startTime -> " + json.get("startTime"));
            //System.out.println("endTime -> " + json.get("endTime"));

            QueryWrapper<NewBankFlow> queryWrapper = new QueryWrapper<>();
            queryWrapper.lambda().between(!StringUtils.isEmpty(json.getString("jyrq")), NewBankFlow::getJyrq, json.get("startTime"), json.get("endTime"));
            queryWrapper.lambda().eq(!StringUtils.isEmpty(json.getString("jykh")), NewBankFlow::getJykh, json.getString("jykh"));
            queryWrapper.lambda().eq(!StringUtils.isEmpty(json.getString("jyzh")), NewBankFlow::getJyzh, json.getString("jyzh"));
            queryWrapper.lambda().eq(!StringUtils.isEmpty(json.getString("jydszkh")), NewBankFlow::getJydszkh, json.getString("jydszkh"));
            queryWrapper.lambda().eq(!StringUtils.isEmpty(json.getString("dshm")), NewBankFlow::getDshm, json.getString("dshm"));
            queryWrapper.lambda().eq(!StringUtils.isEmpty(json.getString("sf")), NewBankFlow::getSf, json.getString("sf"));
            queryWrapper.lambda().eq(!StringUtils.isEmpty(json.getString("jyhm")), NewBankFlow::getJyhm, json.getString("jyhm"));
            queryWrapper.lambda().eq(!StringUtils.isEmpty(json.getString("sfbz")), NewBankFlow::getSfbz, json.getString("sfbz"));

            if (!ObjectUtils.isEmpty(sortMap)) {
                if (!StringUtils.isEmpty(sortMap.get("jyrq"))) {
                    queryWrapper.orderBy(!sortMap.isEmpty(), checkSortMap(sortMap.get("jyrq")), "jyrq");
                }
                if (!StringUtils.isEmpty(sortMap.get("jyje"))) {
                    queryWrapper.orderBy(!sortMap.isEmpty(), checkSortMap(sortMap.get("jyje")), "jyje" + "+0");
                }
                if (!StringUtils.isEmpty(sortMap.get("jyye"))) {
                    queryWrapper.orderBy(!sortMap.isEmpty(), checkSortMap(sortMap.get("jyye")), "jyye" + "+0");
                }
                if (!StringUtils.isEmpty(sortMap.get("jysj"))) {
                    queryWrapper.orderBy(!sortMap.isEmpty(), checkSortMap(sortMap.get("jysj")), "jysj");
                }
            }
            Page<NewBankFlow> page = new Page<>(pageNo, pageSize);
            IPage<NewBankFlow> bankFlows = newBankFlowService.page(page, queryWrapper);
            System.out.println("我的方法 bankFlows.total -> " + bankFlows.getTotal());
            return Result.ok(bankFlows);
        }

        QueryWrapper<NewBankFlow> queryWrapper = QueryGenerator.initQueryWrapper(newBankFlow, req.getParameterMap());
        Page<NewBankFlow> page = new Page<NewBankFlow>(pageNo, pageSize);
        IPage<NewBankFlow> pageList1 = newBankFlowService.page(page, queryWrapper);

        System.out.println("pageList1.getTotal  -> " + pageList1.getTotal());
        return Result.ok(pageList1);

    }

    /****************************************************************
     * @description: 检查是 asc 还是 desc
     * @author: wg
     * @time: 2021/1/22 14:35
     ****************************************************************/
    public boolean checkSortMap(String value) {

        boolean a = false;
        switch (value) {
            case "asc":
                a = true;
                break;

            case "desc":
                a = false;
                break;
        }
        return a;
    }

    /****************************************************************
     * @description: 如果 json 里没有k 或 v, 则 移除 ss ; 如果 ss 为空 也移除 ss
     * @author: wg
     * @time: 2021/1/22 14:35
     ****************************************************************/
    public void checkSS(JSONObject json) {
        JSONArray ss = json.getJSONArray("ss");
        if (ss.size() == 0) {
            json.remove("ss");
        }

        if (ss.size() > 0) {
            for (int i = 0; i < ss.size(); i++) {
                JSONObject jsonObject = ss.getJSONObject(i);
                String k = jsonObject.getString("k");
                String v = jsonObject.getString("v");

                if (StringUtils.isEmpty(k) || StringUtils.isEmpty(v)) {
                    ss.remove(i);
                    //json.remove("ss");
                }
            }
        }
    }
```





mybatisplus 无法处理 resulttype 类型为list 等 开启 结果集自动映射 不管用



## 2. 一些配置

```yaml
# 开启驼峰映射
mybatis.configuration.map-underscore-to-camel-case=true 
```



## 3. 更多配置

```yml
 
mybatis-plus:
  # 如果是放在src/main/java目录下 classpath:/com/yourpackage/*/mapper/*Mapper.xml
  # 如果是放在resource目录 classpath:/mapper/*Mapper.xml
  mapper-locations: classpath:/mapper/*Mapper.xml
  #实体扫描，多个package用逗号或者分号分隔
  typeAliasesPackage: com.yourpackage.*.entity
  global-config:
    #主键类型  0:"数据库ID自增", 1:"用户输入ID",2:"全局唯一ID (数字类型唯一ID)", 3:"全局唯一ID UUID";
    id-type: 3
    #字段策略 0:"忽略判断",1:"非 NULL 判断"),2:"非空判断"
    field-strategy: 2
    #驼峰下划线转换
    db-column-underline: true
    #mp2.3+ 全局表前缀 mp_
    #table-prefix: mp_
    #刷新mapper 调试神器
    #refresh-mapper: true
    #数据库大写下划线转换
    #capital-mode: true
    # Sequence序列接口实现类配置
    key-generator: com.baomidou.mybatisplus.incrementer.OracleKeyGenerator
    #逻辑删除配置（下面3个配置）
    logic-delete-value: 1
    logic-not-delete-value: 0
    sql-injector: com.baomidou.mybatisplus.mapper.LogicSqlInjector
    #自定义填充策略接口实现
    meta-object-handler: com.baomidou.springboot.MyMetaObjectHandler
  configuration:
    #配置返回数据库(column下划线命名&&返回java实体是驼峰命名)，自动匹配无需as（没开启这个，SQL需要写as： select user_id as userId） 
    map-underscore-to-camel-case: true
    cache-enabled: false
    #配置JdbcTypeForNull, oracle数据库必须配置
    jdbc-type-for-null: 'null'
```



## 4. 只查询指定字段 和 排除指定字段查询



```java
// 只查询指定字段
wrapper.select("id", "name").in("id",set);

// 排除指定字段查询, 不查询这些字段
 	@Test
    public void selectByWrapper11() {
        QueryWrapper<User> queryWrapper = new QueryWrapper<>();
        queryWrapper.select(User.class, info -> !info.getColumn().equals("manager_id")
                && !info.getColumn().equals("create_time"));
        List<User> users = userMapper.selectList(queryWrapper);
        users.forEach(System.out::println);
    }
```



## 5.  关于日期的 一些操作

```java
queryWrapper.apply("DATE_FORMAT(assessment_date,'%Y-%m-%d') = '" + assessmentDate.trim().substring(0, 10) + "'");
```



## 6. 一些注解

### 1. updateStrategy = FieldStrategy.IGNORED

```java
 @TableField(value = "`assessment_staff`", updateStrategy = FieldStrategy.IGNORED)
```

其中  updateStrategy = FieldStrategy.IGNORED 的意思是 可以把这个字段更新为null, 

另外, 更新时 是 全字段更新, 即 假如前端没有 传这个 字段 , 更新时, 就会把这个字段更新为 null 

缺点:  不能 指定字段更新;



# 57. 一些代码



## 1. 写了个转驼峰 的方法

```java
 /**
     * 转驼峰
     * @author: wg
     * @time: 2020/4/8 10:54
     */
    public String getHumpString(String string) {

        String[] s = string.split("_");
        StringBuilder stringBuilder= new StringBuilder(s[0]);

        for (int k = 0; k < s.length-1; k++) {
            stringBuilder.append(s[k + 1].substring(0, 1).toUpperCase() + s[k + 1].substring(1)) ;
        }

        return stringBuilder.toString();
    }
```



## 2. 合并两个list 并去重 用Stream

```java
collect = Stream.of(bankFlowList, list)
                      .flatMap(Collection::stream)
                      .distinct()
                      .collect(Collectors.toList());
```



## 3. map转实体类,阿里的json包

```java
BankFlow bankFlow = JSON.parseObject(JSON.toJSONString(bankFlowMap), BankFlow.class);
```



## 4. 去掉数组中的null 和 空值 

```java
		String[] titleNames = readExcelUtil.readExcelTitle(0);

        ArrayList<String> arrayList = new ArrayList<>();
        for (int i = 0; i < titleNames.length; i++) {
            if (titleNames[i] != null) {
                arrayList.add(titleNames[i]);
            }
        }
```



## 5. 数组 与 list 转换

```java
String[] titles = (String[]) arrayList.toArray();
```



## 6. try catch 的作用

  遇到异常,依然往下走





## 7. 枚举与map

````java
package org.jeecg.modules.app.enums;


import java.util.EnumSet;
import java.util.HashMap;
import java.util.Map;

/**
 * @author wg
 * @Package org.jeecg.modules.app.enums
 * @date 2020/4/16 9:51
 * @Copyright
 */
public enum Title {


    TRANSACTION_SUBJECT("transaction_subject", "交易主体"),
    ACCOUNT_SUBJECT("account_subject", "交易主体账号"),
    CARD_ENTITY("card_entity", "交易主体卡号"),
    RECOVERY_MARK("recovery_mark", "收付标志"),
    TRANSACTION_DATE("transaction_date", "交易日期"),
    COUNTER_PARTY("counter_party", "交易对手"),
    ACCOUNT_COUNTERPARTY("account_counterparty", "交易对手账号"),
    CARD_COUNTERPARTY("card_counterparty", "交易对手卡号"),
    TRANSACTION_AMOUNT("transaction_amount", "交易金额"),
    ABSTRACT_CONTENT("abstract_content", "摘要"),
    BALANCE_TRANSACTION("balance_transaction", "交易后余额"),
    TRANSACTION_BANK("transaction_bank", "交易主体归属行"),
    COUNTERPARTY_BANK("counterparty_bank", "交易对手归属行"),
    PLACE_TRANSACTION("place_transaction", "交易地点"),
    TRADING_PLACE("trading_place", "交易方式"),
    TRANSACTION_NUMBER("transaction_number", "交易流水号"),
    MAC("mac", "MAC地址"),
    IP("ip", "IP地址"),
    CURRENCY("currency", "币种"),
    TEMARKS("temarks", "备注"),
    TRADING_NO("trading_no", "交易机构号"),
    TELLER_NUMBER("teller_number", "柜员号"),
    INSTITUTION_PARTY("institution_party", "对方机构号"),
    SUCCESSFUL_NOT("successful_not", "交易是否成功"),
    LOG_NUMBER("log_number", "日志号"),
    CUSTOMER_CODE("customer_code", "客户代码"),
    APSH_PLACE("apsh_place", "APSH地点线索"),
    MATCHER_CODE("matcher_code", "交易对手客户代码"),
    MATCHER_BALANCE("matcher_balance", "对手交易后余额"),
    SUBJECT_CREDENTIALS("subject_credentials", "交易主体证件号"),
    ADVERSARY_CREDENTIALS("adversary_credentials", "交易对手证件号"),
    TRANSACTION_RECORDS_ID("transaction_records_id", "交易记录ID"),
    REPORT_ORGANIZATION("report_organization", "报告机构"),
    SHE_WAI_FEN_LEI("she_wai_fen_lei", "涉外收支分类"),
    AGENT_NAME("agent_name", "代办人名称"),
    AGENT_CREDENTIALS("agent_credentials", "代办人证件号码"),
    VOUCHER_NUMBER("voucher_number", "凭证号码"),
    VOUCHER_TYPE("voucher_type", "凭证类型");


    private String fileCode;
    private String fileName;

    private static final Map<String, String> titleMap = new HashMap<>();

    static {
        for (Title title : EnumSet.allOf(Title.class)) {

            titleMap.put(title.getFileCode(), title.getFileName());
        }

    }


    Title(String fileCode, String fileName) {
        this.fileCode = fileCode;
        this.fileName = fileName;
    }

    public String getFileCode() {
        return fileCode;
    }


    public String getFileName() {
        return fileName;
    }


    public static Map<String, String> getTitleMap() {
        return titleMap;
    }
}

````

## 8. LinkedMultiValueMap

```java
    public static LinkedMultiValueMap<Object, Object> getRiskMap(List<RiskAssessmentHistoryEntity> historyEntityList) {
        LinkedMultiValueMap<Object, Object> multiValueMap = new LinkedMultiValueMap<>();
        if (historyEntityList.size() > 0) {
            Map<String, List<RiskAssessmentHistoryEntity>> assessmentCodeMap = historyEntityList.stream()
                    .filter(t -> !ObjectUtils.isEmpty(t.getAssessmentCode()))
                    .collect(Collectors.groupingBy(RiskAssessmentHistoryEntity::getAssessmentCode));

            // HashMap<String, Object> hashMap = new HashMap<>();
            // Map<String, Map<String, Long>> maxRiskLevelMap = new HashMap<>();
            // k = assessmentCode
            assessmentCodeMap.forEach((k, v) -> {
                List<RiskAssessmentHistoryEntity> entityList = v.stream().filter(t -> StringUtils.isNotBlank(t.getMaxRiskLevel())).collect(Collectors.toList());

                Map<String, Long> countMap = entityList.stream()
                        .collect(Collectors.groupingBy(RiskAssessmentHistoryEntity::getMaxRiskLevel, Collectors.counting()));
                multiValueMap.add(k, countMap);
                // HashMap<String, Long> stringLongHashMap = new HashMap<>();
                // countMap.forEach((k1, v1) -> stringLongHashMap.put(k1 + "个数", v1));
                // multiValueMap.add(k, stringLongHashMap);

                // HashMap<String, String> percentageMap = new HashMap<>();
                //
                // countMap.forEach((riskLevel, count) -> {
                //     BigDecimal multiply = new BigDecimal(count).divide(new BigDecimal(entityList.size()), 10, RoundingMode.HALF_UP).multiply(new BigDecimal(100)).setScale(2, RoundingMode.HALF_UP);
                //     // percentageMap.put(riskLevel, multiply.toString());
                //     percentageMap.put(riskLevel + "百分比", multiply.toString());
                // });
                //
                // multiValueMap.add(k, percentageMap);
            });

            multiValueMap.forEach((k, v) -> System.out.println(k + " = " + v));
            return multiValueMap;
        }
        return multiValueMap;
    }
```





<div style="page-break-after:always"></div>



# 58. 注意



## 1. 定义实体类, 尽量用 Integer Double 等包装类型, 不要 使用int double 等基础数字类型



## 2. 所有整型包装类对象之间值的比较，全部使用 equals 方法比较。

说明：对于 Integer var = ? 在-128 至 127 之间的赋值，Integer 对象是在 IntegerCache.cache 产生，
会复用已有对象，这个区间内的 Integer 值可以直接使用==进行判断，但是这个区间之外的所有数据，都
会在堆上产生，并不会复用已有对象，这是一个大坑，推荐使用 equals 方法进行判断。



## 3. 任何货币金额，均以最小货币单位且整型类型来进行存储。



## 4. 浮点数之间的等值判断，基本数据类型不能用==来比较, 包装数据类型不能用 equals
来判断

正例：
(1) 指定一个误差范围，两个浮点数的差值在此范围之内，则认为是相等的。
float a = 1.0f - 0.9f;
float b = 0.9f - 0.8f;
float diff = 1e-6f;
if (Math.abs(a - b) < diff) {
System.out.println("true");
}
(2) 使用 BigDecimal 来定义值，再进行浮点数的运算操作。
BigDecimal a = new BigDecimal("1.0");
BigDecimal b = new BigDecimal("0.9");
BigDecimal c = new BigDecimal("0.8");
BigDecimal x = a.subtract(b);
BigDecimal y = b.subtract(c);
if (x.equals(y)) {
System.out.println("true");
}



## 5. 禁止使用构造方法 BigDecimal(double)的方式把 double 值转化为 BigDecimal 对象。

说明：BigDecimal(double)存在精度损失风险，在精确计算或值比较的场景中可能会导致业务逻辑异常。

如：BigDecimal g = new BigDecimal(0.1f); 实际的存储值为：0.10000000149



## 6. 【强制】判断所有集合内部的元素是否为空，使用 isEmpty()方法，而不是 size()==0 的方式。
说明：前者的时间复杂度为 O(1)，而且可读性更好。



## 7. 【强制】使用集合转数组的方法，必须使用集合的 toArray(T[] array)，传入的是类型完全一
致、长度为 0 的空数组。
反例：直接使用 toArray 无参方法存在问题，此方法返回值只能是 Object[]类，若强转其它类型数组将出现
ClassCastException 错误。
正例：
List list = new ArrayList<>(2);
list.add("guan");
list.add("bao");
String[] array = list.toArray(new String[0]);
说明：使用 toArray 带参方法，数组空间大小的 length，
1） 等于 0，动态创建与 size 相同的数组，性能最好。
2） 大于 0 但小于 size，重新创建大小等于 size 的数组，增加 GC 负担。
3） 等于 size，在高并发情况下，数组创建完成之后，size 正在变大的情况下，负面影响与 2 相同。
4） 大于 size，空间浪费，且在 size 处插入 null 值，存在 NPE 隐患。



## 8. 【强制】使用工具类 Arrays.asList()把数组转换成集合时，不能使用其修改集合相关的方法，
它的 add/remove/clear 方法会抛出 UnsupportedOperationException 异常。
说明：asList 的返回对象是一个 Arrays 内部类，并没有实现集合的修改方法。Arrays.asList 体现的是适配
器模式，只是转换接口，后台的数据仍是数组。
String[] str = new String[] { "yang", "hao" };
List list = Arrays.asList(str);
第一种情况：list.add("yangguanbao"); 运行时异常。
第二种情况：str[0] = "changed"; 也会随之修改，反之亦然



## 9. 泛型通配符`<? extends T>`来接收返回的数据，此写法的泛型集合不能使用 add 方法，
而 `<? super T>` 不能使用 get 方法，两者在接口调用赋值的场景中容易出错。
说明：扩展说一下 PECS(Producer Extends Consumer Super)原则：第一、频繁往外读取内容的，适合用 `<? extends T> `

第二、经常往里插入的，适合用`<? super T>`

# 59. java 命令



|                                   |                  |      |
| --------------------------------- | ---------------- | ---- |
| javac -encoding utf-8 文件名.java | 编译成.class文件 |      |
|                                   |                  |      |
|                                   |                  |      |





# 60. 后台接收前台数据

1.   

   前台数据 post -> obj

   后台接收 @RequestBody(required = false) JSONObject jsonObject, 注意要加注解 





# 61 供与校验的注解



**JSR提供的校验注解**

```tex
@Null   被注释的元素必须为 null    
@NotNull    被注释的元素必须不为 null    
@AssertTrue     被注释的元素必须为 true    
@AssertFalse    被注释的元素必须为 false    
@Min(value)     被注释的元素必须是一个数字，其值必须大于等于指定的最小值    
@Max(value)     被注释的元素必须是一个数字，其值必须小于等于指定的最大值    
@DecimalMin(value)  被注释的元素必须是一个数字，其值必须大于等于指定的最小值    
@DecimalMax(value)  被注释的元素必须是一个数字，其值必须小于等于指定的最大值    
@Size(max=, min=)   被注释的元素的大小必须在指定的范围内    
@Digits (integer, fraction)     被注释的元素必须是一个数字，其值必须在可接受的范围内    
@Past   被注释的元素必须是一个过去的日期    
@Future     被注释的元素必须是一个将来的日期    
@Pattern(regex=,flag=)  被注释的元素必须符合指定的正则表达式
```



**Hibernate Validator提供的校验注解**

```tex
@NotBlank(message =)   验证字符串非null，且长度必须大于0    
@Email  被注释的元素必须是电子邮箱地址    
@Length(min=,max=)  被注释的字符串的大小必须在指定的范围内    
@NotEmpty   被注释的字符串的必须非空    
@Range(min=,max=,message=)  被注释的元素必须在合适的范围内
```





# 62 定义char时加单引号与不加单引号的区别



不加单引号：在不超过范围情况下可定义多个数字，不能定义字符
定义的数值是国际编码表的码值，此码之会对应一个特定的字符
单独输出时输出的是对应的特定字符
运算时转换为int型的数字（数字的值就本身）

加单引号;只能定义一个字符
定义的字符为国际编码表中特定的字符，此字符对应一个码值
单独输出时输出的是字符本身
运算时转换为int型的数字(数字的值是字符对应的码值)



```java
@Test
    public void basicDataType(){

        char a=1;
        System.out.println(a);

        char b='1';
        System.out.println(b);

        char c=2;
        char d=c;
        int e=c;
        System.out.println(c);
        System.out.println(d);
        System.out.println(e);

    }
```

输出为: 

![](.\img\企业微信截图_16249334068073.png)







# 63. 融合, 合并两个实体类, 

1. 使用 注解

   ```java
   @Mappings({
               @Mapping(source = "entity.id", target = "id")
       })
       public abstract RouteSurveyDetailDTO toDTO(RouteSurveyDetailEntity entity, RouteSurveyHistoryEntity routeSurveyHistoryEntity);
   ```




# 64. list  isEmpty()  size



1. list=null,意味着list在堆中不存在，根本没有地址，如果此时操作list会报空指针异常。

2. list.size()=0，意思堆内有list对象，但是还没来得及放元素，其长度随着元素数量变化而变化，暂时为零。

3. list.isEmpty()跟list.size()差不多 只不过返回的时布尔类型。



当list.add(null) ，此时list.size()=1，所以list.isEmpty()=false



<div style="page-break-after:always"></div>



# 65. stream



## 1. stream filter 多条件过滤

```java
			Stream<ConstructionDataDTO> dtoStream = dtoList.stream();
            List<ConstructionDataDTO> collect = dtoStream.filter(item -> {
                return seachMap.entrySet().stream().allMatch(map -> {
                    return map.getValue().equals(item.getPipelineName())
                            || map.getValue().equals(item.getPipelineNumber())
                            || map.getValue().equals(item.getSmys())
                            || map.getValue().equals(item.getType());
                });
            }).collect(Collectors.toList());
```



两个list 

```java
dictDTOList.stream().filter(item -> {
            return dictTypeDTOList.stream().map(CommonDictTypeDTO::getId).collect(Collectors.toList()).contains(item.getCommonDictTypeId());
        }).collect(Collectors.toList());
```



## 2. sorted()

```java
list.stream().sorted(Comparator.comparing(Student::getAge)) 

list.stream().sorted(Comparator.comparing(Student::getAge).reversed()) 
```

```java
detailEntityList = detailEntityList.stream()
                .filter(detail -> getter(detailFieldMap.get("limitFractureRatioHistoryFieldName"), detail) != null)
                .sorted(Comparator.comparing(e -> {
                    BigDecimal limitFractureRatio = new BigDecimal(getter(detailFieldMap.get("limitFractureRatioHistoryFieldName"), e).toString());
                    BigDecimal fractureRatio = new BigDecimal(getter(detailFieldMap.get("fractureRatioHistoryFieldName"), e).toString());
                    return limitFractureRatio.subtract(fractureRatio).divide(limitFractureRatio, 10, RoundingMode.HALF_UP).setScale(3, RoundingMode.HALF_UP);
                }, (e1, e2) -> e1.compareTo(e2))) // 从小到大 排
                .collect(Collectors.toList());
```



## 3.  stream parallelStream

**串行流**：适合存在线程安全问题、阻塞任务、重量级任务，以及需要使用同一事务的逻辑。

**并行流**：适合没有线程安全问题、较单纯的数据处理任务



## 4. peek, map

1. peek接收一个Consumer，而map接收一个Function。
2. Consumer是没有返回值的，它只是对Stream中的元素进行某些操作，但是操作之后的数据并不返回到Stream中，所以Stream中的元素还是原来的元素。
3. 而Function是有返回值的，这意味着对于Stream的元素的所有操作都会作为新的结果返回到Stream中

#### 中间操作和终止操作

1. 一个java 8的stream是由三部分组成的。数据源，零个或一个或多个中间操作，一个或零个终止操作。
2. 中间操作是对数据的加工，注意，中间操作是lazy操作，并不会立马启动，需要等待终止操作才会执行。
3. 终止操作是stream的启动操作，只有加上终止操作，stream才会真正的开始执行。
4. 从Java 9开始，如果元素的数量预先已知并且在流中保持不变，则由于性能优化，将不会执行.peek()语句。可以通过命令(正式)更改元素数量(例如， .filter(x-> true)
5. 在没有任何终端操作的情况下使用peek不会执行任何操作



## 5. 取 对象 list 的 最大 最小

```java
             T t1 = list.stream()
                     .filter((T t) -> getter(detailFieldMap.get("remainStrengthDetailFieldName"), t) != null)
                     .min(Comparator.comparing(e -> (BigDecimal) getter(detailFieldMap.get("remainStrengthDetailFieldName"), e)))
                     .get();
```



## 6. group

```java
Map<String, List<InformationSchema>> collect = informationSchemaDao.selectInformationSchema(tableName).stream().collect(Collectors.groupingBy(InformationSchema::getColumnName));
```



对象的某个字段分组, 然后返回 对象的 某一个字段

```java
  
            // 试验压力[MPa]
            Set<Long> spBasicIds = dtoList.stream().map(InstallationDTO::getSpBasicId).collect(Collectors.toSet());
            QueryWrapper<BaselinePressureTestEntity> baselinePressureTestEntityQueryWrapper = new QueryWrapper<>();
            baselinePressureTestEntityQueryWrapper.lambda().in(spBasicIds.size() > 0, BaselinePressureTestEntity::getSpBasicId, spBasicIds);
            Map<Long, List<BigDecimal>> pressureMap = baselinePressureTestDao
                    .selectList(baselinePressureTestEntityQueryWrapper)
                    .stream()
                    .collect(Collectors.groupingBy(BaselinePressureTestEntity::getSpBasicId, Collectors.mapping(BaselinePressureTestEntity::getPressure, Collectors.toList())));
```





## 7. 求和

 ```java
BigDecimal:
BigDecimal bb =list.stream().map(Plan::getAmount).reduce(BigDecimal.ZERO,BigDecimal::add);
 

int、double、long:
double max = list.stream().mapToDouble(User::getHeight).sum();
 ```



## 8. 取差集

注意两个list 的长度

```java
	@Test
    public void chajitest() {
        Student student = new Student();
        ArrayList<Student> list1 = new ArrayList<>();
        for (int i = 0; i < 3; i++) {
            student = new Student();
            student.setAge(i);
            list1.add(student);

        }

        ArrayList<Student> list2 = new ArrayList<>(list1);
        for (int i = 0; i < 1; i++) {
            student = new Student();
            student.setAge(4);
            list2.add(student);
        }

        // list2.clear();
        // student = new Student();
        // student.setAge(4);
        // list2.add(student);

        System.out.println("list1  ");
        list1.forEach(System.out::println);
        System.out.println("list2  ");
        list2.forEach(st -> System.out.println(st));

        System.out.println();
        List<Student> collect = list1.stream()
                .filter(st -> !list2.contains(st))
                .collect(Collectors.toList());
        System.out.println("collect差集  " + collect.size());
        collect.forEach(System.out::println);

        System.out.println();
        List<Student> collect2 = list2.stream()
                .filter(st -> !list1.contains(st))
                .collect(Collectors.toList());
        System.out.println("collect2差集  " + collect2.size());
        collect2.forEach(System.out::println);
    }
```



## 9, 取日期最大值的对象

```java
lastedExpirationDate = spBasicId$AssessmentLife.get(spBasic.getId())
                        .stream()
                        .max(Comparator.comparing(AssessmentLifeExtensionHistoryEntity::getAssessmentDate))
                        .get()
                        .getLatestDuetime();
```



## 10. 分组求和

```java
 Map<String, IntSummaryStatistics> collect1 = scoreDtos
                .stream()
                .collect(Collectors.groupingBy(ScoreDto::getGradeClass, Collectors.summarizingInt(ScoreDto::getScore)));
```



## 11. 分组求最小

```java
 Map<String, Optional<ScoreDto>> collect1 = scoreDtos.stream()
                .filter(item -> StringUtils.isNotBlank(item.getGradeClass()) && item.getScore() != null)
                .collect(Collectors.groupingBy(ScoreDto::getGradeClass, Collectors.minBy(Comparator.comparingInt(ScoreDto::getScore))));
```



## 12. 根据某个字段去重

```java
 songList = songList.stream()
                .collect(
                        Collectors.collectingAndThen(
                                Collectors.toCollection(
                                        () -> new TreeSet<>(
                                                Comparator.comparing(t -> t.getHexHash())
                                        )
                                ), ArrayList::new)
                );
```



## 13. 相同部分

```java
           List<Song> listSame = songList.stream()
                    .filter(song ->
                            songs.stream()
                                    .map(Song::getHexHash)
                                    .collect(Collectors.toList())
                                    .contains(song.getHexHash())
                                    &&
                                    songs.stream()
                                            .map(Song::getLocation)
                                            .collect(Collectors.toList())
                                            .contains(song.getLocation())
                    )
                    .collect(Collectors.toList());
```



## 14. 不同部分, 对象的某个属性不同

```java
                // List<Song> databaseSongs
				List<Song> listDiff = songList.stream()
                    .filter(song ->
                            !databaseSongs.stream()
                                    .map(Song::getHexHash)
                                    .collect(Collectors.toList())
                                    .contains(song.getHexHash())
                                    ||
                                    !databaseSongs.stream()
                                            .map(Song::getLocation)
                                            .collect(Collectors.toList())
                                            .contains(song.getLocation())
                    )
                    .collect(Collectors.toList());
```



# 66. function



|                         |                                                              |                                                              |
| ----------------------- | ------------------------------------------------------------ | ------------------------------------------------------------ |
| getOrDefault()          | 获取指定 key 对应对 value，如果找不到 key ，则返回设置的默认值<br /> | default V getOrDefault(Object key, V defaultValue) {    <br />     V v;    <br />     return (((v = get(key)) != null) \|\| containsKey(key))         ? v         : defaultValue;<br /> } |
| Collectors.groupingBy() | list转map, 以类中的某个属性的值分组, 以这个属性的值为键      | myList.stream().collect(Collectors.groupingBy(Section::getPropertyName)) |
|                         |                                                              |                                                              |





# 67. string



## 1. string.format()



| 转换符 | 详细说明                                     | 示例                     |
| :----- | :------------------------------------------- | :----------------------- |
| %s     | 字符串类型                                   | “喜欢请收藏”             |
| %c     | 字符类型                                     | ‘m’                      |
| %b     | 布尔类型                                     | true                     |
| %d     | 整数类型（十进制）                           | 88                       |
| %x     | 整数类型（十六进制）                         | FF                       |
| %o     | 整数类型（八进制）                           | 77                       |
| %f     | 浮点类型                                     | 8.888                    |
| %a     | 十六进制浮点类型                             | FF.35AE                  |
| %e     | 指数类型                                     | 9.38e+5                  |
| %g     | 通用浮点类型（f和e类型中较短的）             | 不举例(基本用不到)       |
| %h     | 散列码                                       | 不举例(基本用不到)       |
| %%     | 百分比类型                                   | ％(%特殊字符%%才能显示%) |
| %n     | 换行符                                       | 不举例(基本用不到)       |
| %tx    | 日期与时间类型（x代表不同的日期与时间转换符) | 不举例(基本用不到)       |



| 标志               | 说明                                                     | 示例                   | 结果             |
| :----------------- | :------------------------------------------------------- | :--------------------- | :--------------- |
| +                  | 为正数或者负数添加符号                                   | (“%+d”,15)             | +15              |
| 0                  | 数字前面补0(加密常用)                                    | (“%04d”, 99)           | 0099             |
| 空格               | 在整数之前添加指定数量的空格                             | (“% 4d”, 99)           | 99               |
| ,                  | 以“,”对数字分组(常用显示金额)                            | (“%,f”, 9999.99)       | 9,999.990000     |
| (                  | 使用括号包含负数, 把负值去掉负号, 然后用括号包起来       | (“%(f”, -99.99)        | (99.990000)      |
| #                  | 如果是浮点数则包含小数点，如果是16进制或8进制则添加0x或0 | (“%#x”, 99)(“%#o”, 99) | 0x63 0143        |
| <                  | 格式化前一个转换符所描述的参数                           | (“%f和%<3.2f”, 99.45)  | 99.450000和99.45 |
| d,%2$s”, 99,”abc”) | 99,abc                                                   |                        |                  |



| 标志 | 说明                        | 示例                             |
| :--- | :-------------------------- | :------------------------------- |
| %tc  | 包括全部日期和时间信息      | 星期六 十月 27 14:21:20 CST 2007 |
| %tF  | “年-月-日”格式              | 2007-10-27                       |
| %tD  | “月/日/年”格式              | 10/27/07                         |
| %tr  | “HH:MM:SS PM”格式（12时制） | 02:25:51 下午                    |
| %tT  | “HH:MM:SS”格式（24时制）    | 14:28:16                         |
| %tR  | “HH:MM”格式（24时制）       | 14:28                            |

转换符	说明	结果
%tH	小时(00~23)	15
%tI	小时(01~12)	03
%tk	小时(0~23)	15
%tl	小时(1~12)	3
%tM	分钟(00~59)	35
%tS	秒(00~59)	55
%tL	毫秒(000~999)	923
%tN	9位数微妙(000000000~999999999)	923000000
%tp	当前语言环境下上午/下午	下午
%tz	时区	+0800
%tZ	时区	CST
%ts	从1970-01-01 00:00:00 到现在的秒	1526196955
%tQ	从1970-01-01 00:00:00 到现在的毫秒	1526196955923



转换符	说明	结果
%tb	指定语言环境下的月份简称	五月
%tB	指定语言环境下的月份全称	五月
%ta	指定语言环境下周几的简称	星期日
%tA	指定语言环境下周几的全称	星期日
%ty	2位数的年份	18
%tY	4位数年份	2018
%tm	月份	05
%te	一个月中的某一天(1~31)	13
%td	一个月中的某一天(01~31)	13
%tj	一年中第几天	133



### 1. 左对齐

```java
// 左对齐, 10 是总长度 ; 右对齐的话 改成 %10s
String.format("%-10s", str)
```



## 2. (String)、toString、String.valueOf 的区别



1. string.valueof 尽量不要用 string.valueof



```java
 /*****************************************************
    * @params:
    * @description: (String)、toString、String.valueOf 的区别
    * @author: wg
    * @date: 2021/8/12 11:46
    *****************************************************/
    public static void main(String[] args) {
        Object s = null;

        System.out.println(String.valueOf(s) == null); // false
        System.out.println(String.valueOf(s).equals("null")); // true

        if (StringUtils.isBlank(String.valueOf(s))){
            System.out.println("s is blank");
        }

        String s1 = (String) s; // 不报异常
        s.toString(); // 报异常  空指针
        
        Object a = new Integer(1);
        String as = (String) a; // 报异常 ClassCastException  .Integer cannot be cast to java.lang.String
    }
```

2.  tostring()

   params 是个map, 

```java
String pipelineNumber =  params.get("pipelineNumber").toString(); // 会报异常
if (params.containsKey("pipelineNumber") && StringUtils.isNotBlank(pipelineNumber)) {

}
```

如上例子, 如果 params.get("pipelineNumber") 为 null, 或 params 没有 pipelineNumber 这个key,  那么 在tostring() 这个步骤就会 报错, 下面的if 就不会 运行了



3. (string) 强转

   ```java
   String pipelineNumber = (String) params.get("pipelineNumber"); // 不会报异常
   ```




## 3. 字符串的最大长度

简单总结

String 的长度是有限制的。

- 编译期的限制：字符串的UTF8编码值的字节数不能超过65535，字符串的长度不能超过65534；
- 运行时限制：字符串的长度不能超过2^31-1，占用的内存数不能超过虚拟机能够提供的最大值



### 编译期限制

有JVM虚拟机相关知识的同学肯定知道，下面定义的字符串常量“自由之路”会被放入方法区的常量池中。

```java
String s = "自由之路";
System.out.println(s);
```

Stirng 长度之所以会受限制，是因JVM规范对常量池有所限制。常量池中的每一种数据项都有自己的类型。Java中的UTF-8编码的Unicode字符串在常量池中以CONSTANT_Utf8类型表示。

CONSTANT_Utf8的数据结构如下：

```
CONSTANT_Utf8_info {
    u1 tag;
    u2 length;
    u1 bytes[length];
}
```

我们重点关注下长度为 length 的那个bytes数组，这个数组就是真正存储常量数据的地方，而 length 就是数组可以存储的最大字节数。length 的类型是u2，u2是无符号的16位整数，因此理论上允许的的最大长度是2^16-1=65535。所以上面byte数组的最大长度可以是65535。

```java
//65535个d，编译报错
String s = "dd..dd";

//65534个d，编译通过
String s1 = "dd..d";
```

上面的列子中长度为65535的字符串s还是编译失败了，但是长度为65534的字符串 s1 编译是成功的。这个好像和我们刚刚的结论不符合。

其实，这是Javac编译器的额外限制。在Javac的源代码中可以找到以下代码：

```java
private void checkStringConstant(DiagnosticPosition var1, Object var2) {
    if (this.nerrs == 0 && var2 != null && var2 instanceof String &&   ((String)var2).length() >= 65535) {
        this.log.error(var1, "limit.string", new Object[0]);
        ++this.nerrs;
    }
}
```

代码中可以看出，当参数类型为String，并且长度大于等于65535的时候，就会导致编译失败。

这里需要重点强调下的是：String 的限制还有一个部分，那就是对字符串底层存储的字节数的限制。**也就是说：在编译时，一个字符串的长度大于等于65535或者底层存储占用的字节数大于65535时就会报错**。这句话可能比较抽象，下面举个列子就清楚了。

Java中的字符常量都是使用UTF8编码的，UTF8编码使用1~4个字节来表示具体的Unicode字符。所以有的字符占用一个字节，而我们平时所用的大部分中文都需要3个字节来存储。

```java
//65534个字母，编译通过
String s1 = "dd..d";

//21845个中文”自“,编译通过
String s2 = "自自...自";

//一个英文字母d加上21845个中文”自“，编译失败
String s3 = "d自自...自";
```

对于s1，一个字母d的UTF8编码占用一个字节，65534字母占用65534个字节，长度是65534，长度和存储都没超过限制，所以可以编译通过。

对于s2，一个中文占用3个字节，21845个正好占用65535个字节，而且字符串长度是21845，长度和存储也都没超过限制，所以可以编译通过。

对于s3，一个英文字母d加上21845个中文”自“占用65536个字节，超过了存储最大限制，编译失败。

### 运行时限制

String 运行时的限制主要体现在 String 的构造函数上。下面是 String 的一个构造函数：

```java
public String(char value[], int offset, int count) {
    ...
}
```

上面的count值就是字符串的最大长度。在Java中，int的最大长度是2^31-1。所以在运行时，String 的最大长度是2^31-1。

但是这个也是理论上的长度，实际的长度还要看你JVM的内存。我们来看下，最大的字符串会占用多大的内存。

```
(2^31-1)*2*16/8/1024/1024/1024 = 4GB
```

所以在最坏的情况下，一个最大的字符串要占用4GB的内存。如果你的虚拟机不能分配这么多内存的话，会直接报错的。

JDK9以后对String的存储进行了优化。底层不再使用char数组存储字符串，而是使用byte数组。对于LATIN1字符的字符串可以节省一倍的



## 4.  isNotBlank, isEmpty (org.apache.commons.lang3)

isEmpty判断的范围更小

isNotBlank 还检查了 空白字符

```java
/** It is a Unicode space character ({@link #SPACE_SEPARATOR},
*      {@link #LINE_SEPARATOR}, or {@link #PARAGRAPH_SEPARATOR})
*      but is not also a non-breaking space ({@code '\u005Cu00A0'},
*      {@code '\u005Cu2007'}, {@code '\u005Cu202F'}).  
*/
public static boolean isBlank(CharSequence cs) {
        int strLen;
        if (cs != null && (strLen = cs.length()) != 0) {
            for(int i = 0; i < strLen; ++i) {
                if (!Character.isWhitespace(cs.charAt(i))) {
                    return false;
                }
            }

            return true;
        } else {
            return true;
        }
    }
```



## 5. hashcode() hash算法

`String` 类重写了 `hashCode()` 方法，它返回一个整数值，通常用来快速查找对象。这个哈希值是基于字符串内容计算出来的，目的是让具有相同内容的字符串产生相同的哈希码。这样可以提高在集合类（如 `HashMap` 和 `HashSet`）中查找字符串的效率

String 类的哈希算法实现相当复杂，但简而言之，它是通过将每个字符转换为整数，并将这些整数进行加法和乘法运算来生成哈希码。具体来说，Java 的 String 类使用以下公式计算哈希码：

```
s[0]*31^(n-1) + s[1]*31^(n-2) + ... + s[n-1]
```

其中 `s` 是字符串，`n` 是字符串的长度。可以看到，这是一个乘法和加法的组合操作，而且乘以的是素数 `31`，这有助于减少哈希冲突的可能性。



# 68. pom常见内置变量

```
${basedir} 项目根目录
${project.build.directory} 构建目录，缺省为target
${project.build.outputDirectory} 构建过程输出目录，缺省为target/classes
${project.build.finalName} 产出物名称，缺省为${project.artifactId}-${project.version}
${project.packaging} 打包类型，缺省为jar
${project.xxx} 当前pom文件的任意节点的内容
```





# 69. github 库的token

有效期3个月

```
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
```



# 64. 同一个包内, @requestmapping 不能有相同的value





# 65. PostMapping  PutMapping

如我们发送两个请求，服务器端是什么样的行为？

如果产生了不相同的效果，那就说明这个服务不是idempotent的，因为多次使用产生了副作用了嘛；如果是这种情况，每次返回结果不一样的时候，应该使用POST方法，

如果后一个请求把第一个请求覆盖掉了，那这个服务就是idempotent的。如果是这种情况，应该使用PUT方法。

常见的有:

  如果执行添加操作, 后面的添加请求不会覆盖前面的请求, 所以使用@Postmapping

  如果执行修改操作, 后面的修改请求会把前面的请求给覆盖掉, 所以使用@PutMapping



# 66. log4j 等级



 off, fatal, error, warn, info, debug, trace, all



<div style="page-break-after:always"></div>



# 67. springboot 配置 https

```
# 配置https
server.ssl.enabled=true
server.ssl.key-store=d:/tomcat.keystore
server.ssl.key-store-type=JKS
server.ssl.key-store-password=123456
server.ssl.key-alias=tomcat
```



![](.\img\jdk配置https过程.png)



下面的命令也是: 

> keytool -genkey -alias tomcat -keypass 123456 -keyalg RSA -keysize 2048 -validity 36500 -keystore D:/tomcat.keystore -storepass 123456
>
> 创建了一个名为tomcat.keystore的别名也为tomcat.keystore的采用RSA加密算法的有效期为100年的证书文件
> -genkey    生成文件。
> -alias        别名。
> -keyalg     指定加密算法。
> -validity    有效期 天数。
> -keystore  指定文件文件名。



最新的用 -genkeypair (-genkey 是老版本的命令) , 参见 `https://docs.oracle.com/javase/8/docs/technotes/tools/unix/keytool.html`

```shell
# 生成 .keystore
keytool -genkeypair -alias tomcat9-wg01 -keypass 123456 -keyalg RSA -keysize 2048 -validity 36500 -keystore /usr/local/keystore/tomcat9-wg01.keystore -storepass 123456

# 查看密钥信息
keytool -list -v -keystore /usr/local/keystore/tomcat9-wg01.keystore -storepass 123456

# 导出证书
keytool -export -alias tomcat9-wg01 -keystore /usr/local/keystore/tomcat9-wg01.keystore -rfc -file /usr/local/keystore/test.cer

# .cer 转换成 .crt
openssl x509 -inform PEM -in /usr/local/keystore/test.cer -out /usr/local/keystore/test.crt

# 查看证书详情 keytool -printcert -file 证书名
keytool -printcert -file test.cer

# .key 转换成 .pem：
openssl rsa -in test.key -out test.pem

# .crt 转换成 .pem：
openssl x509 -in test.crt -out test.pem

# .cer 转换成 .crt
openssl x509 -inform PEM -in test.cer -out test.crt


```





# 68. 静态代码块

随着类的加载而执行，而且只执行一次,  执行完成便销毁, 它仅能初始化类变量





# 69. 对象的销毁

>  finalize

在 Java 虚拟机的堆区，每个对象都可能处于以下三种状态之一。

(1) 可触及状态：当一个对象被创建后，只要程序中还有引用变量引用它，那么它就始终处于可触及状态。

(2) 可复活状态：当程序不再有任何引用变量引用该对象时，该对象就进入可复活状态。在这个状态下，垃圾回收器会准备释放它所占用的内存，在释放之前，会调用它及其他处于可复活状态的对象的 finalize() 方法，这些 finalize() 方法有可能使该对象重新转到可触及状态。

(3) 不可触及状态：当 Java 虚拟机执行完所有可复活对象的 finalize() 方法后，如果这些方法都没有使该对象转到可触及状态，垃圾回收器才会真正回收它占用的内存。



> 一个对象被当作垃圾回收的情况主要如下两种。
> 1. 对象的引用超过其作用范围。
>   {
>    Object o=new Object();    //对象o的作用范围，超过这个范围对象将被视为垃圾
>   }
> 2. 对象被赋值为 null。
>   {
>    Object o=new Object();
>    o=null;    //对象被赋值为null将被视为垃圾
>   }



注: 调用 System.gc() 或者 Runtime.gc() 方法也不能保证回收操作一定执行，它只是提高了 Java 垃圾回收器尽快回收垃圾的可能性



java中的并非总是被垃圾回收，也就是说对象可能不被回收。一般程序只要不到濒临存储空间用光，垃圾回收器一般都不会主动回收内存，

```java
package wg.application.gc;

import java.math.BigDecimal;

public class GcEntity {
    private BigDecimal money;

    public GcEntity(BigDecimal money) {
        this.money = money;
    }

    public GcEntity() {
    }

    public BigDecimal getMoney() {
        return money;
    }

    public void setMoney(BigDecimal money) {
        this.money = money;
    }

    @Override
    protected void finalize() throws Throwable {
        if (money.doubleValue() <= 0D) {
            System.out.println("dead : " + money);
        } else {
            System.out.println("success : " + money);
        }

        super.finalize();
    }
}

```



```java
 @Test
    public void gcTest() {
        GcEntity gcEntity = new GcEntity(new BigDecimal(10.00));
        new Object();
        new GcEntity(new BigDecimal(11.00));
        System.gc();
    }
```

执行结果为: 

 > success : 11







# 70. 修饰符



|           |                                                              |                                                              |
| --------- | ------------------------------------------------------------ | ------------------------------------------------------------ |
| public    |                                                              |                                                              |
| protected | **子类与基类不在同一包中**：那么在子类中，子类实例可以访问其从基类继承而来的 protected 方法，而不能访问基类实例的protected方法。<br /> **子类与基类在同一包中**：被声明为 protected 的变量、方法和构造器能被同一个包中的任何其他类访问； | protected 可以修饰数据成员，构造方法，方法成员，**不能修饰类（内部类除外）**。 |
| private   | 变量和构造方法只能被所属类访问，                             | 类和接口不能声明为 **private**。                             |
| default   | 对同一个包内的类是可见的                                     |                                                              |
|           |                                                              |                                                              |



| 饰符        | 当前类 | 同一包内 | 子孙类(同一包) | 子孙类(不同包)                                               | 其他包 |
| ----------- | ------ | -------- | -------------- | ------------------------------------------------------------ | ------ |
| `public`    | Y      | Y        | Y              | Y                                                            | Y      |
| `protected` | Y      | Y        | Y              | Y/N（[说明](https://www.runoob.com/java/java-modifier-types.html#protected-desc)） | N      |
| `default`   | Y      | Y        | Y              | N                                                            | N      |
| `private`   | Y      | N        | N              | N                                                            | N      |



## protected

```java
package p2;
class MyObject2 {
    protected Object clone() throws CloneNotSupportedException{
       return super.clone();
    }
}
 
package p22;
public class Test2 extends MyObject2 {
    public static void main(String args[]) {
       MyObject2 obj = new MyObject2();
       obj.clone(); // Compile Error         ----（1）
 
       Test2 tobj = new Test2();
       tobj.clone(); // Complie OK         ----（2）
    }
}
```

>  详情: https://www.runoob.com/w3cnote/java-protected-keyword-detailed-explanation.html





- 父类中声明为 public 的方法在子类中也必须为 public。
- 父类中声明为 protected 的方法在子类中要么声明为 protected，要么声明为 public，不能声明为 private。
- 父类中声明为 private 的方法，不能够被继承。



## 非访问修饰符

为了实现一些其他的功能，Java 也提供了许多非访问修饰符。

static 修饰符，用来修饰类方法和类变量。

final 修饰符，用来修饰类、方法和变量，final 修饰的类不能够被继承，修饰的方法不能被继承类重新定义，修饰的变量为常量，是不可修改的。

abstract 修饰符，用来创建抽象类和抽象方法。

synchronized 和 volatile 修饰符，主要用于线程的编程



# 71. 为什么要用spring管理bean

![](.\img\a28c9-718zl.png)



![](.\img\ahqyw-jl4b3.png)



InstantiationAwareBeanPostProcessor

1. 实例化前置

> InstantiationAwareBeanPostProcessor.postProcessBeforeInstantiation(Class<?> beanClass, String beanName)

2. 实例化对象 doCreateBean

3. 实例化后置

> InstantiationAwareBeanPostProcessor.postProcessAfterInstantiation(Object bean, String beanName)

返回值是决定要不要调用 postProcessPropertyValues

4. 属性修改

> InstantiationAwareBeanPostProcessor.PropertyValues postProcessPropertyValues(PropertyValues pvs, PropertyDescriptor[] pds, Object bean, String beanName)

5. 给用户属性赋值

   用户属性指的是用spring 的人自定义的bean对象属性，像 User、Student、Teacher 、UserService、IndexService 这类的对象都是自定义bean对象，第5步主要给这类属性进行赋值操作，使用的是  AbstractAutowireCapableBeanFactory.populateBean() 方法进行赋值；

6. 给容器属性赋值

   

# 72. swagger

 ## 1. apiimplicitparam datatype

```java
public class Types {
  private Types() {
    throw new UnsupportedOperationException();
  }
 
  private static final Set<String> baseTypes = newHashSet(
      "int",
      "date",
      "string",
      "double",
      "float",
      "boolean",
      "byte",
      "object",
      "long",
      "date-time",
      "__file",
      "biginteger",
      "bigdecimal",
      "uuid");
  private static final Map<Type, String> typeNameLookup = ImmutableMap.<Type, String>builder()
      .put(Long.TYPE, "long")
      .put(Short.TYPE, "int")
      .put(Integer.TYPE, "int")
      .put(Double.TYPE, "double")
      .put(Float.TYPE, "float")
      .put(Byte.TYPE, "byte")
      .put(Boolean.TYPE, "boolean")
      .put(Character.TYPE, "string")
 
      .put(Date.class, "date-time")
      .put(java.sql.Date.class, "date")
      .put(String.class, "string")
      .put(Object.class, "object")
      .put(Long.class, "long")
      .put(Integer.class, "int")
      .put(Short.class, "int")
      .put(Double.class, "double")
      .put(Float.class, "float")
      .put(Boolean.class, "boolean")
      .put(Byte.class, "byte")
      .put(BigDecimal.class, "bigdecimal")
      .put(BigInteger.class, "biginteger")
      .put(Currency.class, "string")
      .put(UUID.class, "uuid")
      .put(MultipartFile.class, "__file")
      .build();
 
  public static String typeNameFor(Type type) {
    return typeNameLookup.get(type);
  }
 
  public static boolean isBaseType(String typeName) {
    return baseTypes.contains(typeName);
  }
 
  public static boolean isBaseType(ResolvedType type) {
    return baseTypes.contains(typeNameFor(type.getErasedType()));
  }
 
  public static boolean isVoid(ResolvedType returnType) {
    return Void.class.equals(returnType.getErasedType()) || Void.TYPE.equals(returnType.getErasedType());
  }
}
```



## 2. 

> allowMultiple=true————表示是数组格式的参数 
> dataType="String"————表示数组中参数的类型
>
> dataType="String" 代表请求参数类型为String类型，当然也可以是Map、User、int等；
> paramType="body" 代表参数应该放在请求的什么地方：
>
>     header-->放在请求头。请求参数的获取：@RequestHeader(代码中接收注解)
>     query -->用于get请求的参数拼接。请求参数的获取：@RequestParam(代码中接收注解)
>     path  -->（用于restful接口）-->请求参数的获取：@PathVariable(代码中接收注解)
>     body  -->放在请求体。请求参数的获取：@RequestBody(代码中接收注解)
>     form  -->（不常用）





# 73. 定时任务

## 1. springboot 的定时任务

在启动类上加 @EnableScheduling

然后在需要定时的方法上加 @Scheduled 注解

cron 表达式 由 6位 构成 分别是

秒 分 时 日 月  周(周几)

## 2. renren-fast

```java
	/**
	 * 项目启动时，初始化定时器
	 */
	@PostConstruct
	public void init(){
		List<ScheduleJobEntity> scheduleJobList = this.list();
		for(ScheduleJobEntity scheduleJob : scheduleJobList){
			CronTrigger cronTrigger = ScheduleUtils.getCronTrigger(scheduler, scheduleJob.getJobId());
            //如果不存在，则创建
            if(cronTrigger == null) {
                ScheduleUtils.createScheduleJob(scheduler, scheduleJob);
            }else {
                ScheduleUtils.updateScheduleJob(scheduler, scheduleJob);
            }
		}
	}
```



```java
Method method = target.getClass().getDeclaredMethod("run", String.class);
```







# 74 assert 

assert [boolean 表达式] 

如果为 true 则程序继续执行

如果为false，则程序抛出AssertionError，并终止执行

> 断言  只在 测试的时候有用, catch 捕获不到 断言异常 AssertionError 因为 他是 error 不是 异常



# 75 调用 service 的方法 工具类, 普通类与工具类调用 service 层  @PostConstruct 



```java
package com.cnooc.cpet.pias.util;

import com.cnooc.cpet.pias.dao.*;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import javax.annotation.Resource;
import java.beans.IntrospectionException;
import java.beans.PropertyDescriptor;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

@Component
public class SevenMeUtil {
	@Resource
    private Asmeb31gCodeDao asmeb31gCodeDao;

    @Resource
    private Syt6151CodeDao syt6151CodeDao;

    @Resource
    private Syt6477CodeDao syt6477CodeDao;

    @Resource
    private Syt10048CodeDao syt10048CodeDao;

    @Resource
    private Dnvrpf101CodeDao dnvrpf101CodeDao;

    @Resource
    private Api579CodeDao api579CodeDao;

    @Resource
    private Bs7910CodeDao bs7910CodeDao;

    public static SevenMeUtil sevenMeUtil;

    @PostConstruct
    public void init() {
        sevenMeUtil = this;
    }

    /************************************************************************
     * @description: 通过标准表的id 和 标准名称 获取 标准表的 名称
     * @author: wg
     * @date: 17:31  2021/11/4
     * @params:
     * @return:
     ************************************************************************/
    public static Object getStandardTable(String codeId, String standard) {
        if (standard.contains("SY/T 6151")) {
            return sevenMeUtil.syt6151CodeDao.selectById(codeId);
        }
        if (standard.contains("SY/T 6477")) {
            return sevenMeUtil.syt6477CodeDao.selectById(codeId);
        }
        if (standard.contains("SY/T 10048")) {
            return sevenMeUtil.syt10048CodeDao.selectById(codeId);
        }
        if (standard.contains("DNVGL RP F101")) {
            return sevenMeUtil.dnvrpf101CodeDao.selectById(codeId);
        }
        if (standard.contains("579")) {
            return sevenMeUtil.api579CodeDao.selectById(codeId);
        }
        if (standard.contains("7910")) {
            return sevenMeUtil.bs7910CodeDao.selectById(codeId);
        }

        if (standard.contains("ASME B31G")) {
            return sevenMeUtil.asmeb31gCodeDao.selectById(codeId);
        }

        return null;
    }
}
```



@PostConstruct

​	**该注解被用来修饰一个非静态的void（）方法。被@PostConstruct修饰的方法会在服务器加载Servlet的时候运行，并且只会被服务器执行一次。PostConstruct在构造函数之后执行，init（）方法之前执行**

​	通常我们会是在Spring框架中使用到@PostConstruct注解 该注解的方法在整个Bean初始化中的执行顺序：

Constructor(构造方法) -> @Autowired(依赖注入) -> @PostConstruct(注释的方法)

​	应用：在静态方法中调用依赖注入的Bean中的方法。



# 76. http-nio-8080 springboot tomcat





![](.\img\8d51dd0856c4cc3a218bbef4b28e7df0.png)





![](.\img\b5d45e86fdcbf09f3eb00bd63d860751.png)





# 77. classpath 到底指向哪

  在 target 里面

```
classpath:mapper/*.xml
```



 ![](.\img\1646103152881.png)



这里当使用ClassPathResource时会出现一个bug , 具体看 bug记录.md -> 57条



# 78.  logback

> `https://www.cnblogs.com/warking/p/5710303.html`

1. springboot 配置文件的加载顺序

   ```
   logback.xml -> application.properties -> logback-spring.xml
   ```

2. 日志级别

   ```
   all > trace > debug > info > warn > error > fatal > off
   ```

3. logback-spring.xml 的配置项

   ```
   一个父标签: configuration
   两种属性: contextName, property
   三个节点: appender, root, logger
   ```

4. 一个父标签 configuration

   ```xml
   <configuration>
     <appender> </appender>
     <root> </root>
     <logger> </logger>
   </configuration>
   ```

   

   ```xml
   <!--
   1. scan
   当此属性设置为true时，配置文件如果发生改变，将会被重新加载，默认值为true
   2. scanPeriod
   设置监测配置文件是否有修改的时间间隔，如果没有给出时间单位，默认单位是毫秒。当scan为true时，此属性生效。默认的时间间隔为1分钟。
   3. debug
   当此属性设置为true时，将打印出logback内部日志信息，实时查看logback运行状态。默认值为false。
   -->
   <configuration  scan="true" scanPeriod="10 seconds" debug="false">
   </configuration>
   ```

5. 两种属性

   5.1 contextName

6. 三个节点

   6.1 appender

   ```xml
   <!--
   appender的意思是追加器，在这里可以理解为一个日志的渲染器（或者说格式化日志输出）。比如渲染console日志为某种格式，渲染文件日志为另一种格式。
   appender中有name和class两个属性，有rollingPolicy和encoder两个子节点
   name表示该渲染器的名字，class表示使用的输出策略，常见的有控制台输出策略和文件输出策略
   -->
    <appender name="" class="">
           <encoder class="">            
           </encoder>
           
           <rollingPlcicy class="">       
           </rollingPlcicy>
   </appender>
   ```

   6.1.1 encoder: 表示输出的格式

   ```tex
   %d表示时间
   %thread表示线程名
   %-5level 表示日志级别，允许以五个字符长度输出
   %logger{50}表示具体的日志输出者，比如类名，括号内表示长度
   %msg表示具体的日志消息，就是logger.info("xxx")中的xxx
   %n表示换行
   ```

   ```xml
     <encoder>
       <pattern>%d{MM-dd-yyyy HH:mm:ss.SSS} [%thread] %highlight(%-5level) %cyan(%logger{50}:%L) - %msg%n</pattern>
       <!-- 设置字符集 -->
       <charset>UTF-8</charset>
     </encoder>
   ```
   6.2 root 节点实际上是配置启用哪种appender，可以添加多个appender

   ```xml
   <!--root配置必须在appender下边-->
   <!--root节点是对所有appender的管理,添加哪个appender就会打印哪个appender的日志-->
   <!--root节点的level是总的日志级别控制,如果appender的日志级别设定比root的高,会按照appender的日志级别打印日志,-->
   <!--如果appender的日志级别比root的低,会按照root设定的日志级别进行打印日志-->
   <!--也就是说root设定的日志级别是最低限制,如果root设定级别为最高ERROR,那么所有appender只能打印最高级别的日志-->
   <root level="DEBUG">
     <appender-ref ref="CONSOLE" />
     <appender-ref ref="DEBUG_FILE" />
     <appender-ref ref="INFO_FILE" />
     <appender-ref ref="WARN_FILE" />
     <appender-ref ref="ERROR_FILE" />
   </root>
   ```

   6.3 logger 对单个包或类添加配置：

   ```xml
   <!--
       name:用来指定受此loger约束的某一个包或者具体的某一个类。
       addtivity:是否向上级loger传递打印信息。默认是true。
   -->
   <!-- logback为 com.pikaqiu.logbackdemo 中的包 -->
   <logger name="com.pikaqiu.logbackdemo" level="debug" additivity="false">
     <appender-ref ref="CONSOLE" />
   </logger>
   
   <!-- logback为 com.pikaqiu.logbackdemo.LogbackdemoApplicationTests 这个类 -->
   <logger name="com.pikaiqu.logbackdemo.LogbackdemoApplicationTests" level="INFO" additivity="true">
     <appender-ref ref="STDOUT"/>
   </logger>
   ```



```xml
<?xml version="1.0" encoding="UTF-8"?>
<!-- 日志级别从低到高分为TRACE < DEBUG < INFO < WARN < ERROR < FATAL，如果设置为WARN，则低于WARN的信息都不会输出 -->
<!-- scan:当此属性设置为true时，配置文档如果发生改变，将会被重新加载，默认值为true -->
<!-- scanPeriod:设置监测配置文档是否有修改的时间间隔，如果没有给出时间单位，默认单位是毫秒。
                 当scan为true时，此属性生效。默认的时间间隔为1分钟。 -->
<!-- debug:当此属性设置为true时，将打印出logback内部日志信息，实时查看logback运行状态。默认值为false。 -->
<configuration  scan="true" scanPeriod="10 seconds">
    <contextName>logback</contextName>
 
    <!-- name的值是变量的名称，value的值时变量定义的值。通过定义的值会被插入到logger上下文中。定义后，可以使“${}”来使用变量。 -->
    <property name="log.path" value="G:/logs/pmp" />
 
    <!--0. 日志格式和颜色渲染 -->
    <!-- 彩色日志依赖的渲染类 -->
    <conversionRule conversionWord="clr" converterClass="org.springframework.boot.logging.logback.ColorConverter" />
    <conversionRule conversionWord="wex" converterClass="org.springframework.boot.logging.logback.WhitespaceThrowableProxyConverter" />
    <conversionRule conversionWord="wEx" converterClass="org.springframework.boot.logging.logback.ExtendedWhitespaceThrowableProxyConverter" />
    <!-- 彩色日志格式 -->
    <property name="CONSOLE_LOG_PATTERN" value="${CONSOLE_LOG_PATTERN:-%clr(%d{yyyy-MM-dd HH:mm:ss.SSS}){faint} %clr(${LOG_LEVEL_PATTERN:-%5p}) %clr(${PID:- }){magenta} %clr(---){faint} %clr([%15.15t]){faint} %clr(%-40.40logger{39}){cyan} %clr(:){faint} %m%n${LOG_EXCEPTION_CONVERSION_WORD:-%wEx}}"/>
 
    <!--1. 输出到控制台-->
    <appender name="CONSOLE" class="ch.qos.logback.core.ConsoleAppender">
        <!--此日志appender是为开发使用，只配置最底级别，控制台输出的日志级别是大于或等于此级别的日志信息-->
        <filter class="ch.qos.logback.classic.filter.ThresholdFilter">
            <level>debug</level>
        </filter>
        <encoder>
            <Pattern>${CONSOLE_LOG_PATTERN}</Pattern>
            <!-- 设置字符集 -->
            <charset>UTF-8</charset>
        </encoder>
    </appender>
 
    <!--2. 输出到文档-->
    <!-- 2.1 level为 DEBUG 日志，时间滚动输出  -->
    <appender name="DEBUG_FILE" class="ch.qos.logback.core.rolling.RollingFileAppender">
        <!-- 正在记录的日志文档的路径及文档名 -->
        <file>${log.path}/web_debug.log</file>
        <!--日志文档输出格式-->
        <encoder>
            <pattern>%d{yyyy-MM-dd HH:mm:ss.SSS} [%thread] %-5level %logger{50} - %msg%n</pattern>
            <charset>UTF-8</charset> <!-- 设置字符集 -->
        </encoder>
        <!-- 日志记录器的滚动策略，按日期，按大小记录 -->
        <rollingPolicy class="ch.qos.logback.core.rolling.TimeBasedRollingPolicy">
            <!-- 日志归档 -->
            <fileNamePattern>${log.path}/web-debug-%d{yyyy-MM-dd}.%i.log</fileNamePattern>
            <timeBasedFileNamingAndTriggeringPolicy class="ch.qos.logback.core.rolling.SizeAndTimeBasedFNATP">
                <maxFileSize>100MB</maxFileSize>
            </timeBasedFileNamingAndTriggeringPolicy>
            <!--日志文档保留天数-->
            <maxHistory>15</maxHistory>
        </rollingPolicy>
        <!-- 此日志文档只记录debug级别的 -->
        <filter class="ch.qos.logback.classic.filter.LevelFilter">
            <level>debug</level>
            <onMatch>ACCEPT</onMatch>
            <onMismatch>DENY</onMismatch>
        </filter>
    </appender>
 
    <!-- 2.2 level为 INFO 日志，时间滚动输出  -->
    <appender name="INFO_FILE" class="ch.qos.logback.core.rolling.RollingFileAppender">
        <!-- 正在记录的日志文档的路径及文档名 -->
        <file>${log.path}/web_info.log</file>
        <!--日志文档输出格式-->
        <encoder>
            <pattern>%d{yyyy-MM-dd HH:mm:ss.SSS} [%thread] %-5level %logger{50} - %msg%n</pattern>
            <charset>UTF-8</charset>
        </encoder>
        <!-- 日志记录器的滚动策略，按日期，按大小记录 -->
        <rollingPolicy class="ch.qos.logback.core.rolling.TimeBasedRollingPolicy">
            <!-- 每天日志归档路径以及格式 -->
            <fileNamePattern>${log.path}/web-info-%d{yyyy-MM-dd}.%i.log</fileNamePattern>
            <timeBasedFileNamingAndTriggeringPolicy class="ch.qos.logback.core.rolling.SizeAndTimeBasedFNATP">
                <maxFileSize>100MB</maxFileSize>
            </timeBasedFileNamingAndTriggeringPolicy>
            <!--日志文档保留天数-->
            <maxHistory>15</maxHistory>
        </rollingPolicy>
        <!-- 此日志文档只记录info级别的 -->
        <filter class="ch.qos.logback.classic.filter.LevelFilter">
            <level>info</level>
            <onMatch>ACCEPT</onMatch>
            <onMismatch>DENY</onMismatch>
        </filter>
    </appender>
 
    <!-- 2.3 level为 WARN 日志，时间滚动输出  -->
    <appender name="WARN_FILE" class="ch.qos.logback.core.rolling.RollingFileAppender">
        <!-- 正在记录的日志文档的路径及文档名 -->
        <file>${log.path}/web_warn.log</file>
        <!--日志文档输出格式-->
        <encoder>
            <pattern>%d{yyyy-MM-dd HH:mm:ss.SSS} [%thread] %-5level %logger{50} - %msg%n</pattern>
            <charset>UTF-8</charset> <!-- 此处设置字符集 -->
        </encoder>
        <!-- 日志记录器的滚动策略，按日期，按大小记录 -->
        <rollingPolicy class="ch.qos.logback.core.rolling.TimeBasedRollingPolicy">
            <fileNamePattern>${log.path}/web-warn-%d{yyyy-MM-dd}.%i.log</fileNamePattern>
            <timeBasedFileNamingAndTriggeringPolicy class="ch.qos.logback.core.rolling.SizeAndTimeBasedFNATP">
                <maxFileSize>100MB</maxFileSize>
            </timeBasedFileNamingAndTriggeringPolicy>
            <!--日志文档保留天数-->
            <maxHistory>15</maxHistory>
        </rollingPolicy>
        <!-- 此日志文档只记录warn级别的 -->
        <filter class="ch.qos.logback.classic.filter.LevelFilter">
            <level>warn</level>
            <onMatch>ACCEPT</onMatch>
            <onMismatch>DENY</onMismatch>
        </filter>
    </appender>
 
    <!-- 2.4 level为 ERROR 日志，时间滚动输出  -->
    <appender name="ERROR_FILE" class="ch.qos.logback.core.rolling.RollingFileAppender">
        <!-- 正在记录的日志文档的路径及文档名 -->
        <file>${log.path}/web_error.log</file>
        <!--日志文档输出格式-->
        <encoder>
            <pattern>%d{yyyy-MM-dd HH:mm:ss.SSS} [%thread] %-5level %logger{50} - %msg%n</pattern>
            <charset>UTF-8</charset> <!-- 此处设置字符集 -->
        </encoder>
        <!-- 日志记录器的滚动策略，按日期，按大小记录 -->
        <rollingPolicy class="ch.qos.logback.core.rolling.TimeBasedRollingPolicy">
            <fileNamePattern>${log.path}/web-error-%d{yyyy-MM-dd}.%i.log</fileNamePattern>
            <timeBasedFileNamingAndTriggeringPolicy class="ch.qos.logback.core.rolling.SizeAndTimeBasedFNATP">
                <maxFileSize>100MB</maxFileSize>
            </timeBasedFileNamingAndTriggeringPolicy>
            <!--日志文档保留天数-->
            <maxHistory>15</maxHistory>
        </rollingPolicy>
        <!-- 此日志文档只记录ERROR级别的 -->
        <filter class="ch.qos.logback.classic.filter.LevelFilter">
            <level>ERROR</level>
            <onMatch>ACCEPT</onMatch>
            <onMismatch>DENY</onMismatch>
        </filter>
    </appender>
 
    <!--
        <logger>用来设置某一个包或者具体的某一个类的日志打印级别、
        以及指定<appender>。<logger>仅有一个name属性，
        一个可选的level和一个可选的addtivity属性。
        name:用来指定受此logger约束的某一个包或者具体的某一个类。
        level:用来设置打印级别，大小写无关：TRACE, DEBUG, INFO, WARN, ERROR, ALL 和 OFF，
              还有一个特俗值INHERITED或者同义词NULL，代表强制执行上级的级别。
              如果未设置此属性，那么当前logger将会继承上级的级别。
        addtivity:是否向上级logger传递打印信息。默认是true。
        <logger name="org.springframework.web" level="info"/>
        <logger name="org.springframework.scheduling.annotation.ScheduledAnnotationBeanPostProcessor" level="INFO"/>
    -->
   <!-- hibernate logger -->
   <logger name="com.atguigu" level="debug" />
   <!-- Spring framework logger -->
   <logger name="org.springframework" level="debug" additivity="false"></logger>
 
 
    <!--
        使用mybatis的时候，sql语句是debug下才会打印，而这里我们只配置了info，所以想要查看sql语句的话，有以下两种操作：
        第一种把<root level="info">改成<root level="DEBUG">这样就会打印sql，不过这样日志那边会出现很多其他消息
        第二种就是单独给dao下目录配置debug模式，代码如下，这样配置sql语句会打印，其他还是正常info级别：
        【logging.level.org.mybatis=debug logging.level.dao=debug】
     -->
 
    <!--
        root节点是必选节点，用来指定最基础的日志输出级别，只有一个level属性
        level:用来设置打印级别，大小写无关：TRACE, DEBUG, INFO, WARN, ERROR, ALL 和 OFF，
        不能设置为INHERITED或者同义词NULL。默认是DEBUG
        可以包含零个或多个元素，标识这个appender将会添加到这个logger。
    -->
 
    <!-- 4. 最终的策略 -->
    <!-- 4.1 开发环境:打印控制台-->
    <springProfile name="dev">
        <logger name="com.sdcm.pmp" level="debug"/>
    </springProfile>
 
    <root level="info">
        <appender-ref ref="CONSOLE" />
        <appender-ref ref="DEBUG_FILE" />
        <appender-ref ref="INFO_FILE" />
        <appender-ref ref="WARN_FILE" />
        <appender-ref ref="ERROR_FILE" />
    </root>
 
    <!-- 4.2 生产环境:输出到文档
    <springProfile name="pro">
        <root level="info">
            <appender-ref ref="CONSOLE" />
            <appender-ref ref="DEBUG_FILE" />
            <appender-ref ref="INFO_FILE" />
            <appender-ref ref="ERROR_FILE" />
            <appender-ref ref="WARN_FILE" />
        </root>
    </springProfile> -->
 
</configuration>
```





# 79. Integer

1. Integer.getInteger() : 并不是将字符串转成整型, 底层调用的是 system.getProperty(), 其实是获取的系统属性

2. Integer.vauluOf() : 转成 Integer
3. Integer.parseInt(string s) : 将字符串 转成 基础类型 int



# 80.  匿名内部类

## 1. effectively final

  Java 8 之后，在匿名类或 Lambda 表达式中访问的局部变量，如果不是 final 类型的话，编译器自动加上 final 修饰符，即 Java8 新特性：effectively final。

思考

前面一直说 Lambda 表达式或者匿名内部类不能访问非 final 的局部变量，这是为什么呢?

- 首先思考`外部的局部变量 finalI 和匿名内部类里面的 finalI 是否是同一个变量？`

> 我们知道，每个方法在执行的同时都会创建一个栈帧用于存储局部变量表、操作数栈、动态链接，方法出口等信息，每个方法从调用直至执行完成的过程，就对应着一个栈帧在虚拟机栈中入栈到出栈的过程（《深入理解Java虚拟机》第2.2.2节 Java虚拟机栈）。



  就是说在执行方法的时候，局部变量会保存在栈中，方法结束局部变量也会出栈，随后会被垃圾回收掉，而此时，内部类对象可能还存在，如果内部类对象这时直接去访问局部变量的话就会出问题，因为外部局部变量已经被回收了，解决办法就是把匿名内部类要访问的局部变量复制一份作为内部类对象的成员变量，查阅资料或者通过反编译工具对代码进行反编译会发现，底层确实定义了一个新的变量，通过内部类构造函数将外部变量复制给内部类变量。

- 为何`还需要用final修饰？`

  其实复制变量的方式会造成一个`数据不一致`的问题，在执行方法的时候`局部变量的值改变了却无法通知匿名内部类的变量`，随着程序的运行，就会导致程序运行的结果与预期不同，于是使用final修饰这个变量，使它成为一个常量，这样就保证了数据的一致性。





# 81. cmd

## 1. cmd命令执行窗口开闭指令

> cmd /c dir 是执行完dir命令后关闭命令窗口。
>
> cmd /k dir 是执行完dir命令后不关闭命令窗口。
>
> cmd /c start dir 会打开一个新窗口后执行dir指令，原窗口会关闭。
>
> cmd /k start dir 会打开一个新窗口后执行dir指令，原窗口不会关闭。







# 81. io

## 1. 下载

```java
public void downloadFile(HttpServletResponse response, File file) {
    String fileName = "";
    OutputStream outputStream = null;
    try {
        /************ 设置响应header -> 开始  ************/
        fileName = new String(fileName.getBytes(), "ISO8859-1");
        response.setContentType("application/vnd.ms-excel");
        response.setCharacterEncoding("utf-8");
        response.setHeader("Content-Disposition", "attachment;filename=" + fileName);
        response.addHeader("Pargam", "no-cache");
        response.addHeader("Cache-Control", "no-cache");
        /************ 设置响应header -> 结束  ************/

        /************ 流 -> 开始 ************/
        outputStream = response.getOutputStream();
        byte[] buffer = new byte[1024];
        FileInputStream fileInputStream = new FileInputStream(file);
        int len;
        while ((len = fileInputStream.read(buffer)) > 0) {
            outputStream.write(buffer, 0, len);
        }
        outputStream.flush();
        fileInputStream.close();
        /************ 流 -> 结束 ************/
    } catch (IOException e) {
        e.printStackTrace();
    } finally {
        if (outputStream != null) {
            try {
                outputStream.close();
            } catch (IOException e) {
                e.printStackTrace();
            }

        }
    }
}
```





## 2. 复制 粘贴 下载

```java
/************************************************************************
 * @description: 先从数据库 读取相关数据, 写入 服务器指定位置
 * @author: wg
 * @date: 15:24  2021/11/24
 * @params:
 * @return:
 ************************************************************************/
public File createDataFile(String corrosionAssessmentHistoryId) {
    CorrosionAssessmentHistoryEntity entity = baseDao.selectById(corrosionAssessmentHistoryId);
    String reportContent = entity.getReportContent();

    String path = "d:\\sevenme";
    // String fileName = "report-" + entity.getAssessmentCode() + "-" + entity.getAssessmentDate() + ".md";
    String fileName = "report-.md";
    File file = new File(path, fileName);
    if (!file.exists()) {
        file.getParentFile().mkdir();
        try {
            file.createNewFile();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    StringReader stringReader = new StringReader(reportContent);
    // 把这个 写成 md
    try {
        BufferedReader bufferedReader = new BufferedReader(stringReader);
        BufferedWriter bufferedWriter = new BufferedWriter(new FileWriter(file));
        char[] chars = new char[1024];
        int len;
        while ((len = bufferedReader.read(chars)) != -1) {
            bufferedWriter.write(chars, 0, len);
        }
        bufferedWriter.flush();
        bufferedWriter.close();
        bufferedReader.close();
        return file;
    } catch (IOException e) {
        e.printStackTrace();
    }
    return null;
}

/************************************************************************
 * @description: 把 md 转成 docx
 * @author: wg
 * @date: 15:49  2021/11/24
 * @params:
 * @return:
 ************************************************************************/
public int parseToDocx() {
    String[] command = new String[]{"E:\\pandoc-2.16.1\\pandoc.exe", "d:\\wg\\wg.md", "-o", "D:\\wg\\wg.docx"};
    File file = new File("E:\\pandoc-2.16.1");
    ProcessBuilder processBuilder = new ProcessBuilder();
    processBuilder.command(command);
    processBuilder.directory(file); //设置工作目录
    processBuilder.redirectErrorStream(true);

    try {
        Process process = processBuilder.start();
        int i = process.waitFor();
        System.out.println(i);
        return i;
    } catch (IOException | InterruptedException e) {
        e.printStackTrace();
    }
    return -1;
}

/************************************************************************
 * @description: 下载
 * @author: wg
 * @date: 16:21  2021/11/24
 * @params:
 * @return:
 ************************************************************************/
public void downloadFile(HttpServletResponse response, File file) {
    String fileName = "report-" + System.currentTimeMillis() + ".docx";
    OutputStream outputStream = null;
    try {
        /************ 设置响应header -> 开始  ************/
        fileName = new String(fileName.getBytes(), "ISO8859-1");
        response.setCharacterEncoding("utf-8");
        response.setHeader("Content-Disposition", "attachment;filename=" + fileName);
        response.addHeader("Pargam", "no-cache");
        response.addHeader("Cache-Control", "no-cache");
        /************ 设置响应header -> 结束  ************/

        /************ 流 -> 开始 ************/
        outputStream = response.getOutputStream();
        byte[] buffer = new byte[1024];
        FileInputStream fileInputStream = new FileInputStream(file);
        int len;
        while ((len = fileInputStream.read(buffer)) > 0) {
            outputStream.write(buffer, 0, len);
        }
        outputStream.flush();
        fileInputStream.close();
        /************ 流 -> 结束 ************/
    } catch (IOException e) {
        e.printStackTrace();
    } finally {
        if (outputStream != null) {
            try {
                outputStream.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
}
```





# 81. 加密与安全

## 1. 哈希

| 算法       | 输出长度（位） | 输出长度（字节） |
| ---------- | -------------- | ---------------- |
| MD5        | 128 bits       | 16 bytes         |
| SHA-1      | 160 bits       | 20 bytes         |
| RipeMD-160 | 160 bits       | 20 bytes         |
| SHA-256    | 256 bits       | 32 bytes         |
| SHA-512    | 512 bits       | 64 bytes         |





# 82. 随机, 随机数

## 1. 伪随机数

所谓伪随机数，是指只要给定一个初始的种子，产生的随机数序列是完全一样的。如果不给定种子，就使用系统当前时间戳作为种子。

有童鞋问，每次运行程序，生成的随机数都是不同的，没看出伪随机数的特性来。

这是因为我们创建Random实例时，如果不给定种子，就使用系统当前时间戳作为种子，因此每次运行时，种子不同，得到的伪随机数序列就不同。



## 2. 真随机

实际上真正的真随机数只能通过量子力学原理来获取，而我们想要的是一个不可预测的安全的随机数，SecureRandom就是用来创建安全的随机数的：



<div style="page-break-after:always"></div>



# 83. 删除 与 主表 关联 的 所有表的 数据 

```java
    public static int count;
    public static int step;

	/************************************************************************
     * @description: 删除 与 主表 关联 的 所有表的 数据 , 感觉 成功了
     * @author: wg
     * @date: 16:20  2021/11/30
     * @params:
     * masterTableName = basic_data
     * masterTableId = basic_data.id
     * @return:
     ************************************************************************/
    public static void deleteAllRelation(String masterTableName, String masterTableId) {
        String tableName = "";
        String columnName = "";
        List<InformationSchema> informationSchemas = sevenMeUtil.informationSchemaDao.selectInformationSchema(masterTableName);
        if (informationSchemas.size() > 0) {
            for (InformationSchema informationSchema : informationSchemas) {
                tableName = informationSchema.getTableName(); // corrosion_assessment_history
                columnName = informationSchema.getColumnName(); // basic_data_id
                List<String> ids = sevenMeUtil.informationSchemaDao.getIds(tableName, columnName, masterTableId);
                if (ids.size() > 0) {
                    List<InformationSchema> schemaList = sevenMeUtil.informationSchemaDao.selectInformationSchema(tableName);
                    if (schemaList.size() == 0) {
                        System.out.println("第 " + count + " 次删除, 删除的是 " + tableName + " 里面的相关数据");
                        count++;
                        //  delete from api5792007_detail where corrosion_assessment_history_id = #{masterTableId}
                        // sevenMeUtil.informationSchemaDao.deleteData(tableName, columnName, masterTableId);
                    } else {
                        for (String id : ids) {
                            deleteAllRelation(tableName, id);
                        }
                    }
                }
            }
        }
    }

    public static void deleteAllRelationByStepOrder(String masterTableName, String masterTableId, int stepOrder) {
        String tableName = "";
        String columnName = "";
        List<InformationSchema> informationSchemas = sevenMeUtil.informationSchemaDao.selectInformationSchema(masterTableName);
        if (informationSchemas.size() > 0) {
            for (InformationSchema informationSchema : informationSchemas) {
                tableName = informationSchema.getTableName();
                columnName = informationSchema.getColumnName();
                List<String> ids = sevenMeUtil.informationSchemaDao.getIds(tableName, columnName, masterTableId);
                if (ids.size() > 0) {
                    step++;
                    if (step == stepOrder) {
                        System.out.println("删除, 超过 " + stepOrder + " 次 关联, 给出提示 --- ");
                        return;
                    }
                    List<InformationSchema> schemaList = sevenMeUtil.informationSchemaDao.selectInformationSchema(tableName);
                    if (schemaList.size() == 0) {
                        count++;
                        System.out.println("第 " + count + " 次删除, 删除的是 " + tableName + " 里面的相关数据");
                        //  delete from api5792007_detail where corrosion_assessment_history_id = #{masterTableId}
                        // sevenMeUtil.informationSchemaDao.deleteData(tableName, columnName, masterTableId);
                    } else {
                        for (String id : ids) {
                            deleteAllRelation(tableName, id);
                        }
                    }
                }
            }
        }
    }
```



# 84. Map

## 1. put, putIfAbsent

> put: 放入数据时，如果放入数据的key已经存在与Map中，最后放入的数据会覆盖之前存在的数据
>
> putIfAbsent: 在放入数据时，如果存在重复的key，那么putIfAbsent不会放入值。



## 2. null 键

允许null为键, 但只能有一个



# 85. 为什么环境变量要大写



> 在Windows下是不区分大小写的，所以在Windows下怎么写都能获取到值。
>
> 而Linux下不同，区分大小写，无论是变量还是命令，大小写都会导致输出的值不一样。比如环境变量ENV和env可以定义不同的值，到最后输出这两个变量时也会不一样。



# 86. 内存溢出, 内存泄露

> 内存泄露 ：程序中已动态分配的堆内存由于某种原因程序未释放或无法释放，造成系统内存的浪费，导致程序运行速度减慢甚至系统崩溃等严重后果。
>
> 内存溢出： 没有足够的内存,就是内存不够用



## 内存泄露: 

​	内存泄漏缺陷具有隐蔽性、积累性的特征，比其他内存非法访问错误更难检测。因为内存泄漏的产生原因是内存块未被释放，属于遗漏型缺陷而不是过错型缺陷。此外，内存泄漏通常不会直接产生可观察的错误症状，而是逐渐积累，降低系统整体性能，极端的情况下可能使系统崩溃。



# 87. maven

## 1. 忽略 test 异常

maven-3.5.4 , 经过test 项目验证, 第一次是可以, 过几天 再试的时候 不行了

```shell
mvn test "-Dmaven.test.failure.ignore=ture"
mvn install "-DskipTests=true"
```



# 88. bigdecimal

## 1. double, float 转 bigdecimal 时 推荐用静态方法 valueof()

```java

 public void test02() {
        double a = 0.1D;
        BigDecimal ade = new BigDecimal(a);
        System.out.println(ade); // 0.1000000000000000055511151231257827021181583404541015625

        BigDecimal aval = BigDecimal.valueOf(a);
        System.out.println(aval); // 0.1
    }
```



# 89. 希腊字母

​	

| 序号 | 大写  | 小写 | 英语音标注音                    | 英文    | 汉语名称          | 常用指代意义                                                 |
| ---- | ----- | ---- | ------------------------------- | ------- | ----------------- | ------------------------------------------------------------ |
| 1    | Α     | α    | /'ælfə/                         | alpha   | 阿尔法            | 角度、系数、[角加速度](https://baike.baidu.com/item/%E8%A7%92%E5%8A%A0%E9%80%9F%E5%BA%A6/4166196)、第一个、[电离度](https://baike.baidu.com/item/%E7%94%B5%E7%A6%BB%E5%BA%A6/554241)、转化率 |
| 2    | Β     | β    | /'bi:tə/ 或 /'beɪtə/            | beta    | 贝塔              | 角度、系数、磁通系数                                         |
| 3    | Γ     | γ    | /'gæmə/                         | gamma   | 伽玛              | [电导系数](https://baike.baidu.com/item/%E7%94%B5%E5%AF%BC%E7%B3%BB%E6%95%B0/22289558)、角度、[比热容比](https://baike.baidu.com/item/%E6%AF%94%E7%83%AD%E5%AE%B9%E6%AF%94/3703101) |
| 4    | Δ     | δ    | /'deltə/                        | delta   | 得尔塔/德尔塔     | 变化量、[焓变](https://baike.baidu.com/item/%E7%84%93%E5%8F%98/6449903)、[熵变](https://baike.baidu.com/item/%E7%86%B5%E5%8F%98/3195222)、[屈光度](https://baike.baidu.com/item/%E5%B1%88%E5%85%89%E5%BA%A6/3199182)、一元二次方程中的[判别式](https://baike.baidu.com/item/%E5%88%A4%E5%88%AB%E5%BC%8F/10834494)、[化学位移](https://baike.baidu.com/item/%E5%8C%96%E5%AD%A6%E4%BD%8D%E7%A7%BB/3823772) |
| 5    | Ε     | ε,ϵ  | /'epsɪlɒn/                      | epsilon | 艾普西隆/厄普西隆 | 对数之基数、[介电常数](https://baike.baidu.com/item/%E4%BB%8B%E7%94%B5%E5%B8%B8%E6%95%B0/1017137)、[电容率](https://baike.baidu.com/item/%E7%94%B5%E5%AE%B9%E7%8E%87/5333701)、[应变](https://baike.baidu.com/item/%E5%BA%94%E5%8F%98/13858771) |
| 6    | Ζ     | ζ    | /'zi:tə/                        | zeta    | 泽塔              | 系数、[方位角](https://baike.baidu.com/item/%E6%96%B9%E4%BD%8D%E8%A7%92/493239)、[阻抗](https://baike.baidu.com/item/%E9%98%BB%E6%8A%97/332224)、[相对黏度](https://baike.baidu.com/item/%E7%9B%B8%E5%AF%B9%E9%BB%8F%E5%BA%A6/4520289) |
| 7    | Η     | η    | /'i:tə/                         | eta     | 伊塔              | 迟滞系数、[机械效率](https://baike.baidu.com/item/%E6%9C%BA%E6%A2%B0%E6%95%88%E7%8E%87/2685290) |
| 8    | Θ     | θ    | /'θi:tə/                        | theta   | 西塔              | 温度、角度                                                   |
| 9    | Ι     | ι    | /aɪ'əʊtə/                       | iota    | 约(yāo)塔         | 微小、一点                                                   |
| 10   | Κ     | κ    | /'kæpə/                         | kappa   | 卡帕              | 介质常数、[绝热指数](https://baike.baidu.com/item/%E7%BB%9D%E7%83%AD%E6%8C%87%E6%95%B0/8099046) |
| 11   | Λ     | λ    | /'læmdə/                        | lambda  | 拉姆达            | [波长](https://baike.baidu.com/item/%E6%B3%A2%E9%95%BF/829184)、体积、[导热系数](https://baike.baidu.com/item/%E5%AF%BC%E7%83%AD%E7%B3%BB%E6%95%B0/1935502)、[普朗克常数](https://baike.baidu.com/item/%E6%99%AE%E6%9C%97%E5%85%8B%E5%B8%B8%E6%95%B0/812256) |
| 12   | Μ     | μ    | /mju:/                          | mu      | 谬                | [磁导率](https://baike.baidu.com/item/%E7%A3%81%E5%AF%BC%E7%8E%87/2385561)、微、动摩擦系（因）数、流体动力黏度、货币单位、[莫比乌斯函数](https://baike.baidu.com/item/%E8%8E%AB%E6%AF%94%E4%B9%8C%E6%96%AF%E5%87%BD%E6%95%B0/2567473) |
| 13   | **Ν** | ν    | /nju:/                          | nu      | 纽                | 磁阻系数、流体运动粘度、光波频率、[化学计量数](https://baike.baidu.com/item/%E5%8C%96%E5%AD%A6%E8%AE%A1%E9%87%8F%E6%95%B0/7264890) |
| 14   | Ξ     | ξ    | 希腊 /ksi/英美 /ˈzaɪ/ 或 /ˈsaɪ/ | xi      | 克西              | [随机变量](https://baike.baidu.com/item/%E9%9A%8F%E6%9C%BA%E5%8F%98%E9%87%8F/828980)、（小）区间内的一个未知特定值 |
| 15   | Ο     | ο    | /əuˈmaikrən/或 /ˈɑmɪˌkrɑn/      | omicron | 奥米克戎          | 高阶无穷小函数                                               |
| 16   | ∏     | π    | /paɪ/                           | pi      | 派                | [圆周率](https://baike.baidu.com/item/%E5%9C%86%E5%91%A8%E7%8E%87/139930)、π(n)表示不大于n的质数个数、连乘 |
| 17   | Ρ     | ρ    | /rəʊ/                           | rho     | 柔                | [电阻率](https://baike.baidu.com/item/%E7%94%B5%E9%98%BB%E7%8E%87/786893)、柱坐标和极坐标中的极径、[密度](https://baike.baidu.com/item/%E5%AF%86%E5%BA%A6/718381)、[曲率半径](https://baike.baidu.com/item/%E6%9B%B2%E7%8E%87%E5%8D%8A%E5%BE%84/2036643) |
| 18   | ∑     | σ,ς  | /'sɪɡmə/                        | sigma   | 西格马            | 总和、表面密度、[跨导](https://baike.baidu.com/item/%E8%B7%A8%E5%AF%BC/149772)、[应力](https://baike.baidu.com/item/%E5%BA%94%E5%8A%9B/3075028)、[电导率](https://baike.baidu.com/item/%E7%94%B5%E5%AF%BC%E7%8E%87/1016751) |
| 19   | Τ     | τ    | /tɔ:/ 或 /taʊ/                  | tau     | 陶                | [时间常数](https://baike.baidu.com/item/%E6%97%B6%E9%97%B4%E5%B8%B8%E6%95%B0/3417132)、[切应力](https://baike.baidu.com/item/%E5%88%87%E5%BA%94%E5%8A%9B/9613446)、2π（两倍圆周率） |
| 20   | Υ     | υ    | /ˈipsɪlon/或 /ˈʌpsɪlɒn/         | upsilon | 宇普西隆 [3]      | [位移](https://baike.baidu.com/item/%E4%BD%8D%E7%A7%BB/823868) |
| 21   | Φ     | φ,ϕ  | /faɪ/                           | phi     | 斐                | [磁通量](https://baike.baidu.com/item/%E7%A3%81%E9%80%9A%E9%87%8F/2386940)、[电通量](https://baike.baidu.com/item/%E7%94%B5%E9%80%9A%E9%87%8F/5307225)、角、[透镜焦度](https://baike.baidu.com/item/%E9%80%8F%E9%95%9C%E7%84%A6%E5%BA%A6/6475897)、[热流量](https://baike.baidu.com/item/%E7%83%AD%E6%B5%81%E9%87%8F/5727729)、[电势](https://baike.baidu.com/item/%E7%94%B5%E5%8A%BF/2869498)、直径、[欧拉函数](https://baike.baidu.com/item/%E6%AC%A7%E6%8B%89%E5%87%BD%E6%95%B0/1944850)、[空集](https://baike.baidu.com/item/%E7%A9%BA%E9%9B%86/5016874)、相位、孔隙度 |
| 22   | Χ     | χ    | /kaɪ/                           | chi     | 希 [3]  /恺       | 统计学中有卡方(χ^2)分布                                      |
| 23   | Ψ     | ψ    | /psaɪ/                          | psi     | 普西              | 角速、介质电通量、ψ函数、[磁链](https://baike.baidu.com/item/%E7%A3%81%E9%93%BE/9517557) |
| 24   | Ω     | ω    | /'əʊmɪɡə/或 /oʊ'meɡə/           | omega   | 奥米伽/欧米伽     | 欧姆、[角速度](https://baike.baidu.com/item/%E8%A7%92%E9%80%9F%E5%BA%A6/1532689)、[角频率](https://baike.baidu.com/item/%E8%A7%92%E9%A2%91%E7%8E%87/11053586)、交流电的电角度、化学中的[质量分数](https://baike.baidu.com/item/%E8%B4%A8%E9%87%8F%E5%88%86%E6%95%B0/8118317)、有机物的[不饱和度](https://baike.baidu.com/item/%E4%B8%8D%E9%A5%B1%E5%92%8C%E5%BA%A6/11047939) |



# 90. 数据类型

​	

|         | 占用内存          | 默认值 | 取值范围                                      |
| ------- | ----------------- | ------ | --------------------------------------------- |
| byte    | 1字节 8位         | 0      | -2^7 ~ 2^7-1  (-128 ~ 127)                    |
| short   | 2字节 16位        | 0      | -2^15 ~ 2^15-1 (-32768 ~ 32767)               |
| int     | 4字节 32位        | 0      | -2^31 ~ 2^31-1                                |
| long    | 8字节 64位        | 0L     |                                               |
| float   | 4字节 32位        | 0F     |                                               |
| double  | 8字节             | 0D     |                                               |
| char    | 2字节 16位unicode | \u0000 | 最小\u0000(十进制等效为0), 最大 \uffff(65535) |
| boolean | 1位               | false  |                                               |
|         |                   |        |                                               |



 float

```java
	@Test
    public void floatTest() {
        float a = 1.0000001f;
        float b = 1.00000016f;
        float c = 1000.0000001f;
        float d = 1000.0001f;
        float e = 1000.00016f;
        float f = 1000000.11f;
        float ff = 1000000.15f;
        float g = 10000000.11f;

        System.out.println(a); // 1.0000001
        System.out.println(b); // 1.0000001
        System.out.println(c); // 1000.0
        System.out.println(d); // 1000.0001
        System.out.println(e); // 1000.0002
        System.out.println(f); // 1000000.1
        System.out.println(ff); // 1000000.1
        System.out.println(g); // 1.0E7
        
        float maxValue = Float.MAX_VALUE;
        System.out.println("maxValue = " + maxValue); // 3.4028235E38
    }
```





# 91. pom 配置

68 项 有部分内容	





# 92. 打包项目时

## 1. 将依赖包 和 项目 代码包 分离

## 2. 



# 93. long 型传到前端, 前端发生了精度缺失问题

**问题描述**: 数据库id 为雪花算法生成, 实体类 该字段是 Long 型, 在返回给前端时, 前端显示, 发生了错误, 此id 值 发生了变化

解决: 

```java
import com.alibaba.fastjson.serializer.SerializerFeature;
import com.alibaba.fastjson.serializer.ValueFilter;
import com.alibaba.fastjson.support.config.FastJsonConfig;
import com.alibaba.fastjson.support.spring.FastJsonHttpMessageConverter;
import org.springframework.boot.autoconfigure.http.HttpMessageConverters;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.converter.HttpMessageConverter;

import java.util.ArrayList;
import java.util.List;

@Configuration
public class CustomFastJsonHttpMessageConverter {
    @Bean
    public HttpMessageConverters fastJsonHttpMessageConverters() {
        FastJsonHttpMessageConverter fastConverter = new FastJsonHttpMessageConverter();
        FastJsonConfig fastJsonConfig = new FastJsonConfig();

        List<SerializerFeature> list = new ArrayList<>();
        list.add(SerializerFeature.PrettyFormat);
        list.add(SerializerFeature.WriteMapNullValue);
        list.add(SerializerFeature.WriteNullStringAsEmpty);
        list.add(SerializerFeature.WriteNullListAsEmpty);
        list.add(SerializerFeature.QuoteFieldNames);
        list.add(SerializerFeature.WriteDateUseDateFormat);
        list.add(SerializerFeature.DisableCircularReferenceDetect);
        list.add(SerializerFeature.WriteBigDecimalAsPlain);

        fastJsonConfig.setSerializerFeatures(list.toArray(new SerializerFeature[list.size()]));

        fastConverter.setFastJsonConfig(fastJsonConfig);
        HttpMessageConverter<?> converter = fastConverter;
        fastJsonConfig.setSerializeFilters(new ValueFilter() {
            @Override
            public Object process(Object object, String name, Object value) {
                if (value != null) {
                    if (value instanceof Long) {
                        return value.toString();
                    }
                }
                return value;
            }
        });
        return new HttpMessageConverters(converter);
    }
}
```

# 94. 名词解释



|      |                                    |      |
| ---- | ---------------------------------- | ---- |
| scm  | software configuration manegerment |      |
|      |                                    |      |
|      |                                    |      |



# 95. 序列化

  序列化:  指把堆内存中的 Java 对象数据，通过某种方式把对象存储到磁盘文件中或者传递给其他网络节点（在网络上传输）。这个过程称为序列化。通俗来说就是将数据结构或对象转换成二进制串的过程



**为什么要序列化?**

1. 在分布式系统中，此时需要把对象在网络上传输，就得把对象数据转换为二进制形式，需要共享的数据的 JavaBean 对象，都得做序列化。
2. 服务器钝化：如果服务器发现某些对象好久没活动了，那么服务器就会把这些内存中的对象持久化在本地磁盘文件中（Java对象转换为二进制文件）；如果服务器发现某些对象需要活动时，先去内存中寻找，找不到再去磁盘文件中反序列化我们的对象数据，恢复成 Java 对象。这样能节省服务器内存

**网络传输中 使用 对象 不行吗?**

  不行, 因为网络上只能传输字节，不能传输对象; 在网络传输之前，必须要把传输对象转换成字节数组.

# 96. 端口

1-1024是tcp协议保留端口  尽量不要使用



# 97. 线程

## 1. join(), wait()

Thread类中的join方法的主要作用就是同步，它可以使得线程之间的并行执行变为串行执行。具体看代码：

```java
public class JoinTest {
    public static void main(String [] args) throws InterruptedException {
        ThreadJoinTest t1 = new ThreadJoinTest("小");
        ThreadJoinTest t2 = new ThreadJoinTest("中");
        ThreadJoinTest t3 = new ThreadJoinTest("大");
        t1.start();
        /**join的意思是使得放弃当前线程的执行，并返回对应的线程，例如下面代码的意思就是：
         程序在main线程中调用t1线程的join方法，则main线程放弃cpu控制权，并返回t1线程继续执行直到线程t1执行完毕
         所以结果是t1线程执行完后，才到主线程执行，相当于在main线程中同步t1线程，t1执行完了，main线程才有执行的机会
         */
        t3.start();
        t1.join();
        t2.start();
    }

}
class ThreadJoinTest extends Thread{
    public ThreadJoinTest(String name){
        super(name);
    }
    @Override
    public void run(){
        for(int i=0;i<1000;i++){
            System.out.println(this.getName() + ":" + i);
        }
    }
}
```



> t1.start();
>
> t2.start();
>
> t3.start();

t1, t2, t3 交替执行



> t1.start();
>
> t3.start();
>
> t1.join();
>
> t2.start();

t1 , t3 交替执行,   此时, t1.join() 方法 进入,  使 t1 wait() , 此时 t1, t3 仍在交替执行,  等待 t1 执行完, t3 不用执行完, 之后 t2 run 



> t1.start();
>
> t1.join(10);
>
> t2.start();

t1 run 然后 锁住 synchronized t1 10毫秒 , 等t1 执行10毫秒后, t2 执行



可见, join()  相当于在当前位置 放一块砖头, 使当前 管道 阻塞, 使下方的方法暂时不执行, 等待前方的线程执行完;



# 98. 上传jar包到maven中央库





# 99. arrayduque



<div style="page-break-after:always"></div>



# 100. 各种空格



| 名称                                            | 代码   | 宽度  | 备注 |                                                              |
| ----------------------------------------------- | ------ | ----- | ---- | ------------------------------------------------------------ |
| 制表符(character tabulation)                    | U+0009 | 9     |       水平制表符，实体名称：`&Tab;`                                |
| 空格(space)                                     | U+0020 | 32    |       最常见的ASCII空格，键盘空格键打出的即是它。宽度随字体不同而变化，通常在字体高度的1/5至1/3之间。 |
| 不间断空格(no-break space)                      | U+00A0 | 160   |      不间断空格，与U+0020类似，但不会断行。实体名称：`&nbsp;`     |
| 半宽空白(en quad)                               | U+2000 | 8192  |       早期为铅活字印刷排版制定的半宽空白，宽度是字体高度的一半。U+2002规范等同于此字符；最好使用U+2002。 |
| 全宽空白(em quad)                               | U+2001 | 8193  |       早期为铅活字印刷排版制定的全宽空白，宽度等于字体高度。U+2003规范等同于此字符；最好使用U+2003。 |
| 半宽空格(en space)                              | U+2002 | 8194  |       也称半角空格，宽度是字体高度的一半。实体名称：`&ensp;`       |
| 全宽空格(em space)                              | U+2003 | 8195  |       也称全角空格，宽度等于字体高度。实体名称：`&emsp;`           |
| 三分之一空格(three-per-em space)                | U+2004 | 8196  |       宽度是字体高度的三分之一。实体名称：`&emsp13;`               |
| 四分之一空格(four-per-em space)                 | U+2005 | 8197  |       宽度是字体高度的四分之一。实体名称：`&emsp14;`               |
| 六分之一空格(six-per-em space)                  | U+2006 | 8198  |       宽度是字体高度的六分之一。在计算机排版中，有时等于U+2009。   |
| 数字空格(figure space)                          | U+2007 | 8199  |       在具有等宽数字的字体中，等于一位数字的宽度。实体名称：`&numsp;` |
| 标点空格(punctuation space)                     | U+2008 | 8200  |       与字体中的窄标点符号一样宽，即句点或逗号的前进宽度。实体名称：`&puncsp;` |
| 薄空格(thin space)                              | U+2009 | 8201  |       宽度是字体高度的五分之一（有时是六分之一）。建议用作国际单位制中千位分隔符。与U+2002到U+2008不同，其宽度可能会在排版中调整。实体名称：`&thinsp;` |
| 头发空格(hair space)                            | U+200A | 8202  |       比薄空格还窄，实体名称：`&hairsp;`                           |
| 狭窄的不间断空格(narrow no-break space)         | U+202F | 8239  |       类似于U+00A0不间断空格。与蒙古语一起使用时，其宽度通常是正常空格的三分之一；在其他情况下，其宽度有时类似于薄空格（U+2009）的宽度。 |
| 中等数学空格(medium mathematical space)         | U+205F | 8287  |      MMSP。用于数学公式。字高的四分之一。在数学排版中，空格的宽度通常以字体高的18的整数倍给出，并且4/18字体高可能会在几种情况下使用，例如表达式a + b中的a与+之间以及+与b之间。实体名称：`&MediumSpace;` |
| 表意空格(ideographic space)                     | U+3000 | 12288 |    CJK（中文、日文和韩文）表意文字中使用的全角空格，宽度等于字体高度。 |
| 零宽度空格(zero width space)                    | U+200B | 8203  |     ZWSP，零宽度空格。当使用非显式间距的脚本时，用于向文本处理系统指示单词边界。它与软连字符（U+00AD）相似，不同之处在于后者用于表示音节边界，并且在折线处应显示可见的连字符。实体名称：`&ZeroWidthSpace;` |
| 零宽度非连接符(zero width non-joiner)           | U+200C | 8204  | ‌      ZWNJ，零宽度非连接符。当放置在两个本来可以连接的字符之间时，ZWNJ使它们分别以最终形式和初始形式打印。实体名称：`&zwnj;` |
| 零宽度连接符(zero width joiner)                 | U+200D | 8205  | ‍     ZWJ，零宽度连接符。当放置在两个无法连接的字符之间时，ZWJ会使它们以连接的形式打印。也可以用于单独显示连接形式。取决于默认情况下是否需要连字或连词，可以诱导（如表情符号和僧伽罗语）或抑制（如梵文）用单个字形替换，同时仍允许使用单独的连接形式（与ZWNJ不同）。实体名称：`&zwj;` |
| 零宽度词连接符(word joiner)                     | U+2060 | 8288  | ⁠      WJ，词连接符。与U+200B类似，但不会在词内断行。实体名称：`&NoBreak;` |
| 零宽度不间断空格(zero width non-breaking space) | U+FEFF | 65279 |       主要用作字节顺序标记。从Unicode 3.2开始，不推荐用作不间断的指示。请参阅U+2060。 |



# 101. 加载配置文件顺序

若application.yml 和bootstrap.yml 在同一目录下：bootstrap.yml 先加载 application.yml后加载

bootstrap.yml 由父Spring ApplicationContext加载。可以理解成系统级别的一些参数配置，这些参数一般是不会变动的. 一旦bootStrap.yml 被加载，则内容不会被覆盖。

application.yml 可以用来定义应用级别的， 应用程序特有配置信息，可以用来配置后续各个模块中需使用的公共参数等。



在不指定要被加载文件时，默认的加载顺序：由里向外加载，所以最外层的最后被加载，会覆盖里层的属性



# 102. nacos



| Spring Cloud Alibaba Version                              | Sentinel Version | Nacos Version | RocketMQ Version | Dubbo Version | Seata Version |
| --------------------------------------------------------- | ---------------- | ------------- | ---------------- | ------------- | ------------- |
| 2.2.7.RELEASE*                                            | 1.8.1            | 2.0.3         | 4.6.1            | 2.7.13        | 1.3.0         |
| 2.2.6.RELEASE                                             | 1.8.1            | 1.4.2         | 4.4.0            | 2.7.8         | 1.3.0         |
| 2021.1 or 2.2.5.RELEASE or 2.1.4.RELEASE or 2.0.4.RELEASE | 1.8.0            | 1.4.1         | 4.4.0            | 2.7.8         | 1.3.0         |
| 2.2.3.RELEASE or 2.1.3.RELEASE or 2.0.3.RELEASE           | 1.8.0            | 1.3.3         | 4.4.0            | 2.7.8         | 1.3.0         |
| 2.2.1.RELEASE or 2.1.2.RELEASE or 2.0.2.RELEASE           | 1.7.1            | 1.2.1         | 4.4.0            | 2.7.6         | 1.2.0         |
| 2.2.0.RELEASE                                             | 1.7.1            | 1.1.4         | 4.4.0            | 2.7.4.1       | 1.0.0         |
| 2.1.1.RELEASE or 2.0.1.RELEASE or 1.5.1.RELEASE           | 1.7.0            | 1.1.4         | 4.4.0            | 2.7.3         | 0.9.0         |
| 2.1.0.RELEASE or 2.0.0.RELEASE or 1.5.0.RELEASE           | 1.6.3            | 1.1.1         | 4.4.0            | 2.7.3         | 0.7.1         |



| Spring Cloud Alibaba Version      | Spring Cloud Version        | Spring Boot Version |
| --------------------------------- | --------------------------- | ------------------- |
| 2.2.7.RELEASE                     | Spring Cloud Hoxton.SR12    | 2.3.12.RELEASE      |
| 2021.1                            | Spring Cloud 2020.0.1       | 2.4.2               |
| 2.2.6.RELEASE                     | Spring Cloud Hoxton.SR9     | 2.3.2.RELEASE       |
| 2.1.4.RELEASE                     | Spring Cloud Greenwich.SR6  | 2.1.13.RELEASE      |
| 2.2.1.RELEASE                     | Spring Cloud Hoxton.SR3     | 2.2.5.RELEASE       |
| 2.2.0.RELEASE                     | Spring Cloud Hoxton.RELEASE | 2.2.X.RELEASE       |
| 2.1.2.RELEASE                     | Spring Cloud Greenwich      | 2.1.X.RELEASE       |
| 2.0.4.RELEASE(停止维护，建议升级) | Spring Cloud Finchley       | 2.0.X.RELEASE       |
| 1.5.1.RELEASE(停止维护，建议升级) | Spring Cloud Edgware        | 1.5.X.RELEASE       |



# 103. apache poi



```tex
HSSF － 提供读写Microsoft Excel格式档案的功能。excel 2003 版本
XSSF － 提供读写Microsoft Excel OOXML格式档案的功能。excel 2007 版本
HWPF － 提供读写Microsoft Word格式档案的功能。
HSLF － 提供读写Microsoft PowerPoint格式档案的功能。
HDGF － 提供读写Microsoft Visio格式档案的功能
```



# 104. 数据存储单位

```tex
8 bit = 1 Byte 一字节

1024 B = 1 KB （KiloByte） [千字节]

1024 KB = 1 MB （MegaByte） [兆字节]

1024 MB = 1 GB （GigaByte） [吉字节]

1024 GB = 1 TB （TeraByte） [太字节]

1024 TB = 1 PB （[PetaByte]） [拍字节]

1024 PB = 1 EB （ExaByte） [艾字节]

1024 EB = 1 ZB （ZetaByte） 泽[字节]

1024 ZB = 1 YB （YottaByte） 尧字节

1024 YB = 1BB（Brontobyte）珀字节

1024 BB = 1 NB （NonaByte） 诺字节

1024 NB = 1 DB （[DoggaByte]）刀字节
```



# 105. 注解

1. @Deprecated 

   可以用来注解类、接口、成员方法和成员变量等，用于表示某个元素（类、方法等）已过时。当其他程序使用已过时的元素时，编译器将会给出警告。






# 106. ObjectMapper

```
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.JsonNode;
import org.springframework.util.ResourceUtils;
```



# 107. minio

minioadmin  minioadmin

```shell
 setx MINIO_ROOT_USER admin
 
 setx MINIO_ROOT_PASSWORD admin
 
 .\minio.exe server e:\miniodata --console-address ":9001"
```



# 108.  方法参数解析器, HandlerMethodArgumentResolver

 

```java
import com.sevenme.common.annotation.app.LoginUser;
import com.sevenme.common.interceptor.AuthorizationInterceptor;
import com.sevenme.modules.app.integritymanagement.entity.UserEntity;
import com.sevenme.modules.app.service.UserService;
import com.sevenme.modules.sys.entity.SysUserEntity;
import com.sevenme.modules.sys.service.SysUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.MethodParameter;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.support.WebDataBinderFactory;
import org.springframework.web.context.request.NativeWebRequest;
import org.springframework.web.context.request.RequestAttributes;
import org.springframework.web.method.support.HandlerMethodArgumentResolver;
import org.springframework.web.method.support.ModelAndViewContainer;

import javax.annotation.Resource;

@Component
public class LoginUserHandlerMethodArgumentResolver implements HandlerMethodArgumentResolver {
    @Autowired
    private UserService userService;

    @Resource
    private SysUserService sysUserService;

    @Override
    public boolean supportsParameter(MethodParameter parameter) {
        return parameter.getParameterType().isAssignableFrom(UserEntity.class) || parameter.getParameterType().isAssignableFrom(SysUserEntity.class) && parameter.hasParameterAnnotation(LoginUser.class);
    }

    @Override
    public Object resolveArgument(MethodParameter parameter, ModelAndViewContainer container,
                                  NativeWebRequest request, WebDataBinderFactory factory) throws Exception {
        //获取用户ID
        Object object = request.getAttribute(AuthorizationInterceptor.USER_KEY, RequestAttributes.SCOPE_REQUEST);
        if(object == null){
            return null;
        }

        //获取用户信息
        // UserEntity user = userService.getById((Long)object);
        SysUserEntity sysUser = sysUserService.getById((Long) object);

        return sysUser;
    }
}
```

```java
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.module.SimpleModule;
import com.fasterxml.jackson.databind.ser.std.ToStringSerializer;
import com.sevenme.common.interceptor.AuthorizationInterceptor;
import com.sevenme.common.utils.DateUtils;
import com.sevenme.modules.app.resolver.LoginUserHandlerMethodArgumentResolver;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.converter.ByteArrayHttpMessageConverter;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.http.converter.ResourceHttpMessageConverter;
import org.springframework.http.converter.StringHttpMessageConverter;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.http.converter.support.AllEncompassingFormHttpMessageConverter;
import org.springframework.web.method.support.HandlerMethodArgumentResolver;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import java.text.SimpleDateFormat;
import java.util.List;
import java.util.TimeZone;

@Configuration
public class WebMvcConfig implements WebMvcConfigurer {
    @Autowired
    private AuthorizationInterceptor authorizationInterceptor;
    @Autowired
    private LoginUserHandlerMethodArgumentResolver loginUserHandlerMethodArgumentResolver;

    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(authorizationInterceptor).addPathPatterns("/app/**");
    }

    @Override
    public void addArgumentResolvers(List<HandlerMethodArgumentResolver> argumentResolvers) {
        argumentResolvers.add(loginUserHandlerMethodArgumentResolver);
    }

    @Override
    public void configureMessageConverters(List<HttpMessageConverter<?>> converters) {
        converters.add(new ByteArrayHttpMessageConverter());
        converters.add(new StringHttpMessageConverter());
        converters.add(new ResourceHttpMessageConverter());
        converters.add(new AllEncompassingFormHttpMessageConverter());
        converters.add(new StringHttpMessageConverter());
        converters.add(jackson2HttpMessageConverter());
    }

    @Bean
    public MappingJackson2HttpMessageConverter jackson2HttpMessageConverter() {
        MappingJackson2HttpMessageConverter converter = new MappingJackson2HttpMessageConverter();
        ObjectMapper mapper = new ObjectMapper();

        //日期格式转换
        mapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
        mapper.setDateFormat(new SimpleDateFormat(DateUtils.DATE_TIME_PATTERN));
        mapper.setTimeZone(TimeZone.getTimeZone("GMT+8"));

        //Long类型转String类型, 解决 long 型传到前端, 精度缺失问题
        SimpleModule simpleModule = new SimpleModule();
        simpleModule.addSerializer(Long.class, ToStringSerializer.instance);
        simpleModule.addSerializer(Long.TYPE, ToStringSerializer.instance);
        mapper.registerModule(simpleModule);

        converter.setObjectMapper(mapper);
        return converter;
    }
}
```



# 109. 字段注解怎么起作用





# 110. 时间

### 1. 协调世界时, 世界标准时间 utc

这套时间系统被应用于许多互联网和[万维网](https://baike.baidu.com/item/%E4%B8%87%E7%BB%B4%E7%BD%91)的标准中，例如，[网络时间协议](https://baike.baidu.com/item/%E7%BD%91%E7%BB%9C%E6%97%B6%E9%97%B4%E5%8D%8F%E8%AE%AE)就是协调世界时在互联网中使用的一种方式。

在军事中，协调世界时区会使用“Z”来表示。又由于Z在无线电联络中使用“Zulu”作代称，协调世界时也会被称为"Zulu time"。

中国大陆、中国香港、中国澳门、中国台湾、蒙古国、新加坡、马来西亚、菲律宾、西澳大利亚州的时间与UTC的[时差](https://baike.baidu.com/item/%E6%97%B6%E5%B7%AE/1305648)均为+8，也就是UTC+8。

### 2. 格林尼治标准时

### 3. 原子时

### 4. 恒星时

### 5. 太阳时

### 6. 地方时

### 7. 世界时

### 8. 历书时 (已被原子时取代)



# 111. 时区

| 时区       | 时区范围        | 时区中心线 |
| ---------- | --------------- | ---------- |
| UTC        | 7.5°W~7.5°E     | 0°         |
| UTC+1      | 7.5°E~22.5°E    | 15°E       |
| UTC+2      | 22.5°E~37.5°E   | 30°E       |
| UTC+3      | 37.5°E~52.5°E   | 45°E       |
| UTC+4      | 52.5°E~67.5°E   | 60°E       |
| UTC+5      | 67.5°E~82.5°E   | 75°E       |
| UTC+6      | 82.5°E~97.5°E   | 90°E       |
| UTC+7      | 97.5°E~112.5°E  | 105°E      |
| UTC+8      | 112.5°E~127.5°E | 120°E      |
| UTC+9      | 127.5°E~142.5°E | 135°E      |
| UTC+10     | 142.5°E~157.5°E | 150°E      |
| UTC+11     | 157.5°E~172.5°E | 165°E      |
| 东西十二区 | 172.5°E~172.5°W | 180°       |
| UTC-11     | 172.5°W~157.5°W | 165°W      |
| UTC-10     | 157.5°W~142.5°W | 150°W      |
| UTC-9      | 142.5°W~127.5°W | 135°W      |
| UTC-8      | 127.5°W~112.5°W | 120°W      |
| UTC-7      | 112.5°W~97.5°W  | 105°W      |
| UTC-6      | 97.5°W~82.5°W   | 90°W       |
| UTC-5      | 82.5°W~67.5°W   | 75°W       |
| UTC-4      | 67.5°W~52.5°W   | 60°W       |
| UTC-3      | 52.5°W~37.5°W   | 45°W       |
| UTC-2      | 37.5°W~22.5°W   | 30°W       |
| UTC-1      | 22.5°W~7.5°W    | 15°W       |





# 112. jackson

	## 1. jackson 常用注解

### 1. @JsonAutoDetect：

 该注解的作用是配置自动识别的类型

有以下四个属性：

- getterVisibility：定义getter方法的识别范围。
- isGetterVisibility：定义is-getter方法的识别范围(boolean类型的getter，很少用)。
- setterVisibility：定义setter方法的识别范围。
- creatorVisibility：定义构造器识别范围。
- fieldVisibility：定义属性识别范围。

识别范围是一个枚举，包括：
 Visibility.ANY：表示从 private 到 public 修饰，都可识别。
 Visibility.NON_PRIVATE：表示除 private 修饰不可识别，其他都识别。
 Visibility.PROTECTED_AND_PUBLIC：protected 和 public都识别。
 Visibility.PUBLIC_ONLY：仅 public 可见。
 Visibility.NONE：所有皆不可见。
 Visibility.DEFAULT：缺省，所有被 public 修饰的属性、 getter 和所有 setter皆可见。

### 2. @JsonFormat：

使用在日期属性上，指定封装与转换格式，例如输出日期格式为yyyy-MM-dd HH:mm:ss，可以设置为

```java
@JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
```

### 3. @JsonIgnore：

使用在属性上，也可以使用在set与get方法上，效果都一样，就是忽视。当我们不希望某些字段出现在json字符串上面时，就可以使用该注解忽视该属性

### 4. @JsonIgnoreProperties：
使用在类上，当需要忽略的属性过多，可以通过该属性设置，该注解的值是一个字符串集合。

### 5. @JsonInclude：
作用于类上，用于忽略指定值的字段，例如忽略null的字段

### 6. @JsonSerialize：
可以用于属性上，一般用于指定某种类型属性使用自定义的序列化器，常用与限制Double类型保留指定小数位数。使用步骤如下：

1）自定义Double序列化器

```java
//泛型传入Double类型
public class DoubleJsonExchange  extends JsonSerializer<Double> {

    private DecimalFormat decimalFormat = new DecimalFormat("0.00");

    @Override
    public void serialize(Double aDouble,
                          JsonGenerator jsonGenerator,
                          SerializerProvider serializerProvider) throws IOException {

        //保留两位小数（四舍五入）
        jsonGenerator.writeNumber(decimalFormat.format(aDouble));

    }


}
```

2）指定某个Double属性使用自定义序列器

```java
    @JsonSerialize(using = DoubleJsonExchange.class)
    private Double pai = 3.145592654321;
```



# 113.  空

```
null：    表示对象为空

empty：表示对象为空或长度为0

blank： 表示对象为空或长度为0、空格字符串
```



# 114. getField() 和 getDeclaredField()

getField:  只能获取public的，包括从父类继承来的字段。

getDeclaredField:  可以获取本类所有的字段，包括private的，但是不能获取继承来的字段



# 115. setAccessible(true)

使能访问 private 字段的值



<div style="page-break-after:always"></div>



# 116. stringutils

 

| org.apache.commons.lang3.StringUtils     |                                                       |                                                          |
| ---------------------------------------- | ----------------------------------------------------- | -------------------------------------------------------- |
| .center(str, size, patstr)               | 使str 居中                                            | StringUtils.center("wg", 19, "*");                       |
| .reverse()                               | 颠倒字符串                                            |                                                          |
| .isNumeric(str)                          | 全由数字组成, 返回true                                |                                                          |
| .isAlpha(str)                            | 全由字母组成, 返回 true                               |                                                          |
| .isAlphanumeric(str)                     | 全由数字或字母组成, 返回 true                         |                                                          |
| .isAlphaspace(str)                       | 全由字母或空格组成, 返回 true                         |                                                          |
| .countMatches(str1,str2)                 | 取得str2在str1中出现的次数,未发现则返回零             | int i = StringUtils.countMatches("sssfff", "s");         |
| .ordinalIndexOf(str, searchStr, ordinal) | searchstr 在 str 中, 第 ordinal 次 出现的 位置(index) | int i1 = StringUtils.ordinalIndexOf("22112211", "1", 3); |
|                                          |                                                       |                                                          |
|                                          |                                                       |                                                          |
|                                          |                                                       |                                                          |
|                                          |                                                       |                                                          |
|                                          |                                                       |                                                          |



# 117. biginteger

## 1. BigInteger(int signum, byte[] magnitude)

第一个参数int signum 如果是 1，则代表BigInteger包装的数计算机存储的二进制为正
第一个参数int signum 如果是 0，则代表BigInteger包装的数为0
第一个参数Int signum 如果是 -1，则代表BigInteger包装的数计算机存储的二进制为负



# 118. float double

float: 精确到小数点后6位

double: 精确到小数点后15位



# 119. Map

## 1. 取 map 值最大的键

1. 第一种方法

```java
Map<String, Integer> map = new HashMap();
map.put("1", 8);
map.put("2", 12);
map.put("3", 53);
map.put("4", 33);
map.put("5", 11);
map.put("6", 3);
map.put("7", 3);
List<Map.Entry<String,Integer>> list = new ArrayList(map.entrySet());
Collections.sort(list, (o1, o2) -> (o2.getValue() - o1.getValue()));
```

2. 第二种方法

```java
//第一种方法
Map.Entry<Long, Date> entry = longDateHashMap.entrySet()
                    .stream()
                    .max(Map.Entry.comparingByValue())
                    .orElse(null);

            entry.getKey();
            entry.getValue();

		//第二种方法
        Optional<Map.Entry<String, Integer>> max1 = map.entrySet()
                .stream().max((x1, x2) -> Integer.compare(x1.getValue(), x2.getValue()));

 		//第三种方法
        Optional<Map.Entry<String, Integer>> max3 = map.entrySet()
                .stream()
                .collect(Collectors.maxBy(Map.Entry.comparingByValue()));
 
        //第四种方法
        Optional<Map.Entry<String, Integer>> max4 = map.entrySet()
                .stream()
                .max(Comparator.comparingInt(Map.Entry::getValue));
 
        //第五种方法
        IntSummaryStatistics max5 = map.entrySet()
                .stream()
                .collect(Collectors.summarizingInt(Map.Entry::getValue));
```



# 120. LocalDateTime

## 1. 毫秒 转 localdatetime

```
	/**
     * 毫秒数 转 日期
     */
    public static void toDatetime(Long m){
        LocalDateTime localDateTime = LocalDateTime.ofEpochSecond(m/1000, 0, ZoneOffset.ofHours(8));
        System.out.println(localDateTime);
    }
```



# 130. 单精度 双精度

单精度是1位符号，8位指数，23位有效数。

 ![](.\img\danjingdu.png)

双精度是1位符号，11位指数，52位有效数

 ![](.\img\shuangjingdu.png)

 

# 131. 局部变量 只定义 不赋值 和 定义后赋null





# 132. 事务的失效

1. 数据库不支持, 例如 数据库引擎为 MyISAM 

2. 没有被spring管理

   ```
   // @Service
   public class OrderServiceImpl implements OrderService {
   
       @Transactional
       public void updateOrder(Order order) {
           // update order
       }
   
   }
   ```

3. 方法不是 public

4. 异常被吃

   ```
   @Service
   public class OrderServiceImpl implements OrderService {
   
       @Transactional
       public void updateOrder(Order order) {
           try {
               // update order
           } catch {
   
           }
       }
   
   }
   ```

   



# 133. 校验 Validator 

```
@Null 被注释的元素必须为 null
@NotNull 被注释的元素必须不为 null
@AssertTrue 被注释的元素必须为 true
@AssertFalse 被注释的元素必须为 false
@Min(value) 被注释的元素必须是一个数字，其值必须大于等于指定的最小值
@Max(value) 被注释的元素必须是一个数字，其值必须小于等于指定的最大值
@DecimalMin(value) 被注释的元素必须是一个数字，其值必须大于等于指定的最小值
@DecimalMax(value) 被注释的元素必须是一个数字，其值必须小于等于指定的最大值
@Size(max=, min=) 被注释的元素的大小必须在指定的范围内
@Digits (integer, fraction) 被注释的元素必须是一个数字，其值必须在可接受的范围内
@Past 被注释的元素必须是一个过去的日期
@Future 被注释的元素必须是一个将来的日期
@Pattern(regex=,flag=) 被注释的元素必须符合指定的正则表达式
 
Hibernate Validator提供的校验注解：
@NotBlank(message =) 验证字符串非null，且长度必须大于0
@Email 被注释的元素必须是电子邮箱地址
@Length(min=,max=) 被注释的字符串的大小必须在指定的范围内
@NotEmpty 被注释的字符串的必须非空
@Range(min=,max=,message=) 被注释的元素必须在合适的范围内
```





# 134. TreeSet  HashSet

| treeset                        | hashset                     |      |
| ------------------------------ | --------------------------- | ---- |
| 不允许null                     | 只允许一个null              |      |
| 有序                           | 无序                        |      |
| java.lang.Comparable  判断相等 | hashcode 和 equals 判断相等 |      |





# 135. 哈希, 散列

1. 散列值是怎么散列的, 根据的什么规则

   散列值是通过哈希函数（也称为散列函数）对输入消息进行散列生成的。哈希函数是一种算法，其规则可以使得输入任意长度的数据都能够被转换成固定长度的散列值。散列函数的算法实现通常是通过以下几个步骤进行：

   1. 压缩：将输入消息分成若干块，每块固定长度，对每块进行运算，得到一个固定长度的中间值。如果输入消息长度不是固定长度的倍数，则在最后一块后面填充额外的位，使其达到固定长度。

   2. 混合：将压缩得到的中间值合并为一个最终的散列值。通常采用异或、加、移位等运算来混合中间值。

   哈希函数的具体规则取决于所使用的算法，常见的哈希函数算法有MD5、SHA-1、SHA-2、SHA-3等。这些算法使用不同的压缩和混合方式来计算散列值，以达到不同的安全性和性能要求。

   通常来说，好的哈希函数应该具有以下特点：

   1. 易于计算：哈希函数应该是快速计算的，特别是对于大型数据集合。

   2. 抗碰撞：哈希函数应该有极低的碰撞概率，即不同的输入消息应该生成不同的散列值。

   3. 抗篡改：如果输入数据发生任何微小的更改，哈希函数应该生成不同的散列值。

   4. 难以逆向：对于给定的散列值，很难逆向计算出原始的输入消息。

   哈希函数是密码学和安全领域中非常重要的基础组件，通常用于数字签名、消息完整性验证、密码储存和身份验证等应用。





# 136. java 命名规范

## 1. "_" 作为首字母 命名 java 方法, 为什么不建议, 举几个例子

在 Java 中，虽然 "*" 可以作为标识符的首字母，但通常不建议这样做。主要原因是 "*" 不够清晰明了，容易与其他符号混淆，从而导致代码可读性降低。此外，Java 编程规范建议标识符应该由字母、数字和下划线组成，应以字母或下划线开头。因此，建议遵循 Java 命名规范，将标识符命名为以字母或下划线开头的单词，以提高代码的可读性和可维护性。

以下是一些示例，展示了在 Java 中使用 "_" 作为标识符的一些情况：

1. 与其他符号混淆：

   ```java
   javaCopy code
   public class MyClass {
       public void _myMethod() {
           // Method implementation...
       }
   
       public void myMethod() {
           // Method implementation...
       }
   }
   ```

   在这个例子中，虽然 "_myMethod" 是合法的标识符，但它可能会与其他符号混淆，例如 "myMethod"，容易让其他开发人员产生困惑。

2. 与 Python 编程语言的风格混淆：

   Python 编程语言中使用 `_`作为私有属性或方法的前缀，例如 "my_private_method"。在 Java 中，虽然 `_` 可以作为标识符的一部分，但它不应该被用作私有属性或方法的前缀。Java 中，私有属性或方法应该使用 开头来表示。

   ```java
   javaCopy code
   public class MyClass {
       private int _myPrivateVariable;
   
       private void _myPrivateMethod() {
           // Method implementation...
       }
   }
   ```

   在这个例子中，虽然 "_myPrivateVariable" 和 "_myPrivateMethod" 是合法的标识符，但不太符合 Java 的编码规范和惯例。建议将私有属性或方法命名为类似 "myPrivateVariable" 和 "myPrivateMethod" 的格式，以提高代码的可读性和可维护性。



## 2. "$" 作为首字母 命名 java 方法, 为什么不建议

虽然 "$" 可以作为标识符的首字母，但通常不建议这样做，因为 "$" 在 Java 中具有特殊的含义。在 Java 中，"$" 通常用于以下两种情况：

1. 用于内部类的命名：在 Java 中，内部类的名称由外部类的名称和 $ 符号连接起来，用于表示内部类的层级关系。
2. 用于编译器生成的代码：在 Java 中，编译器生成的代码中可能会包含 $ 符号，用于表示编译器生成的类或方法。例如，如果一个类中包含了匿名内部类，那么编译器会自动为该内部类生成一个类名，并在类名中使用 $ 符号。

因此，建议遵循 Java 命名规范，将标识符命名为以字母或下划线开头的单词，以提高代码的可读性和可维护性。虽然 "$" 可以作为标识符的一部分，但作为首字母并不常见，容易让人产生混淆，而且可能会与编译器生成的代码发生冲突，导致不可预知的结果。

以下是一些示例，展示了在 Java 中使用 "$" 作为标识符的一些情况：

1. 在内部类中使用 "$"：

   ```java
   javaCopy code
   public class OuterClass {
       public class InnerClass {
           // Inner class implementation...
       }
   }
   ```

   在这个例子中，内部类的名称实际上是 "OuterClass$InnerClass"，其中 "$" 符号用于表示内部类的层级关系。

2. 在编译器生成的代码中使用 "$"：

   ```java
   javaCopy code
   public class MyClass {
       public void myMethod() {
           new Runnable() {
               public void run() {
                   // Anonymous class implementation...
               }
           }.run();
       }
   }
   ```

   在这个例子中，编译器会生成一个匿名内部类来实现 Runnable 接口，并为该内部类生成一个类名，例如 "MyClass$1"。在这个类名中，"$" 符号用于区分匿名内部类和外部类。

3. 在自己的代码中使用 "$"：

   ```java
   javaCopy code
   public class MyClass {
       public void $myMethod() {
           // Method implementation...
       }
   }
   ```

   在这个例子中，我们将方法命名为 "$myMethod"，这样虽然是合法的，但不太符合 Java 命名规范，可能会让其他开发人员感到困惑。建议将方法命名为类似 "doSomething" 的格式，以提高代码的可读性和可维护性。



# 137. java 基础

### 1. 符号引用常量

> 符号引用常量主要包括以下信息：
>
> 1. 类或接口的全限定名：例如 "java/lang/Object" 表示 Object 类所在的包名和类名。
> 2. 字段或方法的名称：例如 "length" 表示一个数组的长度，"toString" 表示 Object 类的 toString() 方法。
> 3. 字段或方法的描述符：例如 "([Ljava/lang/String;)V" 表示一个没有返回值、参数为字符串数组的方法。
> 4. 引用类型：包括类、接口、字段和方法四种引用类型。
>    通过符号引用常量，Java 虚拟机可以在运行时动态解析和连接类的引用，实现类的加载和初始化。符号引用常量也为 Java 程序的反射、动态代理等机制提供了基础支持，使得程序可以在运行时对类的结构和行为进行动态操作和调整。



### 2. 常量池存放哪些数据

> Java 中的常量池是一种存储常量的数据结构，主要用于存放字符串常量、基本数据类型常量和符号引用常量。常量池位于方法区（JDK1.8 及之前版本）或元空间（JDK1.8+）中，是每个类或接口的常量池数据结构的一个部分。
>
> 常量池中存放的数据类型包括：
>
> 1. 字符串常量：例如通过 String 类的字面值方式创建的字符串常量，如 "hello"。
> 2. 基本数据类型常量：例如 byte、short、int、long、float、double、char、boolean 等基本数据类型的常量值。
> 3. 符号引用常量：例如类和接口的全限定名、字段、方法的名称和描述符等符号引用信息。
>
> 在运行时，Java 虚拟机会从常量池中获取相应的常量值，并将其加载到操作数栈中供程序使用。常量池的使用可以提高程序的执行效率和内存利用率，同时也方便了程序员对常量的管理和维护。



### 3. java 的虚拟内存

> Java 的虚拟内存是一种管理 Java 应用程序内存使用的机制。在 Java 应用程序运行时，Java 虚拟机（JVM）会为每个 Java 进程分配一个虚拟内存地址空间，将 Java 程序的内存管理交给 JVM 进行控制。
>
> Java 的虚拟内存包含以下部分：
>
> 1. 堆（Heap）：用于存放 Java 对象和数组的区域，是 Java 程序中最大的内存区域，由 JVM 自动进行垃圾回收。
> 2. 方法区（Method Area）：用于存放类和接口的信息、静态变量、常量等，也是由 JVM 自动进行垃圾回收。
> 3. 程序计数器（Program Counter）：用于记录当前线程执行的字节码指令地址，是线程私有的，不会被线程间共享。程序计数器是一块较小的内存区域，它记录了当前线程正在执行的字节码指令地址，而不是记录线程的调用栈。
> 4. 虚拟机栈（Virtual Machine Stack）：用于存放方法执行的栈帧信息，每个线程都有自己的虚拟机栈，线程结束时，其虚拟机栈中的栈帧会被销毁。
> 5. 本地方法栈（Native Method Stack）：用于存放调用本地方法的栈帧信息。
>
> 虚拟内存的使用使得 Java 应用程序可以获得更好的内存管理和保护，同时也提高了应用程序的安全性和稳定性。虚拟内存管理机制使得 Java 应用程序可以在不同的平台和操作系统上运行，并且不必担心物理内存限制的问题，从而提高了 Java 应用程序的可移植性和灵活性



### 4. java 的堆(heap)

> Java 的堆（Heap）是一个运行时数据区，主要用于存储 Java 对象和数组。堆是 JVM 管理的最大的一块内存区域，被所有线程所共享。当创建一个对象时，JVM 就会在堆中分配一块足够大的空间，用于存储对象的实例变量。堆是 Java 程序中动态分配内存的主要区域，也是垃圾回收器（Garbage Collector）进行垃圾回收的重点区域。
>
> 堆的内部结构通常被划分为两个区域：新生代和老年代。新生代是为新创建的对象分配内存的区域，通常占用整个堆的较小部分。当新生代空间不足时，会触发 Minor GC（小型垃圾回收），回收无用对象，释放空间。老年代则是存放经过多次 GC 后仍然存活的对象，通常占用整个堆的较大部分。当老年代空间不足时，会触发 Major GC（全面垃圾回收），回收无用对象，释放空间。Full GC（完全垃圾回收）则是指对整个堆进行垃圾回收。
>
> 堆的大小可以通过 JVM 的参数进行设置，比如-Xmx 和-Xms 参数分别用于指定堆的最大和初始大小。堆的大小应该根据应用程序的需求和系统资源进行调整。过小的堆会导致频繁的垃圾回收，过大的堆则会浪费内存资源。在使用时，我们需要特别注意防止内存泄漏和过度分配内存导致的性能问题。
>
> 总之，Java 的堆是一个重要的内存区域，它为 Java 程序提供了动态分配内存的能力，并通过垃圾回收机制自动释放无用对象所占用的空间。了解堆的结构和管理方式有助于我们优化程序的性能和内存使用效率。

### 5. idea 配置 程序的 堆

> 在 IntelliJ IDEA 中，可以通过以下步骤来配置 Java 程序的堆：
>
> 1. 打开 IntelliJ IDEA，选择需要配置堆的项目。
> 2. 点击菜单栏的 Run -> Edit Configurations。
> 3. 在弹出的窗口中选择需要配置堆的运行配置。
> 4. 在 Configuration 标签页下，找到 VM options 栏。
> 5. 在 VM options 栏中输入以下内容：-Xms<initial heap size> -Xmx<maximum heap size>，其中 <initial heap size> 和 <maximum heap size> 分别表示堆的初始大小和最大大小，单位可以是 M 或 G，比如 -Xms512m -Xmx1024m 表示堆的初始大小为 512MB，最大大小为 1GB。
> 6. 点击 Apply 或 OK 按钮保存配置。
>
> 在配置完成后，运行程序时，JVM 将会根据配置的堆大小来分配内存空间。如果程序需要更大的堆空间，可以适当增加堆的最大大小；如果程序不需要那么多的堆空间，可以适当减小堆的最大大小，以便节省内存资源。
>
> 需要注意的是，如果堆的大小设置得过小，可能会导致 OutOfMemoryError 错误；如果堆的大小设置得过大，可能会导致系统内存不足而影响程序的性能。因此，在配置堆的大小时，需要根据实际情况和程序的需要进行合理的调整。



### 6. idea 配置 程序的 内存

> 在 IntelliJ IDEA 中，可以通过以下步骤来配置 Java 程序的内存：
>
> 1. 打开 IntelliJ IDEA，选择需要配置内存的项目。
>
> 2. 点击菜单栏的 Help -> Edit Custom VM Options。
>
> 3. 在打开的 idea64.exe.vmoptions 或 idea.exe.vmoptions 文件中，找到以下两个选项：
>
>    -Xms<size> ：Java 虚拟机初始化时堆内存的最小值。
>
>    -Xmx<size> ：Java 虚拟机堆内存的最大值。
>
> 4. 修改以上选项中的 <size> 参数来调整内存大小。例如，可以将 -Xms512m -Xmx1024m 修改为 -Xms1024m -Xmx2048m，将初始堆内存设置为 1GB，最大堆内存设置为 2GB。
>
> 5. 保存文件并关闭。
>
> 在配置完成后，运行程序时，JVM 将会根据配置的内存大小来分配内存空间。如果程序需要更多的内存空间，可以适当增加内存的最大大小；如果程序不需要那么多的内存空间，可以适当减小内存的最大大小，以便节省内存资源。
>
> 需要注意的是，如果内存设置得过小，可能会导致 OutOfMemoryError 错误；如果内存设置得过大，可能会导致系统内存不足而影响程序的性能。因此，在配置内存大小时，需要根据实际情况和程序的需要进行合理的调整。



# 138. bug level



![](./img/bug-level.png)


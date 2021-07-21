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

# 4. 数组:  定义数组

	int[] array = {1,2,3};  // 用大括号 而不是 中括号



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



# 9. 两个list 合并去重

1. 用set

2. java8以后可通过stream来完成

   ```java
   List<String> collect = Stream.of(listA, listB)
                         .flatMap(Collection::stream)
                         .distinct()
                         .collect(Collectors.toList());
   ```

   如果合并的是对象,注意重写equals和hashcode方法;

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
tasklist|findstr "2722"  // 根据pid 查 程序
taskkill /f /t /im 程序名;

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
public static Date localDateTime2Date(LocalDateTime localDateTime) {
        return Date.from(localDateTime.atZone(ZoneId.systemDefault()).toInstant());
    }

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

// 排除指定字段查询
 	@Test
    public void selectByWrapper11() {
        QueryWrapper<User> queryWrapper = new QueryWrapper<>();
        queryWrapper.select(User.class, info -> !info.getColumn().equals("manager_id")
                && !info.getColumn().equals("create_time"));
        List<User> users = userMapper.selectList(queryWrapper);
        users.forEach(System.out::println);
    }
```







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







# 58. 注意



## 1. 定义实体类, 尽量用 Integer Double 等包装类型, 不要 使用int double 等基础数字类型





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



# 65. stream



1. stream filter 多条件过滤

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

2. sorted()

   ```java
   list.stream().sorted(Comparator.comparing(Student::getAge)) 
   
   list.stream().sorted(Comparator.comparing(Student::getAge).reversed()) 
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
| (                  | 使用括号包含负数                                         | (“%(f”, -99.99)        | (99.990000)      |
| #                  | 如果是浮点数则包含小数点，如果是16进制或8进制则添加0x或0 | (“%#x”, 99)(“%#o”, 99) | 0x63 0143        |
| <                  | 格式化前一个转换符所描述的参数                           | (“%f和%<3.2f”, 99.45)  | 99.450000和99.45 |
| d,%2$s”, 99,”abc”) | 99,abc                                                   |                        |                  |



| 标志 | 说明                        | 示例                             |
| :--- | :-------------------------- | :------------------------------- |
| c    | 包括全部日期和时间信息      | 星期六 十月 27 14:21:20 CST 2007 |
| F    | “年-月-日”格式              | 2007-10-27                       |
| D    | “月/日/年”格式              | 10/27/07                         |
| r    | “HH:MM:SS PM”格式（12时制） | 02:25:51 下午                    |
| T    | “HH:MM:SS”格式（24时制）    | 14:28:16                         |
| R    | “HH:MM”格式（24时制）       | 14:28                            |

#### 1. isInstance 与 instanceof

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

#### 2. java日期 转 sql日期

	Date date = new Date();
	Timestamp sqlDate = new Timestamp(date.getTime())  // 带 时分秒的
	
	java.sql.Date sqlDate1 = new java.sql.Date(date.getTime());  // 不带时分秒 只有年月日

#### 3. session 与  request
	1. session.setAttribute()和session.getAttribute()配对使用，作用域是整个会话期间，在所有的页面都使用这些数据的时候使用。
 	2. request.setAttribute()和request.getAttribute()配对使用，作用域是请求和被请求页面之间。request.setAttribute()是只在此action的
     下一个forward需
      	3. 要使用的时候使用；request.getAttribute()表示从request范围取得设置的属性，必须要先setAttribute设置属性，才能通过getAttribute来取得，
     设置与取得的为Object对象类型。其实表单控件中的Object的 name与value是存放在一个哈希表中的，所以在这里给出Object的name会到哈希表中找出对应它的value。
     setAttribute()的参数是String和Object。
             	4. request.getParameter()表示接收参数，参数为页面提交的参数。包括：表单提交的参数、URL重写(就是xxx?id=1中的id)传的参数等，因此这个并没有设置参数的
     方法(没有setParameter())，而且接收参数返回的不是Object，而是String类型。

#### 4. 定义数组

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
​	说明：对于 Integer var = ? 在-128 至 127 范围内的赋值，Integer 对象是在 IntegerCache.cache 产生，会复用已有对象，这个区间内的 Integer 值可以直接使用==进行判断，但是这个区间之外的所有数据，都会在堆上产生，并不会复用已有对象，这是一个大坑，推荐使用 equals 方法进行判断。

--**浮点数之间的等值判断，基本数据类型不能用==来比较，包装数据类型不能用equals 来判断**--
**Java中的解决方法，是通过设立一个阈值来消除计算机计算所带来的误差引起的误差**

反例：
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

1. 3.2e11  等同于 3.2 × 10¹¹

2. excel里,导入的数据到excel里显示这样 6.22848E+18 , 但它并不等同于java 的科学计数法,

在excel里 6.22848E+18 = 6228480028317730000



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

#### 18. 关于数据库的日期 类型 和 java的 数据类型的对应

oracle 的时间戳 和 java 的 String 也可以对应



#### 19. 格式字符

1. ```tex
    %d  表示按整型数据的实际长度输出数据。
    %c  用来输出一个字符。
    %s  用来输出一个字符串。
    %x  表示以十六进制数形式输出整数。
    ```





| 字符         | 16进制  | 作用                                                         |
| ------------ | ------- | ------------------------------------------------------------ |
| \n (newline) | \\u000A | 换行,光标往下一行                                            |
| \t           |         | 补全当前字符串长度到8的整数倍，最少1个最多8个空格,补多少要看你\t前字符串长度,比如当前字符串长度10，那么\t后长度是16，也就是补6个空格,如果当前字符串长度12，此时\t后长度是16，补4个空格 |
| \r (return)  | \\u000D | 回车,光标回到本行开头                                        |
| \f           |         | 换页                                                         |
|              | \\u0020 | 半角空格                                                     |
|              | \\u3000 | 全角空格                                                     |



#### 20. continue

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



#### 21. 读取json文件

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



#### 22. 配置

1. 要想application.properties 文件里有mybatis-plus 的提示 需要这个

```xml
<!--mybatis-plus自动的维护了mybatis以及mybatis-spring的依赖，在springboot中这三者不能同时的出现，避免版本的冲突，表示：跳进过这个坑-->
        <dependency>
            <groupId>com.baomidou</groupId>
            <artifactId>mybatis-plus-boot-starter</artifactId>
            <version>3.1.1</version>
        </dependency>
```





#### 23. 乘法 除法 的移位操作

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
        System.out.println("i7 ->= " + i7 + "   ####  i9 ->= " + i9);  // 2

        int i8 = a & 1;
        int i10 = a % 2;
        System.out.println("i8 ->= " + i8 + "   ####  i10->  " + i10);  // 0
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



#### 24. 与运算符  " & " 

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



2. 或运算符 " | "

两个只要有一个为1,那么结果就为1 , 否则为0 ;

3. 非运算符 " ~ "

如果位为0，结果是1，如果位为1，结果是0，

4. 异或运算符 " ^ "

   两个操作数的位中，相同则结果为0，不同则结果为1

   

另: 在if 条件中时 , 或运算符 " | ", 

​	当第一个条件为真,后面的条件依然会执行,而短路或 " || ",只要第一个条件为真,则后面的条件就不再执行;



#### 25. get 与 post

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

​	IE：对URL的最大限制为2083个字符，若超出这个数字，提交按钮没有任何反应。

​    Firefox：对Firefox浏览器URL的长度限制为：65536个字符。

​    Safari：URL最大长度限制为80000个字符。

​    Opera：URL最大长度限制为190000个字符。

​    Google(chrome)：URL最大长度限制为8182个字符。

​    Apache(Server)：能接受的最大url长度为8192个字符（这个准确度待定？？？）

​    Microsoft Internet Information Server(IIS)：n能接受最大url的长度为16384个字符

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

#### 26. getbytes()的意义,

​	有时候,为了让中文字符适应某些特殊要求(**如http header要求其内容必须为iso8859-1编码**),可能会通过将中文字符按照字节方式来编码的情况,如:
String s_iso88591 = new String("中".getBytes("UTF-8"),"ISO8859-1"),

这样得到的s_iso8859-1字符串实际是三个在ISO8859-1中的字符,在将这些字符传递到目的地后,目的地程序再通过相反的方式

String s_utf8 = new String(s_iso88591.getBytes("ISO8859-1"),"UTF-8")

来得到正确的中文汉字"中"，**这样就既保证了遵守协议规定、也支持中文**





#### 27. for 循环 与 iterator 迭代 循环 的 区别

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





#### 28. 查端口号



```
netstat -ano
netstat -aon|findstr "8080"  // 根据端口号查pid
tasklist|findstr "2722"  // 根据pid 查 程序
taskkill /f /t /im 程序名;

```



#### 29. 方法定义 为 final

不可重写,不可动态绑定





#### 30. 迭代器的结构

一般情况下链表的遍历是通过指针的移动来寻找下一个结点，并输出数据的。

但是在数据结构的设定中，结点都是被设为private的，也就是说我们无法在链表以外对链表元素进行逐个处理的，也就是说在对图中链表元素进行分类处理是很不方便的，所以为了比面这种情况，我们将为链表构造迭代器，用来处理链表元素。

实现：

链表迭代器的实现，便是用指针模拟正常的循环，所以我们需要：

1.构造一个迭代器的类

2.重载循环中用到的==、！=、*、++等运算符

3.为链表创建首迭代器，和尾迭代器函数





#### 31. 为什么要有抽象类



​	取决于 抽象方法,这个抽象方法 类似与接口中的方法, 没有方法体,是抽象的,

接口中的方法都是 public abstract,只是平时不写,

​	抽象类区别于接口,抽象类中 有 其他不是abstract 的方法,只是类中的某个方法刚好需要抽象,所以就决定了类是抽象的.



#### 32. 函数式编程思维

函数式编程的思想: 希望可以允许程序员用计算来表示程序,用计算的组合来表达程序的组合;

非函数式编程: 用命令来表示程序,用命令的顺序执行来表达程序的组合;



函数式编程: 关心数据的映射,强调函数的计算比指令的执行重要

命令式编程: 关心解决问题的步骤



**函数式编程的好处**

由于命令式编程语言也可以通过类似函数指针的方式来实现高阶函数，函数式的最主要的好处主要是不可变性带来的。没有可变的状态，函数就是**引用透明（Referential transparency）**的和**没有副作用（No Side Effect）**。

一个好处是，函数即不依赖外部的状态也不修改外部的状态，函数调用的结果不依赖调用的时间和位置，这样写的代码容易进行推理，不容易出错。这使得单元测试和调试都更容易。

不变性带来的另一个好处是：由于（多个线程之间）不共享状态，不会造成**资源争用(Race condition)**，也就不需要用**锁**来保护可变状态，也就不会出现**死锁**，这样可以更好地并发起来，尤其是在**对称多处理器**（SMP）架构下能够更好地利用多个处理器（核）提供的并行处理能力



#### 33.  @DateTimeFormat @JsonFormat

@DateTimeFormat : 前端传入的参数是String , 而后端接收的字段是Date , 此情况用这个注解,

这两个注解组合使用



#### 34. 前台请求有数据,后台接收不到

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



#### 35. asp, jsp

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



#### 36. 正则

密码:

```java
var reg= /^(?=.*[a-zA-Z])(?=.*\d)(?=.*[~!@#$%^&*()_+`\-={}:";'<>?,./]).{8,}$/;
                              
"密码由8位数字、大小写字母和特殊符号组成!"                              
```



#### 37. mybatis plus and or

````java
        queryWrapper.and(wrapper -> wrapper.eq("delete_identity",0));

````



#### 38. mybatis plus 的一个注解

实体类上查数据库是忽略字段

```
/* 忽略字段 */
@TableField(exist = false)
private Integer count;
```



#### 39. aop

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
    private RoleService roleService;


    @Pointcut("execution(public * com.Gzs.demo.SpringSecurityDemo.Controller.PermissionController.saveRolePermission(*))")
    public void executeRequired() {
    }


    @Around(value = "executeRequired()")
    public Object adminRequired(ProceedingJoinPoint joinPoint) throws Throwable {
        boolean b = false;
        Object[] args = joinPoint.getArgs();
        JSONObject jsonObject = null;
        try {
            jsonObject = (JSONObject) args[0];
            ArrayList<Role> list = new ArrayList<>();

            UserDetails userDetails = (UserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
            Collection<? extends GrantedAuthority> authorities = userDetails.getAuthorities();

            for (GrantedAuthority authority : authorities) {

                list.add(roleService.getById(authority.getAuthority()));
            }


            for (int i = 0; i < list.size(); i++) {
                if ("admin".equals(list.get(i).getRoleCode())) {
                    b = true;
                }

            }
        } catch (Exception e) {

        }


        if (b == false) {
            jsonObject.put("adminRequired", "false");

        } else {
            jsonObject.put("adminRequired", "true");
        }

        return joinPoint.proceed(args);
    }
}
```



注意点 :

1. 

 @Pointcut("execution(public * com.Gzs.demo.SpringSecurityDemo.Controller.PermissionController.saveRolePermission(*))")

最后的 saveRolePermission(*)  方法括号里面 有 " * " 号,表示 任意参数, 如果不加 " * "号, 表示 没有参数

2. @Before 目前不知道怎么 改变参数值;


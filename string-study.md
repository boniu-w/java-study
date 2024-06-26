# 1. string



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

在 Java 中，将对象转换为字符串有多种方式，包括 `(String)` 强制转换、`String.valueOf()` 方法和 `toString()` 方法。它们的用途和实现方式各不相同，下面详细解释它们的区别及使用场景：

### 1. `(String) 强制转换`

强制类型转换 `(String)` 是将对象类型强制转换为 `String` 类型。这种方式要求对象本身已经是 `String` 类型，否则会抛出 `ClassCastException`。

#### 使用场景：

- 当你确定一个对象已经是 `String` 类型，并且想通过强制类型转换将其转换为 `String` 类型。

#### 示例：

```
java
复制代码
Object obj = "Hello";
String str = (String) obj; // 这是安全的，因为 obj 本身就是 String 类型

Object obj2 = 123;
String str2 = (String) obj2; // 这是不安全的，会抛出 ClassCastException
```

### 2. `String.valueOf()`

`String.valueOf()` 方法是一个静态方法，它接受各种类型的参数（如 `int`、`double`、`char`、`boolean` 以及 `Object`），并将其转换为 `String` 类型。

#### 实现原理：

- 如果参数为 `null`，返回字符串 `"null"`。
- 如果参数不是 `null`，则调用参数的 `toString()` 方法返回其字符串表示。

#### 使用场景：

- 当你需要将任何类型的对象或原始数据类型转换为字符串，并且需要处理 `null` 值而不会抛出异常时。

#### 示例：

```java
int number = 10;
String strNumber = String.valueOf(number); // "10"

Object obj = null;
String strObj = String.valueOf(obj); // "null"
```

### 3. `toString()`

`toString()` 是所有 Java 对象的父类 `Object` 的方法。每个类都可以覆盖 `toString()` 方法，以返回对象的字符串表示。

#### 实现原理：

- 对于 `Object` 类，默认实现返回对象的类名和哈希码。
- 对于覆盖了 `toString()` 方法的类，返回该类定义的字符串表示。

#### 使用场景：

- 当你需要获取对象的字符串表示，并且确保对象不为 `null` 时。

#### 示例：

```
java
复制代码
Object obj = 123;
String strObj = obj.toString(); // "123"

Object obj2 = null;
String strObj2 = obj2.toString(); // 抛出 NullPointerException
```

### 综上所述

- **(String) 强制转换**：
  - 适用于确定对象本身已经是 `String` 类型的情况。
  - 不安全，若对象不是 `String` 类型会抛出 `ClassCastException`。
- **String.valueOf()**：
  - 适用于将各种类型（包括原始类型和对象）转换为字符串。
  - 安全处理 `null`，返回字符串 `"null"`。
- **toString()**：
  - 适用于将对象转换为字符串，依赖于对象实现的 `toString()` 方法。
  - 需要确保对象不为 `null`，否则会抛出 `NullPointerException`。

### 使用示例总结

```java
// (String) 强制转换
Object strObj = "Hello";
String str1 = (String) strObj; // 安全
Object intObj = 123;
String str2 = (String) intObj; // 抛出 ClassCastException

// String.valueOf()
int number = 10;
String str3 = String.valueOf(number); // "10"
Object nullObj = null;
String str4 = String.valueOf(nullObj); // "null"

// toString()
Integer integer = 123;
String str5 = integer.toString(); // "123"
Object nullObj2 = null;
try {
    String str6 = nullObj2.toString(); // 抛出 NullPointerException
} catch (NullPointerException e) {
    System.out.println("Caught NullPointerException");
}
```

通过理解它们的区别和使用场景，可以在不同的情况下选择最合适的方法将对象转换为字符串。



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



# 2. json string

```java
    /************************************************************************
     * @author: wg
     * @description:
     * 1. jsonArray alibaba
     * 2. list 平均值
     * 3. jsonObject alibaba
     * @params:
     * @return:
     * @createTime: 11:01  2023/1/10
     * @updateTime: 11:01  2023/1/10
     ************************************************************************/
    @Test
    public void testJsonArray() {
        String jsonString = "[{\"key\":2,\"sectionCode\":\"M2\",\"c1\":\"222\",\"min\":222,\"c2\":\"222\"},{\"key\":1,\"sectionCode\":\"M1\",\"min\":333,\"c1\":\"333\",\"c2\":\"333\"},{\"sectionCode\":\"最小值\",\"c1\":222,\"min\":222,\"c2\":222}]";
        JSONArray jsonArray = JSON.parseArray(jsonString);

        System.out.println(jsonArray);

        ArrayList<Double> mins = new ArrayList<>();
        ArrayList<Double> cs = new ArrayList<>();
        for (Object o : jsonArray) {
            JSONObject jsonObject = JSON.parseObject(o.toString());
            Map<String, Object> innerMap = jsonObject.getInnerMap();
            if (innerMap.containsKey("key")) {
                double min = jsonObject.getDouble("min");
                mins.add(min);
            } else {
                double c1 = jsonObject.getDouble("c1");
                double c2 = jsonObject.getDouble("c2");
                cs.add(c1);
                cs.add(c2);
            }
        }

        double ma = mins.stream().mapToDouble(Double::valueOf).average().getAsDouble();
        double ca = cs.stream().mapToDouble(Double::valueOf).average().getAsDouble();

        System.out.println();
        System.out.println(ma);
        System.out.println(ca);
    }
```





# 3. string a; 和 string b=null;



在 Java 中，`String` 类型的变量在声明时存在不同的初始化状态。以下是对 `String a;` 和 `String b = null;` 两种情况在虚拟内存中的分析：

### 情况 1: `String a;`

```
java
复制代码
String a;
```

在这种情况下，变量 `a` 被声明为 `String` 类型，但未初始化。这意味着：

- `a` 是一个局部变量。
- 在局部变量的声明中，如果没有初始化，变量是未赋值的。
- 由于 Java 不允许使用未初始化的局部变量，编译器会强制要求在使用之前对其进行初始化。
- 在方法栈帧中，`a` 被声明，但在其被初始化之前，不能使用。

#### 在虚拟内存中的表示：

- 方法栈中有一个为 `a` 保留的位置，但未赋值。在 Java 的虚拟机中，这意味着该位置的值是未定义的（不同于 `null`）。

### 情况 2: `String b = null;`

```
java
复制代码
String b = null;
```

在这种情况下，变量 `b` 被声明为 `String` 类型，并初始化为 `null`。这意味着：

- `b` 是一个局部变量。
- 它已经初始化，并且值为 `null`。
- 在方法栈帧中，`b` 有一个分配的位置，并且其值为 `null`。
- `null` 表示该变量不引用任何对象。

#### 在虚拟内存中的表示：

- 方法栈中有一个为 `b` 分配的位置，并且这个位置的值是 `null`。

### 内存布局比较

#### 方法栈 (Stack)：

方法栈用于存储局部变量和部分方法调用信息。每次调用方法时，会在栈上创建一个新的栈帧（stack frame）。栈帧中包含该方法的局部变量、操作数栈和帧数据。

1. **String a; 未初始化的局部变量**：
   - 由于 `a` 仅声明但未初始化，因此在方法栈中为其分配了一个位置，但该位置的内容是未定义的，无法使用。
2. **String b = null; 已初始化的局部变量**：
   - `b` 被声明和初始化为 `null`，因此在方法栈中为其分配了一个位置，并将其值设置为 `null`。

### 总结

- **String a;**：变量 `a` 被声明但未初始化，因此在方法栈中有一个未定义的保留位置，使用该变量会导致编译错误。
- **String b = null;**：变量 `b` 被声明并初始化为 `null`，在方法栈中有一个已定义的位置，其值为 `null`，表示该变量不引用任何对象。

通过这两种情况的对比，可以看出初始化局部变量的重要性，以及它们在虚拟内存中不同的表示方式。
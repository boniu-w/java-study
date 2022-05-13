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
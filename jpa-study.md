### 1. GenerationType

---

**TABLE：使用一个特定的数据库表格来保存主键。**

**SEQUENCE：根据底层数据库的序列来生成主键，条件是数据库支持序列。**

**IDENTITY：主键由数据库自动生成（主要是自动增长型）**

**AUTO：主键由程序控制。**



### 2. 关键字

And --- 等价于 SQL 中的 and 关键字，比如 findByUsernameAndPassword(String user, Striang pwd)；

Or --- 等价于 SQL 中的 or 关键字，比如 findByUsernameOrAddress(String user, String addr)；

Between --- 等价于 SQL 中的 between 关键字，比如 findBySalaryBetween(int max, int min)；

LessThan --- 等价于 SQL 中的 "<"，比如 findBySalaryLessThan(int max)；

GreaterThan --- 等价于 SQL 中的">"，比如 findBySalaryGreaterThan(int min)；

IsNull --- 等价于 SQL 中的 "is null"，比如 findByUsernameIsNull()；

IsNotNull --- 等价于 SQL 中的 "is not null"，比如 findByUsernameIsNotNull()；

NotNull --- 与 IsNotNull 等价；

Like --- 等价于 SQL 中的 "like"，比如 findByUsernameLike(String user)；
具体使用：findByUsernameLike("%"+user+"%)；

NotLike --- 等价于 SQL 中的 "not like"，比如 findByUsernameNotLike(String user)；

OrderBy --- 等价于 SQL 中的 "order by"，比如 findByUsernameOrderBySalaryAsc(String user)；
// 多条件
findByUsernameOrderBySalaryAscYearAsc(String user)；

Not --- 等价于 SQL 中的 "！ ="，比如 findByUsernameNot(String user)；

In --- 等价于 SQL 中的 "in"，比如 findByUsernameIn(Collection<String> userList) ，方法的参数可以是 Collection 类型，也可以是数组或者不定长参数；

NotIn --- 等价于 SQL 中的 "not in"，比如 findByUsernameNotIn(Collection<String> 
userList) ，方法的参数可以是 Collection 类型，也可以是数组或者不定长参数；




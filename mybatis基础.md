# 1. mybatis #号 $符号

1. #将传入的数据都当成一个字符串，会对自动传入的数据加一个双引号。如：order by #user_id#，如果传入的值是111,那么解析成sql时的值为order by "111", 如果传入的值是id，则解析成的sql为order by "id".
2. $将传入的数据直接显示生成在sql中。如：order by $user_id$，如果传入的值是111,那么解析成sql时的值为order by user_id, 如果传入的值是id，则解析成的sql为order by id.
3. \#方式能够很大程度防止sql注入。
4. $方式一般用于传入数据库对象，例如传入表名
5. MyBatis排序时使用order by 动态参数时需要注意，用$而不是#



# 2. insert语句

```sql
		<insert id="insertIntoPersonTable" 		    parameterType="org.jeecg.modules.demo.PERSON_TABLE.entity.PERSON_TABLE">
        INSERT INTO "C##DYSJ"."PERSON_TABLE" (
        <if test="id != '' and id !=  null">
            ID,
        </if>
        <if test="company != '' and company !=  null">
            COMPANY,
        </if>
        <if test="relationship != '' and relationship !=  null">
            RELATIONSHIP,
        </if>
        <if test="relationshipPerson != '' and relationshipPerson !=  null">
            RELATIONSHIP_PERSON,
        </if>
        <if test="personTypeId != '' and personTypeId !=  null">
            PERSON_TYPE_ID,
        </if>
        <if test="remarks != '' and remarks !=  null">
            REMARKS,
        </if>
        <if test="timeStamp != '' and timeStamp !=  null">
            TIME_STAMP,
        </if>
        <if test="deleteIdentifier == 0 or deleteIdentifier == 1">
            DELETE_IDENTIFIER,
        </if>
        <if test="reserve != '' and reserve !=  null">
            RESERVE,
        </if>
        <if test="reserve1 != '' and reserve1 !=  null">
            RESERVE1,
        </if>
        <if test="reserve2 != '' and reserve2 !=  null">
            RESERVE2,
        </if>
        <if test="createTime != '' and createTime !=  null">
            CREATE_TIME,
        </if>
        <if test="introductionId != '' and introductionId !=  null">
            INTRODUCTION_ID
        </if>

        )
        VALUES
        (

        <if test="id != '' and id !=  null">
            #{id},
        </if>
        <if test="company != '' and company !=  null">
            #{company},
        </if>
        <if test="relationship != '' and relationship !=  null">
            #{relationship},
        </if>
        <if test="relationshipPerson != '' and relationshipPerson !=  null">
            #{relationshipPerson},
        </if>
        <if test="personTypeId != '' and personTypeId !=  null">
            #{personTypeId},
        </if>
        <if test="remarks != '' and remarks !=  null">
            #{remarks},
        </if>
        <if test="timeStamp != '' and timeStamp !=  null">
            #{timeStamp},
        </if>
        <if test="deleteIdentifier == 0 or deleteIdentifier == 1">
            #{deleteIdentifier},
        </if>
        <if test="reserve != '' and reserve !=  null">
            #{reserve},
        </if>
        <if test="reserve1 != '' and reserve1 !=  null">
            #{reserve1},
        </if>
        <if test="reserve2 != '' and reserve2 !=  null">
            #{reserve2},
        </if>
        <if test="createTime != '' and createTime !=  null">
            to_date(#{createTime},'SYYYY-MM-DD HH24:MI:SS'),
        </if>
        <if test="introductionId != '' and introductionId !=  null">
            #{introductionId}
        </if>


        )

    </insert>
```



# 3. update

```sql
 <update id="updatePersonTableById">
        UPDATE PERSON_TABLE
        SET DELETE_IDENTIFIER = 1
        WHERE
	    ID = #{id}

    </update>
```



```xml
   <!-- 模板语法 -->
	<update id="updateEquipmentUse" parameterType="com.sevenme.modules.pia.entity.EquipmentUseEntity">
        UPDATE
        equipment_use_table <!-- Replace with your actual table name -->
        SET
        <trim suffixOverrides=",">
#foreach($column in $columns)
        <if test="${column.columnName} != null">
        ${column.columnName} = #{${column.attrname}},
        </if>
#end
        </trim>
    </update>
```





```xml
	<update id="updateEquipmentUse" parameterType="com.sevenme.modules.pia.entity.EquipmentUseEntity">
        UPDATE
        equipment_use
        SET
        <trim suffixOverrides=",">
            <!--<if test="ID != null">-->
            <!--    ID = #{id},-->
            <!--</if>-->
            <if test="SET_DATE != null">
                SET_DATE = #{setDate},
            </if>
            <if test="SET_STAFF != null">
                SET_STAFF = #{setStaff},
            </if>
            <if test="SET_REASON != null">
                SET_REASON = #{setReason},
            </if>
            <if test="MATERIAL_STRENGTH_FACTOR != null">
                MATERIAL_STRENGTH_FACTOR = #{materialStrengthFactor},
            </if>
            <if test="MODELING_FACTOR != null">
                MODELING_FACTOR = #{modelingFactor},
            </if>
            <if test="SAFETY_FACTOR != null">
                SAFETY_FACTOR = #{safetyFactor},
            </if>
            <if test="LOSS_OF_ULTIMATE_WALL_THICKNESS != null">
                LOSS_OF_ULTIMATE_WALL_THICKNESS = #{lossOfUltimateWallThickness},
            </if>
            <!--<if test="DEL_FLAG != null">-->
            <!--    DEL_FLAG = #{delFlag},-->
            <!--</if>-->
            <if test="CREATE_USER != null">
                CREATE_USER = #{createUser},
            </if>
            <if test="UPDATE_USER != null">
                UPDATE_USER = #{updateUser},
            </if>
            <if test="CREATE_TIME != null">
                CREATE_TIME = #{createTime},
            </if>
            <if test="UPDATE_TIME != null">
                UPDATE_TIME = #{updateTime},
            </if>
        </trim>
        where
        id=#{id}
        and
        DEL_FLAG=0;
    </update>
```



# 4. jdbcType

```
JDBC Type           Java Type  
CHAR                String  
VARCHAR             String  
LONGVARCHAR         String  
NUMERIC             java.math.BigDecimal  
DECIMAL             java.math.BigDecimal  
BIT                 boolean  
BOOLEAN             boolean  
TINYINT             byte  
SMALLINT            short  
INTEGER             INTEGER  
BIGINT              long  
REAL                float  
FLOAT               double  
DOUBLE              double  
BINARY              byte[]  
VARBINARY           byte[]  
LONGVARBINARY       byte[]  
DATE                java.sql.Date  
TIME                java.sql.Time  
TIMESTAMP           java.sql.Timestamp  
CLOB                Clob  
BLOB                Blob  
ARRAY               Array  
DISTINCT            mapping of underlying type  
STRUCT              Struct  
REF                 Ref  
DATALINK            java.net.URL
```

# 5. 数据类型 映射关系

Mybatis                                  java                                     SQL

               integer                          int OR Integer                              INTEGER
    
               long                              long OR java.lang.Long               BIGINT
    
               short                             short OR java.lang.Short             SMALLINT
    
               float                              float OR java.lang.Float               FLOAT
    
               double                          double OR java.lang.Double        DOUBLE
    
               big_decimal                  java.math.BigDecimal                  NUMERIC
    
               character                      java.lang.String                            CHAR(1)
    
               string                            java.lang.String                             VARCHAR
    
               byte                              byte OR java.lang.Byte                 TINYINT
    
               boolean                        boolean OR java.lang.Boolean     BIT
    
               yes_no                         boolean OR java.lang.Boolean     CHAR(1) ('Y' OR 'N')
    
               true_false                     boolean OR java.lang.Boolean     CHAR(1) ('Y' OR 'N')
    
               date                              java.util.Date OR java.sql.Date     DATE
    
               time                              java.util.Date OR java.sql.Time     TIME
    
               timestamp                    java.util.Date OR java.sql.TimeStamp    TIMESTAMP
    
               calendar                       java.util.Calendar                           TIMESTAMP   
    
               calendar_date              java.util.Calendar                           DTAE
    
               binary                           byte[]                                              VARBINARY OR BLOB
    
               text                               java.lang.String                              CLOB
    
               serializable                   java.io.Seriailzable                         VARBINARY OR BLOB
    
               clob                              java.sql.Clob                                   CLOB
    
               blob                              java.sql.Blob                                   BLOB
    
               class                            java.lang.Class                               VARCHAR                                
    
               locale                           java.util.Locale                               VARCHAR 
    
               timezone                      java.util.TimeZone                          VARCHAR  
    
               currency                       java.util.Currency                           VARCHAR



# 6. resultSetType



当使用MyBatis进行数据库操作时，查询结果可以被映射到Java对象中，以便于进行后续的业务处理。ResultSetType就是用来指定查询结果集的类型，以及结果集是否对数据库中数据的更改敏感。

1. ResultSetType.FORWARD_ONLY

ResultSetType.FORWARD_ONLY是默认的结果集类型，它表示结果集只能向前遍历，不能滚动或后退。这种类型的结果集只包含一次性读取的记录，因此在内存占用和查询性能方面具有优势。如果不需要在结果集中进行滚动或后退，可以使用该类型。

1. ResultSetType.SCROLL_SENSITIVE

ResultSetType.SCROLL_SENSITIVE表示结果集对数据库中数据的更改敏感。这种类型的结果集可以在迭代过程中通过ResultSet对象访问当前行，并且可以更改当前行的数据。此外，当数据库中的数据发生更改时，该结果集也会反映这些更改。这种类型的结果集需要更多的资源，因此应该谨慎使用。

1. ResultSetType.SCROLL_INSENSITIVE

ResultSetType.SCROLL_INSENSITIVE表示结果集不对数据库中数据的更改敏感。这种类型的结果集在迭代过程中也可以通过ResultSet对象访问当前行，但是无法更改当前行的数据，也不会反映数据库中的更改。该类型的结果集比SCROLL_SENSITIVE类型的结果集更加高效。

需要注意的是，ResultSetType的取值可能会因数据库类型或驱动程序的不同而受到限制。在使用时需要查看相关文档，以确保选择正确的ResultSetType。



# 7. 什么是预编译



### **什么是预编译（PreparedStatement）？**

**预编译（PreparedStatement）** 是 JDBC 提供的一种机制，用于**预先编译 SQL 语句结构**，并将参数值以安全的方式绑定，从而避免 SQL 注入。

#### **预编译的工作原理**

1. **SQL 语句预先编译**：
   SQL 语句的骨架（如 `SELECT * FROM users WHERE username = ? AND password = ?`）会被数据库预编译，`?` 是占位符。
2. **参数值安全绑定**：
   后续传入的参数值（如 `username = "admin"`）会被**严格视为数据**，而不会被解析为 SQL 代码。

```java
String sql = "SELECT * FROM users WHERE username = ? AND password = ?";
PreparedStatement stmt = connection.prepareStatement(sql);
stmt.setString(1, username); // 安全绑定参数
stmt.setString(2, password);
ResultSet rs = stmt.executeQuery();
```





# 8. 什么是 sql注入



**SQL 注入（SQL Injection）** 是一种常见的网络安全攻击手段，攻击者通过构造恶意的输入数据，篡改原始 SQL 语句的逻辑，从而绕过验证或窃取数据库中的敏感信息。

#### **SQL 注入示例**

假设有一个登录功能，SQL 语句如下：

```
SELECT * FROM users WHERE username = '${username}' AND password = '${password}'
```

如果用户输入：

- `username = 'admin' --`
- `password = 任意值`

拼接后的 SQL 会变成：

```
SELECT * FROM users WHERE username = 'admin' --' AND password = '任意值'
```

由于 `--` 是 SQL 注释符，后面的条件被忽略，导致攻击者无需密码即可登录 `admin` 账户。
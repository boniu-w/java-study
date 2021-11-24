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


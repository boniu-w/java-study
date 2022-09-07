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



# 例子

### 1. repository 例子

```java
package com.example.jpademo.dao;

import com.example.jpademo.entity.SysUser;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import javax.transaction.Transactional;
import java.util.List;

@Repository
public interface SysUserRepository extends JpaRepository<SysUser, Long> {

    List<SysUser> findAllByName(String name);

    List<SysUser> findAllByNameLike(String name);

    List<SysUser> findAllByNameStartingWith(String name);

    @Query("select u from SysUser u")
    List<SysUser> selectAllSysUser();

    @Query("select new com.example.jpademo.entity.SysUser(u.id)  from SysUser u") // 必须有 相应的构造器
    // @Query("select u.id from SysUser u") // 报错: No converter found capable of converting from type [java.lang.Long] to type [@org.springframework.data.jpa.repository.Query com.example.jpademo.entity.SysUser]
    List<SysUser> selectAllSysUsersId();

    @Query("select u from SysUser u where u.id= ?2 or u.name like CONCAT('%', ?1,'%') ")
    List<SysUser> selectSysUserWhere(String name, Long id);

    @Transactional
    @Modifying
    @Query("update SysUser u set u.name= ?1 where u.id=11111111111111")
    int updateSysUserName(String toBeModifiedName);

    /**
     * 使用 原生 sql 加 nativeQuery = true
     */
    @Query(value = "select id from sys_user", nativeQuery = true)
    List<Long> selectAllSysUser1();
}

```



### 2. entity 例子

```java
package com.example.jpademo.entity;

import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;

/************************************************************************
 * @author: wg
 * @description:
 * @createTime: 11:37 2022/8/19
 * @updateTime: 11:37 2022/8/19
 ************************************************************************/
@Table(name = "sys_user")
@Entity
public class SysUser {
    @Id
    @Column(name = "id", nullable = false)
    @GeneratedValue(strategy = GenerationType.AUTO, generator = "myIdGeneratorConfig")
    @GenericGenerator(name = "myIdGeneratorConfig", strategy="com.example.jpademo.config.MyIdGeneratorConfig")
    private Long id;

    private String name;

    private String password;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    @Override
    public String toString() {
        return "SysUser{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", password='" + password + '\'' +
                '}';
    }

    public SysUser(Long id) {
        this.id = id;
    }

    public SysUser() {
    }
}

```


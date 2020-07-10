# java-framework-2020
Java web framework with all the new techs and features in 2020

[toc]

# Spring Data 相关

## 方法名推断查询：

### 一些示例
```
List<Person> findByEmailAddressAndLastname(EmailAddress emailAddress, String lastname);

// Enables the distinct flag for the query   
List<Person> findDistinctPeopleByLastnameOrFirstname(String lastname, String firstname);    
List<Person> findPeopleDistinctByLastnameOrFirstname(String lastname, String firstname);

// Enabling ignoring case for an individual property   
List<Person> findByLastnameIgnoreCase(String lastname);   
// Enabling ignoring case for all suitable properties      
List<Person> findByLastnameAndFirstnameAllIgnoreCase   (String lastname, String firstname);

  // Enabling static ORDER BY for a query   
List<Person> findByLastnameOrderByFirstnameAsc(String lastname);   
List<Person> findByLastnameOrderByFirstnameDesc(String lastname);
  ```

### 属性表达式

针对如下查询：   

`List<Person> findByAddressZipCode(ZipCode zipCode);`

&emsp;&emsp;假设Person对象拥有一个Address属性，Address属性又拥有ZipCode属性。
该情况下，解析器会创建属性遍历规则： x.address.zipCode。

&emsp;&emsp;整个解析器的算法是：首先将AddressZipCode作为一个整体属性(首字母小写后)名来查找。如果找到就使用该属性。如果找不到，它会将它按照头部最大化优先规则进行拆分，拆分后变为AddressZip和Code两部分，然后用着两个名称继续查找相关属性。如果找不到就继续拆分为Address和ZipCode两部分继续查找。整个过程一直继续。

&emsp;&emsp;如果遇到特殊情况不能被以上规则匹配到，比如Person对象中确实包含一个addressZip属性。对于这种情况可以使用下划线如下方法：  

`List<Person> findByAddress_ZipCode(ZipCode zipCode);`

### 分页、排序处理
可以项查询中传入 Pageable 和 Sort 对象来作为分页和排序参数。
```
Page<User> findByLastname(String lastname, Pageable pageable);

Slice<User> findByLastname(String lastname, Pageable pageable);

List<User> findByLastname(String lastname, Sort sort);

List<User> findByLastname(String lastname, Pageable pageable);
```
**注意：不要向查询方法中传入空的Pageable和Sort对象，而应该传入Sort.unsorted() 和 Pageable.unpaged() 作为非分页和排序的表示**

上面返回值为Page的查询会触发执行count查询总记录数的操作。如果不想触发count查询，可以是用Slice替代，Slice对象只知道下一个Slice是否存在，而不知道总记录数等信息。

排序也可通过Pageable来达成。如果仅仅是需要排序功能，那可以使用Sort对象。

## Spring Data JPA相关
### 方法名称推断SQL查询关键字SQL映射表
|Keyword|Sample|JPQL snippet|
|----|----|----|
|`And`|`findByLastnameAndFirstname`|`… where x.lastname = ?1 and x.firstname = ?2`|   
|`Or`|`findByLastnameOrFirstname`|`… where x.lastname = ?1 or x.firstname = ?2`|
|`Is, Equals`|`findByFirstname,findByFirstnameIs,findByFirstnameEquals`|`… where x.firstname = ?1`|
|`Between`|`findByStartDateBetween`|`… where x.startDate between ?1 and ?2`|
|`LessThan`|`findByAgeLessThan`|`… where x.age < ?1`|
|`LessThanEqual`|`findByAgeLessThanEqual`|`… where x.age <= ?1`|
|`GreaterThan`|`findByAgeGreaterThan`|`… where x.age > ?1`|
|`GreaterThanEqual`|`findByAgeGreaterThanEqual`|`… where x.age >= ?1`|
|`After`|`findByStartDateAfter`|`… where x.startDate > ?1`|
|`Before`|`findByStartDateBefore`|`… where x.startDate < ?1`|
|`IsNull, Null`|`findByAge(Is)Null`|`… where x.age is null`|
|`IsNotNull, NotNull`|`findByAge(Is)NotNull`|`… where x.age not null`|
|`Like`|`findByFirstnameLike`|`… where x.firstname like ?1`|
|`NotLike`|`findByFirstnameNotLike`|`… where x.firstname not like ?1`|
|`StartingWith`|`findByFirstnameStartingWith`|`… where x.firstname like ?1 (会在参数后面追加 %)`|
|`EndingWith`|`findByFirstnameEndingWith`|`… where x.firstname like ?1 (会在参数前面追加 %)`|
|`Containing`|`findByFirstnameContaining`|`… where x.firstname like ?1 (会在参数前后追加 %)`|
|`OrderBy`|`findByAgeOrderByLastnameDesc`|`… where x.age = ?1 order by x.lastname desc`|
|`Not`|`findByLastnameNot`|`… where x.lastname <> ?1`|
|`In`|`findByAgeIn(Collection<Age> ages)`|`… where x.age in ?1`|
|`NotIn`|`findByAgeNotIn(Collection<Age> ages)`|`… where x.age not in ?1`|
|`True`|`findByActiveTrue()`|`… where x.active = true`|
|`False`|`findByActiveFalse()`|`… where x.active = false`|
|`IgnoreCase`|`findByFirstnameIgnoreCase`|`… where UPPER(x.firstame) = UPPER(?1)`|

***Tip: In 和 Not 关键字可接收任何 Collection 的子类、数组和可变参数作为入参。***

### 使用 @Query 自定义查询
示例：在查询方法上定义查询语句(JPQL)
```
public interface UserRepository extends JpaRepository<User, Long> {

  @Query("select u from User u where u.emailAddress = ?1")
  User findByEmailAddress(String emailAddress);
}
```
示例：使用like关键字
```
public interface UserRepository extends JpaRepository<User, Long> {

  @Query("select u from User u where u.firstname like %?1")
  List<User> findByFirstnameEndsWith(String firstname);
}
```
示例：使用本地查询
```
public interface UserRepository extends JpaRepository<User, Long> {

  @Query(value = "SELECT * FROM USERS WHERE EMAIL_ADDRESS = ?1", nativeQuery = true)
  User findByEmailAddress(String emailAddress);
}
```
***Tip:DATA JPA 目前并不支持原生SQL的动态排序功能。但可以使用原生SQL的动态分页功能，只需要在多传入 count 语句就可以。***   
示例：在原生查询中使用count语句来分页
```
public interface UserRepository extends JpaRepository<User, Long> {

  @Query(value = "SELECT * FROM USERS WHERE LASTNAME = ?1",
    countQuery = "SELECT count(*) FROM USERS WHERE LASTNAME = ?1",
    nativeQuery = true)
  Page<User> findByLastname(String lastname, Pageable pageable);
}
```   
示例：使用Sort和JpaSort

```
public interface UserRepository extends JpaRepository<User, Long> {

  @Query("select u from User u where u.lastname like ?1%")
  List<User> findByAndSort(String lastname, Sort sort);

  @Query("select u.id, LENGTH(u.firstname) as fn_len from User u where u.lastname like ?1%")
  List<Object[]> findByAsArrayAndSort(String lastname, Sort sort);
}

repo.findByAndSort("lannister", new Sort("firstname"));                               
repo.findByAndSort("stark", new Sort("LENGTH(firstname)"));                
repo.findByAndSort("targaryen", JpaSort.unsafe("LENGTH(firstname)"));    
repo.findByAsArrayAndSort("bolton", new Sort("fn_len"));               
```
**默认情况下Data JPA 不能在Order中调用函数，可以使用 `JpaSort.unsafe` 来调用函数**

示例：使用命名参数
```
public interface UserRepository extends JpaRepository<User, Long> {

  @Query("select u from User u where u.firstname = :firstname or u.lastname = :lastname")
  User findByLastnameOrFirstname(@Param("lastname") String lastname,
                                 @Param("firstname") String firstname);
}
```

### 提取实体关联数据
&emsp;&emsp;默认情况下 Data JPA 使用懒加载关联实体数据，如果需要立即加载实体数据。可以是用命名视图或者临时视图来指定要加载的关联数据。   
示例：   
（1）定义命名视图
```
@Entity
@NamedEntityGraph(name = "GroupInfo.detail",
  attributeNodes = @NamedAttributeNode("members"))
public class GroupInfo {

  // default fetch mode is lazy.
  @ManyToMany
  List<GroupMember> members = new ArrayList<GroupMember>();

  …
}
```
(2) 在查询中使用命名视图
```
@Repository
public interface GroupRepository extends CrudRepository<GroupInfo, String> {

  @EntityGraph(value = "GroupInfo.detail", type = EntityGraphType.LOAD)
  GroupInfo getByGroupName(String name);

}
```   
&emsp;&emsp;通过在临时视图中指定属性名称来提取数据  
(3) 使用临时视图来提取数据
```
@Repository
public interface GroupRepository extends CrudRepository<GroupInfo, String> {

  @EntityGraph(attributePaths = { "members" })
  GroupInfo getByGroupName(String name);

}
```

### 投影查询
&emsp;&emsp;有时候我们只需要查询实体中的一部分字段，我们可以使用投影查询来优化查询效率。   

#### 基于接口的投影查询

示例：  
（1）定义一个数据对象和仓库
```
class Person {

  @Id UUID id;
  String firstname, lastname;
  Address address;

  static class Address {
    String zipCode, city, street;
  }
}

interface PersonRepository extends Repository<Person, UUID> {

  Collection<Person> findByLastname(String lastname);
}
```
（2）使用基于接口的投影查询
```
interface NamesOnly {

  String getFirstname();
  String getLastname();
}
```
```
interface PersonRepository extends Repository<Person, UUID> {

  Collection<NamesOnly> findByLastname(String lastname);
}
```
&emsp;&emsp;对于上面的示例，查询执行引擎会在运行时创建接口的代理。

（3）投影接口提取子属性
```
interface PersonSummary {

  String getFirstname();
  String getLastname();
  AddressSummary getAddress();

  interface AddressSummary {
    String getCity();
  }
}
```

#### 基于类的投影查询（DTO对象）
示例：  
（1）定义一个DTO对象
```
class NamesOnly {

  private final String firstname, lastname;

  NamesOnly(String firstname, String lastname) {

    this.firstname = firstname;
    this.lastname = lastname;
  }

  String getFirstname() {
    return this.firstname;
  }

  String getLastname() {
    return this.lastname;
  }

  // equals(…) and hashCode() implementations
}
```
&emsp;&emsp;可以使用Project Lombok 来简化类的定义，使用下面的定义等同与上面的定义
```
@Value
class NamesOnly {
	String firstname, lastname;
}
```

#### 动态投影
&emsp;&emsp;可以在运行时传递类型的方式来动态的定义查询的返回类型   
示例：    
（1）使用动态投影查询
```
interface PersonRepository extends Repository<Person, UUID> {

  <T> Collection<T> findByLastname(String lastname, Class<T> type);
}
```
```
void someMethod(PersonRepository people) {

  Collection<Person> aggregates =
    people.findByLastname("Matthews", Person.class);

  Collection<NamesOnly> aggregates =
    people.findByLastname("Matthews", NamesOnly.class);
}
```

### 事务
&emsp;&emsp; 默认情况下，repository 实例的 CRUD 方法会使用事务。对于所有读取的操作会使用 @Transactional(readOnly=true)；其它操作会使用 @Transactional。

&emsp;&emsp;我们很多时候都会在服务层使用事务，来跨越多个repository。
可以通过该如下方式来使用 service 层事务。  
（1）首先开启基于注解的事务配置：在配置类上注解 @EnableTransactionManagement    
（2）在 service 类中使用事务注解：  
```
@Service
class UserManagementImpl implements UserManagement {

  private final UserRepository userRepository;
  private final RoleRepository roleRepository;

  @Autowired
  public UserManagementImpl(UserRepository userRepository,
    RoleRepository roleRepository) {
    this.userRepository = userRepository;
    this.roleRepository = roleRepository;
  }

  @Transactional
  public void addRoleToAllUsers(String roleName) {

    Role role = roleRepository.findByName(roleName);

    for (User user : userRepository.findAll()) {
      user.addRole(role);
      userRepository.save(user);
    }
}
```

***Tip:对于只读查询，使用 readOnly 标记可以优化查询效率。比如在使用Hibernate的情况下，readOnly会避免使用脏检查，从而优化大对象树的查询***



### 更多
&emsp;&emsp;更多Spring Data API的使用请参考官方文档，如以下地址
`https://docs.spring.io/spring-data/jpa/docs/2.3.1.RELEASE/reference/html/#repositories.special-parameters`


# Spring Security 相关
## Spring Security 登录认证
Spring Security 中的表单登录认证需要提供：  
1.一个用于登录的表单  
2.一个登录控制器(Controller)

### 1.登录表单

代码示例：登表单login.html
```
<!DOCTYPE html>
<html xmlns="http://www.w3.org/1999/xhtml" xmlns:th="https://www.thymeleaf.org">
    <head>
        <title>Please Log In</title>
    </head>
    <body>
        <h1>Please Log In</h1>
        <div th:if="${param.error}">
            Invalid username and password.</div>
        <div th:if="${param.logout}">
            You have been logged out.</div>
        <form th:action="@{/login}" method="post">
            <div>
            <input type="text" name="username" placeholder="Username"/>
            </div>
            <div>
            <input type="password" name="password" placeholder="Password"/>
            </div>
            <input type="submit" value="Log in" />
        </form>
    </body>
</html>
``` 
默认的登录表单有如下几个要求：
* 表单应提交post请求到 /login
* 表单中需要包含一个 CSRF Token
* 表单中需要指定一个用户名作为参数，参数名称为 `username`
* 表单中需要指定一个密码作为参数，参数名称为 `password`
* 如果HTTP参数 error 存在，表单中需提示用户输入有效的用户名和密码
* 如果HTTP参数 logout 存在，表单中需提示用户登出成功

### 2.登录控制器
示例代码：LoginController.java
```
@Controller
class LoginController {
    @GetMapping("/login")
    String login() {
        return "login";
    }
}
```

## TODO待完善功能
* Spring Boot 热部署
* 复杂SQL支持，让SQL写入到文件（如XML）而不是写在java源代码中
* GraphQL异常信息处理
* Subscription 功能实现
* 文件上传功能实现
* （可选）自动生成全部或部分Schema文件
* （控制台）日志输出
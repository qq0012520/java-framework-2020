# java-framework-2020
Java web framework with all the new techs and features in 2020

## Spring Data Repository 使用实例

### 方法名推断查询：

#### 一些示例
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

#### 属性表达式

针对如下查询：   

`List<Person> findByAddressZipCode(ZipCode zipCode);`

&emsp;&emsp;假设Person对象拥有一个Address属性，Address属性又拥有ZipCode属性。
该情况下，解析器会创建属性遍历规则： x.address.zipCode。

&emsp;&emsp;整个解析器的算法是：首先将AddressZipCode作为一个整体属性(首字母小写后)名来查找。如果找到就使用该属性。如果找不到，它会将它按照头部最大化优先规则进行拆分，拆分后变为AddressZip和Code两部分，然后用着两个名称继续查找相关属性。如果找不到就继续拆分为Address和ZipCode两部分继续查找。整个过程一直继续。

&emsp;&emsp;如果遇到特殊情况不能被以上规则匹配到，比如Person对象中确实包含一个addressZip属性。对于这种情况可以使用下划线如下方法：  

`List<Person> findByAddress_ZipCode(ZipCode zipCode);`

#### 分页、排序处理
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

更多Spring Data API的使用请参考官方文档，如以下地址
`https://docs.spring.io/spring-data/jpa/docs/2.3.1.RELEASE/reference/html/#repositories.special-parameters`
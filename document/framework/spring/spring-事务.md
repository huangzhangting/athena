# Spring 事务
支持声明式事务：注解、xml配置
支持编程式事务：事务模板类

### 事务传播性
事务传播行为类型	        说明
PROPAGATION_REQUIRED	如果当前没有事务，就新建一个事务，如果已经存在一个事务中，加入到这个事务中。这是最常见的选择。
PROPAGATION_SUPPORTS	支持当前事务，如果当前没有事务，就以非事务方式执行。
PROPAGATION_MANDATORY	使用当前的事务，如果当前没有事务，就抛出异常。
PROPAGATION_REQUIRES_NEW	新建事务，如果当前存在事务，把当前事务挂起。
PROPAGATION_NOT_SUPPORTED	以非事务方式执行操作，如果当前存在事务，就把当前事务挂起。
PROPAGATION_NEVER	以非事务方式执行，如果当前存在事务，则抛出异常。
PROPAGATION_NESTED	如果当前存在事务，则在嵌套事务内执行。如果当前没有事务，则执行与PROPAGATION_REQUIRED类似的操作。

### 事务嵌套，容易混淆点
@Transactional(propagation = Propagation.REQUIRED) 如果当前有则使用当前事务
@Transactional(propagation = Propagation.NESTED) 利用sql的savepoint进行嵌套事务
@Transactional(propagation = Propagation.REQUIRES_NEW) 开启新的事务

REQUIRES_NEW 在父级方法中调用，开启新的事务，回滚与否只与本方法有关，
 父级方法进行捕获异常操作后，可以防止父级方法回滚
 
REQUIRED 在父级方法中调用，沿用父级事务，如果本方法抛出异常，无论父级方法是否捕获，
 都会引起父级与本方法的回滚，因为他们属于一个事务，事务切面同时监控两个方法，出现异常即回滚

NESTED 在父级方法中调用，类似于REQUIRED，但如果父级方法进行捕获异常，整个事务中将只会回滚本方法中的数据库操作，
 父级方法可以根据异常情况进行后续操作，决定整体回滚还是部分提交；
 如果本方法无异常，父级方法出现异常，本方法与父级方法都会回滚

### 事务实现原理



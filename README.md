# SimpleSSM
一个简单的SSM项目
#### 在sqlyog执行数据库脚本
```
CREATE DATABASE IF NOT EXISTS shop CHARSET utf8;
USE shop;
DROP TABLE IF EXISTS `roles`;
CREATE TABLE `roles`  (
  `id` BIGINT(20) NOT NULL AUTO_INCREMENT,
  `name` VARCHAR(100) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  `note` VARCHAR(500) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  `created` DATETIME NULL DEFAULT NULL,
  `updated` DATETIME NULL DEFAULT NULL,
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = INNODB AUTO_INCREMENT = 3 CHARACTER SET = utf8 COLLATE = utf8_general_ci ROW_FORMAT = COMPACT;

INSERT INTO `roles` VALUES (1, '张三', '系统管理员', '2019-03-31 23:45:36', NULL);
INSERT INTO `roles` VALUES (2, '李四', '普通用户', '2019-03-31 23:45:39', NULL);
```
pom
```
<project xmlns="http://maven.apache.org/POM/4.0.0" xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance" xsi:schemaLocation="http://maven.apache.org/POM/4.0.0 http://maven.apache.org/xsd/maven-4.0.0.xsd">
  <modelVersion>4.0.0</modelVersion>
  <groupId>com.school</groupId>
  <artifactId>schoolmanage</artifactId>
  <version>0.0.1-SNAPSHOT</version>
  <packaging>war</packaging>
<dependencies>
        <!--整合spring-mvc -->
        <dependency>
            <groupId>org.springframework</groupId>
            <artifactId>spring-webmvc</artifactId>
            <version>4.3.9.RELEASE</version>
        </dependency>
        <!--整合jackson,spring mvc可以借助这组api将java对象转换为JSON串 -->
        <dependency>
            <groupId>com.fasterxml.jackson.core</groupId>
            <artifactId>jackson-databind</artifactId>
            <version>2.9.9</version>
        </dependency>
        <!--添加 mysql驱动依赖 -->
        <dependency>
            <groupId>mysql</groupId>
            <artifactId>mysql-connector-java</artifactId>
            <version>5.1.40</version>
        </dependency>
        <!--添加druid连接池依赖 -->
        <dependency>
            <groupId>com.alibaba</groupId>
            <artifactId>druid</artifactId>
            <version>1.0.29</version>
        </dependency>
        <!--添加Junit依赖 -->
        <dependency>
            <groupId>junit</groupId>
            <artifactId>junit</artifactId>
            <version>4.12</version>
        </dependency>
        <!--添加mybatis依赖 -->
        <dependency>
            <groupId>org.mybatis</groupId>
            <artifactId>mybatis-spring</artifactId>
            <version>1.3.1</version>
        </dependency>
        <dependency>
            <groupId>org.mybatis</groupId>
            <artifactId>mybatis</artifactId>
            <version>3.2.8</version>
        </dependency>
        <!-- 添加事务管理依赖(整合mybatis时需要) -->
        <dependency>
            <groupId>org.springframework</groupId>
            <artifactId>spring-jdbc</artifactId>
            <version>4.3.9.RELEASE</version>
        </dependency>
        <dependency>
            <groupId>org.springframework</groupId>
            <artifactId>spring-tx</artifactId>
            <version>4.3.9.RELEASE</version>
        </dependency>
        <!--添加log4j依赖 -->
        <dependency>
            <groupId>log4j</groupId>
            <artifactId>log4j</artifactId>
            <version>1.2.17</version>
        </dependency>
    </dependencies>
</project>
```
web.xml
```
<?xml version="1.0" encoding="UTF-8"?>
<web-app xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance" xmlns="http://java.sun.com/xml/ns/javaee" xsi:schemaLocation="http://java.sun.com/xml/ns/javaee http://java.sun.com/xml/ns/javaee/web-app_2_5.xsd" version="2.5">
  <display-name>schoolmanage</display-name>
<welcome-file-list>
        <welcome-file>index.jsp</welcome-file>
    </welcome-file-list>
    <!--配置SpringMVC前端控制器 -->
    <!--注册前端控制器 -->
    <servlet>
        <servlet-name>dispatcherServlet</servlet-name>
        <servlet-class>org.springframework.web.servlet.DispatcherServlet</servlet-class>
        <!--初始化参数读取配置文件 -->
        <init-param>
            <param-name>contextConfigLocation</param-name>
            <param-value>classpath:spring-*.xml</param-value>
        </init-param>
        <!--配置servlet在服务启动时加载(数字越小，优先级越高) 配置如下选项以后，tomcat启动时会初始化这个servlet 这个servlet在初始化时会读取contextConfigLocation参数对应的配置文件。 -->
        <load-on-startup>1</load-on-startup>
    </servlet>
    <!--配置前端控制器 -->
    <servlet-mapping>
        <servlet-name>dispatcherServlet</servlet-name>
        <url-pattern>*.do</url-pattern>
    </servlet-mapping>
</web-app>
```
### 配置数据库连接
dbconfig.properties
```
jdbcDriver=com.mysql.jdbc.Driver
jdbcUrl=jdbc:mysql:///shop?useUnicode=true&characterEncoding=utf-8
jdbcUsername=root
jdbcPassword=root
```
### 配置log4j日志
log4j.properties
```
jdbcDriver=com.mysql.jdbc.Driver
jdbcUrl=jdbc:mysql:///shop?useUnicode=true&characterEncoding=utf-8
jdbcUsername=root
jdbcPassword=root
```
spring-configs.xml
```
<?xml version="1.0" encoding="UTF-8"?>
<beans xmlns="http://www.springframework.org/schema/beans"
    xmlns:p="http://www.springframework.org/schema/p" xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance"
    xmlns:context="http://www.springframework.org/schema/context" xmlns:tx="http://www.springframework.org/schema/tx"
    xmlns:aop="http://www.springframework.org/schema/aop" xmlns:mvc="http://www.springframework.org/schema/mvc"
    xmlns:util="http://www.springframework.org/schema/util" xmlns:jpa="http://www.springframework.org/schema/data/jpa"
    xsi:schemaLocation="  
       http://www.springframework.org/schema/beans   
       http://www.springframework.org/schema/beans/spring-beans-4.3.xsd  
       http://www.springframework.org/schema/mvc   
       http://www.springframework.org/schema/mvc/spring-mvc-4.3.xsd   
       http://www.springframework.org/schema/tx   
       http://www.springframework.org/schema/tx/spring-tx-4.3.xsd   
       http://www.springframework.org/schema/aop 
       http://www.springframework.org/schema/aop/spring-aop-4.3.xsd
       http://www.springframework.org/schema/util 
       http://www.springframework.org/schema/util/spring-util-4.3.xsd
       http://www.springframework.org/schema/data/jpa 
       http://www.springframework.org/schema/data/jpa/spring-jpa-1.3.xsd
       http://www.springframework.org/schema/context
       http://www.springframework.org/schema/context/spring-context-4.3.xsd">
    <import resource="classpath:spring-mvc.xml"/>
    <import resource="classpath:spring-mybatis.xml"/>
</beans>
```
spring-mvc.xml
```
<?xml version="1.0" encoding="UTF-8"?>
<beans xmlns="http://www.springframework.org/schema/beans"
    xmlns:p="http://www.springframework.org/schema/p" xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance"
    xmlns:context="http://www.springframework.org/schema/context" xmlns:tx="http://www.springframework.org/schema/tx"
    xmlns:aop="http://www.springframework.org/schema/aop" xmlns:mvc="http://www.springframework.org/schema/mvc"
    xmlns:util="http://www.springframework.org/schema/util" xmlns:jpa="http://www.springframework.org/schema/data/jpa"
    xsi:schemaLocation="  
       http://www.springframework.org/schema/beans   
       http://www.springframework.org/schema/beans/spring-beans-4.3.xsd  
       http://www.springframework.org/schema/mvc   
       http://www.springframework.org/schema/mvc/spring-mvc-4.3.xsd   
       http://www.springframework.org/schema/tx   
       http://www.springframework.org/schema/tx/spring-tx-4.3.xsd   
       http://www.springframework.org/schema/aop 
       http://www.springframework.org/schema/aop/spring-aop-4.3.xsd
       http://www.springframework.org/schema/util 
       http://www.springframework.org/schema/util/spring-util-4.3.xsd
       http://www.springframework.org/schema/data/jpa 
       http://www.springframework.org/schema/data/jpa/spring-jpa-1.3.xsd
       http://www.springframework.org/schema/context
       http://www.springframework.org/schema/context/spring-context-4.3.xsd">
    <!-- 自动扫描包 -->
    <context:component-scan base-package="com.school" />
    <!-- 启用mvc注解 -->
    <mvc:annotation-driven />
    <!--配置springMVC视图解析器(负责视图解析操作) -->
    <bean id="viewResolver"
        class="org.springframework.web.servlet.view.InternalResourceViewResolver">
        <!--前缀 -->
        <property name="Prefix" value="/WEB-INF/pages/"></property>
        <property name="Suffix" value=".jsp"></property>
    </bean>
</beans>
```
spring-mybatis.xml
```
<?xml version="1.0" encoding="UTF-8"?>
<beans xmlns="http://www.springframework.org/schema/beans"
    xmlns:p="http://www.springframework.org/schema/p" xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance"
    xmlns:context="http://www.springframework.org/schema/context" xmlns:tx="http://www.springframework.org/schema/tx"
    xmlns:aop="http://www.springframework.org/schema/aop" xmlns:mvc="http://www.springframework.org/schema/mvc"
    xmlns:util="http://www.springframework.org/schema/util" xmlns:jpa="http://www.springframework.org/schema/data/jpa"
    xsi:schemaLocation="  
       http://www.springframework.org/schema/beans   
       http://www.springframework.org/schema/beans/spring-beans-4.3.xsd  
       http://www.springframework.org/schema/mvc   
       http://www.springframework.org/schema/mvc/spring-mvc-4.3.xsd   
       http://www.springframework.org/schema/tx   
       http://www.springframework.org/schema/tx/spring-tx-4.3.xsd   
       http://www.springframework.org/schema/aop 
       http://www.springframework.org/schema/aop/spring-aop-4.3.xsd
       http://www.springframework.org/schema/util 
       http://www.springframework.org/schema/util/spring-util-4.3.xsd
       http://www.springframework.org/schema/data/jpa 
       http://www.springframework.org/schema/data/jpa/spring-jpa-1.3.xsd
       http://www.springframework.org/schema/context
       http://www.springframework.org/schema/context/spring-context-4.3.xsd">
    <!-- 配置druid连接池 -->
    <util:properties id="cfg"
        location="classpath:properties/dbconfig.properties"></util:properties>
    <bean id="dataSource" class="com.alibaba.druid.pool.DruidDataSource"
        init-method="init" destroy-method="close" lazy-init="true">
        <property name="driverClassName" value="#{cfg.jdbcDriver}"></property>
        <property name="url" value="#{cfg.jdbcUrl}"></property>
        <property name="username" value="#{cfg.jdbcUsername}"></property>
        <property name="password" value="#{cfg.jdbcPassword}"></property>
        <!-- 配置获取连接等待超时的时间 -->
        <property name="maxWait" value="1800" />
        <property name="MaxActive" value="10" />
    </bean>
    <!--整合mybatis配置SqlSessionFactoryBean对象(通过此对象创建SqlSession) -->
    <bean id="sqlSessionFactory" class="org.mybatis.spring.SqlSessionFactoryBean">
        <property name="dataSource" ref="dataSource" />
        <!--添加mybatis配置文件 -->
        <property name="configLocation" value="classpath:mybatis-configs.xml" />
        <!--自动扫描mapping.xml文件 -->
        <property name="mapperLocations">
            <list>
                <value>classpath:mapper/*.xml</value>
            </list>
        </property>
    </bean>
    <!--配置mybatis接口映射对象的扫描, Mappe接口所在包,Spring会自动查找其下的Mapper -->
    <bean class="org.mybatis.spring.mapper.MapperScannerConfigurer">
        <property name="basePackage" value="com.school.**.dao" />
    </bean>

</beans>
```
配置mybatis的sql日志输出
mybatis-configs.xml
```
<?xml version="1.0" encoding="UTF-8"?>
<!DOCTYPE configuration PUBLIC "-//mybatis.org//DTD Config 3.0//EN"
"http://mybatis.org/dtd/mybatis-3-config.dtd">
<configuration>
    <settings>
    <!--配置mybatis控制台输出  -->
        <setting name="logImpl" value="LOG4J" />
    </settings>
</configuration>
```
### 编写实体类Role
```
package com.school.entity;

import java.io.Serializable;
import java.util.Date;

public class Role implements Serializable {

    private static final long serialVersionUID = 1965384786782405073L;
    public Role() {
    }
    private Integer id;
    private String name;
    private String note;
    private Date created;
    private Date updated;
    public Integer getId() {
        return id;
    }
    public void setId(Integer id) {
        this.id = id;
    }
    public String getName() {
        return name;
    }
    public void setName(String name) {
        this.name = name;
    }
    public String getNote() {
        return note;
    }
    public void setNote(String note) {
        this.note = note;
    }
    public Date getCreated() {
        return created;
    }
    public void setCreated(Date created) {
        this.created = created;
    }
    public Date getUpdated() {
        return updated;
    }
    public void setUpdated(Date updated) {
        this.updated = updated;
    }
    @Override
    public String toString() {
        return "Role [id=" + id + ", name=" + name + ", note=" + note + ", created=" + created + ", updated=" + updated
                + "]";
    }

}
```
### 编写数据层对象RoleDao
```
package com.school.dao;

import java.util.List;

import org.springframework.stereotype.Repository;

import com.school.entity.Role;

/**
 * 持久层(数据访问层)对象
 * 此类型的对象由mybatis创建
 */
@Repository
public interface RoleDao {
    //查询所有角色信息
    List<Role> findPageObjects();
}

```
### 配置映射文件
roleMapper.xml
```
<?xml version="1.0" encoding="UTF-8"?>
<!DOCTYPE mapper 
    PUBLIC "-//mybatis.org//DTD Mapper 3.0//EN"
    "http://mybatis.org/dtd/mybatis-3-mapper.dtd">
<!--mybatis中的映射文件 -->
<!--定义一个sql查询，sql查询必须有一个id,其中resultType表示查询结果会封装到对应的map类型中,一行记录对应一个map -->
<mapper namespace="com.school.dao.RoleDao">
    <!-- 借助sql元素可以对重复编写的sql语句进行提取 -->
    <sql id="tableName"> roles </sql>
    <sql id="fromTable">
        from
        <include refid="tableName" />
    </sql>

    <select id="findPageObjects" resultType="com.school.entity.Role">
        select *
        <include refid="fromTable" />
    </select>

</mapper>
```
### 编写业务类RoleService
```
package com.school.service;

import java.util.List;

import com.school.entity.Role;

public interface RoleService {
    List<Role> findPageObjects();
}

```
### 编写业务类的实现类RoleServiceImpl
```
package com.school.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.school.dao.RoleDao;
import com.school.entity.Role;
import com.school.service.RoleService;

@Service
public class RoleServiceImpl implements RoleService {

    @Autowired
    private RoleDao roleDao;
    public List<Role> findPageObjects() {
        return roleDao.findPageObjects();
    }

}

```
### 编写控制器类RoleController
```
package com.school.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.school.entity.Role;
import com.school.service.RoleService;

@RequestMapping("/role")
@Controller
public class RoleController {
    @Autowired
    private RoleService roleService;
    
    @RequestMapping("/list")
    public String listPage(){
        return "sys/roles";
    }
    @RequestMapping("/listPages")
    @ResponseBody
    public List<Role> listData(){
        return roleService.findPageObjects();
    }
}

```
#### 页面访问地址
http://localhost:8080/schoolmanage/role/list.do

#### 数据访问地址
http://localhost:8080/schoolmanage/role/listPages.do



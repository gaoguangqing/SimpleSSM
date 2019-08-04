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

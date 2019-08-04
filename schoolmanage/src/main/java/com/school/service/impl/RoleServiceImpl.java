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

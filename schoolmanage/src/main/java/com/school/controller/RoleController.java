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

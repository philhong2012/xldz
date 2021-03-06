package com.wyait.manage2.other.controller;

import com.wyait.manage.pojo.User;
import org.apache.shiro.SecurityUtils;
import org.springframework.beans.factory.annotation.Autowired;

import javax.servlet.http.HttpServletRequest;

/**
 * Created by phil hong
 * User: wind
 * Date: 2019/1/24
 * Time: 下午4:30
 * To change this template use File | Settings | File Templates.
 */
public class BaseController {
    @Autowired
    HttpServletRequest request;
    protected User getCurrentUser() {
        User user = (User)SecurityUtils.getSubject().getPrincipal();
        return user;
    }


    protected boolean isCompanyRole() {
        //User user = (User)SecurityUtils.getSubject().getPrincipal();
        return SecurityUtils.getSubject().hasRole("highmanage");
    }

}

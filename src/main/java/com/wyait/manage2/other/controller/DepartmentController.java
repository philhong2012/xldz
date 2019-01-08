package com.wyait.manage2.other.controller;


import cn.afterturn.easypoi.entity.vo.TemplateWordConstants;
import cn.afterturn.easypoi.view.EasypoiTemplateWordView;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.wyait.common.utils.NumberUtils;
import com.wyait.manage.entity.BuyingContractVO;
import com.wyait.manage.entity.DataGridVO;
import com.wyait.manage.entity.SearchEntityVO;
import com.wyait.manage.pojo.User;
import com.wyait.manage.utils.PageDataResult;
import com.wyait.manage2.other.entity.*;
import com.wyait.manage2.other.service.*;
import org.apache.commons.lang3.StringUtils;
import org.apache.shiro.SecurityUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.*;

import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * <p>
 * 部门 前端控制器
 * </p>
 *
 * @author hongxubing
 * @since 2019-01-08
 */
@RestController
@RequestMapping("/department")
public class DepartmentController {
    Logger logger = LoggerFactory.getLogger(DepartmentController.class);

    @Autowired
    IDepartmentService departmentService;

    /**
     * 编辑采购合同
     * @return
     */
    @RequestMapping(value = "/create")
    public ModelAndView create() {
        ModelAndView mv = new ModelAndView("form/department/create");
        return mv;
    }


    /**
     * 编辑采购合同
     * @return
     */
    @RequestMapping(value = "/edit", method = RequestMethod.GET)
    public ModelAndView edit(String id) {
        ModelAndView mv = new ModelAndView("form/department/create");
        Department department = departmentService.getById(id);
        mv.addObject("model",department);
        return mv;
    }

    /**
     * 采购合同列表页面
     * @return
     */
    @RequestMapping("/list")
    public String buyingContractList() {
        return "form/department/list";
    }

    /**
     * 获取采购合同列表
     * @return
     */
    @RequestMapping(value = "/list", method = RequestMethod.POST)
    @ResponseBody
    public PageDataResult list(@RequestParam("page")Integer page,
                               @RequestParam("limit")Integer limit,
                               SearchEntityVO searchEntityVO) {
        PageDataResult pdr = new PageDataResult();
        try {
            if (null == page) {
                page = 1;
            }
            if (null == limit) {
                limit = 10;
            }
            PageHelper.startPage(page, limit);

            QueryWrapper<Department> queryWrapper = new QueryWrapper<>();
            if(searchEntityVO != null) {
                if(StringUtils.isNotEmpty(searchEntityVO.getName())) {
                    queryWrapper.like("name",searchEntityVO.getCode());
                }
                if(StringUtils.isNotEmpty(searchEntityVO.getName())) {
                    //queryWrapper.eq("cont")
                }
                if(searchEntityVO.getStartCreateTime() != null) {
                    queryWrapper.gt("create_time",searchEntityVO.getStartCreateTime());
                }
                if(searchEntityVO.getEndCreateTime() != null) {
                    queryWrapper.lt("create_time",searchEntityVO.getEndCreateTime());
                }
            }
            List<Department> departments = departmentService.list(queryWrapper);
            // 获取分页查询后的数据
            PageInfo<Department> pageInfo = new PageInfo<>(departments);
            // 设置获取到的总记录数total：
            pdr.setTotals(Long.valueOf(pageInfo.getTotal()).intValue());

            pdr.setList(departments);
        } catch (Exception e) {
            //e.printStackTrace();
            logger.error("列表查询异常！", e);
        }
        return pdr;
    }

    /**
     * 保存采购合同及详情
     * @return
     */
    @RequestMapping(value = "/save", method = RequestMethod.POST)
    public String save(@RequestBody Department department) {
        User u = (User) SecurityUtils.getSubject().getPrincipal();
        if(StringUtils.isNotEmpty(department.getId())) {
            department.setUpdateUserId(u.getId().toString());
            department.setUpdateTime(LocalDateTime.now());
            department.setUpdateUserName(u.getUsername());
        } else {
            department.setCreateUserId(u.getId().toString());
            department.setCreateTime(LocalDateTime.now());
            department.setCreateUserName(u.getUsername());

        }
        if(department != null) {
            departmentService.saveOrUpdate(department);
        }
        return "ok";
    }

    @RequestMapping(value = "/delete", method = RequestMethod.POST)
    @ResponseBody
    public String delete(String id) {
        departmentService.removeById(id);
        return "ok";
    }
}

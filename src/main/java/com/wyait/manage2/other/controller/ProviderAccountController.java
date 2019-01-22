package com.wyait.manage2.other.controller;


import cn.afterturn.easypoi.entity.vo.TemplateExcelConstants;
import cn.afterturn.easypoi.excel.entity.TemplateExportParams;
import cn.afterturn.easypoi.view.PoiBaseView;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.wyait.common.utils.BeanUtils;
import com.wyait.manage.entity.SearchEntityVO;
import com.wyait.manage.pojo.User;
import com.wyait.manage.utils.PageDataResult;
import com.wyait.manage2.other.entity.Department;
import com.wyait.manage2.other.entity.ProviderAccount;
import com.wyait.manage2.other.entity.SettlementList;
import com.wyait.manage2.other.service.IDepartmentService;
import com.wyait.manage2.other.service.IProviderAccountService;
import com.wyait.manage2.other.service.ISettlementListService;
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
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * <p>
 * 国内业务合同台账 前端控制器
 * </p>
 *
 * @author hongxubing
 * @since 2019-01-05
 */
@RestController
@RequestMapping("/provideraccount")
public class ProviderAccountController {
    Logger logger = LoggerFactory.getLogger(ProviderAccountController.class);
    
    @Autowired
    IProviderAccountService providerAccountService;

    @Autowired
    IDepartmentService departmentService;

    @RequestMapping("/create")
    public ModelAndView create() {
        ModelAndView mv = new ModelAndView("form/provideraccount/create");
        return mv;

    }

    @RequestMapping("/download")
    public void download(String deptId,
                         String startDate,
                         String endDate,
                         String name,
                         ModelMap modelMap, HttpServletRequest request, HttpServletResponse response) {

        QueryWrapper<ProviderAccount> queryWrapper = new QueryWrapper<>();
        if(StringUtils.isNotEmpty(deptId)) {
            if(!"all".equals(deptId)) {
                queryWrapper.eq("dept_id",deptId);
            }
        }

        if(StringUtils.isNotEmpty(startDate)) {

            queryWrapper.gt("create_time",LocalDate.parse(startDate));
        }

        if(StringUtils.isNotEmpty(endDate)) {
            queryWrapper.lt("create_time",LocalDate.parse(endDate));
        }

        if(StringUtils.isNotEmpty(name)) {
            queryWrapper.like("name",name);
        }

        List<ProviderAccount> providerAccounts = providerAccountService.list(queryWrapper);

        List<Map<String,Object>> maplist = new ArrayList<>();
        BigDecimal totalAmount = BigDecimal.ZERO;
        if(providerAccounts != null && providerAccounts.size() > 0) {
            for (ProviderAccount e : providerAccounts) {
                Map m = BeanUtils.objectToMap(e);
                //m.put("createTime",LocalDate);
                maplist.add(m);
                totalAmount = totalAmount.add(e.getAmount() == null ? BigDecimal.ZERO
                : e.getAmount());
            }
        }



        Department department = departmentService.getById(deptId);
        String deptName="所有部门";
        if(department != null) {
            deptName =  department.getName();
        }
        Map<String,Object> map = new HashMap<>();
        map.put("items",maplist);
        map.put("name",name);
        map.put("deptName",deptName);
        map.put("startDate",startDate);
        map.put("endDate",endDate);

        map.put("totalAmount",totalAmount);

        TemplateExportParams params = new TemplateExportParams(
                "word/temp_供应商台账.xlsx");
        modelMap.put(TemplateExcelConstants.FILE_NAME, "供应商台账报表");
        modelMap.put(TemplateExcelConstants.PARAMS, params);
        modelMap.put(TemplateExcelConstants.MAP_DATA, map);
        PoiBaseView.render(modelMap, request, response,
                TemplateExcelConstants.EASYPOI_TEMPLATE_EXCEL_VIEW);
    }


    /**
     * 编辑采购合同
     * @return
     */
    @RequestMapping(value = "/edit", method = RequestMethod.GET)
    public ModelAndView edit(String id) {
        ModelAndView mv = new ModelAndView("form/provideraccount/create");
        ProviderAccount providerAccount = providerAccountService.getById(id);
        mv.addObject("model",providerAccount);
        return mv;
    }

    /**
     * 采列表页面
     * @return
     */
    @RequestMapping("/list")
    public String listPage() {
        return "form/provideraccount/list";
    }

    /**
     * 结算列表
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

            QueryWrapper<ProviderAccount> queryWrapper = new QueryWrapper<>();
            if(searchEntityVO != null) {
                if(StringUtils.isNotEmpty(searchEntityVO.getCode())) {
                    queryWrapper.like("code",searchEntityVO.getCode());
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

                if(searchEntityVO.getName() != null) {
                    queryWrapper.like("name",searchEntityVO.getName());
                }
            }


            List<ProviderAccount> providerAccounts = providerAccountService.list(queryWrapper);
            // 获取分页查询后的数据
            PageInfo<ProviderAccount> pageInfo = new PageInfo<>(providerAccounts);
            // 设置获取到的总记录数total：
            pdr.setTotals(Long.valueOf(pageInfo.getTotal()).intValue());

            pdr.setList(providerAccounts);
        } catch (Exception e) {
            //e.printStackTrace();
            logger.error("查询异常！", e);
        }
        return pdr;
    }

    /**
     * 保存结算卡
     * @return
     */
    @RequestMapping(value = "/save", method = RequestMethod.POST)
    public String save(@RequestBody ProviderAccount providerAccount) {
        User u = (User) SecurityUtils.getSubject().getPrincipal();
        if(StringUtils.isNotEmpty(providerAccount.getId())) {
            providerAccount.setUpdateUserId(u.getId().toString());
            providerAccount.setUpdateUserName(u.getUsername());
            providerAccount.setUpdateTime(LocalDateTime.now());
        } else {
            providerAccount.setCreateUserId(u.getId().toString());
            providerAccount.setCreateTime(LocalDateTime.now());
            providerAccount.setCreateUserName(u.getUsername());
            providerAccount.setDeptId(u.getDeptId());
            providerAccount.setDeptName(u.getDeptName());
        }

        if(providerAccount != null) {
            providerAccountService.saveOrUpdate(providerAccount);
        }


        return "ok";
    }

    @RequestMapping(value = "/delete", method = RequestMethod.POST)
    @ResponseBody
    public String delete(String id) {

        providerAccountService.removeById(id);

        return "ok";
    }
}

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
import com.wyait.manage2.other.entity.ForeignExchangeAccount;
import com.wyait.manage2.other.entity.ProviderAccount;
import com.wyait.manage2.other.service.IDepartmentService;
import com.wyait.manage2.other.service.IForeignExchangeAccountService;
import com.wyait.manage2.other.service.IProviderAccountService;
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
import java.util.function.Function;

/**
 * <p>
 * 外汇台账 前端控制器
 * </p>
 *
 * @author hongxubing
 * @since 2019-01-05
 */
@RestController
@RequestMapping("/foreignexchangeaccount")
public class ForeignExchangeAccountController extends BaseController{

    Logger logger = LoggerFactory.getLogger(ForeignExchangeAccountController.class);

    @Autowired
    IForeignExchangeAccountService foreignExchangeAccountService;

    @Autowired
    IDepartmentService departmentService;

    @RequestMapping("/create")
    public ModelAndView create() {
        ModelAndView mv = new ModelAndView("form/foreignexchangeaccount/create");
        return mv;

    }


    @RequestMapping("/download")
    public void download(String deptId,
                         String startDate,
                         String endDate,
                         String name,
                         ModelMap modelMap, HttpServletRequest request, HttpServletResponse response) {

        QueryWrapper<ForeignExchangeAccount> queryWrapper = new QueryWrapper<>();
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
            queryWrapper.like("remittance",name);
        }

        List<ForeignExchangeAccount> foreignExchangeAccounts = foreignExchangeAccountService.list(queryWrapper);
        BigDecimal totalAmount = BigDecimal.ZERO;
        List<Map<String,Object>> maplist = new ArrayList<>();
        if(foreignExchangeAccounts != null && foreignExchangeAccounts.size() > 0) {
            for (ForeignExchangeAccount e : foreignExchangeAccounts) {
                maplist.add(BeanUtils.objectToMap(e));
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
                "word/temp_外商台账.xlsx");
        modelMap.put(TemplateExcelConstants.FILE_NAME, "外商台账报表");
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
        ModelAndView mv = new ModelAndView("form/foreignexchangeaccount/create");
        ForeignExchangeAccount foreignExchangeAccount = foreignExchangeAccountService.getById(id);
        mv.addObject("model",foreignExchangeAccount);
        return mv;
    }

    /**
     * 采列表页面
     * @return
     */
    @RequestMapping("/list")
    public String listPage() {
        return "form/foreignexchangeaccount/list";
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

            QueryWrapper<ForeignExchangeAccount> queryWrapper = new QueryWrapper<>();
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
                    queryWrapper.and(wrapper-> wrapper.like("remittance",searchEntityVO.getName()).or()
                    .like("receiver",searchEntityVO.getName()));
                }
            }


            if(!isCompanyRole()) {
                queryWrapper.eq("dept_id",getCurrentUser().getDeptId());
            }
            List<ForeignExchangeAccount> foreignExchangeAccounts = foreignExchangeAccountService.list(queryWrapper);
            // 获取分页查询后的数据
            PageInfo<ForeignExchangeAccount> pageInfo = new PageInfo<>(foreignExchangeAccounts);
            // 设置获取到的总记录数total：
            pdr.setTotals(Long.valueOf(pageInfo.getTotal()).intValue());

            pdr.setList(foreignExchangeAccounts);
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
    public String save(@RequestBody ForeignExchangeAccount foreignExchangeAccount) {
        User u = (User) SecurityUtils.getSubject().getPrincipal();
        if(StringUtils.isNotEmpty(foreignExchangeAccount.getId())) {
            foreignExchangeAccount.setUpdateUserId(u.getId().toString());
            foreignExchangeAccount.setUpdateUserName(u.getUsername());
            foreignExchangeAccount.setUpdateTime(LocalDateTime.now());
        } else {
            foreignExchangeAccount.setCreateUserId(u.getId().toString());
            foreignExchangeAccount.setCreateTime(LocalDateTime.now());
            foreignExchangeAccount.setCreateUserName(u.getUsername());
            foreignExchangeAccount.setDeptId(u.getDeptId());
            foreignExchangeAccount.setDeptName(u.getDeptName());
        }

        if(foreignExchangeAccount != null) {
            foreignExchangeAccountService.saveOrUpdate(foreignExchangeAccount);
        }


        return "ok";
    }

    @RequestMapping(value = "/delete", method = RequestMethod.POST)
    @ResponseBody
    public String delete(String id) {

        foreignExchangeAccountService.removeById(id);

        return "ok";
    }

}

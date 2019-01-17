package com.wyait.manage2.other.controller;


import cn.afterturn.easypoi.entity.vo.TemplateExcelConstants;
import cn.afterturn.easypoi.excel.entity.TemplateExportParams;
import cn.afterturn.easypoi.view.PoiBaseView;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.wyait.common.utils.BeanUtils;
import com.wyait.common.utils.NumberUtils;
import com.wyait.manage.entity.CustomsClearanceVO;
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
 * 业务结算卡 前端控制器
 * </p>
 *
 * @author hongxubing
 * @since 2019-01-03
 */
@RestController
@RequestMapping("/settlementlist")
public class SettlementListController {

    Logger logger = LoggerFactory.getLogger(CustomsClearanceController.class);
    @Autowired
    ISettlementListService settlementListService;

    @RequestMapping("/create")
    public ModelAndView create() {
        ModelAndView mv = new ModelAndView("form/settlementlist/create");
        return mv;

    }


    @RequestMapping("/download")
    public void download(String id, ModelMap modelMap, HttpServletRequest request, HttpServletResponse response) {
        Map<String, Object> map = new HashMap<String, Object>();
        SettlementList settlementList = settlementListService.getById(id);
        map.putAll(BeanUtils.objectToMap(settlementList));
        TemplateExportParams params = new TemplateExportParams(
                "word/temp_结算卡.xlsx");
        modelMap.put(TemplateExcelConstants.FILE_NAME, "结算卡-"+settlementList.getCode());
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
        ModelAndView mv = new ModelAndView("form/settlementlist/create");
        SettlementList settlementList = settlementListService.getById(id);
        mv.addObject("model",settlementList);
        return mv;
    }

    /**
     * 采列表页面
     * @return
     */
    @RequestMapping("/list")
    public String listPage() {
        return "form/settlementlist/list";
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

            QueryWrapper<SettlementList> queryWrapper = new QueryWrapper<>();
            if(searchEntityVO != null) {
                if(StringUtils.isNotEmpty(searchEntityVO.getCode())) {
                    queryWrapper.like("contract_no",searchEntityVO.getCode());
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

                if(searchEntityVO.getStartSignDate() != null) {

                    queryWrapper.gt("sign_date",searchEntityVO.getStartSignDate());
                }

                if(searchEntityVO.getEndSignDate() != null) {
                    queryWrapper.lt("sign_date",searchEntityVO.getEndSignDate());
                }

                if(searchEntityVO.getStartDeclareDate() != null) {
                    queryWrapper.gt("declare_date",searchEntityVO.getStartDeclareDate());
                }

                if(searchEntityVO.getEndDeclareDate() != null) {
                    queryWrapper.lt("declare_date",searchEntityVO.getEndDeclareDate());
                }
            }


            List<SettlementList> settlementLists = settlementListService.list(queryWrapper);
            // 获取分页查询后的数据
            PageInfo<SettlementList> pageInfo = new PageInfo<>(settlementLists);
            // 设置获取到的总记录数total：
            pdr.setTotals(Long.valueOf(pageInfo.getTotal()).intValue());

            pdr.setList(settlementLists);
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
    public String save(@RequestBody SettlementList settlementList) {
        User u = (User) SecurityUtils.getSubject().getPrincipal();
        if(StringUtils.isNotEmpty(settlementList.getId())) {
            settlementList.setUpdateUserId(u.getId().toString());
            settlementList.setUpdateUserName(u.getUsername());
            settlementList.setUpdateTime(LocalDateTime.now());
        } else {
            settlementList.setCreateUserId(u.getId().toString());
            settlementList.setCreateTime(LocalDateTime.now());
            settlementList.setUpdateUserName(u.getUsername());
        }

        if(settlementList != null) {
            settlementListService.saveOrUpdate(settlementList);
        }


        return "ok";
    }

    @RequestMapping(value = "/delete", method = RequestMethod.POST)
    @ResponseBody
    public String delete(String id) {

        settlementListService.removeById(id);

        return "ok";
    }
}

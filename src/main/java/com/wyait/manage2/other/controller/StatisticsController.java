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
import com.wyait.manage2.other.service.IDepartmentService;
import com.wyait.manage2.other.service.IForeignExchangeAccountService;
import com.wyait.manage2.other.service.IStatisticsService;
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
 * 外汇台账 前端控制器
 * </p>
 *
 * @author hongxubing
 * @since 2019-01-05
 */
@RestController
@RequestMapping("/statistics")
public class StatisticsController {

    Logger logger = LoggerFactory.getLogger(StatisticsController.class);

    @Autowired
    IForeignExchangeAccountService foreignExchangeAccountService;

    @Autowired
    IDepartmentService departmentService;

    @Autowired
    IStatisticsService statisticsService;



    @RequestMapping("/download")
    public void download(String deptId,
                         String startDate,
                         String endDate,
                         String name,
                         ModelMap modelMap, HttpServletRequest request, HttpServletResponse response) {
        LocalDate sd =null,ed=null;
        if(StringUtils.isNotEmpty(startDate)) {
            sd = LocalDate.parse(startDate);
        }

        if(StringUtils.isNotEmpty(endDate))  {
            ed = LocalDate.parse(endDate);
        }

        Map<String,Object> map = new HashMap<>();
        List<Map<String,Object>> stats = statisticsService.getTurnOverExportAmountProfit(
               sd,ed,deptId
        );

        map.put("items",stats);
        map.put("startDate",sd == null ? "":sd.toString());
        map.put("endDate",ed== null ? "" : ed.toString());
        TemplateExportParams params = new TemplateExportParams(
                "word/temp_营业额出口额利润.xlsx");
        modelMap.put(TemplateExcelConstants.FILE_NAME, "营业额出口额利润");
        modelMap.put(TemplateExcelConstants.PARAMS, params);
        modelMap.put(TemplateExcelConstants.MAP_DATA, map);
        PoiBaseView.render(modelMap, request, response,
                TemplateExcelConstants.EASYPOI_TEMPLATE_EXCEL_VIEW);
    }





    /**
     * 采列表页面
     * @return
     */
    @RequestMapping("/list")
    public ModelAndView listPage() {
        return new ModelAndView("form/statistics/list");
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
        List<Map<String,Object>>  stats = null;
        try {
            if (null == page) {
                page = 1;
            }
            if (null == limit) {
                limit = 10;
            }

            PageHelper.startPage(page, limit);

           stats = statisticsService.getTurnOverExportAmountProfit(
                   searchEntityVO.getStartCreateTime(),
                   searchEntityVO.getEndCreateTime(),
                   searchEntityVO.getDeptId()

           ) ;

            // 获取分页查询后的数据
            PageInfo<Map<String,Object>> pageInfo = new PageInfo<>(stats);
            //pageInfo.setList(stats);
            //设置获取到的总记录数total：
            pdr.setTotals(Long.valueOf(pageInfo.getTotal()).intValue());

            pdr.setList(stats);
        } catch (Exception e) {
            //e.printStackTrace();
            logger.error("查询异常！", e);
        }
        return pdr;
    }

}

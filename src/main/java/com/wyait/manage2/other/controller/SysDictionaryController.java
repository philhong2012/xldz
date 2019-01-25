package com.wyait.manage2.other.controller;


import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.wyait.manage.entity.SearchEntityVO;
import com.wyait.manage.utils.EasyUIPageDataResult;
import com.wyait.manage.utils.PageDataResult;
import com.wyait.manage2.other.entity.ExportGoodsList;
import com.wyait.manage2.other.entity.SysDictionary;
import com.wyait.manage2.other.service.ISysDictionaryService;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import com.wyait.manage2.other.controller.BaseController;
import org.springframework.web.servlet.ModelAndView;

import java.util.List;

/**
 * <p>
 * 数据字典 前端控制器
 * </p>
 *
 * @author hongxubing
 * @since 2019-01-25
 */
@RestController
@RequestMapping("/dictionary")
public class SysDictionaryController extends BaseController {
    Logger logger = LoggerFactory.getLogger(this.getClass());

    @Autowired
    ISysDictionaryService sysDictionaryService;


    @RequestMapping("/list")
    public ModelAndView list() {
        return new ModelAndView("form/sysdictionary/list");
    }


    @RequestMapping(value={"/listData"},method = RequestMethod.POST)
    public EasyUIPageDataResult listData(@RequestParam("page")Integer page,
                                         @RequestParam("rows")Integer rows,
                                         SearchEntityVO searchEntityVO) {
        EasyUIPageDataResult pdr = new EasyUIPageDataResult();
        try {
            if (null == page) {
                page = 1;
            }
            if (null == rows) {
                rows = 10;
            }
            PageHelper.startPage(page, rows);

            QueryWrapper<SysDictionary> queryWrapper = new QueryWrapper<>();



            List<SysDictionary> sysDictionaries = sysDictionaryService.list(queryWrapper);
            // 获取分页查询后的数据
            PageInfo<SysDictionary> pageInfo = new PageInfo<>(sysDictionaries);
            // 设置获取到的总记录数total：
            pdr.setTotal(Long.valueOf(pageInfo.getTotal()).intValue());

            pdr.setRows(sysDictionaries);
        } catch (Exception e) {
            //e.printStackTrace();
            logger.error("采购列表查询异常！", e);
        }
        return pdr;
    }

    @RequestMapping(value={"/save"},method = RequestMethod.POST)
    public SysDictionary save(SysDictionary sysDictionary) {
        sysDictionaryService.saveOrUpdate(sysDictionary);
        return sysDictionary;
    }


    @RequestMapping(value={"/delete"},method = RequestMethod.POST)
    public String delete(SysDictionary sysDictionary) {
        sysDictionaryService.removeById(sysDictionary.getId());
        return "{\"success\":true}";
    }


}

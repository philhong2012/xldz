package com.wyait.manage2.other.controller;


import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.wyait.manage.entity.SearchEntityVO;
import com.wyait.manage.utils.EasyUIPageDataResult;
import com.wyait.manage2.other.entity.SysDicItem;
import com.wyait.manage2.other.entity.SysDictionary;
import com.wyait.manage2.other.service.ISysDicItemService;
import com.wyait.manage2.other.service.ISysDictionaryService;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;

import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import com.wyait.manage2.other.controller.BaseController;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import java.util.ArrayList;
import java.util.List;

/**
 * <p>
 * 字典项 前端控制器
 * </p>
 *
 * @author hongxubing
 * @since 2019-01-25
 */
@RestController
@RequestMapping("/dictionaryitem")
public class SysDicItemController extends BaseController {

    Logger logger = LoggerFactory.getLogger(this.getClass());

    @Autowired
    ISysDicItemService sysDicItemService;


    @RequestMapping("/list")
    public ModelAndView list() {
        return new ModelAndView("form/sysdictionary/list");
    }


    @RequestMapping(value={"/listData"},method = RequestMethod.POST)
    public EasyUIPageDataResult listData(@RequestParam("page")Integer page,
                                         @RequestParam("rows")Integer rows,
                                         @RequestParam("dicId") String dicId,
                                         SearchEntityVO searchEntityVO) {
        EasyUIPageDataResult pdr = new EasyUIPageDataResult();
        try {
            if (null == page) {
                page = 1;
            }
            if (null == rows) {
                rows = 10;
            }
            //PageHelper.startPage(page, rows);
            if(StringUtils.isNotEmpty(dicId)) {
                QueryWrapper<SysDicItem> queryWrapper = new QueryWrapper<>();

                queryWrapper.eq("dic_id", dicId);

                List<SysDicItem> sysDicItems = sysDicItemService.list(queryWrapper);
                // 获取分页查询后的数据
                PageInfo<SysDicItem> pageInfo = new PageInfo<>(sysDicItems);
                // 设置获取到的总记录数total：
                pdr.setTotal(Long.valueOf(pageInfo.getTotal()).intValue());

                pdr.setRows(sysDicItems);
            } else {
               // List<SysDicItem> empty = new ArrayList<SysDicItem>()
                pdr.setTotal(0);
                pdr.setRows(new ArrayList<SysDicItem>());
            }
        } catch (Exception e) {
            //e.printStackTrace();
            logger.error("采购列表查询异常！", e);
        }
        return pdr;
    }

    @RequestMapping(value={"/save"},method = RequestMethod.POST)
    public String save(SysDicItem sysDictionary){
        String dicId = request.getParameter("dicId");
        sysDicItemService.saveOrUpdate(sysDictionary);
        return "ok";
    }


    @RequestMapping(value={"/delete"},method = RequestMethod.POST)
    public String delete(SysDicItem sysDictionary) {
        sysDicItemService.removeById(sysDictionary.getId());
        return "{success:true}";
    }

}

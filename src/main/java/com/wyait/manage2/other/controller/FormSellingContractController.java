package com.wyait.manage2.other.controller;


import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.wyait.manage.entity.SaleContractDTO;
import com.wyait.manage.pojo.Role;
import com.wyait.manage.utils.PageDataResult;
import com.wyait.manage2.other.entity.FormSellingContract;
import com.wyait.manage2.other.service.IFormSellingContractService;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * <p>
 * 售货合同 前端控制器
 * </p>
 *
 * @author hongxubing
 * @since 2018-12-15
 */
@Controller
@RequestMapping("/sellingcontract")
public class FormSellingContractController {
    @Autowired
    IFormSellingContractService formSellingContractService;

    Logger logger = LoggerFactory.getLogger(FormSellingContractController.class);
    @RequestMapping("/create")
    public String toUserList() {
        return "form/sellingcontract/create";
    }

    /**
     * 保存售货合同
     * @return
     */
    @RequestMapping(value = "/save", method = RequestMethod.POST)
    @ResponseBody
    public String save(FormSellingContract formSellingContract) {
        logger.debug("",formSellingContract);
        formSellingContractService.saveOrUpdate(formSellingContract);
        UpdateWrapper<FormSellingContract> updateWrapper = new UpdateWrapper<FormSellingContract>();
        if(formSellingContract.getPackingExpiredDate() == null) {
            updateWrapper.set("packing_expired_date",null);//updateWrapper.set("")
        }
        if(formSellingContract.getPayingExpiredDate() == null) {
            updateWrapper.set("paying_expired_date",null);//updateWrapper.set("")
        }

        //formSellingContractService.update()


        return "ok";
    }


    /**
     * 编辑售货合同
     * @return
     */
    @RequestMapping(value = "/edit", method = RequestMethod.GET)
    public ModelAndView edit(String id) {
        ModelAndView mv = new ModelAndView("form/sellingcontract/create");
        FormSellingContract formSellingContract = formSellingContractService.getById(id);
        mv.addObject("model",formSellingContract);
        return mv;
    }


    /**
     * 编辑售货合同
     * @return
     */
    @RequestMapping(value = "/list", method = RequestMethod.POST)
    @ResponseBody
    public PageDataResult list(@RequestParam("page")Integer page,
                               @RequestParam("limit")Integer limit,
                               FormSellingContract formSellingContract) {
        PageDataResult pdr = new PageDataResult();
        try {
            if (null == page) {
                page = 1;
            }
            if (null == limit) {
                limit = 10;
            }
            PageHelper.startPage(page, limit);

            List<FormSellingContract> formSellingContracts = formSellingContractService.list();
            // 获取分页查询后的数据
            PageInfo<FormSellingContract> pageInfo = new PageInfo<>(formSellingContracts);
            // 设置获取到的总记录数total：
            pdr.setTotals(Long.valueOf(pageInfo.getTotal()).intValue());

            pdr.setList(formSellingContracts);
            logger.debug("用户列表查询=pdr:" + pdr);

            //1074138657696358402
            //1074138657696358400
         } catch (Exception e) {
            //e.printStackTrace();
            logger.error("用户列表查询异常！", e);
        }
        return pdr;
    }
}

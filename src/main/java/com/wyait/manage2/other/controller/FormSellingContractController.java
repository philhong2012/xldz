package com.wyait.manage2.other.controller;


import com.baomidou.mybatisplus.core.conditions.Wrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.wyait.manage.entity.SaleContractDTO;
import com.wyait.manage.entity.SearchEntityVO;
import com.wyait.manage.entity.SellingContractVO;
import com.wyait.manage.pojo.Role;
import com.wyait.manage.pojo.User;
import com.wyait.manage.utils.PageDataResult;
import com.wyait.manage2.other.entity.FormSellingContract;
import com.wyait.manage2.other.entity.SellingContractDetail;
import com.wyait.manage2.other.service.IFormSellingContractService;
import com.wyait.manage2.other.service.ISellingContractDetailService;
import org.apache.commons.lang3.StringUtils;
import org.apache.shiro.SecurityUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import java.sql.Timestamp;
import java.time.LocalDateTime;
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
    @Autowired
    ISellingContractDetailService sellingContractDetailService;

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
    public String save(@RequestBody SellingContractVO sellingContractVO) {
        //logger.debug("",formSellingContract);
        FormSellingContract formSellingContract = sellingContractVO.getContract();

        User u = (User) SecurityUtils.getSubject().getPrincipal();


        if(formSellingContract != null) {

            if(StringUtils.isNotEmpty(formSellingContract.getId())) {
                formSellingContract.setUpdateUserId(u.getId().toString());
                formSellingContract.setUpdateTime(LocalDateTime.now());
            } else {
                formSellingContract.setCreateUserId(u.getId().toString());
                formSellingContract.setCreateTime(LocalDateTime.now());
            }
            formSellingContractService.saveOrUpdate(sellingContractVO.getContract());
        }

        if(sellingContractVO.getDetails() != null) {
            for(SellingContractDetail detail : sellingContractVO.getDetails()) {
                detail.setSellingContractId(sellingContractVO.getContract().getId());
            }
            sellingContractDetailService.saveOrUpdateBatch(sellingContractVO.getDetails());
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
            QueryWrapper<FormSellingContract> queryWrapper = new QueryWrapper<>();
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
            }

            List<FormSellingContract> formSellingContracts = formSellingContractService.list(queryWrapper);
            // 获取分页查询后的数据
            PageInfo<FormSellingContract> pageInfo = new PageInfo<>(formSellingContracts);
            // 设置获取到的总记录数total：
            pdr.setTotals(Long.valueOf(pageInfo.getTotal()).intValue());

            pdr.setList(formSellingContracts);

         } catch (Exception e) {
            logger.error("error", e);
        }
        return pdr;
    }



    /**
     * 编辑售货合同
     * @return
     */
    @RequestMapping(value = "/list2", method = RequestMethod.POST)
    @ResponseBody
    public List<SellingContractDetail> list2(String id) {

           QueryWrapper<SellingContractDetail> wrapper = new QueryWrapper<SellingContractDetail>();
           wrapper.eq("selling_contract_id",id);
           List<SellingContractDetail> sellingContractDetails = sellingContractDetailService.list(wrapper);
            // 获取分页查询后的数据
           // PageInfo<FormSellingContract> pageInfo = new PageInfo<>(formSellingContracts);
            /*if(sellingContractDetails == null) {
                sellingContractDetails = new ArrayList<>();
                SellingContractDetail sellingContractDetail = new SellingContractDetail();
                sellingContractDetails.add(sellingContractDetail);
            }*/


        return sellingContractDetails;
    }

}

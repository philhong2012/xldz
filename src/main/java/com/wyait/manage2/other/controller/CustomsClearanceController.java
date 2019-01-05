package com.wyait.manage2.other.controller;


import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.wyait.manage.entity.BuyingContractVO;
import com.wyait.manage.entity.CustomsClearanceVO;
import com.wyait.manage.entity.DataGridVO;
import com.wyait.manage.entity.SearchEntityVO;
import com.wyait.manage.pojo.User;
import com.wyait.manage.utils.PageDataResult;
import com.wyait.manage2.other.entity.*;
import com.wyait.manage2.other.service.*;
import io.swagger.models.HttpMethod;
import org.apache.commons.lang3.StringUtils;
import org.apache.shiro.SecurityUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import org.springframework.web.servlet.ModelAndView;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

/**
 * <p>
 * 报关单 前端控制器
 * </p>
 *
 * @author hongxubing
 * @since 2019-01-02
 */
@RestController
@RequestMapping("/customsclearance")
public class CustomsClearanceController {

    Logger logger = LoggerFactory.getLogger(CustomsClearanceController.class);
    @Autowired
    IFormSellingContractService formSellingContractService;
    @Autowired
    ISellingContractDetailService sellingContractDetailService;
    @Autowired
    IFormBuyingContractService formBuyingContractService;

    @Autowired
    IBuyingContractDetailService buyingContractDetailService;

    @Autowired
    ICustomsClearanceDetailService customsClearanceDetailService;

    @Autowired
    ICustomsClearanceService customsClearanceService;

    @RequestMapping("/create")
    public ModelAndView create() {

        ModelAndView mv = new ModelAndView("form/customsclearance/create");
        return mv;
    }


    @RequestMapping("/gen")
    public ModelAndView gen(String sellingContractId) {
        ModelAndView mv = new ModelAndView("form/customsclearance/create");
        FormSellingContract formSellingContract = formSellingContractService.getById(sellingContractId);
        CustomsClearance customsClearance = null;
        if(formSellingContract != null) {
            customsClearance = new CustomsClearance();
            customsClearance.setInnerSender(formSellingContract.getSeller());
            customsClearance.setOuterReceiver(formSellingContract.getBuyer());
            customsClearance.setContractNo(formSellingContract.getContractNo());
            customsClearance.setPackingType(formSellingContract.getPayingType());
            //customsClearance.setQuantity(formSellingContract.)
            //customsClearance.set
        }
        mv.addObject("model",customsClearance);
        return mv;
    }



    @RequestMapping(value={"/genlist"},method = RequestMethod.POST)
    public DataGridVO<CustomsClearanceDetail> genList(String sellingContractId, String id) {
        QueryWrapper<SellingContractDetail> wrapper = new QueryWrapper<>();
        wrapper.eq("selling_contract_id",sellingContractId);
        List<SellingContractDetail> sellingContractDetails = sellingContractDetailService.list(wrapper);
        List<CustomsClearanceDetail> customsClearanceDetails = new ArrayList<>();
        if(StringUtils.isEmpty(id)) {
            if (sellingContractDetails != null) {
                for (SellingContractDetail e : sellingContractDetails) {
                    CustomsClearanceDetail b = new CustomsClearanceDetail();
                    b.setGoodsName(e.getGoodsName());
                    b.setGoodsUnit(e.getGoodsUnit());
                    b.setQuantity(e.getQuantity());
                    b.setPrice(e.getPrice());
                    b.setTotalPrice(e.getTotalPrice());
                    b.setPriceUnit(e.getPriceUnit());
                    b.setGoodsUnit(e.getGoodsUnit());
                    customsClearanceDetails.add(b);
                }
            }
        } else {
            QueryWrapper<CustomsClearanceDetail> wrapper2 = new QueryWrapper<>();
            wrapper2.eq("customs_clearance_id", id);
            customsClearanceDetails = customsClearanceDetailService.list(wrapper2);
        }


        //计算总额

        BigDecimal totalPrice = new BigDecimal(0);
        if (customsClearanceDetails != null) {
            for (CustomsClearanceDetail e : customsClearanceDetails) {
                totalPrice = totalPrice.add(e.getTotalPrice() == null?
                        BigDecimal.ZERO:e.getTotalPrice());

            }
        }

        DataGridVO<CustomsClearanceDetail> detailDataGridVO = new DataGridVO<>();
        detailDataGridVO.setRows(customsClearanceDetails);

        return detailDataGridVO;
    }



    /**
     * 编辑采购合同
     * @return
     */
    @RequestMapping(value = "/edit", method = RequestMethod.GET)
    public ModelAndView edit(String id) {
        ModelAndView mv = new ModelAndView("form/customsclearance/create");
        CustomsClearance customsClearance = customsClearanceService.getById(id);
        mv.addObject("model",customsClearance);
        return mv;
    }

    /**
     * 采购合同列表页面
     * @return
     */
    @RequestMapping("/list")
    public String listPage() {
        return "form/customsclearance/list";
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

            QueryWrapper<CustomsClearance> queryWrapper = new QueryWrapper<>();
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


            List<CustomsClearance> customsClearances = customsClearanceService.list(queryWrapper);
            // 获取分页查询后的数据
            PageInfo<CustomsClearance> pageInfo = new PageInfo<>(customsClearances);
            // 设置获取到的总记录数total：
            pdr.setTotals(Long.valueOf(pageInfo.getTotal()).intValue());

            pdr.setList(customsClearances);
        } catch (Exception e) {
            //e.printStackTrace();
            logger.error("查询异常！", e);
        }
        return pdr;
    }

    /**
     * 保存采购合同及详情
     * @return
     */
    @RequestMapping(value = "/save", method = RequestMethod.POST)
    public String save(@RequestBody CustomsClearanceVO customsClearanceVO) {
        //ModelAndView mv = new ModelAndView("form/buyingContract/save");
        CustomsClearance customsClearance = customsClearanceVO.getContract();

        User u = (User) SecurityUtils.getSubject().getPrincipal();
        if(StringUtils.isNotEmpty(customsClearance.getId())) {
            customsClearance.setUpdateUserId(u.getId().toString());
            customsClearance.setUpdateTime(LocalDateTime.now());
            customsClearance.setUpdateUserName(u.getUsername());
        } else {
            customsClearance.setCreateUserId(u.getId().toString());
            customsClearance.setCreateTime(LocalDateTime.now());
            customsClearance.setCreateUserName(u.getUsername());
            customsClearance.setDeptId(u.getDeptId());
            customsClearance.setDeptName(u.getDeptName());
        }

        if(customsClearance != null) {
            customsClearanceService.saveOrUpdate(customsClearance);
        }

        if(customsClearanceVO.getDetails() != null) {
            for(CustomsClearanceDetail e : customsClearanceVO.getDetails()) {
                e.setCustomsClearanceId(customsClearance.getId());
            }
            customsClearanceDetailService.saveOrUpdateBatch(customsClearanceVO.getDetails());
        }
        return "ok";
    }

    @RequestMapping(value = "/delete", method = RequestMethod.POST)
    @ResponseBody
    public String delete(String id) {
        QueryWrapper<CustomsClearanceDetail> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("customs_clearance_id",id);

        customsClearanceDetailService.remove(queryWrapper);

        customsClearanceService.removeById(id);

        return "ok";
    }


}

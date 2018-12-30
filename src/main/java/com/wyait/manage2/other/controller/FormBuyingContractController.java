package com.wyait.manage2.other.controller;


import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.wyait.common.utils.NumberUtils;
import com.wyait.manage.entity.BuyingContractVO;
import com.wyait.manage.entity.DataGridVO;
import com.wyait.manage.entity.SearchEntityVO;
import com.wyait.manage.pojo.User;
import com.wyait.manage.utils.PageDataResult;
import com.wyait.manage2.other.entity.BuyingContractDetail;
import com.wyait.manage2.other.entity.FormBuyingContract;
import com.wyait.manage2.other.entity.FormSellingContract;
import com.wyait.manage2.other.entity.SellingContractDetail;
import com.wyait.manage2.other.service.IBuyingContractDetailService;
import com.wyait.manage2.other.service.IFormBuyingContractService;
import com.wyait.manage2.other.service.IFormSellingContractService;
import com.wyait.manage2.other.service.ISellingContractDetailService;
import jdk.nashorn.internal.runtime.NumberToString;
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
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * <p>
 * 采购合同 前端控制器
 * </p>
 *
 * @author hongxubing
 * @since 2018-12-22
 */
@RestController
@RequestMapping("/buyingcontract")
public class FormBuyingContractController {
    Logger logger = LoggerFactory.getLogger(FormBuyingContractController.class);
    @Autowired
    IFormSellingContractService formSellingContractService;
    @Autowired
    ISellingContractDetailService sellingContractDetailService;
    @Autowired
    IFormBuyingContractService formBuyingContractService;

    @Autowired
    IBuyingContractDetailService buyingContractDetailService;
    /**
     * 生成采购合同
     * @return
     */
    @RequestMapping(value = "/gen", method = RequestMethod.GET)
    public ModelAndView gen(String sellingContractId) {
        ModelAndView mv = new ModelAndView("form/buyingContract/create");
        FormSellingContract formSellingContract = formSellingContractService.getById(sellingContractId);
        FormBuyingContract formBuyingContract  = new FormBuyingContract();
        if(formSellingContract != null) {
            formBuyingContract.setBuyer(formSellingContract.getSeller());
            formBuyingContract.setContractNo(formSellingContract.getContractNo());
        }

        mv.addObject("model",formBuyingContract);
        return mv;
    }

    /**
     * 生成采购合同货物详情
     * @return
     */
    @RequestMapping(value = "/genlist", method = RequestMethod.POST)
    public DataGridVO<BuyingContractDetail> genlist(String sellingContractId, String id) {
        //ModelAndView mv = new ModelAndView("form/buyingContract/save");
        QueryWrapper<SellingContractDetail> wrapper = new QueryWrapper<>();
        wrapper.eq("selling_contract_id",sellingContractId);
        List<SellingContractDetail> sellingContractDetails = sellingContractDetailService.list(wrapper);
        List<BuyingContractDetail> buyingContractDetails = new ArrayList<>();
        if(StringUtils.isEmpty(id)) {

            if (sellingContractDetails != null) {
                for (SellingContractDetail e : sellingContractDetails) {
                    BuyingContractDetail b = new BuyingContractDetail();
                    b.setGoodsName(e.getGoodsName());
                    b.setGoodsUnit(e.getGoodsUnit());
                    b.setQuantity(e.getQuantity());
                    buyingContractDetails.add(b);
                }
            }
        } else {
            QueryWrapper<BuyingContractDetail> wrapper2 = new QueryWrapper<>();
            wrapper2.eq("buying_contract_id", id);
            buyingContractDetails = buyingContractDetailService.list(wrapper2);
        }


        //计算总额

        BigDecimal totalPrice = new BigDecimal(0);
        if (buyingContractDetails != null) {
            for (BuyingContractDetail e : buyingContractDetails) {
                totalPrice = totalPrice.add(e.getTotalPrice() == null?
                        BigDecimal.ZERO:e.getTotalPrice());

            }
        }

        DataGridVO<BuyingContractDetail>  detailDataGridVO = new DataGridVO<>();
        detailDataGridVO.setRows(buyingContractDetails);




        List<Map<String,String>> footerSection = new ArrayList<>();

        Map<String,String> footer1 = new HashMap<>();
        footer1.put("price","人民币");
        footer1.put("totalPrice",totalPrice.toString());
        Map<String,String> footer2 = new HashMap<>();
        footer2.put("price","价税合计:人民币(大写)");
        footer2.put("totalPrice",NumberUtils.digitUppercase(Double.parseDouble(totalPrice.toString()) ));

        footerSection.add(footer1);
        footerSection.add(footer2);

        detailDataGridVO.setFooter(footerSection);

        return detailDataGridVO;
    }

    /**
     * 编辑采购合同
     * @return
     */
    @RequestMapping(value = "/create", method = RequestMethod.GET)
    public String create() {
        return "form/buyingContract/create";
    }


    /**
     * 编辑采购合同
     * @return
     */
    @RequestMapping(value = "/edit", method = RequestMethod.GET)
    public ModelAndView edit(String id) {
        ModelAndView mv = new ModelAndView("form/buyingContract/create");
        FormBuyingContract formBuyingContract = formBuyingContractService.getById(id);
        mv.addObject("model",formBuyingContract);
        return mv;
    }

    /**
     * 采购合同列表页面
     * @return
     */
    @RequestMapping("/list")
    public String buyingContractList() {
        return "form/buyingcontract/list";
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

            QueryWrapper<FormBuyingContract> queryWrapper = new QueryWrapper<>();
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

                if(searchEntityVO.getStartSignDate() != null) {

                    queryWrapper.gt("sign_date",searchEntityVO.getStartSignDate());
                }

                if(searchEntityVO.getEndSignDate() != null) {
                    queryWrapper.lt("sign_date",searchEntityVO.getEndSignDate());
                }
            }


            List<FormBuyingContract> formBuyingContracts = formBuyingContractService.list(queryWrapper);
            // 获取分页查询后的数据
            PageInfo<FormBuyingContract> pageInfo = new PageInfo<>(formBuyingContracts);
            // 设置获取到的总记录数total：
            pdr.setTotals(Long.valueOf(pageInfo.getTotal()).intValue());

            pdr.setList(formBuyingContracts);
        } catch (Exception e) {
            //e.printStackTrace();
            logger.error("采购列表查询异常！", e);
        }
        return pdr;
    }

    /**
     * 保存采购合同及详情
     * @return
     */
    @RequestMapping(value = "/save", method = RequestMethod.POST)
    public String save(@RequestBody BuyingContractVO buyingContractVO) {
        //ModelAndView mv = new ModelAndView("form/buyingContract/save");
        FormBuyingContract formBuyingContract = buyingContractVO.getContract();

        User u = (User) SecurityUtils.getSubject().getPrincipal();
        if(StringUtils.isNotEmpty(formBuyingContract.getId())) {
            formBuyingContract.setUpdateUserId(u.getId().toString());
            formBuyingContract.setUpdateTime(LocalDateTime.now());
        } else {
            formBuyingContract.setCreateUserId(u.getId().toString());
            formBuyingContract.setCreateTime(LocalDateTime.now());
        }

        if(formBuyingContract != null) {
           formBuyingContractService.saveOrUpdate(formBuyingContract);
        }

        if(buyingContractVO.getDetails() != null) {
            for(BuyingContractDetail e : buyingContractVO.getDetails()) {
                e.setBuyingContractId(formBuyingContract.getId());
            }
            buyingContractDetailService.saveOrUpdateBatch(buyingContractVO.getDetails());
        }
        return "ok";
    }
}

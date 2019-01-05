package com.wyait.manage2.other.controller;


import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.wyait.common.utils.NumberUtils;
import com.wyait.manage.entity.BuyingContractVO;
import com.wyait.manage.entity.DataGridVO;
import com.wyait.manage.entity.ExportGoodsListVO;
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
import org.springframework.web.bind.annotation.*;

import org.springframework.web.servlet.ModelAndView;

import java.math.BigDecimal;
import java.text.Normalizer;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.*;

/**
 * <p>
 * 出口货物单 前端控制器
 * </p>
 *
 * @author hongxubing
 * @since 2018-12-26
 */
@RestController
@RequestMapping("/exportgoodslist")
public class ExportGoodsListController {

    Logger logger = LoggerFactory.getLogger(ExportGoodsListController.class);

    @Autowired
    IFormSellingContractService formSellingContractService;

    @Autowired
    ISellingContractDetailService sellingContractDetailService;

    @Autowired
    IBuyingContractDetailService buyingContractDetailService;
    @Autowired
    IFormBuyingContractService formBuyingContractService;

    @Autowired
    IExportGoodsListDetailService exportGoodsListDetailService;


    @Autowired
    IExportGoodsListService exportGoodsListService;

    /**
     * 生成采购合同
     * @return
     */
    @RequestMapping(value = "/gen", method = RequestMethod.GET)
    public ModelAndView gen(String sellingContractId) {
        ModelAndView mv = new ModelAndView("form/exportgoodslist/create");
        FormSellingContract formSellingContract = formSellingContractService.getById(sellingContractId);
        ExportGoodsList exportGoodsList  = new ExportGoodsList();

        if(formSellingContract != null) {
            exportGoodsList.setSellingContractId(formSellingContract.getId());
            exportGoodsList.setSellingContractNo(formSellingContract.getContractNo());
            exportGoodsList.setPackingKouAn(formSellingContract.getPackingMaiTou());
            exportGoodsList.setSendingKouAn(formSellingContract.getSendingKouAn());
            exportGoodsList.setBuyer(formSellingContract.getBuyer());
            exportGoodsList.setSeller(formSellingContract.getSeller());

        }

        mv.addObject("model",exportGoodsList);
        return mv;
    }

    /**
     * 生成采购合同货物详情
     * @return
     */
    @RequestMapping(value = "/genlist", method = RequestMethod.POST)
    public DataGridVO<ExportGoodsListDetail> genlist(String sellingContractId, String id) {
        //ModelAndView mv = new ModelAndView("form/exportgoodslist/save");
        QueryWrapper<SellingContractDetail> wrapper = new QueryWrapper<>();
        wrapper.eq("selling_contract_id",sellingContractId);
        List<SellingContractDetail> sellingContractDetails = sellingContractDetailService.list(wrapper);


        QueryWrapper<FormBuyingContract> formBuyingContractQueryWrapper = new QueryWrapper<>();
        formBuyingContractQueryWrapper.eq("selling_contract_id",sellingContractId);

        FormBuyingContract buyingContract = null;
        List<FormBuyingContract> buyingContracts = formBuyingContractService.list(formBuyingContractQueryWrapper);
        if(buyingContracts != null && buyingContracts.size() > 0) {
             buyingContract = buyingContracts.get(0);
        }
        List<ExportGoodsListDetail> exportGoodsListDetails = new ArrayList<>();
        if(StringUtils.isEmpty(id)) {
            if (sellingContractDetails != null) {
                for (SellingContractDetail e : sellingContractDetails) {
                    ExportGoodsListDetail egDetail = new ExportGoodsListDetail();
                    egDetail.setGoodsName(e.getGoodsName());
                    egDetail.setGoodsUnit(e.getGoodsName());
                    egDetail.setQuantity(e.getQuantity());
                    egDetail.setSellingPrice(e.getPrice());
                    egDetail.setSubtotalSellingPrice(e.getTotalPrice());

                    if(buyingContract != null) {
                        egDetail.setGoodsProducer(buyingContract.getSeller());
                    }
                    exportGoodsListDetails.add(egDetail);
                }
            }
        } else {
            QueryWrapper<ExportGoodsListDetail> wrapper2 = new QueryWrapper<>();
            wrapper2.eq("export_goods_list_id", id);
            exportGoodsListDetails = exportGoodsListDetailService.list(wrapper2);
        }


        //计算总额

        /*BigDecimal totalPrice = new BigDecimal(0);
        if (exportGoodsListDetails != null) {
            for (ExportGoodsListDetail e : exportGoodsListDetails) {
                totalPrice = totalPrice.add(e.getsu() == null?
                        BigDecimal.ZERO:e.getTotalPrice());

            }
        }*/

        DataGridVO<ExportGoodsListDetail>  detailDataGridVO = new DataGridVO<>();
        detailDataGridVO.setRows(exportGoodsListDetails);


         /*

        List<Map<String,String>> footerSection = new ArrayList<>();

        Map<String,String> footer1 = new HashMap<>();
        footer1.put("price","人民币");
        footer1.put("totalPrice",totalPrice.toString());
        Map<String,String> footer2 = new HashMap<>();
        footer2.put("price","价税合计:人民币(大写)");
        footer2.put("totalPrice",NumberUtils.digitUppercase(Double.parseDouble(totalPrice.toString()) ));

        footerSection.add(footer1);
        footerSection.add(footer2);

        detailDataGridVO.setFooter(footerSection);*/

        return detailDataGridVO;
    }

    /**
     * 编辑采购合同
     * @return
     */
    @RequestMapping(value = "/create", method = RequestMethod.GET)
    public String create() {
        return "form/exportgoodslist/create";
    }


    /**
     * 编辑采购合同
     * @return
     */
    @RequestMapping(value = "/edit", method = RequestMethod.GET)
    public ModelAndView edit(String id) {
        ModelAndView mv = new ModelAndView("form/exportgoodslist/create");
        ExportGoodsList exportGoodsList = exportGoodsListService.getById(id);
        mv.addObject("model",exportGoodsList);
        return mv;
    }

    /**
     * 采购合同列表页面
     * @return
     */
    @RequestMapping("/list")
    public String exportgoodslistList() {
        return "form/exportgoodslist/list";
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

            QueryWrapper<ExportGoodsList> queryWrapper = new QueryWrapper<>();
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


            List<ExportGoodsList> exportGoodsLists = exportGoodsListService.list(queryWrapper);
            // 获取分页查询后的数据
            PageInfo<ExportGoodsList> pageInfo = new PageInfo<>(exportGoodsLists);
            // 设置获取到的总记录数total：
            pdr.setTotals(Long.valueOf(pageInfo.getTotal()).intValue());

            pdr.setList(exportGoodsLists);
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
    public String save(@RequestBody ExportGoodsListVO exportGoodsListVO ) {
        //ModelAndView mv = new ModelAndView("form/exportgoodslist/save");
        ExportGoodsList exportGoodsList = exportGoodsListVO.getContract();
        User u = (User) SecurityUtils.getSubject().getPrincipal();
        if(StringUtils.isNotEmpty(exportGoodsList.getId())) {
           exportGoodsList.setUpdateUserId(u.getId().toString());
           exportGoodsList.setUpdateTime(LocalDateTime.now());
            exportGoodsList.setUpdateUserName(u.getUsername());
        } else {
            exportGoodsList.setCreateUserId(u.getId().toString());
            exportGoodsList.setCreateTime(LocalDateTime.now());
            exportGoodsList.setCreateUserName(u.getUsername());
            exportGoodsList.setDeptId(u.getDeptId());
            exportGoodsList.setDeptName(u.getDeptName());
        }
        exportGoodsListService.saveOrUpdate(exportGoodsList);

        if(exportGoodsListVO.getDetails() != null) {
            for (ExportGoodsListDetail e : exportGoodsListVO.getDetails()) {
                e.setExportGoodsListId(exportGoodsList.getId());
            }
        }

        exportGoodsListDetailService.saveOrUpdateBatch(exportGoodsListVO.getDetails());

        return "ok";
    }


    @RequestMapping(value = "/delete", method = RequestMethod.POST)
    @ResponseBody
    public String save(String id) {
        QueryWrapper<ExportGoodsListDetail> exportGoodsListDetailQueryWrapper = new QueryWrapper<>();
        exportGoodsListDetailQueryWrapper.eq("export_goods_list_id",id);

        exportGoodsListDetailService.remove(exportGoodsListDetailQueryWrapper);

        exportGoodsListService.removeById(id);


        return "ok";
    }

}

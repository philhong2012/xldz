package com.wyait.manage2.other.controller;


import cn.afterturn.easypoi.entity.vo.TemplateExcelConstants;
import cn.afterturn.easypoi.entity.vo.TemplateWordConstants;
import cn.afterturn.easypoi.excel.entity.TemplateExportParams;
import cn.afterturn.easypoi.view.EasypoiTemplateWordView;
import cn.afterturn.easypoi.view.PoiBaseView;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.wyait.common.utils.BeanUtils;
import com.wyait.common.utils.NumberUtils;
import com.wyait.manage.entity.DataGridVO;
import com.wyait.manage.entity.FormOutboundVO;
import com.wyait.manage.entity.PackingListVO;
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
 * 出库单 前端控制器
 * </p>
 *
 * @author hongxubing
 * @since 2019-01-14
 */
@RestController
@RequestMapping("/formoutbound")
public class FormOutboundController {
    Logger logger = LoggerFactory.getLogger(FormOutboundController.class);
    @Autowired
    IFormSellingContractService formSellingContractService;
    @Autowired
    ISellingContractDetailService sellingContractDetailService;
    @Autowired
    IFormBuyingContractService formBuyingContractService;

    @Autowired
    IBuyingContractDetailService buyingContractDetailService;

    @Autowired
    IPackingListService packingListService;

    @Autowired
    IPackingListDetailService packingListDetailService;

    @Autowired
    IFormOutboundService formOutboundService;

    @Autowired
    IFormOutboundDetailService formOutboundDetailService;
    /**
     * 生成装箱单
     * @return
     */
    @RequestMapping(value = "/gen", method = RequestMethod.GET)
    public ModelAndView gen(String sellingContractId) {
        ModelAndView mv = new ModelAndView("form/formoutbound/create");
        FormSellingContract formSellingContract = formSellingContractService.getById(sellingContractId);

        QueryWrapper<FormBuyingContract> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("selling_contract_id",sellingContractId);
        FormBuyingContract formBuyingContract = formBuyingContractService.getOne(queryWrapper);
        if(formBuyingContract == null) {
            logger.error("请先生成采购合同！销售合id:{}",formSellingContract.getId());
            throw new RuntimeException("请先生成采购合同！");
        }
        FormOutbound formOutbound  = new FormOutbound();
        if(formSellingContract != null) {
            formOutbound.setProvider(formBuyingContract.getSeller());
            formOutbound.setCustomer(formSellingContract.getBuyer());
            formOutbound.setCode(formSellingContract.getContractNo());
        }

        mv.addObject("model",formOutbound);
        return mv;
    }
    @RequestMapping("/download")
    public void download(String id, ModelMap modelMap, HttpServletRequest request, HttpServletResponse response) {
        Map<String, Object> map = new HashMap<String, Object>();

        FormOutbound formOutbound = formOutboundService.getById(id);

        QueryWrapper<FormOutboundDetail> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("outbound_id", id);
        List<FormOutboundDetail> formOutboundDetails = formOutboundDetailService.list(queryWrapper);
        //map.put("items", new ExcelListEntity(sellingContractDetails, BuyingContractDetail.class));
        map.putAll(BeanUtils.objectToMap(formOutbound));
        List<Map<String, Object>> mapList = new ArrayList<Map<String, Object>>();
        BigDecimal totalPrice = BigDecimal.ZERO;
        BigDecimal totalGW = BigDecimal.ZERO;
        BigDecimal totalNW = BigDecimal.ZERO;
        BigDecimal totalQT = BigDecimal.ZERO;
        BigDecimal totalPQ = BigDecimal.ZERO;
        String priceUnit = StringUtils.EMPTY;
        BigDecimal totalPackageQuantity = BigDecimal.ZERO;
        if(formOutboundDetails != null) {

            for (FormOutboundDetail e : formOutboundDetails) {
                Map<String,Object> m = new HashMap<>();

                m.putAll(BeanUtils.objectToMap(e));
                mapList.add(m);
                totalPrice = totalPrice.add(e.getTotalPrice()== null ? BigDecimal.ZERO:e.getTotalPrice());
                totalGW = totalGW.add(e.getGrossWeight()== null ? BigDecimal.ZERO: e.getGrossWeight());
                totalNW = totalNW.add(e.getNetWeight()== null ? BigDecimal.ZERO:e.getNetWeight());
                totalPQ = totalPQ.add(e.getPackageQuantity()== null ? BigDecimal.ZERO:e.getPackageQuantity());
                totalQT = totalQT.add(e.getQuantity()== null ? BigDecimal.ZERO:e.getQuantity());
                totalPackageQuantity = totalPackageQuantity.add(e.getPackageQuantity()== null ? BigDecimal.ZERO:e.getPackageQuantity());
            }
            map.put("totalGW",totalGW);
            map.put("totalNW",totalNW);
            map.put("totalPQ",totalPQ);
            map.put("totalQT",totalQT);
            map.put("capTotalPrice",NumberUtils.digitUppercase(totalPrice.doubleValue()));
            map.put("totalPrice", priceUnit + totalPrice.toString());
            map.put("totalPackageQuantity",totalPackageQuantity);
            map.put("totalQuantity",totalQT);
        }
        map.put("items",mapList);

        TemplateExportParams params = new TemplateExportParams(
                "word/temp_出入库明细表.xlsx");

        modelMap.put(TemplateExcelConstants.FILE_NAME, formOutbound.getCode());
        modelMap.put(TemplateExcelConstants.PARAMS, params);
        modelMap.put(TemplateExcelConstants.MAP_DATA, map);
        PoiBaseView.render(modelMap, request, response,
                TemplateExcelConstants.EASYPOI_TEMPLATE_EXCEL_VIEW);
    }
    /**
     * 生成装箱单货物详情
     * @return
     */
    @RequestMapping(value = "/genlist", method = RequestMethod.POST)
    public DataGridVO<FormOutboundDetail> genlist(String sellingContractId, String id) {
        //ModelAndView mv = new ModelAndView("form/buyingContract/save");


        QueryWrapper<PackingList> wrapper = new QueryWrapper<>();
        wrapper.eq("selling_contract_id",sellingContractId);
        PackingList packingList = packingListService.getOne(wrapper);
        List<PackingListDetail> packingListDetails = null;
        if(packingList != null) {
            QueryWrapper<PackingListDetail> wrapper2 = new QueryWrapper<>();
            wrapper.eq("packing_list_Id", packingList.getId());
            packingListDetails = packingListDetailService.list(wrapper2);
        }

        List<FormOutboundDetail> formOutboundDetails  = new ArrayList<>();
        if(StringUtils.isEmpty(id)) {
            if (packingListDetails != null) {
                for (PackingListDetail e : packingListDetails) {
                    FormOutboundDetail b = new FormOutboundDetail();
                    b.setGoodsName(e.getGoodsName());
                    b.setGoodsUnit(e.getGoodsUnit());
                    b.setQuantity(e.getQuantity());
                    b.setPackageQuantity(e.getPackageQuantity());
                    formOutboundDetails.add(b);
                }
            }
        } else {
            QueryWrapper<FormOutboundDetail> wrapper2 = new QueryWrapper<>();
            wrapper2.eq("outbound_id", id);
            formOutboundDetails = formOutboundDetailService.list(wrapper2);
        }


        //计算总额

        BigDecimal totalPrice = new BigDecimal(0);
        if (formOutboundDetails != null) {
            for (FormOutboundDetail e : formOutboundDetails) {
                totalPrice = totalPrice.add(e.getTotalPrice() == null?
                        BigDecimal.ZERO:e.getTotalPrice());

            }
        }

        DataGridVO<FormOutboundDetail>  detailDataGridVO = new DataGridVO<>();
        detailDataGridVO.setRows(formOutboundDetails);




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
     * 装箱单创建视图
     * @return
     */
    @RequestMapping(value = "/create", method = RequestMethod.GET)
    public String create() {
        return "form/formoutbound/create";
    }


    /**
     * 编辑采装箱单
     * @return
     */
    @RequestMapping(value = "/edit", method = RequestMethod.GET)
    public ModelAndView edit(String id) {
        ModelAndView mv = new ModelAndView("form/formoutbound/create");
        FormOutbound formOutbound = formOutboundService.getById(id);
        mv.addObject("model",formOutbound);
        return mv;
    }

    /**
     * 获取装箱单列表
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

            QueryWrapper<FormOutbound> queryWrapper = new QueryWrapper<>();
            if(searchEntityVO != null) {
                if(StringUtils.isNotEmpty(searchEntityVO.getCode())) {
                    queryWrapper.like("code",searchEntityVO.getCode());
                }
                if(StringUtils.isNotEmpty(searchEntityVO.getName())) {
                    //queryWrapper.eq("cont")
                }
                if(searchEntityVO.getStartInboundDate() != null) {
                    queryWrapper.gt("outbound_date",searchEntityVO.getStartOutboundDate());
                }
                if(searchEntityVO.getEndOutboundDate() != null) {
                    queryWrapper.lt("outbound_date",searchEntityVO.getEndOutboundDate());
                }
            }


            List<FormOutbound> formOutbounds = formOutboundService.list(queryWrapper);
            // 获取分页查询后的数据
            PageInfo<FormOutbound> pageInfo = new PageInfo<>(formOutbounds);
            // 设置获取到的总记录数total：
            pdr.setTotals(Long.valueOf(pageInfo.getTotal()).intValue());

            pdr.setList(formOutbounds);
        } catch (Exception e) {
            //e.printStackTrace();
            logger.error("查询异常！", e);
        }
        return pdr;
    }

    /**
     * 保存装箱单及详情
     * @return
     */
    @RequestMapping(value = "/save", method = RequestMethod.POST)
    public String save(@RequestBody FormOutboundVO formOutboundVO) {
        //ModelAndView mv = new ModelAndView("form/buyingContract/save");
        FormOutbound formOutbound = formOutboundVO.getContract();

        User u = (User) SecurityUtils.getSubject().getPrincipal();
        if(StringUtils.isNotEmpty(formOutbound.getId())) {
            formOutbound.setUpdateUserId(u.getId().toString());
            formOutbound.setUpdateTime(LocalDateTime.now());
            formOutbound.setUpdateUserName(u.getUsername());
        } else {
            formOutbound.setCreateUserId(u.getId().toString());
            formOutbound.setCreateTime(LocalDateTime.now());
            formOutbound.setCreateUserName(u.getUsername());
            formOutbound.setDeptId(u.getDeptId());
            formOutbound.setDeptName(u.getDeptName());
        }

        if(formOutbound != null) {
            formOutboundService.saveOrUpdate(formOutbound);
        }

        if(formOutboundVO.getDetails() != null) {
            for(FormOutboundDetail e : formOutboundVO.getDetails()) {
                e.setOutboundId(formOutbound.getId());
            }
            formOutboundDetailService.saveOrUpdateBatch(formOutboundVO.getDetails());
        }
        return "ok";
    }


    @RequestMapping(value = "/delete", method = RequestMethod.POST)
    @ResponseBody
    public String save(String id) {
        //formInvoiceService.remove();
        QueryWrapper<FormOutboundDetail> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("outbound_id",id);

        formOutboundDetailService.remove(queryWrapper);

        formOutboundService.removeById(id);


        return "ok";
    }
}

package com.wyait.manage2.other.controller;


import cn.afterturn.easypoi.entity.vo.TemplateExcelConstants;
import cn.afterturn.easypoi.entity.vo.TemplateWordConstants;
import cn.afterturn.easypoi.excel.entity.TemplateExportParams;
import cn.afterturn.easypoi.view.EasypoiTemplateWordView;
import cn.afterturn.easypoi.view.PoiBaseView;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.wyait.common.utils.NumberUtils;
import com.wyait.manage.entity.BuyingContractVO;
import com.wyait.manage.entity.DataGridVO;
import com.wyait.manage.entity.SearchEntityVO;
import com.wyait.manage.pojo.User;
import com.wyait.manage.utils.PageDataResult;
import com.wyait.manage2.other.entity.*;
import com.wyait.manage2.other.service.IFormBuyingContractService;
import com.wyait.manage2.other.service.IFormInvoiceService;
import com.wyait.manage2.other.service.IFormSellingContractService;
import com.wyait.manage2.other.service.ISellingContractDetailService;
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
 * 发票 前端控制器
 * </p>
 *
 * @author hongxubing
 * @since 2018-12-27
 */
@RestController
@RequestMapping("/invoice")
public class FormInvoiceController extends BaseController{
    Logger logger = LoggerFactory.getLogger(FormInvoiceController.class);

    @Autowired
    IFormSellingContractService formSellingContractService;
    @Autowired
    ISellingContractDetailService sellingContractDetailService;
    @Autowired
    IFormBuyingContractService formBuyingContractService;

    @Autowired
    IFormInvoiceService formInvoiceService;

    @RequestMapping(value = "/gen", method = RequestMethod.GET)
    public ModelAndView gen(String sellingContractId) {
        ModelAndView mv = new ModelAndView("form/invoice/create");
        FormSellingContract formSellingContract = formSellingContractService.getById(sellingContractId);
        FormInvoice formInvoice = new FormInvoice();
        if(formSellingContract != null) {
            formInvoice.setCode(formSellingContract.getContractNo());
            formInvoice.setBuyer(formSellingContract.getBuyer());
            formInvoice.setPackingMaiTou(formSellingContract.getPackingMaiTou());
            formInvoice.setSellingContractId(formSellingContract.getId());
            formInvoice.setSeller("GUANGDONG NEW ELECTRONICS INFORMATION IMPORT & EXPORT LTD\n" +
                    "172 HAOXIAN ROAD, GUANGZHOU, CHINA");
            //formInvoice.set.setContractNo(formSellingContract.getContractNo());
        }

        mv.addObject("model",formInvoice);
        return mv;
    }

    @RequestMapping("/download")
    public void download(String id, ModelMap modelMap, HttpServletRequest request, HttpServletResponse response) {
        Map<String, Object> map = new HashMap<String, Object>();

        FormInvoice formInvoice = formInvoiceService.getById(id);
        FormSellingContract formSellingContract = formSellingContractService.getById(formInvoice.getSellingContractId());
        QueryWrapper<SellingContractDetail> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("selling_contract_Id", formSellingContract.getId());
        List<SellingContractDetail> sellingContractDetails = sellingContractDetailService.list(queryWrapper);
        //map.put("items", new ExcelListEntity(sellingContractDetails, SellingContractDetail.class));
        map.put("sellingContractNo", formSellingContract.getContractNo());
        map.put("invoiceDate",formInvoice.getInvoiceDate());
        map.put("buyer",formSellingContract.getBuyer());
        map.put("code",formInvoice.getCode());
        //map.put("signAddress",formSellingContract.getSignAddress() == null ? "":formSellingContract.getSignAddress());
        List<Map<String, Object>> mapList = new ArrayList<Map<String, Object>>();
        BigDecimal totalPrice = BigDecimal.ZERO;
        String priceUnit = StringUtils.EMPTY;
        if(sellingContractDetails != null) {

            for (SellingContractDetail e : sellingContractDetails) {
                Map<String,Object> m = new HashMap<>();
                m.put("goodsName",e.getGoodsName() == null ? "":e.getGoodsName());
                m.put("goodsUnit", e.getGoodsUnit() == null ? "":e.getGoodsUnit());
                m.put("quantity",(e.getQuantity() == null? BigDecimal.ZERO : e.getQuantity()).toString() + e.getGoodsUnit());
                m.put("price", e.getPriceUnit() +(e.getPrice() == null? BigDecimal.ZERO : e.getPrice()).toString());
                m.put("priceUnit",e.getPriceUnit() == null ? "":e.getPriceUnit());
                if(StringUtils.isNotEmpty(e.getPriceUnit())) {
                    priceUnit = e.getPriceUnit();
                }
                m.put("packingMaiTou",formInvoice.getPackingMaiTou());
                m.put("totalPrice",e.getTotalPrice());
                mapList.add(m);

                totalPrice = totalPrice.add(e.getTotalPrice() == null ? BigDecimal.ZERO:e.getTotalPrice());

                m.put("totalPrice",priceUnit + (e.getTotalPrice() == null ? "0": e.getTotalPrice().toString()));
            }

            map.put("totalPriceCap",NumberUtils.digitUppercase(totalPrice.doubleValue()));
            map.put("totalPrice", priceUnit + totalPrice.toString());

        }
        map.put("items",mapList);

        modelMap.put("checked"," X ");
        modelMap.put("unchecked","  ");

        //modelMap.put(TemplateWordConstants.FILE_NAME, formSellingContract.getContractNo());
        //modelMap.put(TemplateWordConstants.MAP_DATA, map);
        //modelMap.put(TemplateWordConstants.URL, "word/temp_发票.docx");


        TemplateExportParams params = new TemplateExportParams(
                "word/temp_发票.xlsx");
        modelMap.put(TemplateExcelConstants.FILE_NAME, formSellingContract.getContractNo());
        modelMap.put(TemplateExcelConstants.PARAMS, params);
        modelMap.put(TemplateExcelConstants.MAP_DATA, map);
        PoiBaseView.render(modelMap, request, response,
                TemplateExcelConstants.EASYPOI_TEMPLATE_EXCEL_VIEW);

        //EasypoiTemplateWordView
        //EasypoiTemplateWordView.render(modelMap, request, response,TemplateWordConstants.EASYPOI_TEMPLATE_WORD_VIEW);
    }



    /**
     * 生成发票及详情
     * @return
     */
    @RequestMapping(value = "/genlist", method = RequestMethod.POST)
    public DataGridVO<SellingContractDetail> genlist(String sellingContractId, String id) {

        if(StringUtils.isNotEmpty(id)) {
            FormInvoice formInvoice = formInvoiceService.getById(id);
            if (formInvoice != null) {
                sellingContractId = formInvoice.getSellingContractId();
            }
        }

        QueryWrapper<SellingContractDetail> wrapper = new QueryWrapper<>();
        wrapper.eq("selling_contract_id",sellingContractId);
        List<SellingContractDetail> sellingContractDetails = sellingContractDetailService.list(wrapper);

        //计算总额
        BigDecimal totalPrice = new BigDecimal(0);
        if (sellingContractDetails != null) {
            for (SellingContractDetail e : sellingContractDetails) {
                totalPrice = totalPrice.add(e.getTotalPrice() == null?
                        BigDecimal.ZERO:e.getTotalPrice());

            }
        }

        DataGridVO<SellingContractDetail>  detailDataGridVO = new DataGridVO<>();
        detailDataGridVO.setRows(sellingContractDetails);


        List<Map<String,String>> footerSection = new ArrayList<>();

        Map<String,String> footer1 = new HashMap<>();
        footer1.put("price","人民币");
        footer1.put("totalPrice","USD"+totalPrice.toString());

        footerSection.add(footer1);
        detailDataGridVO.setFooter(footerSection);

        return detailDataGridVO;
    }

    /**
     * 创建发票
     * @return
     */
    @RequestMapping(value = "/create", method = RequestMethod.GET)
    public String create() {
        return "form/invoice/create";
    }


    /**
     * 编辑发票
     * @return
     */
    @RequestMapping(value = "/edit", method = RequestMethod.GET)
    public ModelAndView edit(String id) {
        ModelAndView mv = new ModelAndView("form/invoice/create");
        FormInvoice formInvoice = formInvoiceService.getById(id);

        FormSellingContract formSellingContract = formSellingContractService.getById(formInvoice.getSellingContractId());

        formInvoice.setCode(formSellingContract.getContractNo());
        formInvoice.setBuyer(formSellingContract.getBuyer());
        formInvoice.setSellingContractId(formSellingContract.getId());
        formInvoice.setSeller("GUANGDONG NEW ELECTRONICS INFORMATION IMPORT & EXPORT LTD\n" +
                "172 HAOXIAN ROAD, GUANGZHOU, CHINA");
        mv.addObject("model",formInvoice);
        return mv;
    }

    /**
     * 发票列表视图
     * @return
     */
    @RequestMapping("/list")
    public String buyingContractList() {
        return "form/invoice/list";
    }

    /**
     * 发票列表
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

            QueryWrapper<FormInvoice> queryWrapper = new QueryWrapper<>();
            if(searchEntityVO != null) {
                if(StringUtils.isNotEmpty(searchEntityVO.getCode())) {
                    queryWrapper.like("code",searchEntityVO.getCode());
                }
                if(StringUtils.isNotEmpty(searchEntityVO.getName())) {
                    //queryWrapper.eq("cont")
                }
                if(searchEntityVO.getStartCreateTime() != null) {
                    queryWrapper.gt("invoice_date",searchEntityVO.getStartCreateTime());
                }
                if(searchEntityVO.getEndCreateTime() != null) {
                    queryWrapper.lt("invoice_date",searchEntityVO.getEndCreateTime());
                }

                if(searchEntityVO.getStartSignDate() != null) {

                    queryWrapper.gt("sign_date",searchEntityVO.getStartSignDate());
                }

                if(searchEntityVO.getEndSignDate() != null) {
                    queryWrapper.lt("sign_date",searchEntityVO.getEndSignDate());
                }
            }
            if(!isCompanyRole()) {
                queryWrapper.eq("dept_id",getCurrentUser().getDeptId());
            }

            List<FormInvoice> formInvoices = formInvoiceService.list(queryWrapper);
            // 获取分页查询后的数据
            PageInfo<FormInvoice> pageInfo = new PageInfo<>(formInvoices);
            // 设置获取到的总记录数total：
            pdr.setTotals(Long.valueOf(pageInfo.getTotal()).intValue());

            pdr.setList(formInvoices);
        } catch (Exception e) {
            //e.printStackTrace();
            logger.error("异常:", e);
        }
        return pdr;
    }

    /**
     * 保存发票
     * @return
     */
    @RequestMapping(value = "/save", method = RequestMethod.POST)
    public String save(@RequestBody FormInvoice formInvoice) {
        //FormInvoice formInvoice = new FormInvoice();
        User u = (User) SecurityUtils.getSubject().getPrincipal();
        if(StringUtils.isNotEmpty(formInvoice.getId())) {
            formInvoice.setUpdateUserId(u.getId().toString());
            formInvoice.setUpdateTime(LocalDateTime.now());
            formInvoice.setUpdateUserName(u.getUsername());

        } else {
            formInvoice.setCreateUserId(u.getId().toString());
            formInvoice.setCreateTime(LocalDateTime.now());
            formInvoice.setCreateUserName(u.getUsername());
            formInvoice.setDeptId(u.getDeptId());
            formInvoice.setDeptName(u.getDeptName());
        }

        if(formInvoice != null) {
            formInvoiceService.saveOrUpdate(formInvoice);
        }

        return "ok";
    }

    @RequestMapping(value = "/delete", method = RequestMethod.POST)
    @ResponseBody
    public String delete(String id) {
        //formInvoiceService.remove();

        formInvoiceService.removeById(id);


        return "ok";
    }


}

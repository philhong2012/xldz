package com.wyait.manage2.other.controller;


import cn.afterturn.easypoi.entity.vo.TemplateWordConstants;
import cn.afterturn.easypoi.excel.entity.TemplateExportParams;
import cn.afterturn.easypoi.view.EasypoiTemplateWordView;
import cn.afterturn.easypoi.word.entity.params.ExcelListEntity;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.wyait.manage.entity.DataGridVO;
import com.wyait.manage.entity.SearchEntityVO;
import com.wyait.manage.entity.SellingContractVO;
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
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.math.BigDecimal;
import java.text.Bidi;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

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
    public ModelAndView create() {
        ModelAndView mv = new ModelAndView("form/sellingcontract/create");
        return mv;
    }


    @RequestMapping("/download")
    public void download(String id,ModelMap modelMap,HttpServletRequest request, HttpServletResponse response) {
        Map<String, Object> map = new HashMap<String, Object>();

        FormSellingContract formSellingContract = formSellingContractService.getById(id);

        QueryWrapper<SellingContractDetail> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("selling_contract_Id", id);
        List<SellingContractDetail> sellingContractDetails = sellingContractDetailService.list(queryWrapper);
        //map.put("items", new ExcelListEntity(sellingContractDetails, SellingContractDetail.class));
        map.put("contractNo", formSellingContract.getContractNo());
        map.put("signDate",formSellingContract.getSignDate().toString());
        map.put("buyer",formSellingContract.getBuyer());
        map.put("signAddress",formSellingContract.getSignAddress() == null ? "":formSellingContract.getSignAddress());
        map.putAll(com.wyait.common.utils.BeanUtils.objectToMap(formSellingContract));

        List<Map<String, Object>> mapList = new ArrayList<Map<String, Object>>();
        BigDecimal totalPrice = BigDecimal.ZERO;
        String priceUnit = StringUtils.EMPTY;
        if(sellingContractDetails != null) {

            for (SellingContractDetail e : sellingContractDetails) {
                Map<String,Object> m = new HashMap<>();
                m.put("goodsName",e.getGoodsName());
                m.put("goodsUnit", e.getGoodsUnit());
                m.put("quantity",(e.getQuantity() == null? BigDecimal.ZERO : e.getQuantity()).toString() + e.getGoodsUnit());
                m.put("price", e.getPriceUnit() +(e.getPrice() == null? BigDecimal.ZERO : e.getPrice()).toString());
                m.put("priceUnit",e.getPriceUnit());
                if(StringUtils.isNotEmpty(e.getPriceUnit())) {
                    priceUnit = e.getPriceUnit();
                }
                m.put("totalPrice",e.getTotalPrice());
                mapList.add(m);

                totalPrice = totalPrice.add(e.getTotalPrice());

                m.put("totalPrice",e.getPriceUnit() + (e.getTotalPrice() == null ? BigDecimal.ZERO : e.getTotalPrice().toString()));
            }

            map.put("totalPrice", priceUnit + totalPrice.toString());
        }
        map.put("items",mapList);

        modelMap.put("checked"," X ");
        modelMap.put("unchecked","  ");



        modelMap.put(TemplateWordConstants.FILE_NAME, formSellingContract.getContractNo());
        modelMap.put(TemplateWordConstants.MAP_DATA, map);
        modelMap.put(TemplateWordConstants.URL, "word/temp_购货合同.docx");
        //EasypoiTemplateWordView
        EasypoiTemplateWordView.render(modelMap, request, response,
                TemplateWordConstants.EASYPOI_TEMPLATE_WORD_VIEW);
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

        //计算总额
        BigDecimal totalPrice = new BigDecimal(0);
        BigDecimal totalQuantity = BigDecimal.ZERO;
        String priceUnit = StringUtils.EMPTY;
        String quantityUnit = StringUtils.EMPTY;
        if (sellingContractVO.getDetails() != null) {
            for (SellingContractDetail e : sellingContractVO.getDetails()) {
                totalPrice = totalPrice.add(e.getTotalPrice() == null?
                        BigDecimal.ZERO:e.getTotalPrice());

                totalQuantity = totalQuantity.add(e.getQuantity() == null?
                        BigDecimal.ZERO:e.getQuantity());

                priceUnit = e.getPriceUnit();
                quantityUnit = e.getGoodsUnit();

            }
        }

        if(sellingContractVO.getDetails() != null && sellingContractVO.getDetails().size() > 0) {
            for (SellingContractDetail detail : sellingContractVO.getDetails()) {
                detail.setSellingContractId(sellingContractVO.getContract().getId());
            }
        }

        if(formSellingContract != null) {

            if(StringUtils.isNotEmpty(formSellingContract.getId())) {
                formSellingContract.setUpdateUserId(u.getId().toString());
                formSellingContract.setUpdateTime(LocalDateTime.now());
                formSellingContract.setUpdateUserName(u.getUsername());

            } else {
                formSellingContract.setCreateUserId(u.getId().toString());
                formSellingContract.setCreateTime(LocalDateTime.now());
                formSellingContract.setCreateUserName(u.getUsername());
                formSellingContract.setDeptId(u.getDeptId());
                formSellingContract.setDeptName(u.getDeptName());
            }

            formSellingContract.setTotalPrice(totalPrice);
            formSellingContract.setTotalQuantity(totalQuantity);
            formSellingContract.setPriceUnit(priceUnit);
            formSellingContract.setQuantityUnit(quantityUnit);

            formSellingContractService.saveOrUpdate(formSellingContract);
        }

        if(sellingContractVO.getDetails() != null && sellingContractVO.getDetails().size() > 0) {
            for (SellingContractDetail detail : sellingContractVO.getDetails()) {
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
    public DataGridVO<SellingContractDetail> list2(String id) {

           QueryWrapper<SellingContractDetail> wrapper = new QueryWrapper<SellingContractDetail>();
           wrapper.eq("selling_contract_id",id);
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
        footer1.put("price","");
        footer1.put("totalPrice",totalPrice.toString());
       /* Map<String,String> footer2 = new HashMap<>();
        footer2.put("price","价税合计:人民币(大写)");
        footer2.put("totalPrice",NumberUtils.digitUppercase(Double.parseDouble(totalPrice.toString()) ));*/

        footerSection.add(footer1);
        //footerSection.add(footer2);

        detailDataGridVO.setFooter(footerSection);

        return detailDataGridVO;
    }


    @RequestMapping(value = "/delete", method = RequestMethod.POST)
    @ResponseBody
    public String delete(String id) {
        //formInvoiceService.remove();
        QueryWrapper<SellingContractDetail> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("selling_contract_id",id);

        sellingContractDetailService.remove(queryWrapper);

        formSellingContractService.removeById(id);


        return "ok";
    }
}

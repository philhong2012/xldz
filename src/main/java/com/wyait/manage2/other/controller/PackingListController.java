package com.wyait.manage2.other.controller;


import cn.afterturn.easypoi.entity.vo.TemplateWordConstants;
import cn.afterturn.easypoi.view.EasypoiTemplateWordView;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.wyait.common.utils.NumberUtils;
import com.wyait.manage.entity.BuyingContractVO;
import com.wyait.manage.entity.DataGridVO;
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
 * 装箱单 前端控制器
 * </p>
 *
 * @author hongxubing
 * @since 2018-12-27
 */
@RestController
@RequestMapping("packinglist")
public class PackingListController extends BaseController{
    Logger logger = LoggerFactory.getLogger(FormBuyingContractController.class);
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
    /**
     * 生成装箱单
     * @return
     */
    @RequestMapping(value = "/gen", method = RequestMethod.GET)
    public ModelAndView gen(String sellingContractId) {
        ModelAndView mv = new ModelAndView("form/packinglist/create");
        FormSellingContract formSellingContract = formSellingContractService.getById(sellingContractId);
        PackingList packingList  = new PackingList();
        if(formSellingContract != null) {
            packingList.setBuyer(formSellingContract.getBuyer());
            packingList.setSeller(formSellingContract.getSeller());
            packingList.setCode(formSellingContract.getContractNo());
            packingList.setSellingContractId(formSellingContract.getId());
        }

        mv.addObject("model",packingList);
        return mv;
    }
    @RequestMapping("/download")
    public void download(String id, ModelMap modelMap, HttpServletRequest request, HttpServletResponse response) {
        Map<String, Object> map = new HashMap<String, Object>();

        PackingList packingList = packingListService.getById(id);

        QueryWrapper<PackingListDetail> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("packing_list_id", id);
        List<PackingListDetail> packingListDetails = packingListDetailService.list(queryWrapper);
        //map.put("items", new ExcelListEntity(sellingContractDetails, BuyingContractDetail.class));
        map.put("code", packingList.getCode());
        //map.put("signDate",packingList.toString());
        map.put("buyer",packingList.getBuyer() == null ? "":packingList.getBuyer());
        map.put("seller",packingList.getSeller());
        map.put("packingDate",packingList.getPackingDate() == null ? "":packingList.getPackingDate().toString());
        List<Map<String, Object>> mapList = new ArrayList<Map<String, Object>>();
        BigDecimal totalPrice = BigDecimal.ZERO;
        BigDecimal totalGW = BigDecimal.ZERO;
        BigDecimal totalNW = BigDecimal.ZERO;
        BigDecimal totalQT = BigDecimal.ZERO;
        BigDecimal totalPQ = BigDecimal.ZERO;
        BigDecimal totalVe = BigDecimal.ZERO;
        String priceUnit = StringUtils.EMPTY;
        String weightUnit = "KGS";
        String packageUnit = "箱";
        String volumeUnit = "CBM";
        String goodsUnit = StringUtils.EMPTY;
        if(packingListDetails != null) {

            for (PackingListDetail e : packingListDetails) {
                Map<String,Object> m = new HashMap<>();
                m.put("goodsName",e.getGoodsName());
                m.put("goodsUnit", e.getGoodsUnit());
                m.put("quantity", e.getQuantity());
                m.put("packageQuantity",e.getPackageQuantity());
                m.put("grossWeight",e.getGrossWeight());
                m.put("netWeight",e.getNetWeight());
                m.put("price", e.getPrice());
                m.put("priceUnit",e.getPriceUnit());
                m.put("volume",e.getVolume());
                if(StringUtils.isNotEmpty(e.getPriceUnit())) {
                    priceUnit = e.getPriceUnit();
                }
                if(StringUtils.isNotEmpty(e.getGoodsUnit())){
                    goodsUnit = e.getGoodsUnit();
                }
                m.put("totalPrice",e.getTotalPrice());
                mapList.add(m);

                totalPrice = totalPrice.add(e.getTotalPrice()== null ? BigDecimal.ZERO:e.getTotalPrice());
                totalGW = totalGW.add(e.getGrossWeight()== null ? BigDecimal.ZERO: e.getGrossWeight());
                totalNW = totalNW.add(e.getNetWeight()== null ? BigDecimal.ZERO:e.getNetWeight());
                totalPQ = totalPQ.add(e.getPackageQuantity()== null ? BigDecimal.ZERO:e.getPackageQuantity());
                totalQT = totalQT.add(e.getQuantity()== null ? BigDecimal.ZERO:e.getQuantity());
                totalVe = totalVe.add(e.getVolume()== null ? BigDecimal.ZERO:e.getVolume());


                m.put("quantity",e.getQuantity() == null ? "0"+e.getGoodsUnit():e.getQuantity().toString()+e.getGoodsUnit());
                m.put("grossWeight",e.getGrossWeight() == null? "0"+ weightUnit:e.getGrossWeight().toString()+weightUnit);
                m.put("netWeight",e.getNetWeight() == null? "0"+ weightUnit:e.getNetWeight().toString()+weightUnit);
                m.put("packageQuantity",e.getPackageQuantity() == null? "0"+ packageUnit:e.getPackageQuantity().toString()+packageUnit);
                m.put("volume",e.getVolume() == null? "0"+ volumeUnit:e.getVolume().toString()+volumeUnit);
                //m.put("price",e.getPrice()==null ? "0"+priceUnit:e.getPrice().toString() + priceUnit);
            }
            map.put("totalGW",totalGW+weightUnit);
            map.put("totalNW",totalNW+weightUnit);
            map.put("totalPQ",totalPQ+packageUnit);
            map.put("totalQT",totalQT+goodsUnit);
            map.put("totalVE",totalVe+volumeUnit);
           // map.put("capTotalPrice",NumberUtils.digitUppercase(totalPrice.doubleValue()));
            //map.put("totalPrice", priceUnit + totalPrice.toString());
        }
        map.put("items",mapList);

        modelMap.put(TemplateWordConstants.FILE_NAME, packingList.getCode());
        modelMap.put(TemplateWordConstants.MAP_DATA, map);
        modelMap.put(TemplateWordConstants.URL, "word/temp_装箱单.docx");
        //EasypoiTemplateWordView
        EasypoiTemplateWordView.render(modelMap, request, response,
                TemplateWordConstants.EASYPOI_TEMPLATE_WORD_VIEW);
    }
    /**
     * 生成装箱单货物详情
     * @return
     */
    @RequestMapping(value = "/genlist", method = RequestMethod.POST)
    public DataGridVO<PackingListDetail> genlist(String sellingContractId, String id) {
        //ModelAndView mv = new ModelAndView("form/buyingContract/save");
        QueryWrapper<SellingContractDetail> wrapper = new QueryWrapper<>();
        wrapper.eq("selling_contract_id",sellingContractId);
        List<SellingContractDetail> sellingContractDetails = sellingContractDetailService.list(wrapper);
        List<PackingListDetail> packingListDetails = new ArrayList<>();
        if(StringUtils.isEmpty(id)) {

            if (sellingContractDetails != null) {
                for (SellingContractDetail e : sellingContractDetails) {
                    PackingListDetail b = new PackingListDetail();
                    b.setGoodsName(e.getGoodsName());
                    b.setGoodsUnit(e.getGoodsUnit());
                    b.setQuantity(e.getQuantity());
                    packingListDetails.add(b);
                }
            }
        } else {
            QueryWrapper<PackingListDetail> wrapper2 = new QueryWrapper<>();
            wrapper2.eq("packing_list_Id", id);
            packingListDetails = packingListDetailService.list(wrapper2);
        }


        //计算总额

        BigDecimal totalPrice = new BigDecimal(0);
        if (packingListDetails != null) {
            for (PackingListDetail e : packingListDetails) {
                totalPrice = totalPrice.add(e.getTotalPrice() == null?
                        BigDecimal.ZERO:e.getTotalPrice());

            }
        }

        DataGridVO<PackingListDetail>  detailDataGridVO = new DataGridVO<>();
        detailDataGridVO.setRows(packingListDetails);




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
        return "form/packinglist/create";
    }


    /**
     * 编辑采装箱单
     * @return
     */
    @RequestMapping(value = "/edit", method = RequestMethod.GET)
    public ModelAndView edit(String id) {
        ModelAndView mv = new ModelAndView("form/packinglist/create");
        PackingList packingList = packingListService.getById(id);
        mv.addObject("model",packingList);
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

            QueryWrapper<PackingList> queryWrapper = new QueryWrapper<>();
            if(searchEntityVO != null) {
                if(StringUtils.isNotEmpty(searchEntityVO.getCode())) {
                    queryWrapper.like("code",searchEntityVO.getCode());
                }
                if(StringUtils.isNotEmpty(searchEntityVO.getName())) {
                    //queryWrapper.eq("cont")
                }
                if(searchEntityVO.getStartCreateTime() != null) {
                    queryWrapper.gt("packing_date",searchEntityVO.getStartCreateTime());
                }
                if(searchEntityVO.getEndCreateTime() != null) {
                    queryWrapper.lt("packing_date",searchEntityVO.getEndCreateTime());
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

            List<PackingList> packingLists = packingListService.list(queryWrapper);
            // 获取分页查询后的数据
            PageInfo<PackingList> pageInfo = new PageInfo<>(packingLists);
            // 设置获取到的总记录数total：
            pdr.setTotals(Long.valueOf(pageInfo.getTotal()).intValue());

            pdr.setList(packingLists);
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
    public String save(@RequestBody PackingListVO packingListVO) {
        //ModelAndView mv = new ModelAndView("form/buyingContract/save");
        PackingList packingList = packingListVO.getContract();

        User u = (User) SecurityUtils.getSubject().getPrincipal();
        if(StringUtils.isNotEmpty(packingList.getId())) {
            packingList.setUpdateUserId(u.getId().toString());
            packingList.setUpdateTime(LocalDateTime.now());
            packingList.setUpdateUserName(u.getUsername());
        } else {
            packingList.setCreateUserId(u.getId().toString());
            packingList.setCreateTime(LocalDateTime.now());
            packingList.setCreateUserName(u.getUsername());
        }

        if(packingList != null) {
            packingListService.saveOrUpdate(packingList);
        }

        if(packingListVO.getDetails() != null) {
            for(PackingListDetail e : packingListVO.getDetails()) {
                e.setPackingListId(packingList.getId());
            }
            packingListDetailService.saveOrUpdateBatch(packingListVO.getDetails());
        }


        if(packingListVO.getToDeletes() != null) {
            for(PackingListDetail e : packingListVO.getToDeletes()) {
                packingListDetailService.removeById(e.getId());
            }
        }
        return "ok";
    }


    @RequestMapping(value = "/delete", method = RequestMethod.POST)
    @ResponseBody
    public String save(String id) {
        //formInvoiceService.remove();
        QueryWrapper<PackingListDetail> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("packing_list_id",id);

        packingListDetailService.remove(queryWrapper);

        packingListService.removeById(id);


        return "ok";
    }
}

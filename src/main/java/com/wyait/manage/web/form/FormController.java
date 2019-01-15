package com.wyait.manage.web.form;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.wyait.manage.entity.ResponseResult;
import com.wyait.manage.entity.SaleContractDTO;
import com.wyait.manage.entity.UserRoleDTO;
import com.wyait.manage.entity.UserSearchDTO;
import com.wyait.manage.utils.PageDataResult;
import com.wyait.manage.web.user.UserController;
import com.wyait.manage2.other.entity.FormBuyingContract;
import com.wyait.manage2.other.entity.PackingList;
import com.wyait.manage2.other.service.IFormBuyingContractService;
import com.wyait.manage2.other.service.IPackingListService;
import net.sf.jsqlparser.expression.DateTimeLiteralExpression;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * Created by phil hong
 * User: wind
 * Date: 2018/12/10
 * Time: 下午11:45
 * To change this template use File | Settings | File Templates.
 */
@Controller
@RequestMapping("/form")
public class FormController {

    @Autowired
    IFormBuyingContractService buyingContractService;

    @Autowired
    IPackingListService packingListService;


    @ResponseBody
    @RequestMapping("/check")
    public ResponseResult check(String type,String sellingContractId) {
        ResponseResult responseResult = new ResponseResult();
        responseResult.setCode("1");
        QueryWrapper<FormBuyingContract> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("selling_contract_id",sellingContractId);
        QueryWrapper<PackingList> queryWrapper2 = new QueryWrapper<>();
        queryWrapper2.eq("selling_contract_id",sellingContractId);
        List<FormBuyingContract> contracts = buyingContractService.list(queryWrapper);
        List<PackingList> packingLists = packingListService.list(queryWrapper2);
        if("6".equals(type)) {
          //进出库明细，必须先生成"采购合同","装箱单";
            if(contracts == null || contracts.size() == 0) {
                responseResult.setCode("-1");
                responseResult.setMessage("请先生成采购合同");
            }
            if(packingLists == null || packingLists.size() == 0) {
                responseResult.setCode("-1");
                responseResult.setMessage("请先生成装箱单");
            }

        } else if("2".equals(type)) {
            //进出口明细，必须先生成"采购合同"；
            if(contracts == null || contracts.size() == 0) {
                responseResult.setCode("-1");
                responseResult.setMessage("请先生成采购合同");
            }
        }
        return responseResult;
    }

    private static final Logger logger = LoggerFactory
            .getLogger(UserController.class);
    @RequestMapping("/saleContractList")
    public String saleContractList() {
        return "form/sellingcontract/list";
    }


	///form/buyContractList


    @RequestMapping("/buyContractList")
    public String buyContractList()
    {
        return "form/buyingcontract/list";
    }

    @RequestMapping("/exportGoodsList")
    public String exportGoodsList()
    {
        return "form/exportgoodslist/list";
    }

    @RequestMapping("/invoiceList")
    public String invoiceList()
    {
        return "form/invoice/list";
    }

    @RequestMapping("/packingList")
    public String packingList()
    {
        return "form/packinglist/list";
    }
    @RequestMapping("/customsClearanceList")
    public String customsClearanceList()
    {
        return "form/customsclearance/list";
    }


    @RequestMapping("/settlementList")
    public String settlementList()
    {
        return "form/settlementlist/list";
    }


    @RequestMapping("/providerAccountList")
    public String providerAccountList()
    {
        return "form/provideraccount/list";
    }


    @RequestMapping("/foreignExchangeAccountList")
    public String foreignExchangeAccountList()
    {
        return "form/foreignexchangeaccount/list";
    }


    @RequestMapping("/departmentList")
    public String departmentList()
    {
        return "form/department/list";
    }

    @RequestMapping("/formOutboundList")
    public String formOutboundList()
    {
        return "form/formoutbound/list";
    }





    /**
         * 分页查询用户列表
         * @return ok/fail
         */
    @RequestMapping(value = "/getSaleContractList", method = RequestMethod.POST)
    @ResponseBody
    @RequiresPermissions(value="saleContractList")
    public PageDataResult getSaleContractList(@RequestParam("page") Integer page,
                                   @RequestParam("limit") Integer limit, SaleContractDTO saleContractDTO) {
        logger.debug("分页查询售货合同列表！搜索条件：saleContractSearch：" + saleContractDTO + ",page:" + page
                + ",每页记录数量limit:" + limit);
//		ErrorController
        PageDataResult pdr = new PageDataResult();
        try {
            if (null == page) {
                page = 1;
            }
            if (null == limit) {
                limit = 10;
            }


            //PageDataResult pdr = new PageDataResult();
            PageHelper.startPage(page, limit);
            List<SaleContractDTO> saleContractDTOList = new ArrayList<>();

            SaleContractDTO saleContractDTO2 = new SaleContractDTO();
            saleContractDTO2.setId("1");
            saleContractDTO2.setCode("DS180809");
            saleContractDTO2.setName("售货合同");
            saleContractDTO2.setBuyer("AL MAZAYA ALOUTLA COMPANY");
            saleContractDTO2.setSeller("广东新力电子信息进出口有限公司");
            saleContractDTO2.setSignTime(new Timestamp(System.currentTimeMillis()));

            saleContractDTOList.add(saleContractDTO2);

            // 获取分页查询后的数据
            PageInfo<SaleContractDTO> pageInfo = new PageInfo<>(saleContractDTOList);
            // 设置获取到的总记录数total：
            pdr.setTotals(Long.valueOf(pageInfo.getTotal()).intValue());

            pdr.setList(saleContractDTOList);
            logger.debug("用户列表查询=pdr:" + pdr);

        } catch (Exception e) {
            e.printStackTrace();
            logger.error("用户列表查询异常！", e);
        }
        return pdr;
    }
}

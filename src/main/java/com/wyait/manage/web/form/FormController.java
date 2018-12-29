package com.wyait.manage.web.form;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.wyait.manage.entity.SaleContractDTO;
import com.wyait.manage.entity.UserRoleDTO;
import com.wyait.manage.entity.UserSearchDTO;
import com.wyait.manage.utils.PageDataResult;
import com.wyait.manage.web.user.UserController;
import net.sf.jsqlparser.expression.DateTimeLiteralExpression;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
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
    private static final Logger logger = LoggerFactory
            .getLogger(UserController.class);
    @RequestMapping("/saleContractList")
    public String saleContractList() {
        return "form/saleContractList";
    }


	///form/buyContractList


    @RequestMapping("/buyContractList")
    public String buyContractList() {
        return "form/buyingcontract/list";
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

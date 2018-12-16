package com.wyait.manage2.other.controller;


import com.wyait.manage.pojo.Role;
import com.wyait.manage2.other.entity.FormSellingContract;
import com.wyait.manage2.other.service.IFormSellingContractService;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.Date;

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

    Logger logger = LoggerFactory.getLogger(FormSellingContractController.class);
    @RequestMapping("/create")
    public String toUserList() {
        return "form/sellingcontract/create";
    }

    /**
     * 更新角色并授权
     * @return PermTreeDTO
     */
    @RequestMapping(value = "/save", method = RequestMethod.POST)
    @ResponseBody
    public String setRole(FormSellingContract formSellingContract) {
        logger.debug("",formSellingContract);
        formSellingContractService.save(formSellingContract);
        /*logger.debug("更新角色并授权！角色数据role："+role+"，权限数据permIds："+permIds);
        try {
            if(StringUtils.isEmpty(permIds)){
                return "未授权，请您给该角色授权";
            }
            if(null == role){
                return "请您填写完整的角色数据";
            }
            role.setUpdateTime(new Date());
            return authService.updateRole(role,permIds);
        } catch (Exception e) {
            e.printStackTrace();
            logger.error("更新角色并授权！异常！", e);
        }*/
        return "ok";
    }
}

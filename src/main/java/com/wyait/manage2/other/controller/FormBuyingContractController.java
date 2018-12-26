package com.wyait.manage2.other.controller;


import com.wyait.manage2.other.entity.FormBuyingContract;
import com.wyait.manage2.other.entity.FormSellingContract;
import com.wyait.manage2.other.service.IFormBuyingContractService;
import com.wyait.manage2.other.service.IFormSellingContractService;
import com.wyait.manage2.other.service.ISellingContractDetailService;
import com.wyait.manage2.other.service.impl.FormBuyingContractServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;

import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;

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
    @Autowired
    IFormSellingContractService formSellingContractService;
    @Autowired
    ISellingContractDetailService sellingContractDetailService;
    @Autowired
    IFormBuyingContractService formBuyingContractService;
    /**
     * 编辑售货合同
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
}

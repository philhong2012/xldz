package com.wyait.manage2.other.controller;


import com.wyait.manage2.other.entity.CustomsClearance;
import org.springframework.web.bind.annotation.RequestMapping;

import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;

/**
 * <p>
 * 报关单 前端控制器
 * </p>
 *
 * @author hongxubing
 * @since 2019-01-02
 */
@RestController
@RequestMapping("/customsclearance")
public class CustomsClearanceController {

    @RequestMapping("/create")
    public String create() {
        return "form/customsclearance/create";
    }


    @RequestMapping("/gen")
    public ModelAndView gen(String sellingContractId) {
        ModelAndView mv = new ModelAndView("form/customsclearance/create");
        mv.addObject("model",new CustomsClearance());
        return mv;
    }
}

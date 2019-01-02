package com.wyait.manage2.other.controller;


import org.springframework.web.bind.annotation.RequestMapping;

import org.springframework.web.bind.annotation.RestController;

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
}

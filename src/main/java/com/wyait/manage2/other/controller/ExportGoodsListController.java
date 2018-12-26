package com.wyait.manage2.other.controller;


import com.wyait.manage2.other.entity.ExportGoodsListDetail;
import org.springframework.web.bind.annotation.RequestMapping;

import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;

import java.util.ArrayList;
import java.util.List;

/**
 * <p>
 * 出口货物单 前端控制器
 * </p>
 *
 * @author hongxubing
 * @since 2018-12-26
 */
@RestController
@RequestMapping("/exportgoodslist")
public class ExportGoodsListController {
    @RequestMapping("/create")
    public String create() {
        return "form/exportgoodslist/create";
    }

    @RequestMapping("/gen")
    public ModelAndView gen(String id) {
        ModelAndView mv = new ModelAndView("form/exportgoodslist/create");

        return mv;
    }


}

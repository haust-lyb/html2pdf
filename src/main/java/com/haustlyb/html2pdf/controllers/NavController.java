package com.haustlyb.html2pdf.controllers;

import cn.hutool.core.util.StrUtil;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import springfox.documentation.annotations.ApiIgnore;

@Controller
@ApiIgnore
public class NavController {


    @RequestMapping("/index")
    public String index(@RequestParam(value = "page", required = false) String page) {

        if (StrUtil.isBlank(page)) {
            return "index.html";
        }

        return "content/"+page+".html";
    }

    @RequestMapping("/")
    public String emptyurl(){
        return "redirect:/index";
    }
}

package com.zhongruan.web;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class IndexController {

    @GetMapping("/")
    public String index() {
//        int num = 9 / 0;
        return "index";
    }
}

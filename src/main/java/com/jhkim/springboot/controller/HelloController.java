package com.jhkim.springboot.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class HelloController {

    @RequestMapping("/")
    public String index() {
        return "index";
    }

    @RequestMapping("/{num}")
    public String total(@PathVariable int num, Model model) {
        int res = 0;
        for (int i = 1; i <= num; i++) {
            res += i;
        }
        model.addAttribute("msg", "total : " + res);
        return "total";
    }
}

package com.jhkim.springboot.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

@Controller
public class HelloController {

    @RequestMapping("/")
    public String index() {
        return "index";
    }

    @RequestMapping("/{num}")
    public ModelAndView total(@PathVariable int num, ModelAndView modelAndView) {
        int res = 0;
        for (int i = 1; i <= num; i++) {
            res += i;
        }
        modelAndView.addObject("msg", "total : " + res);
        modelAndView.setViewName("total");
        return modelAndView;
    }
}

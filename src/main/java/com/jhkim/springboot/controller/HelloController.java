package com.jhkim.springboot.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

@Controller
public class HelloController {

    @RequestMapping(value = "/", method = RequestMethod.GET)
    public ModelAndView index(ModelAndView modelAndView) {
        modelAndView.setViewName("index");
        modelAndView.addObject("msg", "이름을 적어서 전송해주세요.");
        return modelAndView;
    }

    @RequestMapping(value = "/", method = RequestMethod.POST)
    public ModelAndView send(@RequestParam("name") String name, ModelAndView modelAndView) {
        modelAndView.setViewName("index");
        modelAndView.addObject("msg", "안녕하세요, " + name + "님!");
        modelAndView.addObject("value", name);
        return modelAndView;
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

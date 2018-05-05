package com.jhkim.springboot.controller;

import com.jhkim.springboot.model.User;
import com.jhkim.springboot.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

@Controller
public class HelloController {

    @Autowired
    UserRepository userRepository;

    @RequestMapping(value = "/", method = RequestMethod.GET)
    public ModelAndView index(ModelAndView modelAndView) {
        modelAndView.setViewName("index");
        modelAndView.addObject("msg", "폼을 전송해주세요.");
        return modelAndView;
    }

    @RequestMapping(value = "/", method = RequestMethod.POST)
    public ModelAndView send(
            @RequestParam(value = "name", required = true) String name,
            @RequestParam(value = "check", required = false) boolean check,
            @RequestParam(value = "gender", required = false) String gender,
            @RequestParam(value = "os", required = false) String os,
            @RequestParam(value = "mobile", required = false) String[] mobile,
            ModelAndView modelAndView) {

        String res = "안녕하세요, " + name + "님! ";
        try {
            res += "check : " + check + " / gender : " + gender + " / os : " + os + " / mobile : " + mobile[0];
            for (int i = 1; i < mobile.length; i++) {
                res += ", " + mobile[i];
            }
        } catch (NullPointerException e) {
            res += "null";
        }

        modelAndView.setViewName("index");
        modelAndView.addObject("msg", res);
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

    @RequestMapping("/other")
    public ModelAndView other() {
        return new ModelAndView("redirect:/");
    }

    @RequestMapping("/home")
    public ModelAndView home() {
        return new ModelAndView("forward:/");
    }

    @RequestMapping(value = "/user", method = RequestMethod.GET)
    public ModelAndView userForm(@ModelAttribute("formModel") User user, ModelAndView modelAndView) {
        modelAndView.setViewName("user-form");
        modelAndView.addObject("msg", "this is sample content.");
        Iterable<User> list = userRepository.findAll();
        modelAndView.addObject("datalist", list);
        return modelAndView;
    }

    @RequestMapping(value = "/user", method = RequestMethod.POST)
    @Transactional(readOnly = false)
    public ModelAndView addUser(@ModelAttribute("formModel") User user, ModelAndView modelAndView) {
        userRepository.saveAndFlush(user);
        return new ModelAndView("redirect:/user");
    }
}

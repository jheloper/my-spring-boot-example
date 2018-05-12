package com.jhkim.springboot.controller;

import com.jhkim.springboot.model.User;
import com.jhkim.springboot.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import javax.annotation.PostConstruct;

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
    public ModelAndView addUser(@ModelAttribute("formModel") @Validated User user, BindingResult result, ModelAndView modelAndView) {

        ModelAndView res = null;
        if (!result.hasErrors()) {
            userRepository.saveAndFlush(user);
            res = new ModelAndView("redirect:/user");
        } else {
            modelAndView.setViewName("user-form");
            modelAndView.addObject("msg", "sorry, error is occured...");
            Iterable<User> list = userRepository.findAll();
            modelAndView.addObject("datalist", list);
            res = modelAndView;
        }

        return res;
    }

    @PostConstruct
    public void initUser() {
        User user1 = new User();
        user1.setName("kim");
        user1.setAge(25);
        user1.setMail("kim@test.com");
        user1.setMemo("this is sample data1");
        userRepository.saveAndFlush(user1);

        User user2 = new User();
        user2.setName("lee");
        user2.setAge(30);
        user2.setMail("lee@test.com");
        user2.setMemo("this is sample data2");
        userRepository.saveAndFlush(user2);

        User user3 = new User();
        user3.setName("park");
        user3.setAge(35);
        user3.setMail("park@test.com");
        user3.setMemo("this is sample data3");
        userRepository.saveAndFlush(user3);
    }

    @RequestMapping(value = "/edit/{id}", method = RequestMethod.GET)
    public ModelAndView editUser(@ModelAttribute User user, @PathVariable int id, ModelAndView modelAndView) {
        modelAndView.setViewName("edit-user");
        modelAndView.addObject("title", "Edit user");
        User foundUser = userRepository.findById((long)id);
        modelAndView.addObject("formModel", foundUser);
        return modelAndView;
    }

    @RequestMapping(value = "/edit", method = RequestMethod.POST)
    @Transactional(readOnly = false)
    public ModelAndView update(@ModelAttribute User user, ModelAndView modelAndView) {
        userRepository.saveAndFlush(user);
        return new ModelAndView("redirect:/user");
    }

    @RequestMapping(value = "/delete/{id}", method = RequestMethod.GET)
    public ModelAndView deleteUser(@PathVariable int id, ModelAndView modelAndView) {
        modelAndView.setViewName("delete-user");
        modelAndView.addObject("title", "Delete user");
        User foundUser = userRepository.findById((long)id);
        modelAndView.addObject("formModel", foundUser);
        return modelAndView;
    }

    @RequestMapping(value = "/delete", method = RequestMethod.POST)
    @Transactional(readOnly = false)
    public ModelAndView delete(@RequestParam long id, ModelAndView modelAndView) {
        userRepository.delete(id);
        return new ModelAndView("redirect:/user");
    }
}

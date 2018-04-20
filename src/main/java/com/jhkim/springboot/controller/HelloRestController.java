package com.jhkim.springboot.controller;

import com.jhkim.springboot.model.DataObject;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class HelloRestController {

    @RequestMapping("/{num}")
    public String total(@PathVariable int num) {
        int res = 0;

        for (int i = 1; i <= num; i++) {
            res += i;
        }

        return "total : " + res;
    }

    @RequestMapping("/user/{id}")
    public DataObject getUser(@PathVariable int id) {
        String[] names = {"kim", "lee", "park", "choi", "cho"};
        String[] mails = {"kim@test.com", "lee@test.com", "park@test.com", "choi@test.com", "cho@test.com"};

        return new DataObject(id, names[id], mails[id]);
    }
}

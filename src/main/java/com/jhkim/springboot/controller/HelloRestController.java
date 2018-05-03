package com.jhkim.springboot.controller;

import com.jhkim.springboot.model.DataObject;
import com.jhkim.springboot.model.User;
import com.jhkim.springboot.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class HelloRestController {

    @Autowired
    UserRepository userRepository;

    @RequestMapping("/user/{id}")
    public DataObject getUser(@PathVariable int id) {
        String[] names = {"kim", "lee", "park", "choi", "cho"};
        String[] mails = {"kim@test.com", "lee@test.com", "park@test.com", "choi@test.com", "cho@test.com"};

        return new DataObject(id, names[id], mails[id]);
    }

    @RequestMapping("/users")
    public Iterable<User> getUsers() {
        Iterable<User> list = userRepository.findAll();

        return list;
    }
}

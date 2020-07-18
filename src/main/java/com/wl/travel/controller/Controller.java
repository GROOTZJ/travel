package com.wl.travel.controller;

import com.wl.travel.service.UserService;
import com.wl.travel.vo.UserVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

@RestController("user")
public class Controller {

    @Autowired
    private UserService userService;

    @GetMapping("{id}")
    public UserVo getUser(@PathVariable("id") Long id){
        return userService.get(id);
    }
}

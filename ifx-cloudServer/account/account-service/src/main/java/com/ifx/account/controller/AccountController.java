package com.ifx.account.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author pengpeng
 * @description
 * @date 2023/2/10
 */
@RequestMapping
@RestController
public class AccountController {

    @GetMapping("/test/{args}")
    public String testError(@PathVariable ("args") String args) throws IllegalAccessException {
        if (args .equals("ill")){
            throw new IllegalAccessException();

        } else if (args.equals("exception")) {
            throw new RuntimeException();
        }else {
            return "success";
        }
    }

}

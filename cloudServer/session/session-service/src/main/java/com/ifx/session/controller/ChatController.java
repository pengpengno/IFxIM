package com.ifx.session.controller;

import com.ifx.account.route.accout.AccRoute;
import lombok.extern.slf4j.Slf4j;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author pengpeng
 * @description
 * @date 2023/3/1
 */
@RestController("account")
@RequestMapping(AccRoute.ACCOUNT_ROUTE)
@Slf4j
@Validated
public class ChatController {
}

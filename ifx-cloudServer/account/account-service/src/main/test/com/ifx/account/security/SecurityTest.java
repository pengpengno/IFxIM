package com.ifx.account.security;

import org.junit.Test;
import org.springframework.security.access.prepost.PreAuthorize;

/**
 * @author pengpeng
 * @description
 * @date 2023/2/16
 */
public class SecurityTest {
    @Test
    @PreAuthorize("authenticated")
    public void test(){

    }
}

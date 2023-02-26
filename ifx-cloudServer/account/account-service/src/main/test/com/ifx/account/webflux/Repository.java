package com.ifx.account.webflux;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.r2dbc.core.R2dbcEntityTemplate;

/**
 * @author pengpeng
 * @description
 * @date 2023/2/24
 */
@SpringBootTest
public class Repository {
    
    
    @Autowired
    private R2dbcEntityTemplate r2dbcEntityTemplate;
    
    
    @Test
    public void r2dbcEntity(){
//        new
        
//        r2dbcEntityTemplate.select()
    }
}

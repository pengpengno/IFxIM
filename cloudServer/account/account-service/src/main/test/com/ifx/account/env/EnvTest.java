package com.ifx.account.env;

import org.junit.jupiter.api.Test;

import java.util.Map;
import java.util.Properties;
import java.util.Set;

/**
 * @author pengpeng
 * @description
 * @date 2023/7/3
 */
public class EnvTest {


    @Test
    public void envTest(){

        Properties properties = System.getProperties();
        System.out.println("Java_home-----------"+properties.getProperty("JAVA_HOME"));
        Set<Map.Entry<Object, Object>> entries = properties.entrySet();
        System.out.println(System.getProperties());

    }
}

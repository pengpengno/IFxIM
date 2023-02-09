package com.ifx.client.ann;

import java.lang.annotation.Retention;
import java.lang.annotation.Target;

import static java.lang.annotation.ElementType.*;
import static java.lang.annotation.RetentionPolicy.RUNTIME;

/**
 * 客户端服务代理
 * @author pengpeng
 * @description
 * @date 2023/1/16
 */

@Target({ FIELD})
@Retention(RUNTIME)
public @interface ProxyService {


}

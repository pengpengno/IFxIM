package com.ifx.common.security;

import java.util.concurrent.TimeUnit;

/**
 * @author pengpeng
 * @description
 * @date 2023/3/2
 */
public interface SecurityConstants {


    String JWT_SECRET = "NAMESPACEIFXSECURITYADDMORE";
    String ACCOUNT_JWT_SIGN = "ACCOUNT";


    Integer JWT_LACK_TIME = 1;

    TimeUnit JWT_LACK_TIME_UNIT = TimeUnit.DAYS;

    Integer MAX_JWT = 10000;
}

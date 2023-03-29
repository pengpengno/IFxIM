package com.ifx.account.utils;

import cn.hutool.core.util.RandomUtil;
import cn.hutool.core.util.StrUtil;
import org.apache.commons.codec.digest.DigestUtils;

/**
 *
 * 密码验证工具包
 * @author pengpeng
 * @description
 * @date 2023/1/12
 */
public class PasswordUtils {


    private static final Integer SALT_LENGTH = 8;
    /***
     * 密码验证
     * @param pwd
     * @param salt
     * @param pwdHash
     * @return
     */
    public static boolean verityPassword(String pwd, String salt, String pwdHash) {
        if (pwd == null || StrUtil.isBlank(pwd)){
            return Boolean.FALSE;
        }
        if (StrUtil.isBlank(salt) ){
            return Boolean.TRUE;
        }
        String hashRes = DigestUtils.sha256Hex(pwd + salt);
        return StrUtil.equals(pwdHash,hashRes);
    }

    public static String generatePwdHash(String pwd,String salt){
        return DigestUtils.sha256Hex(pwd + salt);
    }

    public static String generateSalt(){
        return RandomUtil.randomString(SALT_LENGTH);
    }
}

package com.twolz.qiyi.test;

import org.mindrot.jbcrypt.BCrypt;

/**
 * @author liuwei
 * @date 2018-06-03
 */
public class WebMainTest {
    public static void main(String[] args) {
        String salt = BCrypt.gensalt();
        String password = "123456";
        System.out.println(salt);
        System.out.println((BCrypt.hashpw(password , salt)));
    }
}

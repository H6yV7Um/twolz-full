package com.twolz.qiyi.dc.retrofit;


import sun.misc.BASE64Encoder;

import java.util.Random;

/**
 * @author liuwei
 */
public class AuthCodeTools {

    public static String getAuthCode(String authCode,String ueSn) {

        byte[] keyBytes = "www.twolz.com".getBytes(); //24字节的密钥

        if (authCode != null) {

            keyBytes = authCode.getBytes();
        }

        String szSrc = ueSn;

        Random random = new Random();
        while (szSrc.length() < 16) {

            szSrc += String.valueOf(random.nextInt(10));
        }

        byte[] encoded = new byte[0];
        try {
            encoded = Des3.des3EncodeECB(keyBytes, szSrc.getBytes());
        } catch (Exception e) {
            e.printStackTrace();
        }

        return new BASE64Encoder().encode(encoded);
    }
}

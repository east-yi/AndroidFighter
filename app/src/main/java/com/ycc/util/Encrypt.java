package com.ycc.util;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

/**
 * Created by Administrator on 16-11-20.
 */
public class Encrypt {
    private static Encrypt encrypt;
    public static Encrypt getInstance(){
        if (encrypt==null) {
        encrypt=new Encrypt();
        }
        return encrypt;
    };


    public String stringEncrypt( String password, String salt){
        try {
            StringBuffer sb=new StringBuffer();
            //加盐
            password+=salt;
            //1.指定加密类型
            MessageDigest digest = MessageDigest.getInstance("MD5");
            //2.将字符串转换成bayt[]，然后进行随机哈希过程
            byte[] bs=digest.digest(password.getBytes());
            //3.16位转成32位并拼接
            for (byte b:bs) {
                //MD5固定写法
                int i=b & 0xff;
                //int类型转成16进制
                String string=Integer.toHexString(i);
                //给单个位数不是2位的补0
                if(string.length()<2){
                    string = "0" + string;
                }
                sb.append(string);
            }
            //4.拼接字符串返回
            return sb.toString();
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        }
        return "";
    }
}

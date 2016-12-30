package com.jianyujianyu.service;

import org.springframework.stereotype.Service;

import java.math.BigInteger;
import java.security.MessageDigest;

/**
 * Created by aimreant on 12/9/16.
 */
@Service
public class BaseService {
    /**
     * Generate MD5 code
     * @param str
     * @return
     */
    private static String getMD5(String str) throws Exception {

        MessageDigest md = MessageDigest.getInstance("MD5");
        md.update(str.getBytes());
        return new BigInteger(1, md.digest()).toString(16);

    }

    /**
     * Generate hash code by using getMD5()
     * @param str
     * @return
     * @throws Exception
     */
    static String getHashCode(String str) throws Exception{

        String tempStr = null, returnStr = "";

        for(int i=0; i<5; i++) {
            str = str + "Filexplorer";
            tempStr = getMD5(str);
            if(i>2){
                returnStr = returnStr + tempStr;
            }
        }

        return returnStr;
    }

}

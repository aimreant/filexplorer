package com.jianyujianyu.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.math.BigInteger;
import java.security.MessageDigest;

/**
 * Created by aimreant on 11/10/16.
 */
@Controller
public class BaseController {

    final int ERROR = 0;
    final int SUCCESS = 1;
    final int DELETE = 2;
    final int RENAME = 3;

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


    /**
     * Using for simplify the controller
     * @param attr
     * @param returnMsgType
     * @param returnMsg
     */
    static void returnMsg(RedirectAttributes attr, int returnMsgType, String returnMsg) {
        attr.addFlashAttribute("returnMsgType", returnMsgType);
        attr.addFlashAttribute("returnMsg", returnMsg);
    }

    /**
     * Using for simplify the controller
     * @param attr
     * @param requestUrl
     * @param returnMsg
     */
    static void returnConfirmMsg(RedirectAttributes attr, String returnMsg, String requestUrl) {
        attr.addFlashAttribute("returnMsgType", 2);
        attr.addFlashAttribute("returnMsg", returnMsg);
        attr.addFlashAttribute("requestUrl", requestUrl);
    }


    /**
     * Using for simplify the controller
     * @param attr
     * @param requestUrl
     * @param returnMsg
     */
    static void returnRenameMsg(RedirectAttributes attr, String returnMsg, String requestUrl) {
        attr.addFlashAttribute("returnMsgType", 3);
        attr.addFlashAttribute("returnMsg", returnMsg);
        attr.addFlashAttribute("requestUrl", requestUrl);
    }

}

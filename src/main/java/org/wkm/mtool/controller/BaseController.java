/**
 * Create with IntelliJ IDEA
 * Project name : mtool
 * Package name : org.wkm.mtool.controller
 * Author : Wukunmeng
 * User : wkm
 * Date : 16-6-8
 * Time : 上午11:31
 * ---------------------------------
 */
package org.wkm.mtool.controller;

import com.jfinal.core.Controller;
import net.sf.json.JSON;
import net.sf.json.JSONObject;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.BufferedReader;
import java.io.IOException;

/**
 * Create with IntelliJ IDEA
 * Project name : mtool
 * Package name : org.wkm.mtool.controller
 * Author : Wukunmeng
 * User : wkm
 * Date : 16-6-8
 * Time : 上午11:31
 * ---------------------------------
 * To change this template use File | Settings | File and Code Templates.
 */
public abstract class BaseController extends Controller{

    //日志
    protected Logger logger = LoggerFactory.getLogger(this.getClass());

    protected String body = null;

    protected String getBody(){
        if(getRequest().getMethod().equalsIgnoreCase("GET")){
            return body;
        }
        if(body != null) {
            return body;
        }
        try {
            BufferedReader reader = getRequest().getReader();
            StringBuilder body = new StringBuilder();
            String line = null;
            while(!StringUtils.isBlank(line = reader.readLine())){
                body.append(line);
            }
            this.body = body.toString();
        } catch (IOException e){
            logger.error("IOException:" + e.getMessage(),e);
        }
        return  body;
    }

    protected JSONObject ok(){
        return ok(null,null);
    }

    protected JSONObject ok(String message){
        return ok(message,null);
    }

    protected JSONObject ok(JSON data){
        return ok(null,data);
    }

    protected JSONObject ok(String message,JSON data){
        JSONObject ok = new JSONObject();
        ok.put("success",true);
        if(StringUtils.isBlank(message)) {
            ok.put("message", "成功");
        } else {
            ok.put("message", message);
        }
        if(data != null) {
            ok.put("data", data);
        }
        logger.info("response:" + ok.toString(4));
        return ok;
    }

    protected JSONObject fail(String message){
        JSONObject fail = new JSONObject();
        fail.put("success",false);
        if(StringUtils.isBlank(message)) {
            fail.put("message", "失败");
        } else {
            fail.put("message", message);
        }
        logger.info("response:" + fail.toString(4));
        return fail;
    }
}

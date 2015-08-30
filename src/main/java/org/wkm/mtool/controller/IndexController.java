package org.wkm.mtool.controller;

import com.jfinal.core.Controller;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

/**
 * Created with IntelliJ IDEA.
 * User: wkm
 * Date: 15-8-30
 * Time: 上午10:38
 * To change this template use File | Settings | File Templates.
 */
public class IndexController extends Controller {

    private Logger log = LoggerFactory.getLogger(this.getClass());

    public void index(){
        Map<String,String> result = new HashMap<String,String>();
        result.put("requestTime",new Date().toString());
        result.put("name","测试数据库");
        result.put("sex","未知道");
        result.put("location","中华人民共和国");
        log.info(result.toString());
        renderJson(result);
    }

    public void html(){
        render("/html/home.html");
    }

}

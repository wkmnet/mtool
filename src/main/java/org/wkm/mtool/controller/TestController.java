package org.wkm.mtool.controller;

import com.jfinal.core.Controller;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by wkm on 15-8-20.
 */
public class TestController extends Controller{

    public void test(){
        Map<String,String> m = new HashMap<String,String>();
        m.put("a","b");
        renderJson(m);
    }

}
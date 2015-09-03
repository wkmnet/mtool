package org.wkm.mtool.controller;

import com.jfinal.core.Controller;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.*;

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
        render("/html/home.html");
    }

    public void html(){
        render("/html/home.html");
    }

    /**
     * 加载所有菜单
     */
    public void loadMenus(){

        //所有菜单
        List<Map<String,Object>> menus = new ArrayList<Map<String,Object>>();

        //查询所有菜单
        for (int i = 0;i<5;i++){
            Map<String,Object> menu = new HashMap<String, Object>();
            menu.put("menuName","BOSS系统" + i);
            List<Map<String,Object>> childMenus = new ArrayList<Map<String,Object>>();
            for (int j = 0;j < 15;j++){
                Map<String,Object> childMenu = new HashMap<String, Object>();
                childMenu.put("menuName","子菜单" + j);
                childMenu.put("menuUrl","http://www.baidu.com");
                childMenus.add(childMenu);
            }
            menu.put("childMenus",childMenus);
            menus.add(menu);
        }
        log.info("返回数据:" + menus);
        renderJson(menus);
    }

}

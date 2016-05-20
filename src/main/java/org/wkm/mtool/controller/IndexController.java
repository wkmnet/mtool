package org.wkm.mtool.controller;

import com.jfinal.aop.Before;
import com.jfinal.core.Controller;
import net.sf.json.JSONArray;
import net.sf.json.JSONObject;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.wkm.mtool.common.util.CommonUtil;
import org.wkm.mtool.interceptor.LoggerInterceptor;
import org.wkm.mtool.model.ToolMenu;

import java.io.File;
import java.util.*;

/**
 * Created with IntelliJ IDEA.
 * User: wkm
 * Date: 15-8-30
 * Time: 上午10:38
 * To change this template use File | Settings | File Templates.
 */
@Before(LoggerInterceptor.class)
public class IndexController extends Controller {

    private Logger log = LoggerFactory.getLogger(this.getClass());

    public void index(){
        render("/html/index.html");
    }

    public void html(){
        render("/html/index.html");
    }

    public void menus(){
        render("/html/menu.html");
    }

    public void unicode(){
        render("/html/unicode.html");
    }

    /**
     * 加载所有菜单
     */
    public void loadMenus(){

        //所有菜单
        List<Map<String,Object>> menus = new ArrayList<Map<String,Object>>();

        List<ToolMenu> roots = ToolMenu.menu.find("select * from toolMenu where parentId=?","root");
        for(ToolMenu root:roots){
            Map<String,Object> menu = new HashMap<String, Object>();
            menu.put("id",root.getStr("id"));
            menu.put("parentId",root.getStr("parentId"));
            menu.put("menuName",root.getStr("menuName"));
            menu.put("menuLink",root.getStr("menuLink"));
            List<ToolMenu> children = ToolMenu.menu.find("select * from toolMenu where parentId=?",root.getStr("id"));
            List<Map<String,Object>> childMenus = new ArrayList<Map<String,Object>>();
            for (ToolMenu child:children){
                Map<String,Object> childMenu = new HashMap<String, Object>();
                childMenu.put("id",child.getStr("id"));
                childMenu.put("parentId",child.getStr("parentId"));
                childMenu.put("menuName",child.getStr("menuName"));
                childMenu.put("menuLink",child.getStr("menuLink"));
                childMenus.add(childMenu);
            }
            menu.put("childMenus",childMenus);
            menus.add(menu);
        }
        log.info("返回数据:" + JSONArray.fromObject(menus).toString(4));
        renderJson(menus);
    }

    /**
     * 加载所有根结点菜单
     */
    public void loadRootMenus(){
        //所有菜单
        List<Map<String,Object>> menus = new ArrayList<Map<String,Object>>();

        List<ToolMenu> roots = ToolMenu.menu.find("select * from toolMenu where parentId=?","root");

        for(ToolMenu root:roots){
            Map<String,Object> menu = new HashMap<String, Object>();
            menu.put("id",root.getStr("id"));
            menu.put("parentId",root.getStr("parentId"));
            menu.put("menuName",root.getStr("menuName"));
            menu.put("menuLink",root.getStr("menuLink"));
            menus.add(menu);
        }
        log.info("返回数据:" + JSONArray.fromObject(menus).toString(4));
        renderJson(menus);
    }

    /**
     * 加载子菜单信息
     */
    public void loadChildMenu(){

        String itemId = getPara(0);
        if(StringUtils.isBlank(itemId)){
            renderJson(fail("无效参数"));
            return;
        }
        ToolMenu menu = ToolMenu.menu.findById(itemId);
        Map<String,Object> childMenu = new HashMap<String, Object>();
        childMenu.put("id",menu.getStr("id"));
        childMenu.put("parentId",menu.getStr("parentId"));
        childMenu.put("menuName",menu.getStr("menuName"));
        childMenu.put("menuLink",menu.getStr("menuLink"));
        log.info("返回数据:" + JSONObject.fromObject(childMenu).toString(4));
        renderJson(success("成功",childMenu));
    }


    public void saveChildMenu(){
        log.info("id=" + getPara("id"));
        log.info("parentId=" + getPara("parentId"));
        log.info("menuName=" + getPara("menuName"));
        log.info("menuLink=" + getPara("menuLink"));

        if(StringUtils.isBlank(getPara("menuName"))){
            renderJson(fail("菜单不能为空"));
            return;
        }

        ToolMenu menu = new ToolMenu();
        if(StringUtils.isBlank(getPara("id")) && !StringUtils.isBlank(getPara("parentId"))){
            menu.set("id", CommonUtil.createId()).set("parentId",getPara("parentId"));
            menu.set("menuName",getPara("menuName").trim()).set("menuLink",getPara("menuLink"));
        }else if (!StringUtils.isBlank(getPara("id")) && !StringUtils.isBlank(getPara("parentId"))){
            menu = ToolMenu.menu.findById(getPara("id"));
            if("root".equalsIgnoreCase(getPara("parentId"))){
                menu.set("menuName", getPara("menuName").trim());
            } else {
                menu.set("menuName", getPara("menuName").trim()).set("menuLink", getPara("menuLink"));
            }
        } else {
            renderJson(fail("非法请求"));
            return;
        }
        try {
            if(StringUtils.isBlank(getPara("id"))){
                if(!menu.save()){
                    renderJson(fail("操作失败！"));
                    return;
                }
            } else {
                if(!menu.update()){
                    renderJson(fail("操作失败！"));
                    return;
                }
            }
        } catch (RuntimeException e){
            renderJson(fail(e.getMessage()));
            return;
        }

        renderJson(success("操作成功！"));
    }

    public void deleteMenu(){
        String itemId = getPara(0);
        if(StringUtils.isBlank(itemId)){
            renderJson(fail("无效参数"));
            return;
        }
        ToolMenu menu = ToolMenu.menu.findById(itemId);
        if(StringUtils.equalsIgnoreCase(menu.getStr("parentId"),"root")){
            List<ToolMenu> children = ToolMenu.menu.find("select * from toolMenu where parentId=?",menu.getStr("id"));
            for(ToolMenu child:children){
                ToolMenu.menu.deleteById(child.getStr("id"));
            }
            ToolMenu.menu.deleteById(menu.getStr("id"));
        } else {
            ToolMenu.menu.deleteById(menu.getStr("id"));
        }
        renderJson(success("成功"));
    }

    private Map<String,Object> success(String message){
        return success(message,null);
    }

    private Map<String,Object> success(String message,Object data){
        Map<String,Object> result = new HashMap<String, Object>();
        result.put("isSuccess",true);
        if(StringUtils.isBlank(message)){
            result.put("message","成功");
        } else {
            result.put("message",message);
        }
        if(data != null){
            result.put("data",data);
        }
        log.info("response:" + JSONObject.fromObject(result).toString(4));
        return result;
    }

    private Map<String,Object> fail(String message){
        Map<String,Object> result = new HashMap<String, Object>();
        result.put("isSuccess",false);
        if(StringUtils.isBlank(message)){
            result.put("message","失败");
        } else {
            result.put("message",message);
        }
        log.info("response:" + JSONObject.fromObject(result).toString(4));
        return result;
    }

    /**
     * location 检测
     */
    public void location(){

        //经纬度信息
        String location = getPara("location");
        JSONObject result = new JSONObject();
        if(StringUtils.isBlank(location)){
            result.put("isSuccess",false);
            result.put("message", "location:" + location);
            renderJson(result.toString());
            return;
        }

        String[] ls = StringUtils.split(location, ",");
        if (ls.length != 2){
            result.put("isSuccess",false);
            result.put("message", "location:" + location);
            renderJson(result.toString());
            return;
        }

        //经度
        String longitude = ls[0];
        //纬度
        String latitude = ls[1];

        //输出
        log.info("---" + CommonUtil.location(longitude, latitude));
        renderJson(CommonUtil.location(longitude, latitude));

    }

    public void images(){
        String fileName = getPara(0);
        String projectPath = System.getProperty("user.dir");
        File image = new File(new File(projectPath),"src/main/webapp/images");
        File find = new File(image,fileName + ".png");
        log.info("return file:" + find.getAbsolutePath());
        renderFile(find);
    }

}

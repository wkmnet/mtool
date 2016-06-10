/**
 * Create with IntelliJ IDEA
 * Project name : mtool
 * Package name : org.wkm.mtool.service
 * Author : Wukunmeng
 * User : wkm
 * Date : 16-6-9
 * Time : 下午1:07
 * ---------------------------------
 */
package org.wkm.mtool.service;

import net.sf.json.JSONArray;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.wkm.mtool.model.ToolMenu;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Create with IntelliJ IDEA
 * Project name : mtool
 * Package name : org.wkm.mtool.service
 * Author : Wukunmeng
 * User : wkm
 * Date : 16-6-9
 * Time : 下午1:07
 * ---------------------------------
 * To change this template use File | Settings | File and Code Templates.
 */
public class ToolMenuService {

    private Logger logger = LoggerFactory.getLogger(this.getClass());

    public List<Map<String,Object>> loadRootsMenu(){
        List<ToolMenu> roots = ToolMenu.menu.find("select * from toolMenu where parentId=?","root");
        List<Map<String,Object>> menus = beanToMap(roots);
        logger.info("show root:" + JSONArray.fromObject(menus).toString(4));
        return menus;
    }

    public List<Map<String,Object>> loadAllMenus(){
        List<Map<String,Object>> roots = loadRootsMenu();
        for (Map<String,Object> entry:roots){
            entry.put("childMenus",loadMenus(entry.get("id").toString()));
        }
        logger.info("load menu:" + JSONArray.fromObject(roots).toString(4));
        return roots;
    }

    public List<Map<String,Object>> loadMenus(String id){
        List<ToolMenu> children = ToolMenu.menu.find("select * from toolMenu where parentId=?",id);
        List<Map<String,Object>> childMenus = beanToMap(children);
        logger.info("load menus:" + JSONArray.fromObject(childMenus).toString(4));
        return childMenus;
    }

    private List<Map<String,Object>> beanToMap(List<ToolMenu> bean){
        List<Map<String,Object>> maps = new ArrayList<Map<String,Object>>();
        for (ToolMenu child:bean){
            Map<String,Object> childMenu = new HashMap<String, Object>();
            childMenu.put("id",child.getStr("id"));
            childMenu.put("parentId",child.getStr("parentId"));
            childMenu.put("menuName",child.getStr("menuName"));
            childMenu.put("menuLink",child.getStr("menuLink"));
            maps.add(childMenu);
        }
        return maps;
    }

}

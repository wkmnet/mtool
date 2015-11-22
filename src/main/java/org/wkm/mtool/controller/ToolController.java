/**
 * Apache LICENSE-2.0
 * Project name : mtool
 * Package name : org.wkm.mtool.controller
 * Author : Wukunmeng
 * User : wkm
 * Date : 15-11-22
 * Time : 下午1:26
 * 版权所有,侵权必究！
 */
package org.wkm.mtool.controller;

import com.jfinal.aop.Duang;
import com.jfinal.core.Controller;
import org.apache.commons.lang3.StringUtils;
import org.wkm.mtool.service.QRCodeService;

import java.util.HashMap;
import java.util.Map;

/**
 * Create with IntelliJ IDEA
 * Project name : mtool
 * Package name : org.wkm.mtool.controller
 * Author : Wukunmeng
 * User : wkm
 * Date : 15-11-22
 * Time : 下午1:26
 * 版权所有,侵权必究！
 * To change this template use File | Settings | File and Code Templates.
 */
public class ToolController extends Controller{

    public void index(){

        render("/html/tool.html");
    }

    public void image(){
        String resource = getPara("resource");
        Map<String,Object> result = new HashMap<String,Object>();

        if (StringUtils.isBlank(resource)){
            result.put("success",false);
            result.put("message", "输入源不能为空");
            renderJson(result);
            return;
        }

        QRCodeService codeService = Duang.duang(QRCodeService.class);
        String fileName = codeService.createImage(resource);

        result.put("alt",resource);
        result.put("src","/images/" + fileName);
        result.put("success",true);
        result.put("message", "创建成功!");
        renderJson(result);
    }
}

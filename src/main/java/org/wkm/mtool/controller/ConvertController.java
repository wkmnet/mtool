/**
 * Create with IntelliJ IDEA
 * Project name : mtool
 * Package name : org.wkm.mtool.controller
 * Author : Wukunmeng
 * User : wukm
 * Date : 16-5-20
 * Time : 下午4:25
 * -------------------------------------------------
 **/
package org.wkm.mtool.controller;

import com.jfinal.aop.Before;
import com.jfinal.ext.interceptor.Restful;
import net.sf.json.JSONObject;
import org.apache.commons.lang3.StringUtils;
import org.wkm.mtool.common.util.CommonUtil;

import java.io.UnsupportedEncodingException;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Create with IntelliJ IDEA
 * Project name : mtool
 * Package name : org.wkm.mtool.controller
 * Author : Wukunmeng
 * User : wukm
 * Date : 16-5-20
 * Time : 下午4:25
 * ---------------------------------
 * To change this template use File | Settings | File and Code Templates.
 */
@Before(Restful.class)
public class ConvertController extends ToolController {

    public void save(){
        logger.info("request body:" + getBody());
        try {
            JSONObject body = JSONObject.fromObject(getBody());
            String input = body.getString("source");
            if(StringUtils.isBlank(input)){
                renderJson(fail("输入数据不能为空呦!!!!")); return;
            }
            int type = body.getInt("type");
            body.put("target", convert(input,type));
            renderJson(ok(body));
        } catch (Exception e){
            logger.error("Exception:" + e.getMessage(),e);
            renderJson(fail(e.getMessage()));
        }
    }

    private String convert(String source,int type) throws Exception{
        switch (type){
            case 0 :
                return chineseToUnicode(source);
            case 1 :
                return unicodeToChinese(source);
            default:
                return "";
        }
    }

    private String unicodeToChinese(String unicode)throws UnsupportedEncodingException {
        logger.info("unicode:" + unicode);
        Pattern pattern = Pattern.compile("(\\\\u(\\p{XDigit}{4}))");
        Matcher matcher = pattern.matcher(unicode);
        char ch;
        while (matcher.find()) {
            ch = (char) Integer.parseInt(matcher.group(2), 16);
            unicode = unicode.replace(matcher.group(1), ch + "");
        }
        return unicode;
    }

    private String chineseToUnicode(String chinese)throws UnsupportedEncodingException {
        StringBuilder unicode = new StringBuilder();
        for(int i = 0; i < chinese.length(); i++){
            if(CommonUtil.isChinese(chinese.substring(i, i + 1)) && CommonUtil.isChineseByREG(chinese.substring(i, i + 1))) {
                unicode.append(CommonUtil.getUnicode(chinese.substring(i, i + 1)));
            } else {
                unicode.append(chinese.substring(i, i + 1));
            }
        }
        return unicode.toString();
    }

}

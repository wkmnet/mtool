package org.wkm.mtool.common.util;

import java.util.UUID;

/**
 * Created with IntelliJ IDEA.
 * Project name : mtool
 * Package name : org.wkm.mtool.common.util
 * author : Wukunmeng
 * User: wkm
 * Date: 15-8-25
 * Time: 下午4:16
 * 版权所有，侵权必究！
 * To change this template use File | Settings | File Templates.
 */
public class CommonUtil {

    private CommonUtil(){
        //静态方法
    }

    /**
     * UTF-8编码
     */
    public static String CHARSET_UTF_8 = "UTF-8";

    /**
     * ID生成器
     * @return
     */
    public static String createId(){
        return UUID.randomUUID().toString().replace("-","");
    }
}

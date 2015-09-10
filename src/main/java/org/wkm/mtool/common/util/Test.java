/**
 * Apache LICENSE-2.0
 * Project name : mtool
 * Package name : org.wkm.mtool.common.util
 * Author : Wukunmeng
 * User : wkm
 * Date : 15-9-10
 * Time : 下午2:31
 * 版权所有,侵权必究！
 */
package org.wkm.mtool.common.util;

import org.apache.commons.lang3.SystemUtils;

import java.io.File;
import java.io.FileWriter;
import java.text.SimpleDateFormat;
import java.util.Calendar;

/**
 * Create with IntelliJ IDEA
 * Project name : mtool
 * Package name : org.wkm.mtool.common.util
 * Author : Wukunmeng
 * User : wkm
 * Date : 15-9-10
 * Time : 下午2:31
 * 版权所有,侵权必究！
 * To change this template use File | Settings | File and Code Templates.
 */
public class Test {
    public static void main(String [] args)throws Exception{
        FileWriter writer = new FileWriter(new File("/mnt/tool/data.csv"),true);
        Calendar c = Calendar.getInstance();
        c.set(2015,0,1);
        while(c.get(Calendar.YEAR) == 2015){
            SimpleDateFormat format = new SimpleDateFormat("yyyyMMdd");
            writer.write(format.format(c.getTime()) + SystemUtils.LINE_SEPARATOR);
            c.add(Calendar.DAY_OF_MONTH,1);
        }
        writer.close();
    }
}

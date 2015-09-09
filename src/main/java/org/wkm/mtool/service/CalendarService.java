package org.wkm.mtool.service;

import com.jfinal.aop.Before;
import com.jfinal.plugin.activerecord.Db;
import com.jfinal.plugin.activerecord.tx.Tx;
import net.sf.json.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.wkm.mtool.model.HolidayList;
import org.wkm.mtool.model.TradeTime;

/**
 * Create with IntelliJ IDEA
 * Project name : mtool
 * Package name : org.wkm.mtool.service
 * Author : Wukunmeng
 * User : wkm
 * Date : 15-9-9
 * Time : 下午9:07
 * 版权所有,侵权必究！
 * To change this template use File | Settings | File Templates.
 */
public class CalendarService {

    private Logger log = LoggerFactory.getLogger(this.getClass());

    /**
     * 保存节假日信息和对应的交易时间
     * 事物处理
     * @param message
     * @param date
     * @return
     */
    @Before(Tx.class)
    public boolean saveHolidayTime(JSONObject message,String date){
//        try {
            //保存节假日信息
            HolidayList holiday = new HolidayList().set("id",date);
            holiday.set("holidayType",message.getInt(date));
            switch (message.getInt(date)){
                case 0  :
                    holiday.set("holidayDesc","工作日");
                    break;
                case 1  :
                    holiday.set("holidayDesc","休息日");
                    break;
                case 2  :
                    holiday.set("holidayDesc","节假日");
                    break;
                default:
                    holiday.set("holidayDesc","未知信息");
            }
//            holiday.save();
            Db.save("holidayList",holiday.toRecord());

            //保持交易时间信息
            TradeTime tradeTime = new TradeTime();
            tradeTime.set("id",date);
            tradeTime.set("startTime","00:00:00");
            tradeTime.set("endTime","16:50:00");
//            tradeTime.save();
            Db.save("tradeTime",tradeTime.toRecord());


            return true;
//        } catch (Exception e){
//            log.error("保存数据库失败:" + e.getMessage(),e);
//            return false;
//        }

    }

}

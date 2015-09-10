package org.wkm.mtool.service;

import com.jfinal.aop.Before;
import com.jfinal.plugin.activerecord.Db;
import com.jfinal.plugin.activerecord.tx.Tx;
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
    public void saveHolidayTime(){
        TradeTime tradeTime = new TradeTime().set("id","20160101")
        .set("startTime","00:01:02")
        .set("endTime","16:00:00");

        Db.save("tradeTime",tradeTime.toRecord());
        log.info("save:" + tradeTime.toJson());
        HolidayList holidayList = new HolidayList();
        Db.save("", holidayList.toRecord());
        log.info("save:" + holidayList.toJson());
        return;
    }

}

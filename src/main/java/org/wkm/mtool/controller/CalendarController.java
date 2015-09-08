package org.wkm.mtool.controller;

import com.jfinal.core.Controller;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.wkm.mtool.model.HolidayList;
import org.wkm.mtool.model.TradeTime;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created with IntelliJ IDEA.
 * Project name : mtool
 * Package name : org.wkm.mtool.controller
 * author : Wukunmeng
 * User: wkm
 * Date: 15-9-8
 * Time: 下午7:23
 * 版权所有，侵权必究！
 * To change this template use File | Settings | File Templates.
 */
public class CalendarController extends Controller {

    private Logger log = LoggerFactory.getLogger(this.getClass());

    /**
     * 计算今天是否为节假日
     */
    public void  calendar(){
        Map<String,Object> result = new HashMap<String,Object>();
        String date = getPara(0);
        log.info("请求日期为:" + date);
        try {
            HolidayList holiday = HolidayList.holiday.findById(date);
            if(holiday == null || StringUtils.isBlank(holiday.getStr("id"))){
                result.put("isHoliday",false);
            } else {
                result.put("isHoliday",true);
            }

            result.put("startTime","00:00:00");
            result.put("endTime","16:50:00");
            List<TradeTime> trades = TradeTime.tradeTime.find("select * from tradeTime where enable=1");
            if(trades != null){
                if(trades.size() == 1){
                    result.put("startTime",trades.get(0).getStr("startTime"));
                    result.put("endTime",trades.get(0).getStr("endTime"));
                }
            }
        } catch (Exception e){
            log.info("异常信息:" + e.getMessage(),e);
            result.put("isHoliday",false);
            result.put("startTime","00:00:00");
            result.put("endTime","16:50:00");
        }finally {
            log.info("返回结果:" + result);
            renderJson(result);
        }

    }
}

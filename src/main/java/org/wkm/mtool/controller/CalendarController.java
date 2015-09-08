package org.wkm.mtool.controller;

import com.jfinal.core.Controller;
import net.sf.json.JSONObject;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.wkm.mtool.common.util.CommonUtil;
import org.wkm.mtool.model.HolidayList;
import org.wkm.mtool.model.TradeTime;

import java.text.SimpleDateFormat;
import java.util.Calendar;
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
        if(StringUtils.isBlank(date)){
            result.put("isHoliday",true);
            result.put("message","查不到:" + date + ";按照节假日处理!");
            result.put("startTime","00:00:00");
            result.put("endTime","16:50:00");
            log.info("返回结果:" + result);
            renderJson(result);
        }
        try {
            HolidayList holiday = HolidayList.holiday.findById(date);
            if(holiday == null || StringUtils.isBlank(holiday.getStr("id"))){
                //查询不到,则按照节假日处理,并说明原因
                result.put("isHoliday",true);
                result.put("message","查不到:" + date + ";按照节假日处理!");
            } else {
                switch (holiday.getInt("holidayType")){
                    case 0  :
                        result.put("isHoliday",false);
                        result.put("message",date + ":是" + holiday.getStr("holidayDesc"));
                        break;
                    case 1  :
                        result.put("isHoliday",true);
                        result.put("message",date + ":是" + holiday.getStr("holidayDesc"));
                        break;
                    case 2  :
                        result.put("isHoliday",true);
                        result.put("message",date + ":是" + holiday.getStr("holidayDesc"));
                        break;
                    default:
                        result.put("isHoliday",true);
                        result.put("message",date + "的类型不确定:" + holiday.getInt("holidayType"));
                }
            }
            result.put("startTime","00:00:00");
            result.put("endTime","16:50:00");
            TradeTime trades = TradeTime.tradeTime.findById(date);
            if(trades != null){
                result.put("startTime", trades.getStr("startTime"));
                result.put("endTime", trades.getStr("endTime"));
            }
        } catch (Exception e){
            log.info("异常信息:" + e.getMessage(),e);
            result.put("isHoliday",true);
            result.put("message",e.getMessage());
            result.put("startTime","00:00:00");
            result.put("endTime","16:50:00");
        }finally {
            log.info("返回结果:" + result);
            renderJson(result);
        }

    }

    /**
     * 同步节假日数据
     * 资源网:http://www.easybots.cn/api/holiday.php
     * 请求方式:http://www.easybots.cn/api/holiday.php?d=20130101
     * 返回数据:工作日对应结果为 0,休息日对应结果为 1,节假日对应的结果为,2
     */
    public void loadHoliday(){
        SimpleDateFormat format = new SimpleDateFormat("yyyyMMdd");
        Calendar c = Calendar.getInstance();
        int year = c.get(Calendar.YEAR);
        c.set(year,0,1);
        while (year == c.get(Calendar.YEAR)){
            String date = format.format(c.getTime());
            log.error("处理日期:" + date);
            JSONObject result = JSONObject.fromObject(CommonUtil.holiday(date));
            if(result.getBoolean("isSuccess")){
                log.info(result.toString());
                JSONObject message = result.getJSONObject("message");
                if(StringUtils.isBlank(message.getString(date))){
                    log.error(result.toString());
                } else {
                    saveHolidayTime(message,date);
                }
            } else {
                log.error(result.toString());
            }
            c.add(Calendar.DAY_OF_MONTH,1);
        }
        Map<String,Object> result = new HashMap<String,Object>();
        result.put("isSuccess",true);
        result.put("message","同步数据成功!");
        renderJson(result);
    }


    /**
     * 保存节假日信息和对应的交易时间
     * @param message
     * @param date
     * @return
     */
    private boolean saveHolidayTime(JSONObject message,String date){
        try {
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
            holiday.save();

            //保持交易时间信息
            TradeTime tradeTime = new TradeTime();
            tradeTime.set("id",date);
            tradeTime.set("startTime","00:00:00");
            tradeTime.set("endTime","16:50:00");
            tradeTime.save();

            return true;
        } catch (Exception e){
            log.error("保存数据库失败:" + e.getMessage(),e);
            return false;
        }

    }
}

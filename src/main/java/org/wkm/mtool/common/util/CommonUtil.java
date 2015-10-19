package org.wkm.mtool.common.util;

import net.sf.json.JSONObject;
import org.apache.http.HttpEntity;
import org.apache.http.HttpStatus;
import org.apache.http.StatusLine;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;

import java.io.IOException;
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

    /**
     * 调用第三方接口同步节假日信息
     * @param date 当前日期
     * @return 校验结果
     */
    public static String holiday(String date){
        CloseableHttpClient httpClient = HttpClients.createDefault();
        JSONObject result = new JSONObject();
        HttpGet get = new HttpGet("http://www.easybots.cn/api/holiday.php?d=" + date);
        try{
            CloseableHttpResponse response = httpClient.execute(get);
            StatusLine status = response.getStatusLine();
            int code = status.getStatusCode();
            switch (code){
                case    HttpStatus.SC_OK    :
                    HttpEntity entity = response.getEntity();
                    result.put("isSuccess",true);
                    result.put("message",JSONObject.fromObject(EntityUtils.toString(entity)));
                    return result.toString();
                default:
                    HttpEntity errorEntity = response.getEntity();
                    result.put("isSuccess",false);
                    result.put("message",EntityUtils.toString(errorEntity));
            }
        } catch (Exception e){
            result.put("isSuccess",false);
            result.put("message",e.getMessage());
        } finally {
            try {
                get.releaseConnection();
                httpClient.close();
            } catch (IOException e){
                result.put("isSuccess",false);
                result.put("message",e.getMessage());
            }
            return result.toString();
        }

    }

    public static String location(String longitude,String latitude){
        CloseableHttpClient httpClient = HttpClients.createDefault();
        JSONObject result = new JSONObject();
        HttpGet get = new HttpGet(ConstantUtil.GAODE_URL +
                "?key=9e26589f641c31df80e5288b4ee554f1&location=" + longitude + "," + latitude);
        try{
            CloseableHttpResponse response = httpClient.execute(get);
            StatusLine status = response.getStatusLine();
            int code = status.getStatusCode();
            switch (code){
                case    HttpStatus.SC_OK    :
                    HttpEntity entity = response.getEntity();
                    result.put("isSuccess",true);
                    result.put("message",JSONObject.fromObject(EntityUtils.toString(entity)));
                    return result.toString();
                default:
                    HttpEntity errorEntity = response.getEntity();
                    result.put("isSuccess",false);
                    result.put("message",EntityUtils.toString(errorEntity));
            }
        } catch (Exception e){
            result.put("isSuccess",false);
            result.put("message",e.getMessage());
        } finally {
            try {
                get.releaseConnection();
                httpClient.close();
            } catch (IOException e){
                result.put("isSuccess",false);
                result.put("message",e.getMessage());
            }
            return result.toString();
        }
    }
}

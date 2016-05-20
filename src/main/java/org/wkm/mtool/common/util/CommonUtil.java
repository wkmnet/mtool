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
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.util.UUID;
import java.util.regex.Pattern;

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

    //日志输出
    private static Logger LOG = LoggerFactory.getLogger(CommonUtil.class);

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
        StringBuilder ps = new StringBuilder();
        ps.append("&location=" + longitude + "," + latitude);
        ps.append("&output=json");
        ps.append("&extensions=all");
        LOG.info(ConstantUtil.GAODE_URL +
                "?key=" + ConstantUtil.GAODE_KEY + ps.toString());
        HttpGet get = new HttpGet(ConstantUtil.GAODE_URL +
                "?key=" + ConstantUtil.GAODE_KEY + ps.toString());
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

    public static String getUnicode(String chinese) throws UnsupportedEncodingException{
        StringBuffer out = new StringBuffer("");
        byte[] bytes = chinese.getBytes("Unicode");
        for (int i = 0; i < bytes.length - 1; i += 2) {
            out.append("\\u");
            String str = Integer.toHexString(bytes[i + 1] & 0xff);
            for (int j = str.length(); j < 2; j++) {
                out.append("0");
            }
            String str1 = Integer.toHexString(bytes[i] & 0xff);
            out.append(str1);
            out.append(str);
        }
        return out.toString();
    }

    // 根据Unicode编码完美的判断中文汉字和符号
    public static boolean isChinese(char c) {
        Character.UnicodeBlock ub = Character.UnicodeBlock.of(c);
        if (ub == Character.UnicodeBlock.CJK_UNIFIED_IDEOGRAPHS
                || ub == Character.UnicodeBlock.CJK_COMPATIBILITY_IDEOGRAPHS
                || ub == Character.UnicodeBlock.CJK_UNIFIED_IDEOGRAPHS_EXTENSION_A
                || ub == Character.UnicodeBlock.CJK_UNIFIED_IDEOGRAPHS_EXTENSION_B
                || ub == Character.UnicodeBlock.CJK_SYMBOLS_AND_PUNCTUATION
                || ub == Character.UnicodeBlock.HALFWIDTH_AND_FULLWIDTH_FORMS
                || ub == Character.UnicodeBlock.GENERAL_PUNCTUATION) {
            return true;
        }
        return false;
    }

    // 完整的判断中文汉字和符号
    public static boolean isChinese(String strName) {
        char[] ch = strName.toCharArray();
        for (int i = 0; i < ch.length; i++) {
            char c = ch[i];
            if (isChinese(c)) {
                return true;
            }
        }
        return false;
    }

    // 只能判断部分CJK字符（CJK统一汉字）
    public static boolean isChineseByREG(String str) {
        if (str == null) {
            return false;
        }
        Pattern pattern = Pattern.compile("[\\u4E00-\\u9FBF]+");
        return pattern.matcher(str.trim()).find();
    }
}

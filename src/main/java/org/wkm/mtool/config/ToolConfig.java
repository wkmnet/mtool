package org.wkm.mtool.config;

import com.jfinal.config.*;
import com.jfinal.core.Controller;
import com.jfinal.ext.handler.ContextPathHandler;
import com.jfinal.kit.Prop;
import com.jfinal.kit.PropKit;
import com.jfinal.plugin.activerecord.ActiveRecordPlugin;
import com.jfinal.plugin.activerecord.dialect.MysqlDialect;
import com.jfinal.plugin.c3p0.C3p0Plugin;
import com.jfinal.render.ViewType;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.wkm.mtool.common.util.CommonUtil;
import org.wkm.mtool.controller.*;
import org.wkm.mtool.interceptor.LoggerInterceptor;
import org.wkm.mtool.model.HolidayList;
import org.wkm.mtool.model.ToolMenu;
import org.wkm.mtool.model.TradeTime;

import java.util.Map;

/**
 * Created with IntelliJ IDEA.
 * Project name : mtool
 * Package name : org.wkm.mtool.config
 * author : Wukunmeng
 * User: wkm
 * Date: 15-8-25
 * Time: 下午3:51
 * 版权所有，侵权必究！
 * To change this template use File | Settings | File Templates.
 */
public class ToolConfig extends JFinalConfig{

    private Logger log = LoggerFactory.getLogger(this.getClass());

    @Override
    public void configConstant(Constants me) {
        //To change body of implemented methods use File | Settings | File Templates.

        prop = PropKit.use("system.properties");

        //开发模式
        if(prop.getBoolean("open.dev.model")) {
            me.setDevMode(true);
        } else {
            me.setDevMode(false);
        }
        me.setEncoding(CommonUtil.CHARSET_UTF_8);
        me.setViewType(ViewType.JSP);
        me.setError401View("/html/index.html");
        me.setError403View("/html/index.html");
        me.setError404View("/html/index.html");
        me.setError500View("/html/index.html");
        log.info("开发模式:" + me.getDevMode());
        log.info("编码:" + me.getEncoding());
    }

    @Override
    public void configRoute(Routes me) {
        //To change body of implemented methods use File | Settings | File Templates.

        //路由设置
        me.add("/", IndexController.class);
        me.add("/api/menu",MenuController.class);
//        me.add("/tool", CalendarController.class);
//        me.add("/tools", ToolController.class);
//        me.add("/convert", ConvertController.class);
        for(Map.Entry<String, Class<? extends Controller>> entry : me.getEntrySet()){
            log.info("key=" + entry.getKey() + ",class=" + entry.getValue().getName());
        }

    }

    @Override
    public void configPlugin(Plugins me) {
        //To change body of implemented methods use File | Settings | File Templates.
        if(prop.getBoolean("open.database")){
            log.info("start init database...");
            initDatabase(me);
            log.info("end init database.");
        }
    }

    private void initDatabase(Plugins me){
        C3p0Plugin mysqlDatasource = new C3p0Plugin(loadPropertyFile("c3p0.properties"));
        me.add(mysqlDatasource);
        ActiveRecordPlugin mysqlPlugin = new ActiveRecordPlugin(mysqlDatasource);
        me.add(mysqlPlugin);
        mysqlPlugin.setDevMode(true); //是否开启开发模式
        mysqlPlugin.setDialect(new MysqlDialect());
        mysqlPlugin.setTransactionLevel(8);
        mysqlPlugin.setShowSql(true);
        mysqlPlugin.addMapping("toolMenu","id", ToolMenu.class);
        mysqlPlugin.addMapping("holidayList","id", HolidayList.class);
        mysqlPlugin.addMapping("tradeTime","id", TradeTime.class);
    }

    @Override
    public void configInterceptor(Interceptors me) {
        //To change body of implemented methods use File | Settings | File Templates.
        me.add(new LoggerInterceptor());
    }

    @Override
    public void configHandler(Handlers me) {
        //To change body of implemented methods use File | Settings | File Templates.
//        me.add(new ContextPathHandler("cxt"));
    }
}

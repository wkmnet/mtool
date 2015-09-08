package org.wkm.mtool.config;

import com.jfinal.config.*;
import com.jfinal.ext.handler.ContextPathHandler;
import com.jfinal.plugin.activerecord.ActiveRecordPlugin;
import com.jfinal.plugin.activerecord.dialect.MysqlDialect;
import com.jfinal.plugin.c3p0.C3p0Plugin;
import com.jfinal.render.ViewType;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.wkm.mtool.common.util.CommonUtil;
import org.wkm.mtool.controller.CalendarController;
import org.wkm.mtool.controller.IndexController;
import org.wkm.mtool.model.HolidayList;
import org.wkm.mtool.model.ToolMenu;
import org.wkm.mtool.model.TradeTime;

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

        //开发模式
        me.setDevMode(true);
        me.setEncoding(CommonUtil.CHARSET_UTF_8);
        me.setViewType(ViewType.JSP);

        log.info("开发模式:" + me.getDevMode());
        log.info("编码:" + me.getEncoding());
    }

    @Override
    public void configRoute(Routes me) {
        //To change body of implemented methods use File | Settings | File Templates.

        //路由设置
        me.add("/", IndexController.class);
        me.add("/tool", CalendarController.class);
    }

    @Override
    public void configPlugin(Plugins me) {
        //To change body of implemented methods use File | Settings | File Templates.
//        String jdbcUrl = "jdbc:mysql://localhost:3306/oracle?useUnicode=true&characterEncoding=UTF-8";
//        String userName = "root";
//        String password = "root";
//        String driverClass = "com.mysql.jdbc.Driver";

        C3p0Plugin mysqlDatasource = new C3p0Plugin(loadPropertyFile("c3p0.properties"));
        me.add(mysqlDatasource);
        ActiveRecordPlugin mysqlPlugin = new ActiveRecordPlugin(mysqlDatasource);
        me.add(mysqlPlugin);
//        mysqlPlugin.setDevMode(true);
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
    }

    @Override
    public void configHandler(Handlers me) {
        //To change body of implemented methods use File | Settings | File Templates.
        me.add(new ContextPathHandler("cxt"));
    }
}

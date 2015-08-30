package org.wkm.mtool.config;

import com.jfinal.config.*;
import com.jfinal.render.ViewType;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.wkm.mtool.common.util.CommonUtil;
import org.wkm.mtool.controller.IndexController;

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
    }

    @Override
    public void configPlugin(Plugins me) {
        //To change body of implemented methods use File | Settings | File Templates.
    }

    @Override
    public void configInterceptor(Interceptors me) {
        //To change body of implemented methods use File | Settings | File Templates.
    }

    @Override
    public void configHandler(Handlers me) {
        //To change body of implemented methods use File | Settings | File Templates.
    }
}

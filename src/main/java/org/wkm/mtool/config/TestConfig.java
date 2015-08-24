package org.wkm.mtool.config;

import com.jfinal.config.*;
import com.jfinal.render.ViewType;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.wkm.mtool.controller.TestController;

/**
 * Created by wkm on 15-8-20.
 */
public class TestConfig extends JFinalConfig{

    private static Logger logger = LoggerFactory.getLogger(TestConfig.class);


    @Override
    public void configConstant(Constants me) {
        logger.info("init configConstant");
        me.setDevMode(true);
        me.setEncoding("UTF-8");
        me.setViewType(ViewType.OTHER);
    }

    @Override
    public void configRoute(Routes me) {
        logger.info("init configRoute");
        me.add("/", TestController.class);
    }

    @Override
    public void configPlugin(Plugins me) {

    }

    @Override
    public void configInterceptor(Interceptors me) {

    }

    @Override
    public void configHandler(Handlers me) {

    }
}

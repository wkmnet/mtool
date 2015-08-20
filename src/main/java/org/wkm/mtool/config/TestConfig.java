package org.wkm.mtool.config;

import com.jfinal.config.*;
import com.jfinal.render.ViewType;
import org.wkm.mtool.controller.TestController;

/**
 * Created by wkm on 15-8-20.
 */
public class TestConfig extends JFinalConfig{


    @Override
    public void configConstant(Constants me) {
        me.setDevMode(true);
        me.setEncoding("UTF-8");
        me.setViewType(ViewType.OTHER);
    }

    @Override
    public void configRoute(Routes me) {
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

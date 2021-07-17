package pers.wtk.listener;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;
import javax.servlet.annotation.WebListener;

/**
 * @author 寒洲
 * @description Tomcat监听
 * @date 2020/8/21
 */
@WebListener
public class TomcatListener implements ServletContextListener {

    private Logger logger = LoggerFactory.getLogger("simpleAsyncLogger");

    @Override
    public void contextDestroyed(ServletContextEvent arg0) {
        logger.info("tomcat关闭......");
    }

    @Override
    public void contextInitialized(ServletContextEvent arg0) {
        logger.info("tomcat初始化......");

    }

}


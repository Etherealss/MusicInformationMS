package pers.wtk.listener;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import pers.wtk.common.enums.AppAttribute;

import javax.servlet.annotation.WebListener;
import javax.servlet.http.HttpSession;
import javax.servlet.http.HttpSessionEvent;
import javax.servlet.http.HttpSessionListener;

/**
 * @author 寒洲
 * @description 判断用户在线状况的监听器
 * @date 2020/8/21
 */
@WebListener
public class UserStateListener implements HttpSessionListener {

	private Logger logger = LoggerFactory.getLogger("root");

	@Override
	public void sessionCreated(HttpSessionEvent se) {
		logger.info("用户访问创建session");
	}

	@Override
	public void sessionDestroyed(HttpSessionEvent se) {
		//用户session连接断开，异常退出
		HttpSession session = se.getSession();
		Object userId = session.getAttribute(AppAttribute.LOGGED);
		logger.info("StateListener 用户session连接断开 / 用户退出 : " + userId);
		// 出去存储在ServletContext中的SESSIONID，避免登录时冲突
		session.getServletContext().removeAttribute(String.valueOf(userId));
	}
}

package com.github.cjm0000000.mmt.web.global;

import java.io.File;

import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

/**
 * 监听容器生命周期
 * @author lemon
 * @version 1.0
 *
 */
public class MMTListener implements ServletContextListener {
	private static Log logger = LogFactory.getLog(MMTListener.class);
	@Override
	public void contextDestroyed(ServletContextEvent arg0) {
		logger.info("MMTListener destroyed successfully.");
	}

	@Override
	public void contextInitialized(ServletContextEvent arg0) {
		String realPath = arg0.getServletContext().getContextPath();
		if(realPath.lastIndexOf("/") == 0)
			realPath = realPath + "/";
		MMT.setContextRoot(realPath);
		MMT.setUploadFileRoot(arg0.getServletContext().getRealPath("/")
				+ File.separator + "data" + File.separator);
		logger.info("[File upload path=" + MMT.getUploadFileRoot() + "]");
		logger.info("[Context=" + realPath + "] MMTListener initialized successfully!");
	}

}

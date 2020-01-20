package org.dmontagnese.changeMaker.util;

import org.apache.logging.log4j.LogManager;

/**
 * Logging is to the 'catalina.out' log with entries labeled 'ChangeMaker'
 */
import org.apache.logging.log4j.Logger;

/**
 * Logs to the catalina.out file in the Tomcat installation logs directory
 */
public class ServerLog {

	private static final String LOG_NAME = "ChangeMaker";

	public static final Logger serverLog;

	static {
		serverLog = LogManager.getLogger(LOG_NAME);
	}

}

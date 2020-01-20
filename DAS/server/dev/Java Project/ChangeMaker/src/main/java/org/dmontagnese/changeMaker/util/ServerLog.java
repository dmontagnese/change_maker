
package org.dmontagnese.changeMaker.util;

import org.apache.logging.log4j.LogManager;

/**
 * Logging is to the 'catalina.out' log with entries labeled 'ChangeMaker'
 */
import org.apache.logging.log4j.Logger;

public class ServerLog {

	private static final String LOG_NAME = "ChangeMaker";

	public static final Logger serverLog;

	static {
		serverLog = LogManager.getLogger(LOG_NAME);
	}

}

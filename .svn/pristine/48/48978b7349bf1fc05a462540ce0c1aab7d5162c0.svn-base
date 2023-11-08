package egovframework.lab.dataaccess.common;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.egovframe.rte.fdl.cmmn.exception.handler.ExceptionHandler;

public class JdbcLoggingExcepHndlr implements ExceptionHandler {

	private static final Logger LOGGER = LoggerFactory.getLogger(JdbcLoggingExcepHndlr.class);

    public void occur(Exception ex, String packageName) {
    	LOGGER.error(ex.getMessage());
    }

}

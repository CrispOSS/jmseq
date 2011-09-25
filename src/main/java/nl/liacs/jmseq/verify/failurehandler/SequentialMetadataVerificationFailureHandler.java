/*
 * Created on May 28, 2010 - 2:17:40 PM
 */
package nl.liacs.jmseq.verify.failurehandler;

import javax.management.RuntimeErrorException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import nl.liacs.jmseq.annotation.SequencedMethod;
import nl.liacs.jmseq.execution.Execution;

/**
 * 
 * 
 * @author Behrooz Nobakht [behrooz dot nobakht at gmail dot com]
 */
public class SequentialMetadataVerificationFailureHandler implements VerificationFailureHandler {
	
	protected final Logger logger = LoggerFactory.getLogger(getClass());

	public void handleVerificationFailure(Execution execution, SequencedMethod annotation) {
		Class<?> handlerClass = annotation.verificationFailureHandler();
		if (null == handlerClass) {
			logger.warn("No handler class for verification failure is specified.");
			throw new RuntimeErrorException(new Error("Execution failed."));
		}
		if (!VerificationFailureHandler.class.isAssignableFrom(handlerClass)) {
			logger.warn("No compatible " + VerificationFailureHandler.class + " handler is provided. " +
					"Skipping the failure handling of " + execution);
			return;
		}
		try {
			VerificationFailureHandler handler = (VerificationFailureHandler) handlerClass.newInstance();
			handler.handleFailedExecution(execution);
		} catch (InstantiationException e) {
			logger.error("Failed to instantiate verification handler instance from: " + handlerClass);
		} catch (IllegalAccessException e) {
			logger.error("Failed to instantiate verification handler instance from: " + handlerClass);
		}
	}

	@Override
	public final void handleFailedExecution(Execution<?> execution) {
	}

}

/*
 * Created on May 28, 2010 - 2:37:25 PM
 */
package nl.liacs.jmseq.sample;

import nl.liacs.jmseq.execution.Execution;
import nl.liacs.jmseq.verify.failurehandler.VerificationFailureHandler;

/**
 * 
 * 
 * @author Behrooz Nobakht [behrooz dot nobakht at gmail dot com]
 */
public class ApplesVerificationFailureHandler implements VerificationFailureHandler {

	@Override
	public void handleFailedExecution(Execution<?> execution) {
		System.err.println("OOOPS! " + execution);
	}

}

/*
 * Created on May 28, 2010 - 2:09:36 PM
 */
package nl.liacs.jmseq.verify.failurehandler;

import nl.liacs.jmseq.execution.Execution;

/**
 * An interface used to plug in the custom code that is to be run when the
 * verification of a method is failed.
 * 
 * @author Behrooz Nobakht [behrooz dot nobakht at gmail dot com]
 */
public interface VerificationFailureHandler {

	/**
	 * It is expected to handle the verification failure in this method. It is
	 * called right after a verification failure has happened.
	 * 
	 * @param execution
	 *            The {@link Execution}instance that holds the data of the
	 *            object.
	 */
	void handleFailedExecution(Execution<?> execution);

}

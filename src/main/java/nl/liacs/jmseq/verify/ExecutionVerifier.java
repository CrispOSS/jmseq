/*
 * Created on Apr 13, 2010 - 6:12:17 PM
 */
package nl.liacs.jmseq.verify;

import nl.liacs.jmseq.annotation.SequentialExecutionMetaData;
import nl.liacs.jmseq.execution.Execution;
import nl.liacs.jmseq.execution.ExecutionUtils;
import nl.liacs.jmseq.execution.MethodEntryExecution;

/**
 * 
 * 
 * @author Behrooz Nobakht [behrooz dot nobakht at gmail dot com]
 */
public class ExecutionVerifier {

	private SequentialExecutionMetaData sequentialExecutionMetaData;

	private CallExpressionStateMachineFactory factory = CallExpressionStateMachineFactory.getInstance();
	private CallExpressionStateMachine activeCallExpressionStateMachine;

	public ExecutionVerifier(SequentialExecutionMetaData metdata) {
		this.sequentialExecutionMetaData = metdata;
	}

	public void verfiyExecution(Execution<?> execution) {
		if (null == execution) {
			return;
		}
		if (null != activeCallExpressionStateMachine) {
			if (activeCallExpressionStateMachine.isRunning()) {
				activeCallExpressionStateMachine.isValidNextExecution(execution);
				return;
			}
			// null != activeCallExpressionStateMachine && !isRunning()
			// ==>
			// create a new instance for a new sequence
		}
		// no active state machine
		if (!sequentialExecutionMetaData.contains(execution)) {
			return;
		}
		if (ExecutionUtils.isMethodEntryExecution(execution)) {
			activeCallExpressionStateMachine = factory.create((MethodEntryExecution) execution,
					sequentialExecutionMetaData);
		}
		// TODO here should NOT be executable 'cause possible situations include
		// a methodExitEvent while no valid activeCallExpressionStateMachine
	}

}

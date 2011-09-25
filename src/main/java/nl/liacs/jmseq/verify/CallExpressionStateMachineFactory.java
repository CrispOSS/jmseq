/*
 * Created on Apr 17, 2010 - 2:27:23 PM
 */
package nl.liacs.jmseq.verify;

import nl.liacs.jmseq.annotation.SequencedMethod;
import nl.liacs.jmseq.annotation.SequencedObjectMetaData;
import nl.liacs.jmseq.annotation.SequentialExecutionMetaData;
import nl.liacs.jmseq.execution.MethodEntryExecution;
import nl.liacs.jmseq.verify.builder.AspecJCallExpressionBuilder;
import nl.liacs.jmseq.verify.builder.CallExpressionBuilder;
import nl.liacs.jmseq.verify.builder.StateTransitionMap;
import nl.liacs.jmseq.verify.callexpression.CallExpression;
import nl.liacs.jmseq.verify.matcher.AspectJCallExpressionMatcher;
import nl.liacs.jmseq.verify.matcher.CallExpressionMatcher;
import nl.liacs.jmseq.verify.proposer.CallExpressionProposer;
import nl.liacs.jmseq.verify.proposer.SimpleCallExpressionProposer;

/**
 * 
 * 
 * @author Behrooz Nobakht [behrooz dot nobakht at gmail dot com]
 */
public class CallExpressionStateMachineFactory {

	private static final CallExpressionStateMachineFactory INSTANCE = new CallExpressionStateMachineFactory();

	private CallExpressionBuilder callExpressionBuilder = new AspecJCallExpressionBuilder();
	private CallExpressionMatcher callExpressionMatcher = new AspectJCallExpressionMatcher();
	private CallExpressionProposer callExpressionProposer = new SimpleCallExpressionProposer();

	private CallExpressionStateMachineFactory() {
	}

	public static CallExpressionStateMachineFactory getInstance() {
		return INSTANCE;
	}

	public CallExpressionStateMachine create(MethodEntryExecution execution, SequentialExecutionMetaData metadata) {
		String className = execution.getExecutingClassType();
		SequencedObjectMetaData classMetadata = metadata.getMetadata(className);
		if (null == classMetadata) {
			// TODO ! ERROR ?!
		}
		String methodName = execution.getExecutingEvent().method().name();
		SequencedMethod annotation = classMetadata.getAnnotation(methodName);
		if (null == annotation) {
			// TODO ! ERROR ?!
		}
		String sequenceExpression = annotation.value();
		CallExpression callExpression = callExpressionBuilder.buildCallExpression(sequenceExpression);
		StateTransitionMap transitions = callExpressionBuilder.getTransitions();
		CallExpressionStateMachine cesm = new CallExpressionStateMachine(execution, annotation, metadata,
				callExpression, transitions);
		cesm.setCallExpressionBuilder(callExpressionBuilder);
		cesm.setCallExpressionMatcher(callExpressionMatcher);
		cesm.setCallExpressionProposer(callExpressionProposer);
		return cesm;
	}
	
}

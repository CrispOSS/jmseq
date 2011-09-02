/*
 * Created on Apr 18, 2010 - 5:42:14 PM
 */
package nl.liacs.jmseq.verify;

import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;
import java.util.Stack;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import nl.liacs.jmseq.annotation.SequencedMethod;
import nl.liacs.jmseq.annotation.SequentialExecutionMetaData;
import nl.liacs.jmseq.execution.Execution;
import nl.liacs.jmseq.execution.ExecutionUtils;
import nl.liacs.jmseq.execution.MethodEntryExecution;
import nl.liacs.jmseq.execution.MethodExitExecution;
import nl.liacs.jmseq.verify.builder.CallExpressionBuilder;
import nl.liacs.jmseq.verify.callexpression.CallExpression;
import nl.liacs.jmseq.verify.failurehandler.SequentialMetadataVerificationFailureHandler;
import nl.liacs.jmseq.verify.matcher.CallExpressionMatcher;
import nl.liacs.jmseq.verify.proposer.CallExpressionProposer;

/**
 * 
 * 
 * @author Behrooz Nobakht [behrooz dot nobakht at gmail dot com]
 */
public class CallExpressionStateMachine {
	
	protected final Logger logger = LoggerFactory.getLogger(getClass());

	private SequentialExecutionMetaData metadata;
	private SequencedMethod annotation;
	private CallExpressionBuilder callExpressionBuilder;
	private CallExpressionProposer callExpressionProposer;
	private CallExpressionMatcher callExpressionMatcher;
	private SequentialMetadataVerificationFailureHandler verificationFailureHandler = new SequentialMetadataVerificationFailureHandler();

	/**
	 * This is the method that is annotated for sequence specification.
	 */
	private MethodEntryExecution initialMethodEntryExecution;

	/**
	 * This is the initialMethodEntryExecution's annotation call expression
	 */
	private CallExpression initialCallExpression;

	// State Holders
	private CallExpression currentCallExpression;
	private Stack<CallExpression> expressions = new Stack<CallExpression>();
	private Map<CallExpression, Set<CallExpression>> proposalCache = new HashMap<CallExpression, Set<CallExpression>>();
	private boolean running = false;

	public CallExpressionStateMachine(MethodEntryExecution execution,
			SequencedMethod annotation, SequentialExecutionMetaData metadata,
			CallExpression initialCallExpression) {
		this.initialMethodEntryExecution = execution;
		this.metadata = metadata;
		this.annotation = annotation;
		this.initialCallExpression = initialCallExpression;
		this.currentCallExpression = this.initialCallExpression;
		this.running = true;
	}

	public boolean isValidNextExecution(Execution<?> execution) {
		boolean isMethodEntryEvent = ExecutionUtils
				.isMethodEntryExecution(execution);
		boolean isMethodExitEvent = ExecutionUtils
				.isMethodExitExecution(execution);
		if (isMethodEntryEvent) {
			return isValidNextMethodEntryExecution((MethodEntryExecution) execution);
		} else if (isMethodExitEvent) {
			return isValidNexMethodExitExecution((MethodExitExecution) execution);
		}
		throw new Error("An unacceptable execution ....");
	}

	private boolean isValidNextMethodEntryExecution(
			MethodEntryExecution execution) {
		CallExpression callExpression = callExpressionBuilder
				.buildCallExpression(execution);
		CallExpression matchFound = match(this.currentCallExpression,
				callExpression);
		if (null == matchFound) {
			handleVerificationFailure(execution, annotation);
		}
		this.expressions.push(this.currentCallExpression);
		this.currentCallExpression = matchFound;
		this.running = true;
		return true;
	}

	public boolean isRunning() {
		return this.running;
	}

	private boolean isValidNexMethodExitExecution(MethodExitExecution execution) {
		CallExpression returningToCallExpression = this.currentCallExpression
				.getOuterCallExpression();
		CallExpression lastCallExpression = this.expressions.pop();

		// the stack of expresssions is empty: it means that we've reached back
		// to the original method starting with having the annotations on the
		// method
		CallExpression matchesWithInitialCE = callExpressionMatcher.match(
				initialCallExpression, lastCallExpression);
		if (expressions.isEmpty() && matchesWithInitialCE != null) {
			running = false;
			return true;
		}

		CallExpression matchFound = match(returningToCallExpression,
				lastCallExpression);
		if (!this.expressions.isEmpty() && null == matchFound) {
			handleVerificationFailure(execution, annotation);
		}
		this.currentCallExpression = lastCallExpression;
		return true;
	}

	private CallExpression match(CallExpression candidateCallExpression,
			CallExpression callExpression) {
		Set<CallExpression> possibleNextExpressions = null;
//		possibleNextExpressions = proposalCache.get(candidateCallExpression);
//		if (possibleNextExpressions == null) {
			possibleNextExpressions = this.callExpressionProposer
					.proposePossibleNextExpressions(candidateCallExpression);
//			proposalCache.put(candidateCallExpression, possibleNextExpressions);
//		}
		long start = System.currentTimeMillis();
		CallExpression matchFound = null;
		for (Iterator<CallExpression> i = possibleNextExpressions.iterator(); i
				.hasNext() && matchFound == null;) {
			CallExpression candidate = i.next();
			matchFound = this.callExpressionMatcher.match(candidate,
					callExpression);
		}
		long time = System.currentTimeMillis() - start;
		logger.debug("Match took [{}]ms", time);
		return matchFound;
	}

	private void handleVerificationFailure(Execution execution,
			SequencedMethod annotation) {
		logger.error("Illegal execution: {}; checking for custom handlers", execution);
		verificationFailureHandler.handleVerificationFailure(execution,
				annotation);
	}

	public void setCallExpressionBuilder(
			CallExpressionBuilder callExpressionBuilder) {
		this.callExpressionBuilder = callExpressionBuilder;
	}

	public void setCallExpressionMatcher(
			CallExpressionMatcher callExpressionMatcher) {
		this.callExpressionMatcher = callExpressionMatcher;
	}

	public void setCallExpressionProposer(
			CallExpressionProposer callExpressionProposer) {
		this.callExpressionProposer = callExpressionProposer;
	}

	public void setVerificationFailureHandler(
			SequentialMetadataVerificationFailureHandler verificationFailureHandler) {
		this.verificationFailureHandler = verificationFailureHandler;
	}

}

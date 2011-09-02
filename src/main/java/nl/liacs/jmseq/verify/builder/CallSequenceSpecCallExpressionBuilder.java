/*
 * Created on Jul 24, 2010 - 2:21:12 PM
 */
package nl.liacs.jmseq.verify.builder;

import java.util.Stack;
import java.util.StringTokenizer;

import nl.liacs.jmseq.execution.Execution;
import nl.liacs.jmseq.verify.callexpression.CallExpression;
import nl.liacs.jmseq.verify.callexpression.Occurrence;
import nl.liacs.jmseq.verify.callexpression.SimpleAspectJCallExpression;

/**
 * 
 * 
 * @author Behrooz Nobakht [behrooz dot nobakht at gmail dot com]
 */
public class CallSequenceSpecCallExpressionBuilder implements CallExpressionBuilder {

	@Override
	public CallExpression buildCallExpression(String sequentialSpecificationExpresssion) {
		StringTokenizer tokenizer = new StringTokenizer(sequentialSpecificationExpresssion, CALL_EXPRESSION_DELIMITERS,
				true);
		Stack<CallExpression> expressions = new Stack<CallExpression>();
		Stack<CallExpression> outerExpressions = new Stack<CallExpression>();
		CallExpression currentCallExpression = null;
		CallExpression lastClosedExpression = null;
		for (; tokenizer.hasMoreTokens();) {
			String token = tokenizer.nextToken();
			if (CALL_OPENING.equals(token)) {
				String callExpression = tokenizer.nextToken();
				// build an expression
				CallExpression expression = new SimpleAspectJCallExpression(callExpression);
				if (!outerExpressions.isEmpty()) {
					// this expression will be executed inside another
					// expression call stack
					CallExpression lastOuterExpression = outerExpressions.peek();
					expression.setOuterCallExpression(lastOuterExpression);
					lastOuterExpression.getInnerCallExpressions().add(expression);
				}
				// the lastClosedExpression is the previous sibling of this
				// expression
				if (null != lastClosedExpression) {
					lastClosedExpression.getSiblingCallExpressions().add(expression);
				}
				// preserve the currentCallExpression
				if (null != currentCallExpression) {
					expressions.push(currentCallExpression);
				}
				currentCallExpression = expression;
			} else if (INNERCALL_OPENING.equals(token)) {
				outerExpressions.push(currentCallExpression);
			} else if (OCCURRENCE_STAR.equals(token)) {
				if (null != lastClosedExpression) {
					lastClosedExpression.setReocurrence(Occurrence.Star);
				}
			} else if (OCCURRENCE_PLUS.equals(token)) {
				if (null != lastClosedExpression) {
					lastClosedExpression.setReocurrence(Occurrence.Plus);
				}
			} else if (INNERCALL_CLOSING.equals(token)) {
				outerExpressions.pop();
			} else if (CALL_CLOSING.equals(token)) {
				lastClosedExpression = currentCallExpression;
				if (!expressions.isEmpty()) {
					currentCallExpression = expressions.pop();
				}
			} else {
				// TODO ERROR ?!
			}
		}
		// here the currentCallExpression should be the first and the root
		// of all expressions
		return currentCallExpression;
	}

	@Override
	public CallExpression buildCallExpression(Execution<?> execution) {
		throw new UnsupportedOperationException("Can only build call expressions from string specifications.");
	}

}

/*
 * Created on Jul 24, 2010 - 2:21:12 PM
 */
package nl.liacs.jmseq.verify.builder;

import java.util.HashMap;
import java.util.Map;
import java.util.Stack;
import java.util.StringTokenizer;

import nl.liacs.jmseq.execution.Execution;
import nl.liacs.jmseq.utils.CollectionUtils;
import nl.liacs.jmseq.verify.callexpression.CallExpression;
import nl.liacs.jmseq.verify.callexpression.CallExpressionFactory;
import nl.liacs.jmseq.verify.callexpression.Occurrence;

import com.continuent.tungsten.commons.patterns.fsm.PositiveGuard;
import com.continuent.tungsten.commons.patterns.fsm.StateType;
import com.continuent.tungsten.commons.patterns.fsm.Transition;

/**
 * 
 * 
 * @author Behrooz Nobakht [behrooz dot nobakht at gmail dot com]
 */
public class CallSequenceSpecCallExpressionBuilder implements CallExpressionBuilder {

	private StateTransitionMap transitions;

	@Override
	public CallExpression buildCallExpression(String sequentialSpecificationExpresssion) {
		StringTokenizer tokenizer = new StringTokenizer(sequentialSpecificationExpresssion, CALL_EXPRESSION_DELIMITERS,
				true);

		Stack<CallExpression> expressions = new Stack<CallExpression>();
		Stack<CallExpression> outerExpressions = new Stack<CallExpression>();
		CallExpression currentCallExpression = null;
		CallExpression lastClosedExpression = null;

		transitions = new StateTransitionMap();
		State s_0 = new State("START", StateType.START);
		State s_f = null;
		Map<CallExpression, State> states = CollectionUtils.createMap();
		State currentState = null;
		State lastClosedExpressionState = null;
		State lastClosedExpressionStatePlus = null;
		boolean lastClosedExpressionStatePlusProcessed = false;
		boolean firstTransition = true;
		transitions.addState(s_0);

		for (; tokenizer.hasMoreTokens();) {
			String token = tokenizer.nextToken();
			if (CALL_OPENING.equals(token)) {
				String callExpression = tokenizer.nextToken();
				// build an expression
				CallExpression expression = CallExpressionFactory.FACTORY.createCallExpression(callExpression);
				State state = new State(callExpression, StateType.ACTIVE);
				transitions.addState(state);

				// this expression will be executed inside another
				// expression call stack
				if (!outerExpressions.isEmpty()) {
					CallExpression lastOuterExpression = outerExpressions.peek();
					expression.setOuterCallExpression(lastOuterExpression);
					lastOuterExpression.getInnerCallExpressions().add(expression);

					// Redundant?
					Transition t = new Transition(callExpression, new CallExpressionGuard(expression),
							states.get(lastOuterExpression), null, state);
					transitions.addTransition(t);
				}

				// the lastClosedExpression is the previous sibling of this
				// expression
				if (null != lastClosedExpression) {
					lastClosedExpression.getSiblingCallExpressions().add(expression);

					// Redundant?
					Transition t = new Transition(callExpression, new CallExpressionGuard(expression),
							lastClosedExpressionState, null, state);
					transitions.addTransition(t);

					if (!lastClosedExpressionStatePlusProcessed && lastClosedExpression.getOccurrence().equals(Occurrence.Plus)) {
						t = new Transition(callExpression, new CallExpressionGuard(expression), lastClosedExpressionStatePlus, null, state);
						lastClosedExpressionStatePlusProcessed = true;
						transitions.addTransition(t);
					}
				}

				// preserve the currentCallExpression
				if (null != currentCallExpression) {
					expressions.push(currentCallExpression);
				}

				Transition t = null;
				if (firstTransition) {
					t = new Transition(callExpression, new CallExpressionGuard(expression), s_0, null, state);
					firstTransition = false;
				} else if (null != currentState) {
					t = new Transition(callExpression, new CallExpressionGuard(expression), currentState, null, state);
				}
				transitions.addTransition(t);

				currentCallExpression = expression;
				currentState = state;
				states.put(currentCallExpression, currentState);

			} else if (INNERCALL_OPENING.equals(token)) {

				outerExpressions.push(currentCallExpression);

			} else if (OCCURRENCE_STAR.equals(token)) {

				if (null != lastClosedExpression) {
					lastClosedExpression.setReocurrence(Occurrence.Star);
					Transition t = new Transition(lastClosedExpression.getExpression(), new CallExpressionGuard(
							lastClosedExpression), lastClosedExpressionState, null, lastClosedExpressionState);
					transitions.addTransition(t);
				}

			} else if (OCCURRENCE_PLUS.equals(token)) {

				if (null != lastClosedExpression) {
					lastClosedExpression.setReocurrence(Occurrence.Plus);
					lastClosedExpressionStatePlus = new State(lastClosedExpression.getExpression() + ":PLUS",
							StateType.ACTIVE);
					transitions.addState(lastClosedExpressionStatePlus);

					Transition t = new Transition(lastClosedExpression.getExpression(), new CallExpressionGuard(
							lastClosedExpression), lastClosedExpressionState, null, lastClosedExpressionStatePlus);
					transitions.addTransition(t);

					t = new Transition(lastClosedExpression.getExpression(), new CallExpressionGuard(
							lastClosedExpression), lastClosedExpressionStatePlus, null, lastClosedExpressionStatePlus);
					transitions.addTransition(t);
					
					lastClosedExpressionStatePlusProcessed = false;

				}

			} else if (INNERCALL_CLOSING.equals(token)) {

				outerExpressions.pop();

			} else if (CALL_CLOSING.equals(token)) {

				if (lastClosedExpression != null) {
					Transition t = new Transition(currentState.getBaseName() + ":RETURN", new CallExpressionGuard(
							lastClosedExpression), currentState, null, lastClosedExpressionState);
					transitions.addTransition(t);
				}

				lastClosedExpression = currentCallExpression;
				lastClosedExpressionState = states.get(lastClosedExpression);
				if (!expressions.isEmpty()) {
					currentCallExpression = expressions.pop();
					currentState = states.get(currentCallExpression);
				}

			} else {
				// TODO ERROR ?!
			}
		}
		// here the currentCallExpression should be the first and the root
		// of all expressions
		s_f = new State("END", StateType.END);
		transitions.addState(s_f);
		Transition t = new Transition("END", new CallExpressionGuard(null), currentState, null, s_f);
		transitions.addTransition(t);
		return currentCallExpression;
	}

	@Override
	public CallExpression buildCallExpression(Execution<?> execution) {
		throw new UnsupportedOperationException("Can only build call expressions from string specifications.");
	}

	public StateTransitionMap getTransitions() {
		return transitions;
	}

}

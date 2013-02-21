/*
 * Created on Sep 25, 2011 | 10:33:53 AM
 */
package nl.liacs.jmseq.verify.builder;

import nl.liacs.jmseq.verify.callexpression.CallExpression;
import nl.liacs.jmseq.verify.matcher.AspectJCallExpressionMatcher;
import nl.liacs.jmseq.verify.matcher.CallExpressionExactMatcher;
import nl.liacs.jmseq.verify.matcher.CallExpressionMatcher;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.continuent.tungsten.commons.patterns.fsm.Entity;
import com.continuent.tungsten.commons.patterns.fsm.Event;
import com.continuent.tungsten.commons.patterns.fsm.Guard;

/**
 * 
 * @author Behrooz Nobakht
 */
public class CallExpressionGuard implements Guard {

	protected final Logger logger = LoggerFactory.getLogger(getClass());

	private CallExpression callExpression;
//	private static CallExpressionMatcher matcher = new CallExpressionExactMatcher();
	private static CallExpressionMatcher matcher = new AspectJCallExpressionMatcher();

	public CallExpressionGuard(CallExpression callExpression) {
		this.callExpression = callExpression;
	}

	@Override
	public boolean accept(Event message, Entity entity, com.continuent.tungsten.commons.patterns.fsm.State state) {
		State s = (State) state;
		CallExpression ce = (CallExpression) message.getData();
//		logger.debug("Guard on {}; Current State: {}; received: {}", new Object[] { this.callExpression.getExpression(),
//				s.getBaseName(), ce.getExpression() });
		if (matcher.match(callExpression, ce) != null) {
			return true;
		}
		return false;
	}

}

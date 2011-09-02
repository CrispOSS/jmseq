/*
 * Created on Jul 26, 2010 - 7:45:59 PM
 */
package nl.liacs.jmseq.verify.matcher;

import java.util.Set;

import nl.liacs.jmseq.verify.callexpression.CallExpression;

/**
 * 
 * 
 * @author Behrooz Nobakht [behrooz dot nobakht at gmail dot com]
 */
public abstract class AbstractCallExpressionMatcher implements CallExpressionMatcher {

	@Override
	public CallExpression match(CallExpression expr, Set<CallExpression> candidates) {
		for (CallExpression candidate : candidates) {
			CallExpression matched = match(expr, candidate);
			if (null != matched) {
				return matched;
			}
		}
		return null;
	}

}

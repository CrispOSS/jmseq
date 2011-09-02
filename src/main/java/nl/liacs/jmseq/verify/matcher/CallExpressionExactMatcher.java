/*
 * Created on Apr 18, 2010 - 4:35:44 PM
 */
package nl.liacs.jmseq.verify.matcher;

import nl.liacs.jmseq.verify.callexpression.CallExpression;

/**
 * 
 * 
 * @author Behrooz Nobakht [behrooz dot nobakht at gmail dot com]
 */
public class CallExpressionExactMatcher extends AbstractCallExpressionMatcher {

	@Override
	public CallExpression match(CallExpression candidate, CallExpression target) {
		String str1 = candidate.getExpression();
		String str2 = target.getExpression();
		if (str1.equals(str2)) {
			return candidate;
		}
		return null;
	}

}

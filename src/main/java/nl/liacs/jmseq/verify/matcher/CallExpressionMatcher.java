/*
 * Created on Apr 18, 2010 - 4:28:00 PM
 */
package nl.liacs.jmseq.verify.matcher;

import java.util.Set;

import nl.liacs.jmseq.verify.callexpression.CallExpression;

/**
 * 
 * 
 * @author Behrooz Nobakht [behrooz dot nobakht at gmail dot com]
 */
public interface CallExpressionMatcher {

	CallExpression match(CallExpression candidate, CallExpression target);

	CallExpression match(CallExpression expr, Set<CallExpression> candidates);

}

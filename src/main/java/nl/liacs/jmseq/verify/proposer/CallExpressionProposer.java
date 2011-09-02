/*
 * Created on Apr 18, 2010 - 4:48:50 PM
 */
package nl.liacs.jmseq.verify.proposer;

import java.util.Set;

import nl.liacs.jmseq.verify.callexpression.CallExpression;

/**
 * 
 * 
 * @author Behrooz Nobakht [behrooz dot nobakht at gmail dot com]
 */
public interface CallExpressionProposer {

	Set<CallExpression> proposePossibleNextExpressions(CallExpression callExpression);

}

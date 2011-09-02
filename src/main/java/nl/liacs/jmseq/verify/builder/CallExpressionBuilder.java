/*
 * Created on Apr 13, 2010 - 6:52:14 PM
 */
package nl.liacs.jmseq.verify.builder;

import nl.liacs.jmseq.execution.Execution;
import nl.liacs.jmseq.verify.callexpression.CallExpression;

/**
 * 
 * 
 * @author Behrooz Nobakht [behrooz dot nobakht at gmail dot com]
 */
public interface CallExpressionBuilder {

	String CALL_CLOSING = "}";
	String INNERCALL_CLOSING = "]";
	String OCCURRENCE_PLUS = "$";
	String OCCURRENCE_STAR = "%";
	String INNERCALL_OPENING = "[";
	String CALL_OPENING = "{";
	String CALL_EXPRESSION_DELIMITERS = CALL_OPENING + CALL_CLOSING + INNERCALL_OPENING
			+ INNERCALL_CLOSING + OCCURRENCE_PLUS + OCCURRENCE_STAR;

	/**
	 * @param execution
	 * @return
	 */
	CallExpression buildCallExpression(Execution<?> execution);

	/**
	 * @param execution
	 * @param metadata
	 * @return
	 */
	CallExpression buildCallExpression(String sequentialSpecificationExpresssion);

}

/*
 * Created on Apr 17, 2010 - 6:45:36 PM
 */
package nl.liacs.jmseq.verify.callexpression;

import java.util.List;

/**
 * 
 * 
 * @author Behrooz Nobakht [behrooz dot nobakht at gmail dot com]
 */
public interface CallExpression {
	
	String CALL_ANY = "call(*)";

	String getExpression();

	CallExpression getOuterCallExpression();

	void setOuterCallExpression(CallExpression expression);

	List<CallExpression> getInnerCallExpressions();

	List<CallExpression> getSiblingCallExpressions();

	void setReocurrence(Occurrence occurrence);

	Occurrence getOccurrence();

	String toString(String tab);
	
	MethodSignature getMethodSignature();

	public interface MethodSignature {

		String getReturnTypeName();

		String getClassName();

		String getMethodName();

		List<String> getArgumentTypeNames();

		Class<?> getReturnType();

		Class<?> getDeclaringClass();

		List<Class<?>> getArgumentTypes();
	}

}

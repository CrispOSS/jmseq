/*
 * Created on Apr 17, 2010 - 6:45:50 PM
 */
package nl.liacs.jmseq.verify.callexpression;

import java.util.Iterator;
import java.util.List;

/**
 * 
 * 
 * @author Behrooz Nobakht [behrooz dot nobakht at gmail dot com]
 */
public class SimpleAspectJCallExpression extends BaseCallExpression {

	public SimpleAspectJCallExpression(String callExpression) {
		this.originalExpression = callExpression;
	}

	public SimpleAspectJCallExpression(String modifier, String returnType, String className, String methodName,
			List<String> argumentTypeNameList) {
		this("call(" + modifier + " " + returnType + " " + className + "." + methodName + "("
				+ delimit(argumentTypeNameList) + ")" + ")");
//		this.methodSignature = buildMethodSignature(returnType, className, methodName, argumentTypeNameList);
	}

	public SimpleAspectJCallExpression(java.lang.reflect.Method method) {
		super(method);
	}

	static String delimit(List<String> coll) {
		if (coll == null || coll.isEmpty()) {
			return "";
		}
		String s = "";
		Iterator<String> it = coll.iterator();
		while (it.hasNext()) {
			s += it.next();
			if (it.hasNext()) {
				s += ',';
			}
		}
		return s;
	}

}

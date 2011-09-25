/*
 * Created on Apr 17, 2010 - 6:45:50 PM
 */
package nl.liacs.jmseq.verify.callexpression;

import java.util.List;

import org.springframework.util.StringUtils;

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
				+ StringUtils.collectionToCommaDelimitedString(argumentTypeNameList) + ")" + ")");
		this.methodSignature = buildMethodSignature(returnType, className, methodName, argumentTypeNameList);
	}


}

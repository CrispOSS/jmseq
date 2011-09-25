/*
 * Created on Apr 20, 2010 - 8:56:32 AM
 */
package nl.liacs.jmseq.verify.builder;

import java.util.List;

import nl.liacs.jmseq.execution.Execution;
import nl.liacs.jmseq.execution.ExecutionUtils;
import nl.liacs.jmseq.verify.callexpression.CallExpression;
import nl.liacs.jmseq.verify.callexpression.SimpleAspectJCallExpression;

import com.sun.jdi.Method;
import com.sun.jdi.event.MethodEntryEvent;
import com.sun.jdi.event.MethodExitEvent;

/**
 * 
 * 
 * @author Behrooz Nobakht [behrooz dot nobakht at gmail dot com]
 */
public class AspecJCallExpressionBuilder extends CallSequenceSpecCallExpressionBuilder {

	@Override
	public CallExpression buildCallExpression(Execution<?> execution) {
		if (!ExecutionUtils.isMethodEntryExecution(execution) && !ExecutionUtils.isMethodExitExecution(execution)) {
			throw new Error("Cannot build call expression out of " + execution);
		}
		Method method = getMethod(execution);
		String returnType = method.returnTypeName();
		String modifier = method.isPublic() ? "public" : method.isProtected() ? "protected"
				: method.isPackagePrivate() ? "" : method.isPrivate() ? "private" : "";
		modifier += method.isFinal() ? " final " : "";
		List<String> argumentTypeNamesList = method.argumentTypeNames();
		String methodName = execution.getExecutingMethodName();
		String className = execution.getExecutingClassType();
		return new SimpleAspectJCallExpression(modifier, returnType, className, methodName, argumentTypeNamesList);
	}

	private Method getMethod(Execution<?> execution) {
		if (ExecutionUtils.isMethodEntryExecution(execution)) {
			return ((MethodEntryEvent) execution.getExecutingEvent()).method();
		}
		return ((MethodExitEvent) execution.getExecutingEvent()).method();
	}

}

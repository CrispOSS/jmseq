/*
 * Created on Apr 17, 2010 - 6:45:50 PM
 */
package nl.liacs.jmseq.verify.callexpression;

import java.util.ArrayList;
import java.util.List;

import org.springframework.util.ClassUtils;
import org.springframework.util.StringUtils;

/**
 * 
 * 
 * @author Behrooz Nobakht [behrooz dot nobakht at gmail dot com]
 */
public class SimpleAspectJCallExpression implements CallExpression {

	private String originalExpression;
	private Occurrence occurrence = Occurrence.Once;
	private CallExpression outerCallExpression;
	private List<CallExpression> innerCallExpressions;
	private List<CallExpression> siblingCallExpressions;
	private MethodSignature methodSignature;

	public SimpleAspectJCallExpression(String callExpression) {
		this.originalExpression = callExpression;
		this.outerCallExpression = null;
		this.innerCallExpressions = new ArrayList<CallExpression>();
		this.siblingCallExpressions = new ArrayList<CallExpression>();
		this.methodSignature = new MethodSignatureImpl();
	}

	public SimpleAspectJCallExpression(String modifier, String returnType, String className, String methodName,
			List<String> argumentTypeNameList) {
		this("call(" + modifier + " " + returnType + " " + className + "." + methodName + "("
				+ StringUtils.collectionToCommaDelimitedString(argumentTypeNameList) + ")" + ")");
		this.methodSignature = buildMethodSignature(returnType, className, methodName, argumentTypeNameList);
	}

	@Override
	public String getExpression() {
		return this.originalExpression;
	}

	@Override
	public void setReocurrence(Occurrence occurrence) {
		this.occurrence = occurrence;
	}

	@Override
	public Occurrence getOccurrence() {
		return this.occurrence;
	}

	@Override
	public List<CallExpression> getInnerCallExpressions() {
		return this.innerCallExpressions;
	}

	@Override
	public CallExpression getOuterCallExpression() {
		return this.outerCallExpression;
	}

	@Override
	public void setOuterCallExpression(CallExpression expression) {
		this.outerCallExpression = expression;
	}

	@Override
	public List<CallExpression> getSiblingCallExpressions() {
		return this.siblingCallExpressions;
	}

	@Override
	public MethodSignature getMethodSignature() {
		return this.methodSignature;
	}

	public String toString(String tab) {
		StringBuffer sb = new StringBuffer(tab + "{\n");
		sb.append(tab + originalExpression);
		sb.append(" : " + this.occurrence);
		if (!this.innerCallExpressions.isEmpty()) {
			sb.append(tab + "\n" + tab + "\tInner Calls:" + "\n");
			for (CallExpression ce : this.innerCallExpressions) {
				sb.append(ce.toString(tab + "\t"));
			}
		}
		if (!this.siblingCallExpressions.isEmpty()) {
			sb.append(tab + "\n" + tab + "\tSibling Calls:" + "\n");
			for (CallExpression ce : this.siblingCallExpressions) {
				sb.append(ce.toString(tab + "\t"));
			}
		}
		sb.append("\n" + tab + "}\n");
		return sb.toString();
	}

	@Override
	public String toString() {
		return toString("");
	}

	@Override
	public int hashCode() {
		return this.originalExpression.hashCode();
	}

	@Override
	public boolean equals(Object obj) {
		if (null == obj) {
			return false;
		}
		if (!(obj instanceof CallExpression)) {
			return false;
		}
		CallExpression o = (CallExpression) obj;
		return getExpression().equals(o.getExpression());
	}

	private MethodSignature buildMethodSignature(String returnType, String className, String methodName,
			List<String> argumentTypeNames) {
		MethodSignatureImpl ms = new MethodSignatureImpl();
		ms.returnTypeName = returnType;
		ms.className = className;
		ms.methodName = methodName;
		ms.argumentTypeNames = argumentTypeNames;

		try {
			ClassLoader cl = getClass().getClassLoader();
			ms.returnType = returnType.equals("void") ? Void.TYPE : ClassUtils.forName(returnType, cl);
			ms.clazz = ClassUtils.forName(className, cl);
			List<Class<?>> argumentTypes = new ArrayList<Class<?>>();
			for (String argTypeName : argumentTypeNames) {
				argumentTypes.add(ClassUtils.forName(argTypeName, cl));
			}
			ms.argumentTypes = argumentTypes;
		} catch (ClassNotFoundException e) {
			return null;
		} catch (LinkageError e) {
			return null;
		}

		return ms;
	}

	class MethodSignatureImpl implements MethodSignature {

		private Class<?> returnType;
		private Class<?> clazz;
		private List<Class<?>> argumentTypes;

		private String returnTypeName;
		private String className;
		private String methodName;
		private List<String> argumentTypeNames;

		@Override
		public List<String> getArgumentTypeNames() {
			return argumentTypeNames;
		}

		@Override
		public List<Class<?>> getArgumentTypes() {
			return argumentTypes;
		}

		@Override
		public String getClassName() {
			return className;
		}

		@Override
		public Class<?> getDeclaringClass() {
			return clazz;
		}

		@Override
		public String getMethodName() {
			return methodName;
		}

		@Override
		public Class<?> getReturnType() {
			return returnType;
		}

		@Override
		public String getReturnTypeName() {
			return returnTypeName;
		}
	}

}

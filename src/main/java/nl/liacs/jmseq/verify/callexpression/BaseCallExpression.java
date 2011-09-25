package nl.liacs.jmseq.verify.callexpression;

import java.util.ArrayList;
import java.util.List;

import org.springframework.util.ClassUtils;

/**
 * 
 * @author Behrooz Nobakht
 *
 */
public class BaseCallExpression implements CallExpression {

	protected String originalExpression;
	protected Occurrence occurrence = Occurrence.Once;
	protected CallExpression outerCallExpression = null;
	protected List<CallExpression> innerCallExpressions = new ArrayList<CallExpression>();
	protected List<CallExpression> siblingCallExpressions = new ArrayList<CallExpression>();
	protected MethodSignature methodSignature = new MethodSignatureImpl();

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

	protected MethodSignature buildMethodSignature(String returnType, String className, String methodName,
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

package nl.liacs.jmseq.verify.callexpression;

public class CallExpressionFactory {
	
	public static final CallExpressionFactory FACTORY = new CallExpressionFactory(); 
	
	public CallExpression createCallExpression(String expression) {
		if (expression.equals(CallExpression.CALL_ANY)) {
			return new CallAnyExpression();
		}
		return new SimpleAspectJCallExpression(expression);
	}
	
	private CallExpressionFactory() {
	}

}

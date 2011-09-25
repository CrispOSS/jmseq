package nl.liacs.jmseq.verify.callexpression;


/**
 * 
 * @author Behrooz Nobakht
 * 
 */
public class CallAnyExpression extends BaseCallExpression {
	
	public CallAnyExpression() {
		originalExpression = "call(* *(..))";
	}

}
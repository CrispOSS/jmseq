/*
 * Created on Jul 26, 2010 - 7:43:08 PM
 */
package nl.liacs.jmseq.verify.matcher;

import java.util.List;

import nl.liacs.jmseq.verify.callexpression.CallExpression;
import nl.liacs.jmseq.verify.callexpression.CallExpressionParser;

import org.aspectj.weaver.tools.PointcutParser;
import org.aspectj.weaver.tools.TypePatternMatcher;
import org.aspectj.weaver.tools.UnsupportedPointcutPrimitiveException;

/**
 * 
 * 
 * @author Behrooz Nobakht [behrooz dot nobakht at gmail dot com]
 */
public class AspectJCallExpressionMatcher extends AbstractCallExpressionMatcher {

	private PointcutParser parser;

	@Override
	public CallExpression match(CallExpression candidate, CallExpression target) {
		if (target.equals(candidate)) {
			return candidate;
		}
		PointcutParser parser = getPointcutParser();
		String candidateCE = candidate.getExpression();
		try {
			parser.parsePointcutExpression(candidateCE);
		} catch (UnsupportedPointcutPrimitiveException e) {
			// TODO Log
			e.printStackTrace();
			return null;
		} catch (IllegalArgumentException e) {
			// TODO Log
			e.printStackTrace();
			return null;
		}
		if (!matchesDeclaringClass(target, parser, candidateCE)) {
			return null;
		}
		if (!matchesMethodName(target, candidateCE)) {
			return null;
		}
		if (!matchesArgumentTypes(target, parser, candidateCE)) {
			return null;
		}
		return candidate;
	}

	private boolean matchesArgumentTypes(CallExpression target, PointcutParser parser, String candidateCE) {
		List<String> argumentTypePatternList = CallExpressionParser.parseMethodArgumentTypePatternList(candidateCE);
		if (argumentTypePatternList.size() != target.getMethodSignature().getArgumentTypeNames().size()) {
			return false;
		}
		for (int i = 0; i < argumentTypePatternList.size(); ++i) {
			String typePattern = argumentTypePatternList.get(i);
			TypePatternMatcher tpm = parser.parseTypePattern(typePattern);
			Class<?> actual = target.getMethodSignature().getArgumentTypes().get(i);
			if (!tpm.matches(actual)) {
				return false;
			}
		}
		return true;
	}

	private boolean matchesMethodName(CallExpression target, String candidateCE) {
		String methodName = CallExpressionParser.parseMethodName(candidateCE);
		if (!methodName.equals(target.getMethodSignature().getMethodName())) {
			return false;
		}
		return true;
	}

	private boolean matchesDeclaringClass(CallExpression target, PointcutParser parser, String candidateCE) {
		String typePattern = CallExpressionParser.parseClassTypePattern(candidateCE);
		TypePatternMatcher tpm = parser.parseTypePattern(typePattern);
		Class<?> declaringClass = target.getMethodSignature().getDeclaringClass();
		if (declaringClass == null || !tpm.matches(declaringClass)) {
			// TODO Log
			return false;
		}
		return true;
	}

	private PointcutParser getPointcutParser() {
		if (null == this.parser) {
			this.parser = PointcutParser
					.getPointcutParserSupportingAllPrimitivesAndUsingContextClassloaderForResolution();
		}
		return this.parser;
	}

}

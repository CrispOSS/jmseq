/*
 * Created on Jul 26, 2010 - 7:43:08 PM
 */
package nl.liacs.jmseq.verify.matcher;

import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.Map;

import nl.liacs.jmseq.utils.CollectionUtils;
import nl.liacs.jmseq.verify.callexpression.CallExpression;

import org.aspectj.weaver.tools.PointcutExpression;
import org.aspectj.weaver.tools.PointcutParser;
import org.aspectj.weaver.tools.ShadowMatch;

/**
 * 
 * 
 * @author Behrooz Nobakht [behrooz dot nobakht at gmail dot com]
 */
public class AspectJCallExpressionMatcher extends AbstractCallExpressionMatcher {

	private PointcutParser parser;
	private Map<String, PointcutExpression> cache = CollectionUtils.createMap();
	private Map<PointcutExpression, Map<CallExpression, Boolean>> matchCache = CollectionUtils.createMap();

	@Override
	public CallExpression match(CallExpression candidate, CallExpression target) {
		PointcutParser parser = getPointcutParser();
		PointcutExpression pe = null;
		if (cache.containsKey(candidate.getExpression())) {
			pe = cache.get(candidate.getExpression());
		} else {
			logger.warn("Parsing pointcuts at {}", candidate.getExpression());
			pe = parser.parsePointcutExpression(candidate.getExpression());
			cache.put(candidate.getExpression(), pe);
		}
		if (!matchCache.containsKey(pe)) {
			matchCache.put(pe, new HashMap<CallExpression, Boolean>());
		}
		Map<CallExpression, Boolean> matches = matchCache.get(pe);
		Boolean currentMatch = matches.get(candidate);
		if (currentMatch != null && currentMatch) {
			return candidate;
		} else if (currentMatch != null && !currentMatch) {
			return null;
		}
		Method method = target.getMethod();
		ShadowMatch match = pe.matchesMethodCall(method, Object.class);
		if (match.neverMatches()) {
			matches.put(candidate, false);
			return null;
		}
		matches.put(candidate, true);
		return candidate;
	}

	// public CallExpression match(CallExpression candidate, CallExpression
	// target) {
	// if (target.equals(candidate)) {
	// return candidate;
	// }
	// if (target instanceof CallAnyExpression) {
	// return candidate;
	// }
	// PointcutParser parser = getPointcutParser();
	// String candidateCE = candidate.getExpression();
	// try {
	// logger.debug("Parsing call expression: {}", candidateCE);
	// // parser.parsePointcutExpression(candidateCE);
	// } catch (UnsupportedPointcutPrimitiveException e) {
	// e.printStackTrace();
	// return null;
	// } catch (IllegalArgumentException e) {
	// e.printStackTrace();
	// return null;
	// }
	// if (!matchesDeclaringClass(target, parser, candidateCE)) {
	// return null;
	// }
	// if (!matchesMethodName(target, candidateCE)) {
	// return null;
	// }
	// if (!matchesArgumentTypes(target, parser, candidateCE)) {
	// return null;
	// }
	// return candidate;
	// }

//	private boolean matchesArgumentTypes(CallExpression target, PointcutParser parser, String candidateCE) {
//		List<String> argumentTypePatternList = CallExpressionParser.parseMethodArgumentTypePatternList(candidateCE);
//		if (argumentTypePatternList.size() != target.getMethodSignature().getArgumentTypeNames().size()) {
//			return false;
//		}
//		for (int i = 0; i < argumentTypePatternList.size(); ++i) {
//			String typePattern = argumentTypePatternList.get(i);
//			TypePatternMatcher tpm = parser.parseTypePattern(typePattern);
//			Class<?> actual = target.getMethodSignature().getArgumentTypes().get(i);
//			if (!tpm.matches(actual)) {
//				return false;
//			}
//		}
//		return true;
//	}
//
//	private boolean matchesMethodName(CallExpression target, String candidateCE) {
//		String methodName = CallExpressionParser.parseMethodName(candidateCE);
//		if (!methodName.equals(target.getMethodSignature().getMethodName())) {
//			return false;
//		}
//		return true;
//	}
//
//	private boolean matchesDeclaringClass(CallExpression target, PointcutParser parser, String candidateCE) {
//		String typePattern = CallExpressionParser.parseClassTypePattern(candidateCE);
//		TypePatternMatcher tpm = parser.parseTypePattern(typePattern);
//		Class<?> declaringClass = target.getMethodSignature().getDeclaringClass();
//		if (declaringClass == null || !tpm.matches(declaringClass)) {
//			// TODO Log
//			return false;
//		}
//		return true;
//	}

	private PointcutParser getPointcutParser() {
		if (null == this.parser) {
			this.parser = PointcutParser
					.getPointcutParserSupportingAllPrimitivesAndUsingContextClassloaderForResolution();
		}
		return this.parser;
	}

}

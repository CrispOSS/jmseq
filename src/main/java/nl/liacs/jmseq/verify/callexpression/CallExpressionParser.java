/*
 * Created on Jul 27, 2010 - 5:36:48 PM
 */
package nl.liacs.jmseq.verify.callexpression;

import java.util.Arrays;
import java.util.List;

import org.springframework.util.StringUtils;

/**
 * 
 * 
 * @author Behrooz Nobakht [behrooz dot nobakht at gmail dot com]
 */
public abstract class CallExpressionParser {

	/**
	 * @param expr
	 * @return
	 */
	public static String parseReturnTypePattern(String expr) {
		String chunk = expr.substring(expr.indexOf('(') + 1, expr.lastIndexOf('('));
		int count = StringUtils.countOccurrencesOf(chunk, " ");
		if (count == 2) {
			int beginIndex = chunk.indexOf(' ') + 1;
			int endIndex = chunk.lastIndexOf(' ');
			String typePattern = chunk.substring(beginIndex, endIndex);
			return typePattern.trim();
		}
		if (count == 1) {
			int endIndex = chunk.lastIndexOf(' ');
			String typePattern = chunk.substring(0, endIndex);
			return typePattern.trim();
		}
		return null;
	}

	/**
	 * @param expr
	 * @return
	 */
	public static String parseClassTypePattern(String expr) {
		String typePattern = expr.substring(expr.indexOf('(') + 1);
		typePattern = typePattern.substring(0, typePattern.indexOf('(')).trim();
		int beginIndex = typePattern.lastIndexOf(" ");
		int endIndex = typePattern.lastIndexOf('.');
		typePattern = typePattern.substring(beginIndex, endIndex);
		return typePattern.trim();
	}

	/**
	 * @param expr
	 * @return
	 */
	public static String parseMethodName(String expr) {
		String typePattern = expr.substring(expr.indexOf('(') + 1);
		typePattern = typePattern.substring(0, typePattern.indexOf('(')).trim();
		typePattern = typePattern.substring(typePattern.lastIndexOf('.') + 1);
		return typePattern;
	}

	/**
	 * @param expr
	 * @return
	 */
	public static String parseMethodArgumentTypePatterns(String expr) {
		String typePattern = expr.substring(expr.lastIndexOf('(') + 1);
		typePattern = typePattern.substring(0, typePattern.length() - 2);
		typePattern = typePattern.trim();
		return typePattern;
	}

	/**
	 * @param expr
	 * @return
	 */
	public static List<String> parseMethodArgumentTypePatternList(String expr) {
		String pattern = parseMethodArgumentTypePatterns(expr);
		String[] array = StringUtils.delimitedListToStringArray(pattern, ",");
		return Arrays.asList(array);
	}

}

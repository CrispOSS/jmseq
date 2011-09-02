/*
 * Created on Jul 27, 2010 - 5:43:47 PM
 */
package nl.liacs.jmseq.verify.builder;

import java.util.List;

import nl.liacs.jmseq.verify.callexpression.CallExpressionParser;

import org.junit.Assert;
import org.junit.Test;

/**
 * 
 * 
 * @author Behrooz Nobakht [behrooz dot nobakht at gmail dot com]
 */
public class CallExpressionParserTest {

	@Test
	public void testParseClassTypePattern() throws Exception {
		String expression = "call(public void nl..Pat.eat())";
		String typePattern = CallExpressionParser.parseClassTypePattern(expression);
		System.out.println(typePattern);
		Assert.assertEquals("nl..Pat", typePattern);
	}

	@Test
	public void testParseMethodArgumentTypePatterns1() throws Exception {
		String expression = "call(public void nl..Pat.eat())";
		List<String> typePatterns = CallExpressionParser.parseMethodArgumentTypePatternList(expression);
		System.out.println(typePatterns);
		Assert.assertEquals(0, typePatterns.size());
	}
	
	@Test
	public void testParseMethodArgumentTypePatterns2() throws Exception {
		String expression = "call(public void nl..Pat.eat(int, java.lang.String))";
		List<String> typePatterns = CallExpressionParser.parseMethodArgumentTypePatternList(expression);
		System.out.println(typePatterns);
		Assert.assertEquals(2, typePatterns.size());
	}
	
	@Test
	public void testParseMethodName() throws Exception {
		String expression = "call(public void nl..Pat.eat(int, java.lang.String))";
		String methodName = CallExpressionParser.parseMethodName(expression);
		System.out.println(methodName);
		Assert.assertEquals("eat", methodName);
	}
	
	@Test
	public void testParseReturnType() throws Exception {
		String expression = "call(public void nl..Pat.eat(int, java.lang.String))";
		String returnTypePattern = CallExpressionParser.parseReturnTypePattern(expression);
		System.out.println(returnTypePattern);
		Assert.assertEquals("void", returnTypePattern);
	}
	
}

/*
 * Created on Apr 18, 2010 - 4:51:38 PM
 */
package nl.liacs.jmseq.verify.proposer;

import java.util.HashSet;
import java.util.Set;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import nl.liacs.jmseq.verify.callexpression.CallExpression;
import nl.liacs.jmseq.verify.callexpression.Occurrence;

/**
 * 
 * 
 * @author Behrooz Nobakht [behrooz dot nobakht at gmail dot com]
 */
public class SimpleCallExpressionProposer implements CallExpressionProposer {
	
	protected final Logger logger = LoggerFactory.getLogger(getClass());

	@Override
	public Set<CallExpression> proposePossibleNextExpressions(
			CallExpression callExpression) {
		long start = System.currentTimeMillis();
		Set<CallExpression> possibilities = new HashSet<CallExpression>();
		if (null == callExpression) {
			return possibilities;
		}
		if (!callExpression.getInnerCallExpressions().isEmpty()) {
			CallExpression innerCallExpression = callExpression
					.getInnerCallExpressions().get(0);
			if (Occurrence.Star.equals(innerCallExpression.getOccurrence())) {
				possibilities
						.addAll(proposePossibleNextExpressions(innerCallExpression));
			} else {
				possibilities.add(innerCallExpression);
			}
		}
		if (!callExpression.getSiblingCallExpressions().isEmpty()) {
			CallExpression siblingCallExpression = callExpression
					.getSiblingCallExpressions().get(0);
			if (Occurrence.Star.equals(siblingCallExpression.getOccurrence())) {
				possibilities
						.addAll(proposePossibleNextExpressions(siblingCallExpression));
			} else {
				possibilities.add(siblingCallExpression);
			}
		}
		if (Occurrence.Star.equals(callExpression.getOccurrence())
				|| Occurrence.Plus.equals(callExpression.getOccurrence())) {
			possibilities.add(callExpression);
		}
		long time = System.currentTimeMillis() - start;
		logger.debug("Proposals took [" + time + "]ms");
		return possibilities;
	}

}

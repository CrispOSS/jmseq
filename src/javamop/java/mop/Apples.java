/*
 * Created on Apr 29, 2010 - 3:15:28 PM
 */

package mop;

import nl.liacs.jmseq.annotation.SequencedObject;

/**
 * 
 * 
 * @author Behrooz Nobakht [behrooz dot nobakht at gmail dot com]
 */
@SequencedObject
public class Apples {

	private Pat pat;

	public Apples(Basket basket) {
		this.pat = new Pat(basket);
	}

	public void start() {
		pat.eat();
	}

}

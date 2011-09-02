/*
 * Created on Apr 29, 2010 - 3:15:28 PM
 */
package nl.liacs.jmseq.sample;

import nl.liacs.jmseq.annotation.SequencedMethod;
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

	@SequencedMethod(value = "{call(public void nl..Pat.eat())[{call(public int nl..Basket.get())}$]}$", verificationFailureHandler = ApplesVerificationFailureHandler.class)
	public void start() {
		pat.eat();
		pat.eat();
		pat.eat();
		pat.eat();
	}

}

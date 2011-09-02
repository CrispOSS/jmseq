/*
 * Created on Mar 14, 2010 - 5:09:00 PM
 */
package nl.liacs.jmseq.sample;

/**
 * 
 * 
 * @author Behrooz Nobakht [behrooz dot nobakht at gmail dot com]
 */
public class Pat {

	private Basket basket;

	public Pat(Basket b) {
		this.basket = b;
	}

	public void eat() {
		// ERROR
		//this.basket.put();
		int count = this.basket.get();
		if (count > 0) {
			System.out.println("Ate an apple.");
		}
	}

}

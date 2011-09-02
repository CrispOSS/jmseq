package mop;

/*
 * Created on Mar 14, 2010 - 5:09:00 PM
 */

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
		for (int i = 0; i < 10; ++i) {
			// ERROR
			this.basket.store();
			int count = this.basket.retrieve();
			if (count > 0) {
				System.out.println("Ate an apple.");
			}
		}
	}

}

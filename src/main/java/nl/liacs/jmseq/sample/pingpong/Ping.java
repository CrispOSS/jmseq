/*
 * Created on Oct 2, 2011 | 6:45:36 AM
 */
package nl.liacs.jmseq.sample.pingpong;

/**
 * 
 * @author Behrooz Nobakht
 */
public class Ping {

	private Pong pong;
	private int count = 0;

	public Ping() {
	}

	public void setPong(Pong pong) {
		this.pong = pong;
	}

	public void ping() {
		System.out.println("Pinged " + (++count) + ", ponging ...");
		if (Math.random() > 0.7) {
			System.out.println("Enough.");
			System.exit(0);
			return;
		}
		pong.pong();
	}

}

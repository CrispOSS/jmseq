package mop.ERE.HasNext;

public class Vector<E> extends java.util.Vector<E> {

	private static final long serialVersionUID = -8225671634880377195L;

	@Override
	public Iterator<E> iterator() {
		return new IteratorDelegate<E>(super.iterator());
	}

}
package mop.ERE.HasNext;

public class IteratorDelegate<E> implements Iterator<E> {
	
	private java.util.Iterator<E> i;
	
	public IteratorDelegate(java.util.Iterator<E> iterator) {
		i = iterator;
	}

	@Override
	public boolean hasNext() {
		return i.hasNext();
	}

	@Override
	public E next() {
		return i.next();
	}

	@Override
	public void remove() {
		i.remove();
	}

}

package hyrax.structs;

public class MutablePair<T, E> {
	private T one;
	private E two;
	
	public MutablePair(T _one, E _two) {
		this.one = _one;
		this.two = _two;
	}
	
	public T one() { return this.one; }
	public void one(T v) { this.one = v; }
	
	public E two() { return this.two; }
	public void two(E v) { this.two = v; }

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((one == null) ? 0 : one.hashCode());
		result = prime * result + ((two == null) ? 0 : two.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (!(obj instanceof MutablePair))
			return false;
		@SuppressWarnings("unchecked")
		MutablePair<T, E> other = (MutablePair<T, E>) obj;
		if (one == null) {
			if (other.one != null)
				return false;
		} else if (!one.equals(other.one))
			return false;
		if (two == null) {
			if (other.two != null)
				return false;
		} else if (!two.equals(other.two))
			return false;
		return true;
	}
	
	public String toString() {
		String thingOne = this.one == null ? "null" : one.toString();
		String thingTwo = this.two == null ? "null" : two.toString();
		return String.format("Pair:(%s, %s)", thingOne, thingTwo);
	}
	
	public static <T, E> MutablePair<T, E> of(T t1, E t2) {
		return new MutablePair<T, E>(t1, t2);
	}
}

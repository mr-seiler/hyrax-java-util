package hyrax.structs;

/**
 * In immutable pair of any two types
 *
 * @param <T> Generic thing one
 * @param <E> Generic thing two
 */
public class Pair<T, E> {
	private final T one;
	private final E two;
	private final int hash;
	
	public Pair(T _one, E _two) {
		this.one = _one;
		this.two = _two;
		this.hash = (one == null ? 0 : one.hashCode() * 31)
				  + (two == null ? 0 : two.hashCode());
	}
	
	public T one() { return this.one; }
	public E two() { return this.two; }
	
	@Override
	public int hashCode() { return this.hash; }

	@Override
	public boolean equals(Object that) {
		if (this == that) {
			return true;
		}
		else if (that == null || !(that instanceof Pair)) {
			return false;
		}
		else {
			@SuppressWarnings("unchecked")
			Pair<T, E> other = (Pair<T, E>) that;
			return (this.one == null ? other.one() == null : this.one.equals(other.one())
				 && this.two == null ? other.two() == null : this.two.equals(other.two()));
		}
	}
	
	public String toString() {
		String thingOne = this.one == null ? "null" : one.toString();
		String thingTwo = this.two == null ? "null" : two.toString();
		return String.format("Pair:(%s, %s)", thingOne, thingTwo);
	}
	
	public static <T, E> Pair<T, E> of(T t1, E t2) {
		return new Pair<T, E>(t1, t2);
	}
}

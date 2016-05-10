package de.hegmanns.core.utils;

/**
 * Data-Holder for two related values, vor example a point in cartesian system,
 * or for subject-matter knowledge, my be a value with its unit (e.g. money-amount and money-currency).
 * 
 * 
 * @author B. Hegmanns
 *
 * @param <S> first value type
 * @param <T> second value type
 */
public interface Pair<S, T> extends Comparable<Pair<S, T>>{

	/**
	 * Get the first value. If that, what you get is the original instance or a copy depends from
	 * the implementation.
	 * 
	 * @return first element 
	 */
	S getFirst();
	
	/**
	 * Get the second value. If that, what you get is the original instance or a copy depends from
	 * the implementation.
	 * 
	 * @return second element
	 */
	T getSecond();
}

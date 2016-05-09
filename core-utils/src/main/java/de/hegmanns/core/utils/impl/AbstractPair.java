package de.hegmanns.core.utils.impl;

import java.util.Objects;

import org.apache.commons.lang3.builder.CompareToBuilder;

import de.hegmanns.core.utils.Pair;

public abstract class AbstractPair<S, T> implements Pair<S, T> {

	private S firstValue;
	private T secondValue;
	
	protected AbstractPair(S firstValue, T secondValue){
		this.firstValue = firstValue;
		this.secondValue = secondValue;
	}
	
	/**
	 * Helper method for gathering the original first instance.
	 * A subclass can delegate to this method in {@link #getFirst())-implementation or
	 * use this method and make a copy.
	 * 
	 * @return original first instance
	 */
	final S getFirstInstance() {
		return this.firstValue;
	}
	
	/**
	 * Helper method for gathering the original first instance.
	 * A subclass can delegate to this method in {@link #getSecond())-implementation or
	 * use this method and make a copy.
	 * 
	 * @return original second instance
	 */
	final T getSecondInstance() {
		return this.secondValue;
	}
	
	@Override
	public final int compareTo(Pair<S, T> otherPair) {
		Objects.requireNonNull(otherPair);
		return new CompareToBuilder().append(this.getFirst(), otherPair.getFirst()).append(this.getSecond(), otherPair.getSecond()).build();
	}
}

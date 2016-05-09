package de.hegmanns.core.utils.impl;

import de.hegmanns.core.utils.Pair;

/**
 * simple Implementation.
 * 
 * @author B. Hegmanns
 *
 * @param <S> type S
 * @param <T> type T
 */
public class DefaultPair<S, T> extends AbstractPair<S, T> implements Pair<S, T>{

	public DefaultPair(S firstValue, T secondValue){
		super(firstValue, secondValue);
	}
	
	@Override
	public S getFirst() {
		return this.getFirstInstance();
	}
	
	@Override
	public T getSecond() {
		return this.getSecondInstance();
	}

	
}

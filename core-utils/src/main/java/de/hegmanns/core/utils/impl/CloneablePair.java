package de.hegmanns.core.utils.impl;

import de.hegmanns.core.utils.CloneableInstance;
import de.hegmanns.core.utils.Pair;

/**
 * This special pair returns a clone from first and second instance.
 * 
 * @author B. Hegmanns
 *
 * @param <S> type S
 * @param <T> type T
 */
public class CloneablePair<S extends CloneableInstance<S>, T extends CloneableInstance<T>> extends AbstractPair<S, T> implements Pair<S, T> {

	public CloneablePair(S firstValue, T secondValue){
		super(firstValue, secondValue);
	}

	@Override
	public S getFirst() {
		return this.getFirstInstance().clone();
	}

	@Override
	public T getSecond() {
		return this.getSecondInstance().clone();
	}

	
}

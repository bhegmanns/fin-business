package de.hegmanns.core.utils.impl;

import de.hegmanns.core.utils.ClonableInstance;
import de.hegmanns.core.utils.Pair;

/**
 * This special pair returns a clone from first and second instanc.
 * 
 * @author B. Hegmanns
 *
 * @param <S> type S
 * @param <T> type T
 */
public class ClonablePair<S extends ClonableInstance<S>, T extends ClonableInstance<T>> extends AbstractPair<S, T> implements Pair<S, T> {

	public ClonablePair(S firstValue, T secondValue){
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

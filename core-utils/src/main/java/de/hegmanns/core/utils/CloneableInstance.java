package de.hegmanns.core.utils;

/**
 * Its the concrete interface for the marker interface {@link Cloneable}.
 * Its needed for some implementations in this bundle.
 * You only have to know, that you work with cloneable-instances.
 * 
 * @author B. Hegmanns
 *
 * @param <T> a cloneable instance
 */
public interface CloneableInstance<T> extends Cloneable{

	/**
	 * the clone method, which is guaranteed by the marker-interface {@link Cloneable}
	 * @return the object copy
	 */
	T clone();
	
}

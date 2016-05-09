package de.hegmanns.core.utils;

/**
 * Its the concrete interface for the marker interface {@link Cloneable}.
 * Its needed for some implementations in this bundle.
 * You only have to know, that you work with clonable-instances.
 * 
 * @author B. Hegmanns
 *
 * @param <T> a clonable instance
 */
public interface ClonableInstance<T> extends Cloneable{

	/**
	 * the clone method, which is garanteed by the marker-interface {@link Cloneable}
	 * @return the object copy
	 */
	T clone();
	
}

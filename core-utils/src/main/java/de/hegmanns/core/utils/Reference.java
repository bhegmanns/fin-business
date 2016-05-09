package de.hegmanns.core.utils;

import java.io.Serializable;

/**
 * A reference, e.g. for use in parameter-list and you can change the instance.
 * 
 * @author B. Hegmanns
 *
 * @param <T> type
 */
public class Reference<T> implements Serializable {
	
	private static final long serialVersionUID = 1L;
	private T instance;
	
	public Reference(T instance){
		this.instance = instance;
	}
	
	
	public static <T> Reference<T> create(T instance){
		return new Reference<>(instance);
	}
	
	public T get(){
		return instance;
	}
}

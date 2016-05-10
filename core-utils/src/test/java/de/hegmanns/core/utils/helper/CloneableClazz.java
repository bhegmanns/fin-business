package de.hegmanns.core.utils.helper;

import de.hegmanns.core.utils.CloneableInstance;

public class CloneableClazz implements CloneableInstance<CloneableClazz> {
	
	public CloneableClazz(){}
	public CloneableClazz(int value){
		this.value = value;
	}
	
	private int value;
	
	public int getValue(){
		return value;
	}
	
	public void setValue(int value){
		this.value = value;
	}
	
	
	@Override
	public CloneableClazz clone() {
		return new CloneableClazz(value);
	}

}

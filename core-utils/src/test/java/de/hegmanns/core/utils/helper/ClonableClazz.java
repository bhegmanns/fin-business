package de.hegmanns.core.utils.helper;

import de.hegmanns.core.utils.ClonableInstance;

public class ClonableClazz implements ClonableInstance<ClonableClazz>{
	
	public ClonableClazz(){}
	public ClonableClazz(int value){
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
	public ClonableClazz clone() {
		return new ClonableClazz(value);
	}

}

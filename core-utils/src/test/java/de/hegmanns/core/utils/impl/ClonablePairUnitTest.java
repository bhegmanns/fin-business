package de.hegmanns.core.utils.impl;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;
import static org.hamcrest.Matchers.not;
import static org.hamcrest.Matchers.sameInstance;

import org.junit.Test;

import de.hegmanns.core.utils.Pair;
import de.hegmanns.core.utils.helper.CloneableClazz;

public class ClonablePairUnitTest {

	@Test
	public void getFirstAlwaysReturnsNewInstance(){
		CloneableClazz firstIntance = new CloneableClazz(22);
		CloneableClazz secondInstance = new CloneableClazz(50);
		Pair<CloneableClazz, CloneableClazz> pair = new CloneablePair<>(firstIntance, secondInstance);
		
		assertThat(pair.getFirst(), not(sameInstance(pair.getFirst())));
		assertThat(pair.getFirst().getValue(), is(firstIntance.getValue()));
	}
	
	@Test
	public void getSecondAlwaysReturnsNewInstance(){
		CloneableClazz firstIntance = new CloneableClazz(22);
		CloneableClazz secondInstance = new CloneableClazz(50);
		Pair<CloneableClazz, CloneableClazz> pair = new CloneablePair<>(firstIntance, secondInstance);
		
		assertThat(pair.getSecond(), not(sameInstance(pair.getSecond())));
		assertThat(pair.getFirst().getValue(), is(firstIntance.getValue()));
	}
}

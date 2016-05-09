package de.hegmanns.core.utils.impl;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.greaterThan;

import org.junit.Test;
import org.mockito.Mockito;

public class AbstractPairUnitTest {

	@Test
	public void firstCompareToDeterminesCompareTo(){
		@SuppressWarnings("unchecked")
		AbstractPair<Long, Long> firstPair = Mockito.mock(AbstractPair.class);
		@SuppressWarnings("unchecked")
		AbstractPair<Long, Long> secondPair = Mockito.mock(AbstractPair.class);
		
		Mockito.when(firstPair.getFirst()).thenReturn(Long.MAX_VALUE);
		Mockito.when(secondPair.getFirst()).thenReturn(Long.MAX_VALUE);
		Mockito.when(firstPair.getSecond()).thenReturn(Long.MAX_VALUE);
		Mockito.when(secondPair.getSecond()).thenReturn(Long.MAX_VALUE);
		
		firstPair.compareTo(secondPair);
	}
	
	@Test
	public void secondDeterminesCompareToIfFirstsAreEqual(){
		AbstractPair<Long, Long> firstPair = new DefaultPair<>(Long.MAX_VALUE, Long.MAX_VALUE);
		AbstractPair<Long, Long> secondPair = new DefaultPair<>(Long.MAX_VALUE, Long.MAX_VALUE-1);
		
		assertThat(firstPair, greaterThan(secondPair));
	}
}

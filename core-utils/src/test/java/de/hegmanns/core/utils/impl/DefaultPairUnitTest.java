package de.hegmanns.core.utils.impl;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.comparesEqualTo;
import static org.hamcrest.Matchers.greaterThan;
import static org.hamcrest.Matchers.lessThan;
import static org.hamcrest.Matchers.not;
import static org.hamcrest.Matchers.nullValue;
import static org.hamcrest.Matchers.sameInstance;

import java.math.BigDecimal;
import java.time.LocalDate;

import org.junit.Test;

public class DefaultPairUnitTest {

	@Test
	public void returnsOriginalInstances(){
		BigDecimal myFirstValue = BigDecimal.valueOf(222);
		LocalDate mySecondValue = LocalDate.now();
		DefaultPair<BigDecimal, LocalDate> myPair = new DefaultPair<BigDecimal, LocalDate>(myFirstValue, mySecondValue);
		
		assertThat(myPair.getFirst(), sameInstance(myFirstValue));
		assertThat(myPair.getSecond(), sameInstance(mySecondValue));
	}
	
	@Test
	public void worksWithNullInstance(){
		DefaultPair<BigDecimal, LocalDate> myPair = new DefaultPair<>(null, null);
		
		assertThat(myPair.getFirst(), nullValue());
		assertThat(myPair.getSecond(), nullValue());
	}
	
	@Test
	public void sameTypeSameValueCompareToEqual(){
		BigDecimal myFirstValue = BigDecimal.valueOf(222);
		LocalDate mySecondValue = LocalDate.now();
		
		DefaultPair<BigDecimal, LocalDate> firstPair = new DefaultPair<>(myFirstValue, mySecondValue);
		DefaultPair<BigDecimal, LocalDate> secondPair = new DefaultPair<>(myFirstValue, mySecondValue);
		
		assertThat(firstPair, not(sameInstance(secondPair)));
		assertThat(firstPair, comparesEqualTo(secondPair));
	}
	
	@Test
	public void sameTypeAndGreaterCompareTo(){
		DefaultPair<BigDecimal, BigDecimal> firstPair = new DefaultPair<>(BigDecimal.ONE, BigDecimal.TEN);
		DefaultPair<BigDecimal, BigDecimal> secondPair = new DefaultPair<>(BigDecimal.ONE, BigDecimal.ZERO);
		
		assertThat(firstPair, greaterThan(secondPair));
		assertThat(secondPair, lessThan(firstPair));
	}
	
	@Test
	public void sameTypeOneLessOneGreatCompareTo(){
		DefaultPair<BigDecimal, BigDecimal> firstPair = new DefaultPair<>(BigDecimal.ZERO, BigDecimal.ONE);
		DefaultPair<BigDecimal, BigDecimal> secondPair = new DefaultPair<>(BigDecimal.ONE, BigDecimal.TEN);
		
		assertThat(firstPair, lessThan(secondPair));
		assertThat(secondPair, greaterThan(firstPair));
	}
	
	@Test
	public void sameTypeFirstValueLessSecondGreatCompareTo(){
		DefaultPair<BigDecimal, BigDecimal> firstPair = new DefaultPair<>(BigDecimal.ONE, BigDecimal.ONE);
		DefaultPair<BigDecimal, BigDecimal> secondPair = new DefaultPair<>(BigDecimal.TEN, BigDecimal.TEN);
		
		assertThat(firstPair, lessThan(secondPair));
		assertThat(secondPair, greaterThan(firstPair));
	}
	
	@Test
	public void firstCompareToDeterminesCompareTo(){
		AbstractPair<Long, Long> firstPair = new DefaultPair<>(Long.MAX_VALUE, Long.MIN_VALUE);
		AbstractPair<Long, Long> secondPair = new DefaultPair<>(Long.MAX_VALUE-1, Long.MAX_VALUE);
		
		assertThat(firstPair, greaterThan(secondPair));
	}
	
	@Test
	public void secondDeterminesCompareToIfFirstsAreEqual(){
		AbstractPair<Long, Long> firstPair = new DefaultPair<>(Long.MAX_VALUE, Long.MAX_VALUE);
		AbstractPair<Long, Long> secondPair = new DefaultPair<>(Long.MAX_VALUE, Long.MAX_VALUE-1);
		
		assertThat(firstPair, greaterThan(secondPair));
	}
	
}

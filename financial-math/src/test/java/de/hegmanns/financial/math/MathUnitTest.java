package de.hegmanns.financial.math;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.comparesEqualTo;

import java.math.BigDecimal;

import org.junit.Test;

import de.hegmanns.financial.money.Currency;
import de.hegmanns.financial.money.Money;

public class MathUnitTest {

	@Test
	public void addWithEqualCurrency(){
		Currency anyCurrency = new Currency();
		
		Money firstMoney = new Money(BigDecimal.ONE, anyCurrency);
		Money secondMoney = new Money(BigDecimal.ONE, anyCurrency);
		
		Money sumMoney = firstMoney.add(secondMoney);
		assertThat(sumMoney.getAmount(), comparesEqualTo(BigDecimal.ONE.add(BigDecimal.ONE)));
	}

	@Test(expected = IllegalArgumentException.class)
	public void addWithUnequalCurrency(){
		Currency firstCurrency = new Currency();
		Currency seondCurrency = new Currency();
		
		Money firstMoney = new Money(BigDecimal.ONE, firstCurrency);
		Money secondMoney = new Money(BigDecimal.ONE, seondCurrency);
		
		firstMoney.add(secondMoney);
	}
	
	@Test
	public void subtractWithEqualCurrency(){
		Currency anyCurrency = new Currency();
		
		Money firstMoney = new Money(BigDecimal.ONE, anyCurrency);
		Money secondMoney = new Money(BigDecimal.ONE, anyCurrency);
		
		Money sumMoney = firstMoney.subtract(secondMoney);
		assertThat(sumMoney.getAmount(), comparesEqualTo(BigDecimal.ZERO));
	}

	@Test(expected = IllegalArgumentException.class)
	public void subtractWithUnequalCurrency(){
		Currency firstCurrency = new Currency();
		Currency seondCurrency = new Currency();
		
		Money firstMoney = new Money(BigDecimal.ONE, firstCurrency);
		Money secondMoney = new Money(BigDecimal.ONE, seondCurrency);
		
		firstMoney.subtract(secondMoney);
	}
}

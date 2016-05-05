package de.hegmanns.financial.math;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.function.Function;

public class Money implements Cloneable, Serializable, Comparable<Money> {
	
	private static final long serialVersionUID = 1L;
	
	private Currency currency;
	private BigDecimal amount;
	
	/**
	 * Builds a new Money-instance with a clone of the given amount and the currency-instance.
	 * 
	 * @param amount the given amount
	 * @param currency the currency
	 */
	public Money(BigDecimal amount, Currency currency){
		this.amount = new BigDecimal(amount.toString());
		this.currency = currency;
	}
	
	/**
	 * a copy-constructor.
	 * @param money money to copy
	 */
	Money(Money money){
		this.currency = money.currency;
		this.amount = new BigDecimal(this.amount.toString());
	}
	
	private Money buildSameCurrency(BigDecimal amount){
		return new Money(amount, this.currency);
	}
	
	public Currency getCurrency(){
		return currency;
	}
	
	public BigDecimal getAmount(){
		return amount;
	}
	
	public Money clone(){
		return new Money(this);
	}
	
	private static Function<Money, Function<Money, BigDecimal>> addAmounts = a -> b -> a.amount.add(b.amount);
	private static Function<Money, Function<Money, BigDecimal>> subtractAmounts = a -> b -> a.amount.subtract(b.amount);
	
	public Money add(Money money){
		// FUENFTE VARIANTE
		return doItCurrencySave(addAmounts, this, money);
		
		// VIERTE VARIANTE
//		return doItCurrencySave(i -> j -> i.amount.add(j.amount), this, money);
		
		// DRITTE VARIANTE
//		return doWithMoneyAsBigDecimal(money, (b) -> this.amount.add(b));
		
		// ZWEITE VARIANTE
//		return doWithMoney(money, (b) -> buildSameCurrency(this.amount.add(b)));
		
		
		// ERSTE VARIANTE
//		if (hasSameCurrency(money)){
//			return buildSameCurrency(this.amount.add(money.amount));
//		}
//		throw new IllegalArgumentException("");
	}
	
	public Money subtract(Money money){
		return doItCurrencySave(subtractAmounts, this, money);
//		return doItCurrencySave(a -> b -> a.amount.subtract(b.amount), this, money);
//		return doWithMoneyAsBigDecimal(money, (b) -> this.amount.subtract(b));
//		return doWithMoney(money, (b) -> buildSameCurrency(this.amount.subtract(b)));
	}
	
	private Money doWithMoney(Money money, Function<BigDecimal, Money> yourFunction){
		if (hasSameCurrency(money)){
			return yourFunction.apply(money.amount);
		}
		throw new IllegalArgumentException("");
	}
	
	private Money doWithMoneyAsBigDecimal(Money money, Function<BigDecimal, BigDecimal> yourFunction){
		return doWithMoney(money, (b) -> buildSameCurrency(yourFunction.apply(b)));
	}
	
	private static Money doItCurrencySave(Function<Money, Function<Money, BigDecimal>> function, Money first, Money second){
		if (first.hasSameCurrency(second)){
			return new Money(function.apply(first).apply(second), first.currency);
		}
		throw new IllegalArgumentException("");
	}
	
	public Money add(BigDecimal amount){
		return buildSameCurrency(this.amount.add(amount));
	}
	
	public Money subtract(BigDecimal amount){
		return buildSameCurrency(this.amount.subtract(amount));
	}
	
	public Money multiply(BigDecimal factor){
		return buildSameCurrency(this.amount.multiply(factor));
	}
	
	public Money divide(BigDecimal divisor){
		return buildSameCurrency(this.amount.divide(divisor));
	}
	
	
	public boolean hasSameCurrency(Money money){
		return this.currency.equals(money.currency);
	}
	
	private static BigDecimal getAmount(Money money){
		return money.amount;
	}
	
	

	@Override
	public int compareTo(Money o) {
		if (o.currency.equals(currency)){
			return amount.subtract(o.amount).intValue();
		}
		throw new IllegalArgumentException("unequal currencies for comparing");
	}

}

package de.hegmanns.financial.money;

public interface ExchangeRate {
	
	public Money getTargetMoney(Money sourceMoney, Currency targetCurrency);
	
	public Money getTargetMoney(Money sourceMoney);
}

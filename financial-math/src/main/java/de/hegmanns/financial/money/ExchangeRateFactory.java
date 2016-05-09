package de.hegmanns.financial.money;

public interface ExchangeRateFactory {

	ExchangeRate locateExchangeRate(Currency sourceCurrency, Currency targetCurrency);
}

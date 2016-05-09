package de.hegmanns.financial.money;

import java.io.Serializable;
import java.util.Optional;

public class Currency implements Comparable<Currency> {
	
	public static class CurrencyData{
		private String currencyName;
		
		/** ISO 4217, e.g. USD for US-Dollar */
		private String letterCode;
		
		/** ISO 4217, e.g. 840 for US-Dollar */
		private int numericCode;
		
		private Optional<String> currencySymbol;
		
		private Optional<String> fractionSymbol;
		
		private Optional<Integer> fractionsPerUnit;
		
		private int rounding;
		
		private String formatString;
	}

	private String name;
	
	
	@Override
	public int compareTo(Currency o) {
		// TODO Auto-generated method stub
		return 0;
	}

}

package com.poc.services;

import java.math.BigDecimal;
import java.util.function.Function;
import java.util.logging.ConsoleHandler;
import java.util.logging.Formatter;
import java.util.logging.Logger;

import com.poc.config.LogFormatter;
import com.poc.utils.YahooFinance;

public class CalculateNav {
	
	public static final Logger log = Logger.getLogger(CalculateNav.class.getName());
	private Function<String, BigDecimal> priceFinder;
	
	public CalculateNav(final Function<String, BigDecimal> aPriceFinder) {
		configLog();
		priceFinder = aPriceFinder;
	}
	
	private void configLog() {
		log.setUseParentHandlers(false);
		ConsoleHandler handler = new ConsoleHandler();
		Formatter formatter = new LogFormatter();
		handler.setFormatter(formatter);        
		log.addHandler(handler);
	}
	
	private BigDecimal computeStockWorth(final String ticker, final int shares) {
		return priceFinder.apply(ticker).multiply(BigDecimal.valueOf(shares));
	}
	
	public static void main(String[] args) {
		final  CalculateNav calculateNav = new CalculateNav(YahooFinance::getPrice);
		log.info(String.format("100 shares of Google worth: $%.2f", calculateNav.computeStockWorth("GOOG", 100)));
	}

}

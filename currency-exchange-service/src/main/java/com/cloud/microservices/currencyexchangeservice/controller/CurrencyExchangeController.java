package com.cloud.microservices.currencyexchangeservice.controller;

import java.math.BigDecimal;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import com.cloud.microservices.currencyexchangeservice.model.CurrencyExchange;
import com.cloud.microservices.currencyexchangeservice.repository.ExchangeServiceRepository;

@RestController
public class CurrencyExchangeController {
	
	@Autowired
	private ExchangeServiceRepository exchangeServiceRepo;
	
	@Autowired
	private Environment environment;

	private CurrencyExchange findByFromAndTo;
	
	@GetMapping("/currency-exchange/from/{from}/to/{to}")
	public CurrencyExchange retrieveExchangeValue(@PathVariable String from, @PathVariable String to) {
//		CurrencyExchange currencyExchange = new CurrencyExchange(1000L,from,to,BigDecimal.valueOf(50));
		CurrencyExchange currencyExchange = exchangeServiceRepo.findByFromAndTo(from, to);
		if(currencyExchange == null) {
			throw new RuntimeException("Unable to find data for "+from +" and " +to);
		}
		String port= environment.getProperty("local.server.port");
		currencyExchange.setEnvironment(port);
		return currencyExchange;
		
	}

}

package com.cloud.microservices.currencyconversionservice.controller;

import java.math.BigDecimal;
import java.util.HashMap;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

import com.cloud.microservices.currencyconversionservice.model.CurrencyConversion;
import com.cloud.microservices.currencyconversionservice.proxy.CurrencyExchangeProxy;

@RestController
public class CurrencyConversionController {
	
	private RestTemplate restTemplate;
	
	@Autowired
	private CurrencyExchangeProxy currencyExchangeProxy;
	
	@GetMapping("/currency-conversion/from/{from}/to/{to}/quantity/{units}")
	public CurrencyConversion calucateCurrencyConversion(@PathVariable String from,@PathVariable String to,@PathVariable BigDecimal units) {
		
		HashMap<String,String> uriVariables = new HashMap<>();
		uriVariables.put("from", from);
		uriVariables.put("to", to);

		ResponseEntity<CurrencyConversion> forEntity = restTemplate.getForEntity("http://localhost:8000/currency-exchange/from/{from}/to/{to}",CurrencyConversion.class,uriVariables);
		CurrencyConversion currencyConversion = forEntity.getBody();
		return new CurrencyConversion(currencyConversion.getId(),from, to,currencyConversion.getConversionMultiple(),units.multiply(currencyConversion.getConversionMultiple()),units,currencyConversion.getEnvironment()+" RestTemplate");
	}
	
	@GetMapping("/currency-conversion-feign/from/{from}/to/{to}/quantity/{units}")
	public CurrencyConversion calucateCurrencyConversionFeign(@PathVariable String from,@PathVariable String to,@PathVariable BigDecimal units) {
		 CurrencyConversion currencyConversion = currencyExchangeProxy.retrieveExchangeValue(from, to);
		return new CurrencyConversion(currencyConversion.getId(),from, to,currencyConversion.getConversionMultiple(),units.multiply(currencyConversion.getConversionMultiple()),units,currencyConversion.getEnvironment());
	}

}

package com.cloud.microservices.currencyexchangeservice.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.cloud.microservices.currencyexchangeservice.model.CurrencyExchange;

public interface ExchangeServiceRepository extends JpaRepository<CurrencyExchange, Long>{

	CurrencyExchange findByFromAndTo(String from,String to);
}

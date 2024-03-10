package com.cloud.microservices.limitsservice.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import com.cloud.microservices.limitsservice.configuration.Configuration;
import com.cloud.microservices.limitsservice.model.Limits;

@RestController
public class LimitsController {
	
	@Autowired
	private Configuration configuration;
	
	@GetMapping("/limits")
	public  Limits retrieveLimits() {
		return new Limits(configuration.getMinimum(),configuration.getMaximum());
	}

}

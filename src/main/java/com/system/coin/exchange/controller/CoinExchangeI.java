package com.system.coin.exchange.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.system.coin.exchange.request.CoinExchangeRequest;

@RestController
public interface CoinExchangeI {

	@PostMapping(value = "/exchange", headers="Accept=application/json")
	public ResponseEntity<Object> exchangeBills(@RequestBody CoinExchangeRequest request);
}

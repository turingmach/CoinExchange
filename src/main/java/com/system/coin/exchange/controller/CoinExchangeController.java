package com.system.coin.exchange.controller;

import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.system.coin.exchange.request.CoinExchangeRequest;
import com.system.coin.exchange.service.CoinExchangeService;

@RestController
@RequestMapping
public class CoinExchangeController implements CoinExchangeI {

	@Autowired
	private CoinExchangeService coinExchangeService;
	
	private Map<Double, Integer> sortedCoinCountMap;
	
	@Override
	public ResponseEntity<Object> exchangeBills(@RequestBody CoinExchangeRequest request) {
		try {
			sortedCoinCountMap = coinExchangeService.exchange(request);
		} catch (Exception e) {
			return new ResponseEntity<Object>(e.getMessage(), HttpStatus.BAD_REQUEST);
		}
		return new ResponseEntity<Object>(sortedCoinCountMap, HttpStatus.ACCEPTED);
	}

}

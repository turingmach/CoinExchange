package com.system.coin.exchange.controller;

import java.math.BigDecimal;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.system.coin.exchange.exception.CoinExchangeException;
import com.system.coin.exchange.request.CoinExchangeRequest;
import com.system.coin.exchange.service.CoinExchangeService;

@RestController
@RequestMapping
public class CoinExchangeController implements CoinExchangeI {

	@Autowired
	private CoinExchangeService coinExchangeService;
	
	private Map<BigDecimal, Integer> sortedCoinCountMap;
	
    @Override
	public ResponseEntity<Object> exchangeBills(@RequestBody CoinExchangeRequest request) {
		try {
			sortedCoinCountMap = coinExchangeService.exchange(request);
		} catch (CoinExchangeException e) {
			return new ResponseEntity<Object>(e.getMessage(), HttpStatus.BAD_REQUEST);
		} catch (Exception e) {
			return new ResponseEntity<Object>(e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
		}
		return new ResponseEntity<Object>(sortedCoinCountMap, HttpStatus.ACCEPTED);
	}

}

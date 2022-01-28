package com.system.coin.exchange.service;

import static org.junit.Assert.assertEquals;

import java.util.ArrayList;
import java.util.List;

import org.junit.Assert;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;

import com.system.coin.exchange.data.CoinCount;
import com.system.coin.exchange.request.CoinExchangeRequest;

@WebMvcTest(ValidatorServiceTest.class)
public class ValidatorServiceTest {

	@Autowired
	private ValidatorService validatorService;
	
    @Test
	public void test_validate() throws Exception {
		CoinExchangeRequest request = new CoinExchangeRequest();
		request.setBill(50);
		CoinCount coinCount = new CoinCount(0.1, 5);
		List<CoinCount> cointCountList = new ArrayList<CoinCount>();
		cointCountList.add(coinCount);
		request.setCoinCountList(cointCountList);
		validatorService.validate(request);
    }
	
    @Test
	public void test_validate_bill_failure() throws Exception {
		CoinExchangeRequest request = new CoinExchangeRequest();
		request.setBill(41);
		Throwable exception = Assert.assertThrows(Exception.class, () -> validatorService.validate(request));
	    assertEquals("Please provide appropriate bill to exchange. Valid values are [1, 2, 5, 10, 20, 50, 100]",
	    		exception.getMessage());
    }
	
    @Test
	public void test_validate_coins_failure() throws Exception {
		CoinExchangeRequest request = new CoinExchangeRequest();
		request.setBill(50);
		CoinCount coinCount = new CoinCount(0.03, 5);
		List<CoinCount> cointCountList = new ArrayList<CoinCount>();
		cointCountList.add(coinCount);
		request.setCoinCountList(cointCountList);
		Throwable exception = Assert.assertThrows(Exception.class, () -> validatorService.validate(request));
	    assertEquals("Please provide appropriate coin inputs to exchange. Valid values are [0.01, 0.05, 0.1, 0.25]",
	    		exception.getMessage());
    }
	
}

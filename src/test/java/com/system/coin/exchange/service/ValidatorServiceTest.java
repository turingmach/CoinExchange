package com.system.coin.exchange.service;

import static org.junit.Assert.assertEquals;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.junit.Assert;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.mockito.MockedStatic;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.system.coin.exchange.data.CoinCount;
import com.system.coin.exchange.properties.ApplicationConfiguration;
import com.system.coin.exchange.request.CoinExchangeRequest;

@WebMvcTest(ValidatorServiceTest.class)
public class ValidatorServiceTest {

	@Autowired
	private ValidatorService validatorService;
	
	private static Map<Double,Integer> coinCountMap;
	static {
		
		coinCountMap = new HashMap<Double, Integer>();
		coinCountMap.put(Double.valueOf(0.25), Integer.valueOf(100));
		coinCountMap.put(Double.valueOf(0.10), Integer.valueOf(100));
		coinCountMap.put(Double.valueOf(0.05), Integer.valueOf(100));
		coinCountMap.put(Double.valueOf(0.01), Integer.valueOf(100));
	}
	
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

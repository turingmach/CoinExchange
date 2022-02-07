package com.system.coin.exchange.service;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.junit.Assert;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;

import com.system.coin.exchange.data.CoinCount;
import com.system.coin.exchange.exception.CoinExchangeException;
import com.system.coin.exchange.request.CoinExchangeRequest;

@WebMvcTest(CoinExchangeServiceTest.class)
public class CoinExchangeServiceTest {

	@Autowired
	private CoinExchangeService coinExchangeService;
	
	@MockBean
	private ValidatorService validatorService;
	
    @Test
	public void test_exchangeBills_success() throws Exception {
		CoinExchangeRequest request = new CoinExchangeRequest();
		request.setBill(1);
		CoinCount coinCount = new CoinCount(BigDecimal.valueOf(0.1), 5);
		List<CoinCount> cointCountList = new ArrayList<CoinCount>();
		cointCountList.add(coinCount);
		request.setCoinCountList(cointCountList);
		Mockito.doNothing().when(validatorService).validate(request);
    	Map<BigDecimal, Integer> responseMap = coinExchangeService.exchange(request);
    	assertNotNull(responseMap);  
    	assertNotNull(responseMap.get(BigDecimal.valueOf(0.25)));
    	assertEquals(Integer.valueOf(2), responseMap.get(BigDecimal.valueOf(0.25)));
    	assertEquals(Integer.valueOf(5), responseMap.get(BigDecimal.valueOf(0.1)));
    }
    
    @Test
	public void test_exchangeBills_failure() throws Exception {
		CoinExchangeRequest request = new CoinExchangeRequest();
		request.setBill(50);
		CoinCount coinCount = new CoinCount(BigDecimal.valueOf(0.1), 5);
		List<CoinCount> cointCountList = new ArrayList<CoinCount>();
		cointCountList.add(coinCount);
		request.setCoinCountList(cointCountList);
		Mockito.doNothing().when(validatorService).validate(request);
		Throwable exception = Assert.assertThrows(CoinExchangeException.class, () -> coinExchangeService.exchange(request));
	    assertEquals("Insufficient Funds", exception.getMessage());
    }
}

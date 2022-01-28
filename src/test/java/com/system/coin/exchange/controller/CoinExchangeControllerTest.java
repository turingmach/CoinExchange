package com.system.coin.exchange.controller;

import static org.hamcrest.CoreMatchers.notNullValue;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.util.HashMap;
import java.util.Map;

import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockHttpServletRequestBuilder;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.system.coin.exchange.request.CoinExchangeRequest;
import com.system.coin.exchange.service.CoinExchangeService;

@WebMvcTest(CoinExchangeControllerTest.class)
public class CoinExchangeControllerTest {
	
    @Autowired
    MockMvc mockMvc;

    @Autowired
    ObjectMapper mapper;	
    
    @MockBean
	private CoinExchangeService mockCoinExchangeService;

    @Test
	public void test_exchangeBills() throws Exception {
		
		CoinExchangeRequest request = new CoinExchangeRequest();
		request.setBill(1);
		Map<Double, Integer> mockResponseMap = new HashMap<Double, Integer>();
		mockResponseMap.put(0.25, 4);
		
	    Mockito.when(mockCoinExchangeService.exchange(request)).thenReturn(mockResponseMap);
	    MockHttpServletRequestBuilder mockRequest = MockMvcRequestBuilders.post("/exchange")
	            .contentType(MediaType.APPLICATION_JSON)
	            .accept(MediaType.APPLICATION_JSON)
	            .content(this.mapper.writeValueAsString(request));

	    mockMvc.perform(mockRequest)
        .andExpect(status().isAccepted())
        .andExpect(jsonPath("$", notNullValue()));
	}
}

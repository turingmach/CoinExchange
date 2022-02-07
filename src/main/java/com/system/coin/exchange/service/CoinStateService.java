package com.system.coin.exchange.service;

import java.math.BigDecimal;
import java.util.Arrays;
import java.util.Comparator;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.stream.Collectors;

import javax.annotation.PostConstruct;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.system.coin.exchange.properties.ApplicationConfiguration;

@Service
public class CoinStateService {

    @Autowired
    public ApplicationConfiguration config;
	private static Map<BigDecimal,Integer> coinCountMap;
    
    @PostConstruct
	public void initializeMap() {
    	coinCountMap = new HashMap<BigDecimal,Integer>();
		Integer loadCoinsAmt = config.getLoadCoinsAmt();
		String coins = config.getCoinList();
		List<String> cointListStr = Arrays.asList(coins.split(","));
		for (String coin :cointListStr) {
			coinCountMap.put(BigDecimal.valueOf(Double.valueOf(coin)), Integer.valueOf(loadCoinsAmt));
		}
    }
 
	public Map<BigDecimal, Integer> getCoinCountMap() {
		return coinCountMap.entrySet().stream()
                .sorted(Map.Entry.comparingByKey(Comparator.reverseOrder()))
                .collect(Collectors.toMap(Map.Entry::getKey, Map.Entry::getValue,
                        (oldValue, newValue) -> oldValue, LinkedHashMap::new));
	}
	
	public static void setCoinCountMap(Map<BigDecimal, Integer> newMap) {
		coinCountMap = newMap;
	}

	public static void printCoinAvailability() {
		for (Entry<BigDecimal, Integer> entry : coinCountMap.entrySet()) {
		    System.out.println("Anount of "+ entry.getKey() + " coins : " + entry.getValue());
		}
	}
}

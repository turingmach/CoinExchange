package com.system.coin.exchange.service;

import java.util.Comparator;
import java.util.HashMap;
import java.util.LinkedHashMap;
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
	private static Map<Double,Integer> coinCountMap;
    
    @PostConstruct
	public void initializeMap() {
		Integer loadCoinsAmt = config.getLoadCoinsAmt();
		coinCountMap = new HashMap<Double, Integer>();
		coinCountMap.put(Double.valueOf(0.25), Integer.valueOf(loadCoinsAmt));
		coinCountMap.put(Double.valueOf(0.10), Integer.valueOf(loadCoinsAmt));
		coinCountMap.put(Double.valueOf(0.05), Integer.valueOf(loadCoinsAmt));
		coinCountMap.put(Double.valueOf(0.01), Integer.valueOf(loadCoinsAmt));
    }
 
	public Map<Double, Integer> getCoinCountMap() {
		return coinCountMap.entrySet().stream()
                .sorted(Map.Entry.comparingByKey(Comparator.reverseOrder()))
                .collect(Collectors.toMap(Map.Entry::getKey, Map.Entry::getValue,
                        (oldValue, newValue) -> oldValue, LinkedHashMap::new));
	}
	
	public static void setCoinCountMap(Map<Double, Integer> newMap) {
		coinCountMap = newMap;
	}

	public static void printCoinAvailability() {
		for (Entry<Double, Integer> entry : coinCountMap.entrySet()) {
		    System.out.println("Anount of "+ entry.getKey().doubleValue() + " coins : " + entry.getValue());
		}
	}
}

package com.system.coin.exchange.data;

import java.util.Comparator;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Map.Entry;
import java.util.stream.Collectors;

public class CoinState {

	private static final int COIN_LOAD_AMOUNT = 100;
	private static Map<Double,Integer> coinCountMap;
	static {
		coinCountMap = new HashMap<Double, Integer>();
		coinCountMap.put(Double.valueOf(0.25), Integer.valueOf(COIN_LOAD_AMOUNT));
		coinCountMap.put(Double.valueOf(0.10), Integer.valueOf(COIN_LOAD_AMOUNT));
		coinCountMap.put(Double.valueOf(0.05), Integer.valueOf(COIN_LOAD_AMOUNT));
		coinCountMap.put(Double.valueOf(0.01), Integer.valueOf(COIN_LOAD_AMOUNT));
	}
	
	public static Map<Double, Integer> getCoinCountMap() {
		return coinCountMap.entrySet().stream()
                .sorted(Map.Entry.comparingByKey(Comparator.reverseOrder()))
                .collect(Collectors.toMap(Map.Entry::getKey, Map.Entry::getValue,
                        (oldValue, newValue) -> oldValue, LinkedHashMap::new));
	}
	
	public static int getCoinLoadAmount() {
		return COIN_LOAD_AMOUNT;
	}

	public static void setCoinCountMap(Map<Double, Integer> coinCountMap) {
		CoinState.coinCountMap = coinCountMap;
	}

	public static void printCoinAvailability() {
		for (Entry<Double, Integer> entry : coinCountMap.entrySet()) {
		    System.out.println("Anount of "+ entry.getKey().doubleValue() + " coins : " + entry.getValue());
		}
	}
}

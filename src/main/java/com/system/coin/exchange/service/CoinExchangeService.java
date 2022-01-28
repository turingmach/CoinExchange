package com.system.coin.exchange.service;

import java.util.Comparator;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import com.system.coin.exchange.data.CoinCount;
import com.system.coin.exchange.data.CoinState;
import com.system.coin.exchange.request.CoinExchangeRequest;

@Service
public class CoinExchangeService {

	@Autowired
	private ValidatorService validatorService;
	
	private Map<Double, Integer> responseCoinCountMap;
	
	public Map<Double, Integer> exchange(CoinExchangeRequest request) throws Exception {
		validatorService.validate(request);
		responseCoinCountMap = new HashMap<Double, Integer>();
		Double totalAmount = Double.valueOf(request.getBill());
		Map<Double, Integer> coinCountMap = CoinState.getCoinCountMap();
		if (!CollectionUtils.isEmpty(request.getCoinCountList())) {
			List<CoinCount> sortedCoins = sortInReverseList(request);
			for (CoinCount coinCount: sortedCoins) {
				totalAmount -= coinCount.getCoin()* coinCount.getCount();
				Integer count = coinCountMap.get(coinCount.getCoin());
				coinCountMap.put(coinCount.getCoin(), count - coinCount.getCount());
				if (responseCoinCountMap.containsKey(coinCount.getCoin())) {
					Integer originalCount = responseCoinCountMap.get(coinCount.getCoin());
					responseCoinCountMap.put(coinCount.getCoin(), originalCount+coinCount.getCount());
				} else {
					responseCoinCountMap.put(coinCount.getCoin(), coinCount.getCount());
				}
			}
		}
		if (totalAmount > 0.00) { 
			for (Entry<Double, Integer> entry : coinCountMap.entrySet()) {
				while (totalAmount > 0 && entry.getValue() > 0) {
					Integer numOfCoins = Integer.min(entry.getValue(), 
							Integer.valueOf(((int)(totalAmount*100))/(int)(entry.getKey()*100)));
					totalAmount -= numOfCoins*entry.getKey(); 
					Integer count = coinCountMap.get(entry.getKey());
					coinCountMap.put(entry.getKey(), count - numOfCoins);
					if (responseCoinCountMap.containsKey(entry.getKey())) {
						Integer originalCount = responseCoinCountMap.get(entry.getKey());
						responseCoinCountMap.put(entry.getKey(), originalCount+numOfCoins);
					} else {
						responseCoinCountMap.put(entry.getKey(), numOfCoins);
					}
				}
			}
		}
		if (totalAmount > 0.00) { 
			throw new Exception("Insufficient Funds");
		}
        Map<Double, Integer> sortedCoinCountMap = sortInReverseMap(coinCountMap);
        CoinState.setCoinCountMap(sortedCoinCountMap);
        CoinState.printCoinAvailability();
		return responseCoinCountMap;
	}

	private Map<Double, Integer> sortInReverseMap(Map<Double, Integer> coinCountMap) {
		Map<Double, Integer> sortedCoinCountMap = coinCountMap.entrySet().stream()
                .sorted(Map.Entry.comparingByKey())
                .collect(Collectors.toMap(Map.Entry::getKey, Map.Entry::getValue,
                        (oldValue, newValue) -> oldValue, LinkedHashMap::new));
		return sortedCoinCountMap;
	}

	private List<CoinCount> sortInReverseList(CoinExchangeRequest request) {
		List<CoinCount> sortedCoins = request.getCoinCountList().stream()
				.sorted(Comparator.comparing(CoinCount::getCoin).reversed()).collect(Collectors.toList());
		return sortedCoins;
	}

}

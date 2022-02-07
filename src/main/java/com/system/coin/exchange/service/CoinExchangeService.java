package com.system.coin.exchange.service;

import java.math.BigDecimal;
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
import com.system.coin.exchange.exception.CoinExchangeException;
import com.system.coin.exchange.request.CoinExchangeRequest;

@Service
public class CoinExchangeService {

	@Autowired
	private ValidatorService validatorService;
	
	@Autowired
	private CoinStateService coinStateService;
	private Map<BigDecimal, Integer> responseCoinCountMap;
	
	public Map<BigDecimal, Integer> exchange(CoinExchangeRequest request) throws Exception {
		validatorService.validate(request);
		responseCoinCountMap = new HashMap<BigDecimal, Integer>();
		BigDecimal totalAmount = BigDecimal.valueOf(request.getBill());
		Map<BigDecimal, Integer> coinCountMap = coinStateService.getCoinCountMap();
		if (!CollectionUtils.isEmpty(request.getCoinCountList())) {
			List<CoinCount> sortedCoins = sortInReverseList(request);
			for (CoinCount coinCount: sortedCoins) {
				totalAmount = totalAmount.subtract(coinCount.getCoin().multiply(BigDecimal.valueOf(coinCount.getCount().longValue())));
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
		if (totalAmount.compareTo(BigDecimal.valueOf(0.00)) == 1) { 
			for (Entry<BigDecimal, Integer> entry : coinCountMap.entrySet()) {
				while ((totalAmount.compareTo(entry.getKey()) == 1 || totalAmount.compareTo(entry.getKey()) == 0)
						&& entry.getValue() > 0
						) {
					Integer numOfCoins = Integer.min(entry.getValue(), 
							Integer.valueOf(
									(totalAmount.multiply(BigDecimal.valueOf(100.00))).intValue())/
									(entry.getKey().multiply(BigDecimal.valueOf(100.00)).intValue()));
					totalAmount = totalAmount.subtract(entry.getKey().multiply(BigDecimal.valueOf(numOfCoins))); 
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
		if (totalAmount.compareTo(BigDecimal.valueOf(0.00)) == 1) { 
			throw new CoinExchangeException("Insufficient Funds");
		}
        Map<BigDecimal, Integer> sortedCoinCountMap = sortInReverseMap(coinCountMap);
        CoinStateService.setCoinCountMap(sortedCoinCountMap);
        CoinStateService.printCoinAvailability();
		return responseCoinCountMap;
	}

	private Map<BigDecimal, Integer> sortInReverseMap(Map<BigDecimal, Integer> coinCountMap) {
		Map<BigDecimal, Integer> sortedCoinCountMap = coinCountMap.entrySet().stream()
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

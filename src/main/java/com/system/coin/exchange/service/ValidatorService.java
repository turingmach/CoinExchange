package com.system.coin.exchange.service;

import java.text.MessageFormat;
import java.util.Arrays;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import com.system.coin.exchange.data.CoinCount;
import com.system.coin.exchange.exception.CoinExchangeException;
import com.system.coin.exchange.request.CoinExchangeRequest;

@Service
public class ValidatorService {
	
	@Autowired
	private CoinStateService coinStateService;
	private List<Integer> validBills = Arrays.asList(1, 2, 5, 10, 20, 50, 100);
	private List<Double> validCoins = Arrays.asList(0.01, 0.05, 0.10, 0.25);

	public void validate(CoinExchangeRequest request) throws Exception {
		
		validateBill(request.getBill());
		validateCoins(request.getCoinCountList());
		validateInputCoinsTotal(request.getBill(), request.getCoinCountList());
	}

	private void validateInputCoinsTotal(Integer bill, List<CoinCount> coinCountList) throws Exception {
		Map<Double, Integer> coinCountMap = coinStateService.getCoinCountMap();
		Double total = Double.valueOf(0.00);
		if (!CollectionUtils.isEmpty(coinCountList)) {
			for (CoinCount coinCount: coinCountList) {
				validateCoinsAvailability(coinCount, coinCountMap);
				total += coinCount.getCoin()* coinCount.getCount();
			}
		}
		if (total>bill) {
			throw new CoinExchangeException("Total value of Coin selection cannot be greater than the bill produced for exchange");
		}
	}

	private void validateCoinsAvailability(CoinCount coinCount, Map<Double, Integer> coinCountMap) throws Exception {
		Integer cointCountInBank = coinCountMap.get(coinCount.getCoin());
		if (coinCount.getCount() > cointCountInBank) {
			throw new Exception(MessageFormat.format("Only {0} number of {1} are available", 
					cointCountInBank, coinCount.getCoin()));
		}
	}

//	private void validateFundsAvailability(Integer bill, List<CoinCount> coinCountList) throws Exception {
//		Map<Double, Integer> coinCountMap = CoinState.getCoinCountMap();
//		Double total = Double.valueOf(0.00);
//		if (!CollectionUtils.isEmpty(coinCountList)) {
//			for (CoinCount coinCount: coinCountList) {
//				validateCoinsAvailability(coinCount, coinCountMap);
//				total += coinCount.getCoin()* coinCount.getCount();
//			}
//		}
//		if (total>bill) {
//			throw new Exception("Total value of Coin selection cannot be greater than the bill produced for exchange");
//		}
//		
//	}

	private void validateBill(Integer bill) throws Exception {
		if (bill == null || !validBills.contains(bill) ) {
			throw new CoinExchangeException("Please provide appropriate bill to exchange. "
					+ "Valid values are " + validBills.toString());
		}
	}

	private void validateCoins(List<CoinCount> coinCountList) throws Exception {
		if (!CollectionUtils.isEmpty(coinCountList)) {
			for (CoinCount coinCount: coinCountList) {
				if (!validCoins.contains(coinCount.getCoin()))
					throw new CoinExchangeException("Please provide appropriate coin inputs to exchange. "
							+ "Valid values are " + validCoins.toString());
			}
		}
	}

}

package com.system.coin.exchange.request;

import java.util.List;

import com.system.coin.exchange.data.CoinCount;

public class CoinExchangeRequest {

	private Integer bill;
	private List<CoinCount> coinCountList;
	public Integer getBill() {
		return bill;
	}
	public void setBill(Integer bill) {
		this.bill = bill;
	}
	public List<CoinCount> getCoinCountList() {
		return coinCountList;
	}
	public void setCoinCountList(List<CoinCount> coinCountList) {
		this.coinCountList = coinCountList;
	}
	
}

package com.system.coin.exchange.data;

public class CoinCount {
	
	public CoinCount(Double coin, Integer count) {
		super();
		this.coin = coin;
		this.count = count;
	}
	
	private Double coin;
	private Integer count;
	
	public Double getCoin() {
		return coin;
	}
	public void setCoin(Double coin) {
		this.coin = coin;
	}
	public Integer getCount() {
		return count;
	}
	public void setCount(Integer count) {
		this.count = count;
	}

}

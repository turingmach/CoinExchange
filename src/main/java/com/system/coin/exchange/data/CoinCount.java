package com.system.coin.exchange.data;

import java.math.BigDecimal;

public class CoinCount {
	
	public CoinCount(BigDecimal coin, Integer count) {
		super();
		this.coin = coin;
		this.count = count;
	}
	
	private BigDecimal coin;
	private Integer count;
	
	public BigDecimal getCoin() {
		return coin;
	}
	public void setCoin(BigDecimal coin) {
		this.coin = coin;
	}
	public Integer getCount() {
		return count;
	}
	public void setCount(Integer count) {
		this.count = count;
	}

}

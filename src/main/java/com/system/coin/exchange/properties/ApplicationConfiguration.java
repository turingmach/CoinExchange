package com.system.coin.exchange.properties;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Component
public class ApplicationConfiguration {

    @Value("${loadCoinsAmt}")
    private int loadCoinsAmt;

    @Value("${coins}")
    private String coinList;

    public Integer getLoadCoinsAmt() {
		return Integer.valueOf(loadCoinsAmt);
    }

	public void setLoadCoinsAmt(int loadCoinsAmt) {
		this.loadCoinsAmt = loadCoinsAmt;
	}

	public String getCoinList() {
		return coinList;
	}

	public void setCoinList(String coinList) {
		this.coinList = coinList;
	}
        
}
package com.system.coin.exchange.properties;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Component
public class ApplicationConfiguration {

    @Value("${loadCoinsAmt}")
    private int loadCoinsAmt;

    public Integer getLoadCoinsAmt() {
		return Integer.valueOf(loadCoinsAmt);
    }

	public void setLoadCoinsAmt(int loadCoinsAmt) {
		this.loadCoinsAmt = loadCoinsAmt;
	}
        
}
package com.system.coin.exchange;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;

@SpringBootApplication
@ComponentScan({"com.system.coin.exchange.controller",
	"com.system.coin.exchange.service",
	"com.system.coin.exchange.properties",
	"com.system.coin.exchange.data"})
public class CoinExchangeApplication {

	public static void main(String[] args) {
		SpringApplication.run(CoinExchangeApplication.class, args);
	}

}

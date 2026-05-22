package huynguyen.exchange_lab.market.config;

import huynguyen.exchange_lab.market.common.PriceEngine;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class MarketConfig {

    @Bean
    public PriceEngine priceEngine() {
        return new PriceEngine();
    }
}

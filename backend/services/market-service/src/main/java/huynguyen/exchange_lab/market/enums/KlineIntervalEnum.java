package huynguyen.exchange_lab.market.enums;

import lombok.Getter;

@Getter
public enum KlineIntervalEnum {
    m1("1m", 60000L),
    m5("5m", 300000L);

    private final long millis;
    private final String code;

    KlineIntervalEnum(String code, long millis) {
        this.code = code;
        this.millis = millis;
    }

}

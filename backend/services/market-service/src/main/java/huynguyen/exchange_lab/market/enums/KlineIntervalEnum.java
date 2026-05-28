package huynguyen.exchange_lab.market.enums;

public enum KlineIntervalEnum {
    ONE_MINUTES(60000L),
    FIVE_MINUTES(300000L);

    private final long millis;

    KlineIntervalEnum(long millis) {
        this.millis = millis;
    }

    public long getMillis() {
        return this.millis;
    }
}

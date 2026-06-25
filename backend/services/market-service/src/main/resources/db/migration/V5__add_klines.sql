create table market_data.trading_pair_kline_5m (
    like market_data.trading_pair_kline_1m
    including all
);

create table market_data.trading_pair_kline_1h (
    like market_data.trading_pair_kline_1m
    including all
);
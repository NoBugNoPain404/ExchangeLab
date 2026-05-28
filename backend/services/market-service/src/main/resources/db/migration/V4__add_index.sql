create index idx_asset_info
on market_data.asset(id)
include(name, ticker, icon_url, status);

create index idx_category_id
on market_data.asset_in_category(category_id);

create index idx_spot_trading_pair_base_asset
on market_data.spot_trading_pair(base_asset_id);

create index idx_spot_trading_pair_quote_asset
on market_data.spot_trading_pair(quote_asset_id);

create index idx_spot_trading_pair_status
on market_data.spot_trading_pair(status);

create index idx_trading_pair_kline_pair_1m
on market_data.trading_pair_kline_1m(
    trading_pair_id,
    start_time desc
);

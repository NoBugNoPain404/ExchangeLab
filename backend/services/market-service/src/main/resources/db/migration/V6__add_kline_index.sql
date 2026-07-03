create index idx_kline_1m_pair on market_data.trading_pair_kline_1m(trading_pair_id, start_time desc );

create index idx_kline_5m_pair on market_data.trading_pair_kline_5m(trading_pair_id, start_time desc );

create index idx_kline_1h_pair on market_data.trading_pair_kline_1h(trading_pair_id, start_time desc );
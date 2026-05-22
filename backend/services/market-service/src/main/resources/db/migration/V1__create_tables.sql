create schema if not exists market_data;

create sequence market_data.asset_id_seq
    as int
    increment by 1
    minvalue 1
    start with 1;

create sequence market_data.category_id_seq
    as int
    increment by 1
    minvalue 1
    start with 1;

create sequence market_data.spot_trading_pair_id_seq
    as int
    increment by 1
    minvalue 1
    start with 1;

create table market_data.category(
    id int default nextval('market_data.category_id_seq') not null primary key,
    name varchar(100) not null unique,
    created_at timestamptz not null default now(),
    updated_at timestamptz not null default now()
);

create table market_data.asset(
    id int default nextval('market_data.asset_id_seq') not null primary key,
    name varchar(50) not null unique,
    ticker varchar(20) not null unique,
    icon_url text not null unique,
    status varchar(20) not null default 'PENDING',
    listed_time timestamptz,
    delisted_time timestamptz,
    created_at timestamptz not null,
    updated_at timestamptz not null
);

create table market_data.asset_in_category(
    asset_id int not null references market_data.asset(id),
    category_id int not null references market_data.category(id),
    primary key (asset_id, category_id)
);

create table market_data.spot_trading_pair(
    id int default nextval('market_data.spot_trading_pair_id_seq') not null primary key,
    symbol varchar(20) unique not null,
    base_asset_id int not null references market_data.asset(id),
    quote_asset_id int not null references market_data.asset(id),
    status varchar(20) not null default 'PENDING',
    listed_time timestamptz,
    delisted_time timestamptz,
    created_at timestamptz not null,
    updated_at timestamptz not null,
    check (base_asset_id <> quote_asset_id)
);

create table market_data.trading_pair_kline(
    interval varchar(10) not null,
    trading_pair_id int not null references market_data.spot_trading_pair(id),
    start_time timestamptz not null,
    open decimal(38,18) not null,
    close decimal(38,18) not null,
    low decimal(38,18) not null,
    high decimal(38,18) not null,
    change decimal(38,18) not null,
    change_percent decimal(10,4) not null,
    volume decimal(38,18) not null,
    quote_volume decimal(38,18) not null,
    trade_count bigint not null,
    taker_buy_volume decimal(38,18) not null,
    taker_buy_quote_volume decimal(38,18) not null,
    primary key (interval, trading_pair_id, start_time)
);
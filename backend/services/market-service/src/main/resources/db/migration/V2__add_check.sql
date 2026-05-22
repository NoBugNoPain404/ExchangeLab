alter table market_data.asset
add constraint check_status_asset
check (
    (
        status = 'PENDING'
            and listed_time is null
            and delisted_time is null
        )
        or
    (
        status in ('LISTED', 'UNAVAILABLE')
            and listed_time is not null
            and delisted_time is null
        )
        or
    (
        status = 'DELISTED'
            and listed_time is not null
            and delisted_time is not null
            and delisted_time > listed_time
        )
    );

alter table market_data.spot_trading_pair
add constraint check_status_spot_trading_pair
    check (
        (
            status = 'PENDING'
                and listed_time is null
                and delisted_time is null
            )
            or
        (
            status in ('LISTED', 'SUSPENDED', 'HALTED')
                and listed_time is not null
                and delisted_time is null
            )
            or
        (
            status = 'DELISTED'
                and listed_time is not null
                and delisted_time is not null
                and delisted_time > listed_time
            )
        );
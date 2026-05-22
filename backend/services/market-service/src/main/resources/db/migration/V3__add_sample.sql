insert into market_data.asset (
    name,
    ticker,
    icon_url,
    status,
    listed_time,
    delisted_time,
    created_at,
    updated_at
) values
-- PENDING
(
    'Bitcoin',
    'BTC',
    'https://cdn.exchange-lab.dev/icons/btc.png',
    'PENDING',
    null,
    null,
    now(),
    now()
),

-- LISTED
(
    'Ethereum',
    'ETH',
    'https://cdn.exchange-lab.dev/icons/eth.png',
    'LISTED',
    now() - interval '30 days',
    null,
    now(),
    now()
),

-- UNAVAILABLE
(
    'Solana',
    'SOL',
    'https://cdn.exchange-lab.dev/icons/sol.png',
    'UNAVAILABLE',
    now() - interval '120 days',
    null,
    now(),
    now()
),

-- DELISTING
(
    'Terra Luna Classic',
    'LUNC',
    'https://cdn.exchange-lab.dev/icons/lunc.png',
    'DELISTED',
    now() - interval '400 days',
    now() - interval '7 days',
    now(),
    now()
);
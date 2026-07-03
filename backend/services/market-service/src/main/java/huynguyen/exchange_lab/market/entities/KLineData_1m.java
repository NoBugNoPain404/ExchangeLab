package huynguyen.exchange_lab.market.entities;

import jakarta.persistence.Entity;
import jakarta.persistence.Table;

@Entity
@Table(name = "trading_pair_kline_1m", schema = "market_data")
public class KLineData_1m extends KlineData {
}

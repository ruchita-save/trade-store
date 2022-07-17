package com.db.tradestore.repository;

import com.db.tradestore.model.Trade;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Date;
import java.util.List;

public interface TradeRepository extends JpaRepository<Trade, Long> {
List<Trade> findByTradeIdContaining(String tradeId);
List<Trade> findByMaturityDateLessThanEqual(Date currentDate);
}

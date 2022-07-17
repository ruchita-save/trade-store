package com.db.tradestore;

import com.db.tradestore.controller.TradeController;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;

@SpringBootTest
@ExtendWith(SpringExtension.class)
class TradeStoreApplicationTests {

	@Autowired
	TradeController tradeController;

	@Test
	void contextLoads() {

		Assertions.assertThat(tradeController).isNotNull();
	}

}

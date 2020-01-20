package org.dmontagnese.changeMaker;

import org.dmontagnese.changeMaker.domain.CoinCount;
import org.dmontagnese.changeMaker.service.ChangeService;
import org.dmontagnese.changeMaker.serviceImpl.ChangeServiceImpl;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class ChangeMakerApplicationTests {

	@Test
	void contextLoads() {
	}

	@Test
	void testZero() {
		float zero =(float) 0.0;
		ChangeService changeService = new ChangeServiceImpl();
		CoinCount coinCount = changeService.getCoinCount((float)zero);
		assertEquals( coinCount.getDollar_amount() , zero);
		
		
		
	}

}

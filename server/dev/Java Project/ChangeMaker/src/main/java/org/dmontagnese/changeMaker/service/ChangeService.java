package org.dmontagnese.changeMaker.service;

import org.dmontagnese.changeMaker.domain.CoinCount;

/**
 * interface that is implemented by ChangeServiceImpl to generate a CoinCount
 * object appropriate to a dollar_amount
 */
public interface ChangeService {

	CoinCount getCoinCount(float dollar_amount);

}
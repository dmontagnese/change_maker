package org.dmontagnese.changeMaker.serviceImpl;

import static org.dmontagnese.changeMaker.util.ServerLog.serverLog;

import java.util.Calendar;

import org.dmontagnese.changeMaker.domain.CoinCount;
import org.dmontagnese.changeMaker.repository.CoinCountRepository;
import org.dmontagnese.changeMaker.service.ChangeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * Performs the coin count calculations to instantiate a CoinCount object and
 * manages caching
 * 
 * The Service methods are called by the Controller and the Service calls
 * methods in the Repository
 */
@Service
public class ChangeServiceImpl implements ChangeService {

	private static final int SILVER_DOLLAR = 100;
	private static final int HALF_DOLLAR = 50;
	private static final int QUARTER = 25;
	private static final int DIME = 10;
	private static final int NICKEL = 5;

	private static final long MAX_CACHE_SIZE = 100;

	/**
	 * No-arg constructor that sets the class name used for logging
	 */
	public ChangeServiceImpl() {
		this.className = this.getClass().getName();
	}

	/**
	 * Checks the cache for an object that corresponds to the dollar_amount. If
	 * it is not found, a CoinCount object is instantiated, the values are
	 * computed and set and the object is returned
	 * 
	 * @param dollar_amount
	 * @return CoinCount
	 */
	@Override
	public CoinCount getCoinCount(float dollar_amount) {
		CoinCount coinCount = new CoinCount();
		int cent_count = (int) (dollar_amount * 100);

		coinCount.setDollar_amount(dollar_amount);

		if (cent_count > 0) {
			// Caching is used to demonstrate it and the Hibernate ORM
			CoinCount cached = coinCountRepository.getCachedCoinCount(dollar_amount);

			if (null == cached) {

				int silver_dollar = cent_count / SILVER_DOLLAR;
				coinCount.setSilver_dollar(silver_dollar);
				cent_count -= SILVER_DOLLAR * silver_dollar;

				int half_dollar = cent_count / HALF_DOLLAR;
				coinCount.setHalf_dollar(half_dollar);
				cent_count -= HALF_DOLLAR * half_dollar;

				int quarter = cent_count / QUARTER;
				coinCount.setQuarter(quarter);
				cent_count -= QUARTER * quarter;

				int dime = cent_count / DIME;
				coinCount.setDime(dime);
				cent_count -= DIME * dime;

				int nickel = cent_count / NICKEL;
				coinCount.setNickel(nickel);
				cent_count -= NICKEL * nickel;

				coinCount.setPenny(cent_count);
				coinCount.setLast_access_time(Calendar.getInstance());

				coinCountRepository.save(coinCount);

				if (coinCountRepository.count() > MAX_CACHE_SIZE) {
					Calendar minAccessTime = coinCountRepository.getMinAccessTime();

					try {
						CoinCount byLastAccessTime = coinCountRepository.getByLastAccessTime(minAccessTime);

						serverLog.info(String.format(
								"%s: Deleting earliest cached record for dollar_value %2.2f to limit cache size to %d",
								className, byLastAccessTime.getDollar_amount(), MAX_CACHE_SIZE));
						// Delete the earliest cached object to limit cache size
						coinCountRepository.delete(byLastAccessTime);
					} catch (Exception exc) {
						serverLog.error(className + ":" + exc.toString());
					}

				}

			} else {
				coinCount = cached;
				// Update the last access time of the object retrieved from
				// cache so that it is not deleted when the cache is truncated
				cached.setLast_access_time(Calendar.getInstance());
				serverLog.info(
						String.format("%s: using cached record for dollar_amount %2.2f", className, dollar_amount));
				coinCountRepository.save(cached);
			}
		}

		return coinCount;
	}

	@Autowired
	private CoinCountRepository coinCountRepository;

	private String className;

}

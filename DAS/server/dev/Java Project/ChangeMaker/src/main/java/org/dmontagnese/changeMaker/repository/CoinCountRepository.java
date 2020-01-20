package org.dmontagnese.changeMaker.repository;

import java.util.Calendar;

import org.dmontagnese.changeMaker.domain.CoinCount;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

/**
 * interface that extends JpaRepository, inheriting all of its methods
 * 
 * Repository uses the Hibernate ORM and the in-memory H2 database The connect
 * configuration, including pool size, is in the 'application.properties' file
 */
@Repository
public interface CoinCountRepository extends JpaRepository<CoinCount, Long> {

	/**
	 * Retrieves a CoinCount object from the database corresponding to a
	 * dollar_amount
	 * 
	 * @param dollar_amount
	 * 
	 * @return CoinCount
	 */
	@Query("FROM CoinCount WHERE dollar_amount = :dollar_amount")
	public CoinCount getCachedCoinCount(@Param("dollar_amount") float dollar_amount);

	/**
	 * Retrieves the earliest last_access_time of an object in the cache for use
	 * in determining the object to delete to limit the cache size
	 * 
	 * @return Calendar
	 */
	@Query("SELECT MIN(last_access_time) FROM CoinCount")
	public Calendar getMinAccessTime();

	/**
	 * Retrieves the object from that database that has the earliest
	 * last_access_time
	 * 
	 * @param last_access_time
	 * 
	 * @return CoinCount
	 */
	@Query("FROM CoinCount WHERE last_access_time = :last_access_time")
	public CoinCount getByLastAccessTime(@Param("last_access_time") Calendar last_access_time);

}

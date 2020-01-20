package org.dmontagnese.changeMaker.domain;

import java.util.Calendar;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

/**
 * A bean that is instantiated and returned to the client containing the
 * computed values appropriate to a dollar_amount
 * 
 * The class is an ORM Entity ( Hibernate ) that, along with being returned to
 * the client, is cached in the database
 */
@Entity
@Table(name = "coin_count")
public class CoinCount {

	/**
	 * No-arg constructor that sets the last_access_time that is used to
	 * determine which object to delete from the database when the cache is full
	 */
	public CoinCount() {
		this.last_access_time = Calendar.getInstance();
	}

	/**
	 * A constructor that sets all values
	 * 
	 * @param dollar_amount
	 * @param silver_dollar
	 * @param half_dollar
	 * @param quarter
	 * @param dime
	 * @param nickel
	 * @param penny
	 * @param total_coin_count
	 * @param last_access_time
	 */
	public CoinCount(float dollar_amount, int silver_dollar, int half_dollar, int quarter, int dime, int nickel,
			int penny, int total_coin_count, Calendar last_access_time) {
		super();
		this.dollar_amount = dollar_amount;
		this.silver_dollar = silver_dollar;
		this.half_dollar = half_dollar;
		this.quarter = quarter;
		this.dime = dime;
		this.nickel = nickel;
		this.penny = penny;
		this.total_coin_count = total_coin_count;
		this.last_access_time = last_access_time;
	}

	public float getDollar_amount() {
		return dollar_amount;
	}

	public void setDollar_amount(float dollar_amount) {
		this.dollar_amount = dollar_amount;
	}

	public int getSilver_dollar() {
		return silver_dollar;
	}

	public void setSilver_dollar(int silver_dollar) {
		this.silver_dollar = silver_dollar;
	}

	public int getHalf_dollar() {
		return half_dollar;
	}

	public void setHalf_dollar(int half_dollar) {
		this.half_dollar = half_dollar;
	}

	public int getQuarter() {
		return quarter;
	}

	public void setQuarter(int quarter) {
		this.quarter = quarter;
	}

	public int getDime() {
		return dime;
	}

	public void setDime(int dime) {
		this.dime = dime;
	}

	public int getNickel() {
		return nickel;
	}

	public void setNickel(int nickel) {
		this.nickel = nickel;
	}

	public int getPenny() {
		return penny;
	}

	public void setPenny(int penny) {
		this.penny = penny;
	}

	public int getTotal_coin_count() {
		total_coin_count = silver_dollar + half_dollar + quarter + dime + nickel + penny;

		return total_coin_count;
	}

	public void setTotal_coin_count(int total_coin_count) {
		this.total_coin_count = total_coin_count;
	}

	public Calendar getLast_access_time() {
		return last_access_time;
	}

	public void setLast_access_time(Calendar last_access_time) {
		this.last_access_time = last_access_time;
	}

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private int coin_count_id;

	@Column(nullable = false, unique = true)
	private float dollar_amount;

	@Column(nullable = false)
	private int silver_dollar;

	@Column(nullable = false)
	private int half_dollar;

	@Column(nullable = false)
	private int quarter;

	@Column(nullable = false)
	private int dime;

	@Column(nullable = false)
	private int nickel;

	@Column(nullable = false)
	private int penny;

	@Column(nullable = false)
	private int total_coin_count;

	@Column(nullable = false)
	private Calendar last_access_time;
}

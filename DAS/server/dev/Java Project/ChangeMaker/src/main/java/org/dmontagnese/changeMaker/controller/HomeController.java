/**
 * The controller that handles HTTP requests
 * 
 * @author Dan Montagnese dmontagnese@gmail.com
 */

package org.dmontagnese.changeMaker.controller;

import java.io.IOException;

import javax.servlet.http.HttpServletResponse;

import org.dmontagnese.changeMaker.domain.CoinCount;
import org.dmontagnese.changeMaker.service.ChangeService;
import org.hibernate.hql.internal.QueryExecutionRequestException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import static org.dmontagnese.changeMaker.util.ServerLog.serverLog;

@Controller
public class HomeController {

	/**
	 * Constructor that sets the className for use in logging
	 */

	public HomeController() {
		this.className = this.getClass().getName();
	}

	/**
	 * @return String - to verify that the application is running
	 */
	@GetMapping("/test")
	public @ResponseBody String test() {
		return "OK";
	}

	/**
	 * Handles the 'GET' request to return the CounCount appropriate to a
	 * dollar_amount
	 * 
	 * @param dollar_amount
	 * @param response
	 *            - used to send an error message back to the client
	 * 
	 * @return CoinCount object ( as JSON ) computed from the dollar_amount
	 * 
	 *         argument
	 */
	@GetMapping("/getChange")
	public @ResponseBody CoinCount getChange(@RequestParam String dollar_amount, HttpServletResponse response) {
		CoinCount coinCount = new CoinCount();

		response.setHeader("Access-Control-Allow-Origin", "*");

		try {
			float f_dollar_amount = Float.parseFloat(dollar_amount);
			coinCount = changeService.getCoinCount(f_dollar_amount);
		}

		catch (QueryExecutionRequestException queryExc) {
			try {
				serverLog.error(className + ": " + queryExc.toString());
				response.sendError(HttpServletResponse.SC_BAD_REQUEST,
						queryExc.getMessage() + " - dollar_amount must be a float");
			} catch (IOException io) {
				serverLog.error(className + ": " + io.toString());
			}

		}

		catch (Exception exc) {
			serverLog.error(className + ": " + exc.toString());

			try {
				response.sendError(HttpServletResponse.SC_BAD_REQUEST,
						exc.getMessage() + " - dollar_amount must be a float");
			} catch (IOException io) {
			}

		}

		return coinCount;
	}

	@Autowired
	private ChangeService changeService;

	private String className;

}

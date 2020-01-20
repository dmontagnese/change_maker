import React, {PureComponent} from "react";
import "./CoinCountTable.css";

class CoinCountTable extends PureComponent {

	render() {
		var list_key = 0;
		var formatted_list = this.props.global_state.coin_count_list.map(
			(c) => { c.dollar_amount = parseFloat(c.dollar_amount ).toFixed(2); return c; }
		);

		var coin_count_map = formatted_list.map (( c ) => 
			<tr key={list_key++}>
				<td>{c.dollar_amount}</td>
				<td>{c.silver_dollar}</td>
				<td>{c.half_dollar}</td>
				<td>{c.quarter}</td>
				<td>{c.dime}</td>
				<td>{c.nickel}</td>
				<td>{c.penny}</td>
				<td>{c.total_coin_count}</td>
			</tr>
		);

		return (

			<div id="coin_counts_container">
				<h3 id="coin_counts">Coin Counts</h3>
				<div id="server_response">
					Server Response: {this.props.global_state.last_response_text}
				</div>

				<div id="coin_counts_content">

					<table id="coin_count_table">
						<thead>
							<tr id="top_row">
								<td>Dollar Amount</td>
								<td>Silver Dollar</td>
								<td>Half Dollar</td>
								<td>Quarter</td>
								<td>Dime</td>
								<td>Nickel</td>
								<td>Penny</td>
								<td>Total</td>
							</tr>
						</thead>
						<tbody>
							{coin_count_map}
						</tbody>
					</table>

				</div>

			</div>

		);
	}

}

export default CoinCountTable;

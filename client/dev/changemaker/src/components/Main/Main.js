import React, {PureComponent} from "react"
import "./Main.css"
import TitleHeader from "../TitleHeader/TitleHeader"
import DollarForm from "../DollarForm/DollarForm"
import CoinCountTable from "../CoinCountTable/CoinCountTable"

class Main extends PureComponent {

	constructor(props) {
		super(props);

		this.state = {
			coin_count_list: [],
			request_count: 0,
			last_response_text: ""
		};

		this.setGlobalState = this.setGlobalState.bind(this);
		this.processRequest = this.processRequest.bind(this);

		var url = new URL( window.location );
		var host_array = url.host.split(":");
		this.server_url = url.protocol + "//" + host_array[0] + ":8080/ChangeMaker/";
		this.get_change_url = this.server_url + "getChange?dollar_amount=";
		this.xhr = new XMLHttpRequest();

		this.request_count = 0;
	}

  processRequest() {

			if ( this.xhr.readyState === 4 ) {

					if ( this.xhr.status === 200 ) {
						var response = JSON.parse( this.xhr.responseText );
						var new_coin_count_list = this.state.coin_count_list;
						// Use a uniform format to avoid duplicates in the table
						response.dollar_amount = parseFloat( response.dollar_amount ).toFixed(2);
						new_coin_count_list.push( response ); 
						var new_state = {};
						new_state.coin_count_list = new_coin_count_list;
						new_state.request_count = ++this.request_count;
						new_state.last_response_text = this.xhr.responseText;
						this.setState( new_state );
				} else {
						var error_state = {};
						error_state.coin_count = this.state.coin_count_list;

						error_state.last_response_text = "ERROR: " + this.xhr.status + ": " + this.xhr.statusText;
						error_state.request_count = ++this.request_count;
						this.setState( error_state );
				}

			} 

	}

	setGlobalState( dollar_amount ) {
		// Use a uniform format to avoid duplicates in the table
		dollar_amount = parseFloat( dollar_amount).toFixed(2);
		//console.log( "dollar_amount: " + dollar_amount );
		var is_duplicate = false;

		//console.log( "list: " + this.state.coin_count_list );

		for ( var index in this.state.coin_count_list ) {
			var coin_count = this.state.coin_count_list[index];
			
			//console.log( "c: " + coin_count.dollar_amount );
			if ( dollar_amount === coin_count.dollar_amount ) { 
				//console.log( "duplicate" );
				is_duplicate = true;
				break;
			}

		}

		if ( false === is_duplicate ) {
			var request = this.get_change_url + dollar_amount;
			this.xhr.open( "GET", request, true );
			this.xhr.send();

			this.xhr.addEventListener( "readystatechange", this.processRequest, false );
		}

	}

	render() {

		return (

			<div id="main_container">
				<TitleHeader/>
				<DollarForm setGlobalState={this.setGlobalState}/>
				<CoinCountTable global_state={this.state}/>
			</div>

		)
	}
}

export default Main;

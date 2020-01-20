import React, {PureComponent} from "react";
import "./DollarForm.css";

class DollarForm extends PureComponent {

	constructor(props) {
		super(props);

		this.editDollarAmount = this.editDollarAmount.bind(this);
		this.getChange = this.getChange.bind(this);

		this.dollar_pattern = new RegExp( /(\d*\.?\d{0,2})/ );
		this.current_dollar_amount = "";

	}

	editDollarAmount( event) {
		var target = event.target;
		var dollar_amount = target.value;

		// Check that the dollar_amount is valid
		dollar_amount = dollar_amount.trim();
		var valid_dollar_amount = this.dollar_pattern.exec( dollar_amount );

		if ( valid_dollar_amount ) {
			target.value = valid_dollar_amount[1];
			this.current_dollar_amount = valid_dollar_amount[1];
			//console.log( valid_dollar_amount );
		} else {
			// Since the dollar_amount is invalid, replace it with the list valid value
			target.value = this.current_dollar_amount;
		}

	}

	getChange( event ) {

		if ( this.current_dollar_amount.length > 0 ) {
			//console.log( this.current_dollar_amount );
			this.props.setGlobalState( this.current_dollar_amount );
			document.getElementById( "dollar_input").value = "";
		}

	}

	render() {

		return (

			<div id="dollar_container">
				<label id="dollar_form_label">Enter Dollar Amount to get change $</label>
				<input onChange={this.editDollarAmount} id="dollar_input" name="dollar_input" type="text"/>
				<input type="button" onClick={this.getChange} id="get_change_button" name="get_change_button" value="Get Change"/>

			</div>

		);
	}
}

export default DollarForm;

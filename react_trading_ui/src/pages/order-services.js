import React from 'react';
import BuyForm from '../forms/buy-form';
import SellForm from '../forms/sell-form';

export default class OrderServices extends React.Component {

    constructor(props) {
        super(props);
        this.lastButton = false;
        this.forms = {
            buy: <BuyForm />,
            sell: <SellForm />,
        };
        this.state = {
            currentEndpoint: [],
            currentEndpointName: "",
        };
    }

    loadForm(event) {
        const button = event.target;
        button.disabled = true;
        if(this.lastButton) {
            this.lastButton.disabled = false;
        }
        this.lastButton = button;
        this.setState({
            currentEndpoint: this.forms[button.id],
            currentEndpointName: button.innerText,
        });
    }

    render() {
        return(
        <div style={{display:"flex", flexDirection:"column"}} >
            <div className="button-row">
                <button id="buy" onClick={e => this.loadForm(e)}>Buy Stock</button>
                <button id="sell" onClick={e => this.loadForm(e)}>Sell Stock</button>
            </div>
            <h3 style={{alignSelf:"center"}}>{this.state.currentEndpointName}</h3>
            {this.state.currentEndpoint}
        </div>
        );
    }
}
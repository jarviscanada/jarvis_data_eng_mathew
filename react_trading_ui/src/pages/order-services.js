import React from 'react';
import OrderForm from '../forms/order-form';

export default class OrderServices extends React.Component {

    constructor(props) {
        super(props);
        this.buttonRef = React.createRef();
        this.lastButton = false;
        this.forms = {
            buy: <OrderForm id="buy" key="buy" op="buy" />,
            sell: <OrderForm id="sell" key="sell" op="sell" />,
        };
        this.state = {
            currentEndpoint: [],
            currentEndpointName: "",
        };
    }

    componentDidMount() {
        this.lastButton = this.buttonRef.current;
        this.lastButton.disabled = true;
        this.setState({
            currentEndpoint: this.forms[this.lastButton.id],
            currentEndpointName: this.lastButton.innerText,
        })
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
                <button ref={this.buttonRef} id="buy" onClick={e => this.loadForm(e)}>Buy Stock</button>
                <button id="sell" onClick={e => this.loadForm(e)}>Sell Stock</button>
            </div>
            <h3>{this.state.currentEndpointName}</h3>
            {this.state.currentEndpoint}
        </div>
        );
    }
}
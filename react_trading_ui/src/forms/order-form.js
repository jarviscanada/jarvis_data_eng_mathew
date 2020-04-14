import React from 'react';
import Fetch from 'node-fetch';
import JsonComponent from '../components/json-component';

export default class OrderForm extends React.Component {

    constructor(props) {
        super(props);
        this.baseUrl = "";
        if(this.props.op==="buy") {
            this.baseUrl = `${process.env.REACT_APP_API_URL}:${process.env.REACT_APP_API_PORT}/order/buy`;
        } else if(this.props.op==="sell") {
            this.baseUrl = `${process.env.REACT_APP_API_URL}:${process.env.REACT_APP_API_PORT}/order/sell`;
        }
        this.state = {
            message: [],
            amount: "",
            symbol: "",
            accountId: "",
        };
    }

    updateNumber(event) {
        if(/^\d*$/.test(event.target.value)){
            this.setState({
                [event.target.id]: event.target.value,
            });
        }
    }

    updateText(event) {
        this.setState({
            symbol: event.target.value.toUpperCase(),
        });
    }

    placeOrder(event) {
        event.preventDefault();
        Fetch(`${this.baseUrl}?accountid=${this.state.accountId}&symbol=${this.state.symbol}&amount=${this.state.amount}`,
        {
            method:"post",
        }).then(res => {
            if(res.ok) {
                return res.json();
            } else {
                return (res.json().then(err => {
                    return ({error: err["error"], message: err["message"]});
                }));
            }
        }).then(json => {
            if(json.size) {
                json.size = Math.abs(json.size);
            }
            this.setState({
                message: <JsonComponent json={json} />
            });
        }).catch(err => console.log(err));
    }

    render() {
        return(
            <div style={{display:"flex"}} >
                    <form className="input-form" onSubmit={(e) => this.placeOrder(e)}>
                        <label>
                            <p className="input-label">Account ID:</p>
                            <input type="text" id="accountId" className="input-field" required
                            onChange={(e) => this.updateNumber(e)} value={this.state.accountId} />
                        </label>
                        <label>
                            <p className="input-label">Symbol:</p>
                            <input type="text" id="symbol" className="input-field" required
                            onChange={(e) => this.updateText(e)} value={this.state.symbol} />
                        </label>
                        <label>
                            <p className="input-label">Amount:</p>
                            <input type="text" id="amount" className="input-field" required
                            onChange={(e) => this.updateNumber(e)} value={this.state.amount} />
                        </label>
                        <input type="submit" value="Submit" className="input-submit" />
                    </form>
                {this.state.message}
            </div>
        );
    }
}
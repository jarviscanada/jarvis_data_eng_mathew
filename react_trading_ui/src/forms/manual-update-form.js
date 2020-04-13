import React from 'react';
import Fetch from 'node-fetch';
import JsonComponent from '../components/json-component';

const url = "http://localhost:8080/quote/update";

export default class ManualUpdate extends React.Component {

    constructor(props) {
        super(props);
        this.moneyPattern = /^\d*(\.\d{0,2})?$/;
        this.state = {
            message: "",
            askPrice: "",
            bidPrice: "",
            lastPrice: "",
            askSize: "",
            bidSize: "",
            symbol: "",
        };
    }

    textUpdate(event) {
        this.setState({
            [event.target.id]: event.target.value,
        });
    }

    moneyUpdate(event) {
        if(this.moneyPattern.test(event.target.value)) {
            this.setState({
                [event.target.id]: event.target.value,
            });
        }
    }

    sendUpdate(event) {
        event.preventDefault();
        const quote = {
            askPrice: this.state.askPrice,
            askSize: this.state.askSize,
            bidPrice: this.state.bidPrice,
            bidSize: this.state.bidSize,
            lastPrice: this.state.lastPrice,
            ticker: this.state.symbol,
            id: this.state.symbol,
        }
        Fetch(url, {
            method: "put",
            headers: {"Content-Type": "application/json"},
            body: JSON.stringify(quote),
        }).then(res => {
            if(res.ok) {
                return res.json();
            } else {
                res.text().then(err => console.log(err));
            }
        }).then(json => {
            this.setState({
                message: (<JsonComponent json={json} />),
            });
        }).catch(err => console.log(err));
    }

    render() {
        return(
            <div style={{display:"flex"}}>
                <form className="input-form" onSubmit={(e) => this.sendUpdate(e)}>
                    <label>
                        Symbol:
                        <input type="text" id="symbol" onChange={(e) => this.textUpdate(e)} value={this.state.symbol}
                        className="input-field" />
                    </label>
                    <label>
                        Ask Price:
                        <input type="text" id="askPrice" onChange={(e) => this.moneyUpdate(e)} value={this.state.askPrice}
                        className="input-field" />
                    </label>
                    <label>
                        Ask Size:
                        <input type="number" id="askSize" onChange={(e) => this.textUpdate(e)} value={this.state.askSize}
                        className="input-field" />
                    </label>
                    <label>
                        Bid Price:
                        <input type="text" id="bidPrice" onChange={(e) => this.moneyUpdate(e)} value={this.state.bidPrice}
                        className="input-field" />
                    </label>
                    <label>
                        Bid Size:
                        <input type="number" id="bidSize" onChange={(e) => this.textUpdate(e)} value={this.state.bidSize}
                        className="input-field"/>
                    </label>
                    <label>
                        Last Price:
                        <input type="text" id="lastPrice" onChange={(e) => this.moneyUpdate(e)} value={this.state.lastPrice}
                        className="input-field" />
                    </label>
                    <input type="submit" value="Submit" />
                </form>
                {this.state.message}
            </div>
        );
    }
}
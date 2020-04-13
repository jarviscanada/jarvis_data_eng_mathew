import React from 'react';
import Fetch from 'node-fetch';
import JsonComponent from '../components/json-component';

const baseUrl = 'http://localhost:8080/trader/deposit'

export default class WithdrawForm extends React.Component {

    constructor(props){
        super(props);
        this.state = {
            message: [],
            user: '0',
            amount: '0.00',
        }
    }

    withdraw(e) {
        e.preventDefault();
        const url = `${baseUrl}/${this.state.user}/?amount=${this.state.amount}`

        Fetch(url, {
            method: 'put',
            headers: {"Content-Type":"application/json"},
        }).then(res => {
            if(res.ok) {
                return res.json();
            } else {
                return (res.json().then(err => {
                    return({error: err['error'], message: err['message']});
                }));
            }
        }).then(json => {
            this.setState({
                message: <JsonComponent json={json} />
            })
        }).catch(err => console.log(err));
    }

    updateInput(event) {
        const field = event.target;
        const pattern = field.id === 'user'? /^\d*$/ : /^\d*(\.\d{0,2})?$/;
        if (pattern.test(field.value)){ //Try to enforce pattern by refusing changes that break it
            this.setState({
                [field.id]: field.value,
            });
        }
    }

    render() {
        return(
            <div style={{display:'flex'}}>
                <form className="input-form" onSubmit={(e) => this.withdraw(e)}>
                    <label>
                        <p className="input-label">User ID:</p>
                        <input id='user' type='text' onChange={(e) => this.updateInput(e)} required
                          value={this.state.user} className="input-field" />
                    </label>
                    <label>
                        <p className="input-label">Amount($)</p>
                        <input id='amount' type='text' onChange={(e) => this.updateInput(e)} required
                          value={this.state.amount} className="input-field" /> 
                    </label>
                    <input type='submit' value='Submit' className="input-submit" />
                </form>
                {this.state.message}
            </div>
        );
    }
}
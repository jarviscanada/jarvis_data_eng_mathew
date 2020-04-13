import React from 'react';
import CreateForm from '../forms/create-form';
import SingleFieldForm from '../forms/single-field-form';
import DepositForm from '../forms/deposit-form';
import WithdrawForm from '../forms/withdraw-form';

export default class AccountServices extends React.Component {

    constructor(props) {
        super(props);
        this.buttonRef = React.createRef();
        this.lastButton = false;
        this.forms = {
            "create": <CreateForm />,
            "delete": <SingleFieldForm label="User ID:" verb="delete"
              url="http://localhost:8080/trader/delete" />,
            "deposit": <DepositForm />,
            "withdraw": <WithdrawForm />,
        }
        this.state = {
            currentEndpoint: "",
            currentEndpointName: "",
        };
    }

    componentDidMount() {
        this.lastButton = this.buttonRef.current;
        this.lastButton.disabled = true;
        this.setState({
            currentEndpoint: this.forms[this.lastButton.id],
            currentEndpointName: this.lastButton.innerText,
        });
    }

    loadForm(event) {
        const clicked = event.target;
        if(this.lastButton) {
            this.lastButton.disabled=false;
        }
        clicked.disabled=true;
        this.lastButton=clicked;
        this.setState({
            currentEndpointName: clicked.innerText,
            currentEndpoint: this.forms[clicked.id],
        })
    }

    render() {
        return(
            <div style={{width:"100%"}}>
                <div className="button-row">
                    <button ref={this.buttonRef} id="create" onClick={(e) => this.loadForm(e)}>Create</button>
                    <button id="delete" onClick={(e) => this.loadForm(e)}>Delete</button>
                    <button id="deposit" onClick={(e) => this.loadForm(e)}>Deposit funds</button>
                    <button id="withdraw" onClick={(e) => this.loadForm(e)}>Withdraw funds</button>
                </div>
                <h3>{this.state.currentEndpointName}</h3>
                {this.state.currentEndpoint}
            </div>
        );
    }
}
import React from 'react';
import SingleFieldForm from '../forms/single-field-form';
import PortfolioForm from '../forms/portfolio-form';

export default class Dashboards extends React.Component {

    constructor(props) {
        super(props);
        this.lastButton = false;
        this.forms = {
            "profile": <SingleFieldForm label="User ID:" verb="get"
            url="http://localhost:8080/dashboard" suffix="profile"/>,
            "portfolio": <PortfolioForm />,
        };
        this.state = {
            currentEndpoint: <p>Placeholder</p>,
            currentEndpointname: "",
        }
    }

    loadForm(event) {
        event.target.disabled = true;
        if(this.lastButton) {
            this.lastButton.disabled = false;
        }
        this.lastButton = event.target;
        this.setState({
            currentEndpoint: this.forms[event.target.id],
            currentEndpointname: event.target.innerText,
        });
    }

    render() {
        return(
            <div style={{width:"100%"}}>
                <div className="button-row">
                    <button id="profile" onClick={(e) => this.loadForm(e)}>Profiles</button>
                    <button id="portfolio" onClick={(e) => this.loadForm(e)}>Portfolios</button>
                </div>
                {this.state.currentEndpoint}
            </div>
        );
    }
}
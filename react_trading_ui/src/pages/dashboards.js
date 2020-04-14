import React from 'react';
import SingleFieldForm from '../forms/single-field-form';
import PortfolioForm from '../forms/portfolio-form';

export default class Dashboards extends React.Component {

    constructor(props) {
        super(props);
        this.buttonRef = React.createRef();
        this.lastButton = false;
        this.forms = {
            "profile": <SingleFieldForm label="User ID:" verb="get"
            url={`${process.env.REACT_APP_API_URL}:${process.env.REACT_APP_API_PORT}/dashboard`} suffix="profile"/>,
            "portfolio": <PortfolioForm />,
        };
        this.state = {
            currentEndpoint: "",
            currentEndpointName: "",
        }
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
        event.target.disabled = true;
        if(this.lastButton) {
            this.lastButton.disabled = false;
        }
        this.lastButton = event.target;
        this.setState({
            currentEndpoint: this.forms[event.target.id],
            currentEndpointName: event.target.innerText,
        });
    }

    render() {
        return(
            <div style={{width:"100%"}}>
                <div className="button-row">
                    <button ref={this.buttonRef} id="profile" onClick={(e) => this.loadForm(e)}>Profiles</button>
                    <button id="portfolio" onClick={(e) => this.loadForm(e)}>Portfolios</button>
                </div>
                <h3>{this.state.currentEndpointName}</h3>
                {this.state.currentEndpoint}
            </div>
        );
    }
}
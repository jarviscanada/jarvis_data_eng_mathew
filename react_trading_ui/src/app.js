import React from 'react';
import Welcome from './pages/welcome';
import AccountServices from './pages/account-services';
import QuoteServices from './pages/quote-services';
import Dashboards from './pages/dashboards';
import OrderServices from './pages/order-services';

// This is the top-level component definition. It defines a sidebar with buttons to swap between
// the various endpoints, and a main content area to display endpoint-specific information.
export default class App extends React.Component {

    constructor(props) {
        super(props);
        this.welcomeButton = React.createRef();
        this.pages = { // List of all the "Pages" in the app. The app is all under one address.
            "welcome": <Welcome />,
            "account": <AccountServices />,
            "quote": <QuoteServices />,
            "dash": <Dashboards />,
            "order": <OrderServices />,
        }
        this.state = {
            currentEndpoint: "Welcome", //Name of the endpoint
            currentEndpointData: <Welcome />, //Content to display in main-content div
        };
    }

    // Disable the welcome button since we start on that page.
    componentDidMount(){
        this.inactiveButton = this.welcomeButton.current;
        this.inactiveButton.disabled=true;
    }

    // Called when a main menu button is clicked. Disables the button and loads the associated page.
    loadPage(e) {
        e.target.disabled=true; // Disable the clicked button
        this.inactiveButton.disabled=false; // Enable the last button
        this.inactiveButton=e.target;
        if(this.pages[e.target.id]){
            this.setState({
                currentEndpoint: e.target.innerText,
                currentEndpointData: this.pages[e.target.id],
            });
        }
    }

    // Set up the top-level page layout.
    render() {           
        return(
            <div className="wrapper">
                <div className="sidebar">
                    <h2 style={{textAlign:"center"}}>Main Menu</h2>
                    <button id="welcome" onClick={(e) => this.loadPage(e)} ref={this.welcomeButton}>Welcome Page</button>
                    <button id="account" onClick={(e) => this.loadPage(e)}>Account Services</button>
                    <button id="quote" onClick={(e) => this.loadPage(e)}>Quote Services</button>
                    <button id="order" onClick={(e) => this.loadPage(e)}>Order Services</button>
                    <button id="dash" onClick={(e) => this.loadPage(e)}>User Dashboards</button>
                </div>
                <div className="main-content">
                    <h2 style={{width:"100%", textAlign:"center"}}>{this.state.currentEndpoint}</h2>
                    {this.state.currentEndpointData}
                </div> 
            </div>
        );
    }
}
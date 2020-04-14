import React from 'react';
import SingleFieldForm from '../forms/single-field-form';
import DailyListView from '../components/daily-list-view';
import UpdateAll from '../components/update-all-view';
import ManualUpdate from '../forms/manual-update-form';

export default class QuoteServices extends React.Component {

    constructor(props) {
        super(props);
        this.buttonRef = React.createRef();
        this.lastButton = false;
        this.forms = {
            "iex": <SingleFieldForm label="Symbol:" verb="get"
              url={`${process.env.REACT_APP_API_URL}:${process.env.REACT_APP_API_PORT}/quote/iex/ticker`} />,
            "track": <SingleFieldForm label="Symbol:" verb="post"
              url={`${process.env.REACT_APP_API_URL}:${process.env.REACT_APP_API_PORT}/quote/track`} />,
            "daily": <DailyListView />,
            "updateall": <UpdateAll />,
            "manual": <ManualUpdate />,
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
        })
    }

    loadForm(event) {
        const clicked = event.target;
        if(this.lastButton) {
            this.lastButton.disabled = false;
        }
        clicked.disabled = true;
        this.lastButton = clicked;
        this.setState({
            currentEndpointName: clicked.innerText,
            currentEndpoint: this.forms[clicked.id],
        });
    }

    render() {
        return (
        <div style={{width:"100%"}}>
            <div className="button-row">
                <button ref={this.buttonRef} id="iex" onClick={e => this.loadForm(e)}>Quote from IEX</button>
                <button id="track" onClick={e => this.loadForm(e)}>Track Symbol</button>
                <button id="daily" onClick={e => this.loadForm(e)}>Daily List</button>
                <button id="updateall" onClick={e => this.loadForm(e)}>Update Tracked</button>
                <button id="manual" onClick={e => this.loadForm(e)}>Manual Update</button>
            </div>
            <h3>{this.state.currentEndpointName}</h3>
            {this.state.currentEndpoint}
        </div>
        );
    }
}
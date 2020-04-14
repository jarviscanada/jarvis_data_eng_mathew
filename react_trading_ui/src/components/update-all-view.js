import React from 'react';
import Fetch from 'node-fetch';

const url = `${process.env.REACT_APP_API_URL}:${process.env.REACT_APP_API_PORT}/quote/iexMarketData`;

//This component triggers a market data update in the Springboot app
//The message shows if the update completed or not.
export default class UpdateAll extends React.Component {

    constructor(props) {
        super(props);
        this.state = {
            message: "",
        };
    }

    componentDidMount() {
        Fetch(url, {method:"put"}).then( res => {
            if(res.ok) {
                this.setState({
                    message: (<p>Update Successful</p>),
                });
            } else {
                this.setState({
                    message: (<p>Update Failed</p>),
                });
            }
        });
        this.setState({
            message: (<p>Please wait...</p>),
        })
    }

    render() {
        return (
            this.state.message
        );
    }
}
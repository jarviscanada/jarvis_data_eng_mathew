import React from 'react';
import Fetch from 'node-fetch';

const url = "http://localhost:8080/quote/iexMarketData";

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
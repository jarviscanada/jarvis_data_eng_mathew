import React from 'react';
import Fetch from 'node-fetch';
import Kvp from '../components/kvp-component';
import JsonComponent from '../components/json-component';

// While this is technically a single field form, The returned JSON has a nested array,
// which messes with JsonComponent a bit as array indices are treated as objects.
// The output for this endpoint also lends itself to folding, so we do that.
export default class PortfolioForm extends React.Component {

    constructor(props) {
        super(props);
        this.state = {
            message:[],
            id:"",
        };
    }

    // User ID field should only ever be a number
    updateField(event) {
        if(/^\d*$/.test(event.target.value)) {
            this.setState({
                id: event.target.value,
            });
        }
    }

    // Get the user's portfolio from Springboot
    getPortfolio(event) {
        event.preventDefault();
        Fetch(`${process.env.REACT_APP_API_URL}:${process.env.REACT_APP_API_PORT}/dashboard/${this.state.id}/portfolio`).then( res => {
            if(res.ok) {
                return res.json();
            } else {
                return res.json().then(err => {
                    return {error: err.error, message:err.message};
                });
            }
        }).then(data => {
            let jsx;
            if(data.hasOwnProperty("error")) { //Check if an error happened previously
                this.setState({
                    message: <JsonComponent json={data} />, //Output the error message
                });
                return;
            } else {
                jsx = this.convertToJsx(data);
            }
            this.setState({
                message: jsx,
            });
        }).catch(err => console.log(err));
    }

    // Folds/unfolds the div the clicked button is associated with
    showHide(event) {
        const button = event.target;
        if(this.state[button.id]){
            let info = this.state[button.id];
            if(info.style.display === "none") {
                info.style.display = "flex";
                button.innerText = "v";
            } else {
                info.style.display = "none";
                button.innerText = ">";
            }
        } else {
            console.log("ref not set");
        }
    }

    // Convert the user's Portfolio into JSX. An unknown number of Positions may be present,
    // so we check if there's any to work with, then map them to JSX representations.
    convertToJsx(data) {
        return(
            <div style={{display:"flex", flexDirection:"column"}}>
                <Kvp kvpKey={"Trader ID"} value={data.traderId} />
                <Kvp kvpKey={"Funds"} value={data.funds} />
                <div style={{display:"flex"}}>
                    <p>Positions:</p>
                    <div style={{display:"flex", flexDirection:"column"}}>
                        {data.positions.length <= 0? <p>None</p> : //If there's no Positions, just say "None"
                            data.positions.map(pos => {
                            return(
                            <div key={pos.position.ticker} style={{display:"flex", alignItems:"flex-start"}}>
                                <p>{pos.position.ticker}</p>
                                <button id={pos.position.ticker} onClick={(e) => this.showHide(e)}>></button>
                                <div style={{display:"none"}} ref={ref => this.setState({[pos.position.ticker]: ref})}>
                                    <div style={{display:"flex", flexDirection:"column"}}>
                                        <p style={{textDecoration:"underline"}}>Position</p>
                                        <JsonComponent json={pos.position} />
                                    </div>
                                    <div style={{display:"flex", flexDirection:"column"}}>
                                        <p style={{textDecoration:"underline"}}>Quote</p>
                                        <JsonComponent json={pos.quote} />
                                    </div>
                                </div>
                            </div>
                            );
                        })}
                    </div>
                </div>
            </div>
        );
    }

    render() {
        return(
        <div style={{display:"flex"}}>
            <form className="input-form" onSubmit={(e) => this.getPortfolio(e)}>
                <label>
                    <p className="input-label">User ID:</p>
                    <input type="text" onChange={(e) => this.updateField(e)} value={this.state.id} 
                    className="input-field" />
                </label>
                <input type="submit" value="Submit" className="input-submit" />
            </form>
            {this.state.message}
        </div>
        );
    }
}
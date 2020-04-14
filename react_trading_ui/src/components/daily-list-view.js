import React from 'react';
import Fetch from 'node-fetch';
import JsonComponent from './json-component';

const url = `${process.env.REACT_APP_API_URL}:${process.env.REACT_APP_API_PORT}/quote/dailylist`

export default class DailyListView extends React.Component {

    constructor(props) {
        super(props);
        this.state = {
            quotesJsx: [],
        };
    }

    componentDidMount() {
        this.fetchQuotes();
    }

    fetchQuotes() {
        let quotes = [];

        Fetch(url).then( res => {
            if(res.ok) {
                return res.json();
            }
        }).then(json => {
            json.forEach(quote => {
                quotes.push(quote); 
            });
            return quotes;
        }).then(quotes => this.convertQuotes(quotes)).catch(err => console.log(err));
    }

    convertQuotes(quotes) {
        let jsx = [];
        quotes.forEach(quote => {
            jsx.push(
                <div key={quote.ticker} style={{display:"flex", alignItems:"flex-start"}}>
                    <button id={quote.ticker} onClick={e => this.showHide(e)}>></button>
                    <p>{quote.ticker}</p>
                    <div style={{display:"none"}} ref={ref => this.setState({[quote.ticker]: ref})} >
                        <JsonComponent json={quote} />
                    </div>
                </div>
            );
        });
        this.setState({
            quotesJsx: jsx,
        });
    }

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

    render() {
        return(
            <div style={{display:"flex", flexDirection:"column"}}>
                {this.state.quotesJsx}
            </div>
        );
    }
}
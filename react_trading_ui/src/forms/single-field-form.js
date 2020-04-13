import React from 'react';
import Fetch from 'node-fetch';
import JsonComponent from '../components/json-component';

/* 
 * This class presents a generic single field form for use with any endpoint which only accepts
 * a single value as part of the url path, such as /dashboard/{userID}/portfolio or /quote/track/{symbol}
 * Props are: label, url (endpoint url), verb (http verb), and optionally suffix (URL suffix, if param is not last)
 */
export default class SingleFieldForm extends React.Component {

    constructor(props) {
        super(props);
        this.state = {
            message: [],
            fieldText: '',
        }
    }

    // Keeps React state consistent with Form value.
    updateText(e) {
        this.setState({
            fieldText: e.target.value,
        });
    }

    // Call the endpoint and render the result.
    submitForm(event) {
        event.preventDefault();
        let url = `${this.props.url}/${this.state.fieldText}`;
        if (this.props.suffix) {
            url = `${url}/${this.props.suffix}`;
        }
        const verb = this.props.verb;

        // Send the request and render the result using JsonComponent
        Fetch(url, {
            method: verb,
            headers: {"Content-Type":"application/json",},
        }).then(res => {
            let responseBody;
            if (res.ok){
                responseBody = res.json().catch(err => {
                    return ({status: "Success"})
                });
            } else {
                responseBody = res.json().then(err => {
                    return({error: err['error'],message:err['message']});
                });
            }
            return responseBody;
        }).then(json => this.setState({
            message:<JsonComponent json={json} />,
        })).catch(err => this.setState({message:<p>Request Failed<br/>{err}</p>}));
    }

    render() {
        return (
            <div style={{display:'flex'}}>
                <form className='input-form' onSubmit={(e) => this.submitForm(e)}>
                    <label>
                        <p className="input-label">{this.props.label}</p>
                        <input type='text' onChange={(e) => this.updateText(e)} value={this.state.fieldText}
                          required={true} className="input-field" />
                    </label>
                    <input type='submit' value='Submit' className="input-submit" />
                </form>
                {this.state.message}
            </div>
        );
    }
}
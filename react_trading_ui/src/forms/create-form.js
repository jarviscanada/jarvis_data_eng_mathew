import React from 'react';
import Fetch from 'node-fetch';
import JsonComponent from '../components/json-component';

const endpointUrl = "http://localhost:8080/trader/create/prebuilt"

export default class CreateForm extends React.Component {

    constructor(props) {
        super(props);
        this.state = {
            message: "",
            firstName: "",
            lastName: "",
            email: "",
            country: "",
            dob:"",
            id: 0,
        };
    }

    createUser(event) {
        event.preventDefault();
        const button = event.target;
        const {message, ...user} = this.state;
        button.disabled=true;

        Fetch(endpointUrl, {
            method: "post",
            body: JSON.stringify(user),
            headers: { "Content-Type": "application/json"},
        }).then(res => {
            if(res.ok) {
                return res.json();
            } else {
                throw new Error("Request failed.");
            }
        }).then(json => this.setState({
            message: <JsonComponent json={json} />,
        })).catch(err => console.log(err));
    }

    update(event) {
        const textBox = event.target;
        this.setState({
            [textBox.id]: textBox.value,
        });
    }

    render() {
        return(
            <div style={{display:"flex", flexDirection:"row"}}>
            <form className="input-form" onSubmit={(e) => this.createUser(e)}>
                <label>
                    First Name:
                    <input type="text" id="firstName" value={this.state.fName} onChange={(e) => this.update(e)}
                    className="input-field" />
                </label>
                <label>
                    Last Name:
                    <input type="text" id="lastName" value={this.state.lName} onChange={(e) => this.update(e)}
                    className="input-field" />
                </label>
                <label>
                    Email Address:
                    <input type="text" id="email" value={this.state.email} onChange={(e) => this.update(e)}
                      required={true} className="input-field" />
                </label>
                <label>
                    Country:
                    <input type="text" id="country" value={this.state.country} onChange={(e) => this.update(e)}
                    className="input-field" />
                </label>
                <label>
                    Birthdate:
                    <input type="date" id="dob" value={this.state.birthdate} onChange={(e) => this.update(e)}
                    className="input-field" />
                </label>
                <input type="submit" value="Submit" />
            </form>
            {this.state.message}
            </div>
        );
    }
}
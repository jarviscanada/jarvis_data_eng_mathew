import React from 'react';
import Fetch from 'node-fetch';
import JsonComponent from '../components/json-component';

const endpointUrl = `${process.env.REACT_APP_API_URL}:${process.env.REACT_APP_API_PORT}/trader/create/prebuilt`

//This form is used to create a new user. Shows the created user on success.
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

    //Send the form data to the API and parse the response
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
                    <p className="input-label">First Name:</p>
                    <input type="text" id="firstName" value={this.state.fName} onChange={(e) => this.update(e)}
                    className="input-field" />
                </label>
                <label>
                    <p className="input-label">Last Name:</p>
                    <input type="text" id="lastName" value={this.state.lName} onChange={(e) => this.update(e)}
                    className="input-field" />
                </label>
                <label>
                    <p className="input-label">Email Address:</p>
                    <input type="text" id="email" value={this.state.email} onChange={(e) => this.update(e)}
                      required={true} className="input-field" />
                </label>
                <label>
                    <p className="input-label">Country:</p>
                    <input type="text" id="country" value={this.state.country} onChange={(e) => this.update(e)}
                    className="input-field" />
                </label>
                <label>
                    <p className="input-label">Birthdate:</p>
                    <input type="date" id="dob" value={this.state.birthdate} onChange={(e) => this.update(e)}
                    className="input-field" />
                </label>
                <input type="submit" value="Submit" className="input-submit" />
            </form>
            {this.state.message}
            </div>
        );
    }
}
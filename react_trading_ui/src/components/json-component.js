import React from 'react';
import Kvp from './kvp-component';

/* This function provides a simple means of displaying the KVPs of an object or JSON
 * document (from Fetch's response.json()) passed to it. Nested objects are handled
 * but nested arrays are treated as objects.
 */
export default function JsonComponent(props) {
    let rows = [];
    const parent = props.parent? props.parent : "";

    for (const key in props.json) {
        const value = props.json[key];

        // If the value's an object, render the object as a JsonComponent.
        if(typeof(value) === "object") {
            rows.push(
                <div key={parent + key} style={{display:"flex",flexDirection:"row"}}>
                    <p style={{float:"left",order:0}} key={key}>{key + ":"}</p>
                    <JsonComponent id={key} json={value} parent={key}/>
                </div>
            );
        } else {
            rows.push(<Kvp key={key + "val"} kvpKey={key} value={value} />);
        }
    }

    return (
        <div style={{display:"flex", flexDirection:"column",}}>
            {rows}
        </div>
    );
}
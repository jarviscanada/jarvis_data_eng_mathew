import React from 'react';

/* This function provides a simple means of displaying the KVPs of an object or JSON
 * document (from Fetch's response.json()) passed to it.
 */
export default function JsonComponent(props) {
    let rows = [];
    const parent = props.parent? props.parent : "";

    for (const key in props.json) {
        const value = props.json[key];
        let valueJsx;

        if(typeof(value) === "object") {
            valueJsx = (
                <JsonComponent json={value} parent={key}/>
            );
        } else {
            valueJsx = (<p style={{float:"left", order:1}} key={key + "val"}>{value}</p>);
        }

        rows.push(
            <div key={parent + key} style={{display:"flex",flexDirection:"row"}}>
            <p style={{float:"left",order:0}} key={key}>{key + ":"}</p>
            {valueJsx}
            </div>
        );
    }

    return (
        <div style={{display:"flex", flexDirection:"column",}}>
            {rows}
        </div>
    );
}
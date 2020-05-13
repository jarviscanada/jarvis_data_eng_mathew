import React from 'react';

// This component is used to render a simple key-value pair as side-by-side paragraphs
// This component assumes that the passed keys and values are string-like
export default function kvpComponent(props) {
    return (
        <div key={props.kvpKey + "value"} style={{display:"flex", flexDirection:"row"}}>
            <p>{`${props.kvpKey}:`}</p>
            <p>{`${props.value.toString()}`}</p>
        </div>
    );
}
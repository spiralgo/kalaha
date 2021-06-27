import React from 'react';

function Pocket(props){
    var inlineStyle = {
        display: 'inline-block',
        border: '1px solid black',
        minHeight: '150px',
        width: '150px',
        height: 'inherit',
        textAlign: 'center'
    }
    return (
        <div style={inlineStyle} onClick={() => props.onPocketClick(props.id)}>
            {props.count}
        </div>
    )
}

export default Pocket;
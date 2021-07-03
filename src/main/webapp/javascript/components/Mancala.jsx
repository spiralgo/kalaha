import React from 'react';

function Mancala(props){
    const inlineStyle = {
        display: 'inline-block',
        border: '1px solid white',
        minHeight: '330px',
        width: '150px',
        height: 'inherit',
        textAlign: 'center'
    }
    return (
        <div style={inlineStyle}>
             {props.label}
             <br></br>
            {props.score}
        </div>
    )

}

export default Mancala;
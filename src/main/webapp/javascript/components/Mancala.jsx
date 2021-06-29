import React from 'react';

function Mancala(props){
    var inlineStyle = {
        display: 'inline-block',
        border: '1px solid white',
        minHeight: '330px',
        width: '150px',
        height: 'inherit',
        textAlign: 'center'
    }
    return (
        <div style={inlineStyle} onClick={() => props.onPocketClick(props.id)}>
             {props.label}
             <br></br>
            {props.score}
        </div>
    )

}

export default Mancala;
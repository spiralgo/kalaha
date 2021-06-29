import React from 'react';
import {move} from "../actions/games-action";
import {connect} from "react-redux";

function Pit(props){
     const inlineStyle = {
        display: 'inline-block',
        border: '1px solid black',
        minHeight: '150px',
        width: '150px',
        height: 'inherit',
        textAlign: 'center',
    }
     function onPitClick(){
         props.move(props.gameToJoin.id, props.content.position);
     }
     return (
        <div style={inlineStyle} onClick={() => onPitClick() }>
             Pit {props.content.position}
             <br/>
             {props.content.value}
        </div>
    )

}

const mapStateToProps = state => {
    return {
        gameToJoin: state.gameToJoin
    }
}

const mapDispatchToProps = dispatch => ({
    move: (gameId, pitPosition) => dispatch(move(gameId, pitPosition))
});
export default connect(mapStateToProps, mapDispatchToProps)(Pit);
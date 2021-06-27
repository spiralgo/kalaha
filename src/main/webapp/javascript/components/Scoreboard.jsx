import React from 'react'

function Scoreboard(props){
    var style = {
        
    }
    return(
        <div>
           <h2>Player 1 score: {props.player1Score}</h2>
           <h2>Player 2 score: {props.player2Score}</h2>
        </div>
    )
}
export default Scoreboard
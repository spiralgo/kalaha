import React from 'react'
import Pocket from './Pocket';

function Board(props){
    var inlineStyle = {
        display: 'inline-block',
        height: '300px',
        verticalAlign: 'top'
    }
    function makePlayRow(rowArray, rowIndex){
        let rowCopy = rowArray.slice();
        if(rowIndex === 0){
            rowCopy.reverse();
            console.log(rowCopy);
        }
        return(
            <div key={rowIndex}>
                {rowCopy.map((content, index) =><Pocket count={content} id={`${rowIndex}-${rowIndex === 0 ? rowArray.length -1 - index : index}`} key={`${rowIndex}-${index}`} onPocketClick={props.onPocketClick}/>)}
            </div>
        )
    }
    return (
        <div style={inlineStyle}>
            <div id='player2-score' style={inlineStyle}>
                <Pocket count={props.player2Score} />
            </div>
            <div id='play-area' style={inlineStyle}>
                {props.gameBoard.map((playerSideArray, index) => {
                    return(makePlayRow(playerSideArray, index));
                })}
            </div>
            <div id='player1-score' style={inlineStyle} >
                <Pocket count={props.player1Score}/>
            </div>
        </div>
    )
}
export default Board;
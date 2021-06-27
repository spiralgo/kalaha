import React, { useState } from 'react'
import Board from './Board'
import GameList from './GameList'
import Scoreboard from './Scoreboard';
import { connect } from 'react-redux'
import Player from "./Player";

function Game(props) {
    let [playerTurn, changeTurn] = useState(1);

    function handlePocketClick(id) {
        console.log(props);
        let tempBoard = [...props.board];
        let row = Number(id.substring(0, 1));
        if (playerTurn === row) {
            let index = Number(id.substring(id.indexOf('-') + 1));
            const pocketValue = tempBoard[row][index];
            const removedForScore = Math.floor((index + pocketValue + 6) / 12);
            const forPlayArea = pocketValue - removedForScore;
            updateScore(tempBoard, forPlayArea, index, row)
            updateBoard(forPlayArea, index, row)
            updatePlayerTurn(index, pocketValue, row)
        }
    }
    function updatePlayerTurn(index, pocketValue, row) {
        if ((pocketValue + index + 6) % 12 !== 0) {
            changeTurn((row + 1) % 2);
        }
    }

    function updateBoard(forPlayArea, index, row) {
        const { dispatch } = props
        const action = {
            type: "UPDATE_BOARD",
            forPlayArea: forPlayArea,
            index: index,
            row: row
        }
        dispatch(action)
    }

    function updateScore(tempBoard, forPlayArea, index, row) {
        const { dispatch } = props
        const action = {
            type: "UPDATE_SCORE",
            tempBoard: tempBoard,
            forPlayArea: forPlayArea,
            index: index,
            row: row
        }
        dispatch(action)
    }
    return (
        <div>
            <Board gameBoard={props.board} player1Score={props.scores.player1Score} player2Score={props.scores.player2Score} onPocketClick={handlePocketClick} />
            <Scoreboard player1Score={props.scores.player1Score} player2Score={props.scores.player2Score} />

        </div>
    )
}

const mapStateToProps = state => {
    return {
        board: state.board,
        scores: state.scores,
        games: state.games
    }
}
export default connect(mapStateToProps)(Game);
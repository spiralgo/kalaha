import React from 'react'
import Pit from './Pit';
import {Table} from "react-bootstrap";
import Mancala from "./Mancala";

function Board(props) {
    var inlineStyle = {
        display: 'inline-block',
        height: '300px',
        verticalAlign: 'top'
    }

    function addPit(pit, gameToJoin) {

        return (

            <td>
                <Pit content={pit} gameToJoin={gameToJoin}/>
            </td>
        )
    }

    let board;
    if (props.gameToJoin == null) {
        return <div></div>;
    } else {
        board = props.gameToJoin.board;
    }

    return (
        <div style={inlineStyle}>
            <Table striped bordered hover variant="dark">
                <tbody>
                <tr>
                    <td rowSpan={2}><Mancala count={props.player1Score} label={"Kalaha 1:"}
                                             score={board.pits[6].value}/></td>


                    {board.pits.slice(0, 6).reverse().map((playerSideArray) => {
                        return (addPit(playerSideArray, props.gameToJoin));

                    })}
                    <td rowSpan={2}><Mancala count={props.player2Score} label={"Kalaha 2:"}
                                             score={board.pits[13].value}/></td>

                </tr>
                <tr>
                    {board.pits.slice(7, 13).map((playerSideArray) => {
                        return (addPit(playerSideArray, props.gameToJoin));

                    })}
                </tr>
                </tbody>
            </Table>

        </div>
    )
}

export default Board;
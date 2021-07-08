import React from 'react'
import Pit from './Pit';
import {Table} from "react-bootstrap";
import Mancala from "./Mancala";

function Board(props) {

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
             <Table striped bordered hover variant="dark" responsive="sm">
                <tbody>
                <tr>
                    <td rowSpan={2} colSpan={1}><Mancala isTurn = {props.gameToJoin.playerOne.id == props.gameToJoin.turnOf.id} playerName={props.gameToJoin.playerOne==null?'': props.gameToJoin.playerOne.name} label={"Kalaha 1:"}
                                             score={board.pits[6].value}/></td>


                    {board.pits.slice(0, 6).reverse().map((playerSideArray) => {
                        return (addPit(playerSideArray, props.gameToJoin));

                    })}
                    <td rowSpan={2} colSpan={1}><Mancala  isTurn = {props.gameToJoin.playerTwo!==null && props.gameToJoin.playerTwo.id == props.gameToJoin.turnOf.id} playerName={props.gameToJoin.playerTwo==null?'': props.gameToJoin.playerTwo.name} label={"Kalaha 2:"}
                                             score={board.pits[13].value}/></td>

                </tr>
                <tr>
                    {board.pits.slice(7, 13).map((playerSideArray) => {
                        return (addPit(playerSideArray, props.gameToJoin));

                    })}
                </tr>
                </tbody>
            </Table>

    )
}

export default Board;
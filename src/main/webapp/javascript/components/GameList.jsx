import React, {useEffect, useState} from 'react'
import {connect} from "react-redux";
import {fetchGames, joinAGame, refreshGame} from "../actions/games-action";
import SockJsClient from 'react-stomp';
import Button from 'react-bootstrap/Button';
import {properties} from "../config/properties";
import {showNotification} from "../config/notification";
import TableScrollbar from 'react-table-scrollbar';
import {Container, Col, Row, Table} from "react-bootstrap";
import Board from "./Board";
import axios from "axios";

const GameList = ({fetchGames, games, joinAGame, gameToJoin, refreshGame}) => {

    let onConnected = () => {
        console.log("Connected!!")
    }

    let onMessageReceived = (msg) => {
        switch (msg.action) {
            case "refresh_game_list":
                fetchGames();
                showNotification("Game Message", "success", msg.message) ;

                break;

            case "refresh_game":
                if(msg.game!=""){
                    refreshGame(msg.game);
                }
                showNotification("Game Message", "default", msg.message) ;

                break;

            case "end":
                if(msg.game!=""){
                    refreshGame(msg.game);
                }
                showNotification("Game Message", "warning", msg.message) ;

                break;
        }

    }

    function handleSelectChange(gameId) {
         joinAGame(gameId);
    }

    useEffect(() => {
        fetchGames();
    }, []);


    function startAGame() {

        const playerId = localStorage.getItem("playerId");
        const playerName = localStorage.getItem("playerName");

        axios.post("/game/create",                 {"id": localStorage.getItem("playerId"), "name": localStorage.getItem("playerName")})
            .then((response) => {
                if (!response.ok) {

                    console.log("Failed to create a game.", "danger");

                }
            }
        ).catch((error) => {
            showNotification("Game warning", "warning", error.response.data)

        });
        return false;
    }

    const listItems = games.map((game, i) =>
        <tr   onClick={() => handleSelectChange(game.id)}>
            <td>#{game.id}</td>
            <td colSpan="1">{game.playerOne.name}</td>
        </tr>
    );
    return (
        <div>

            <SockJsClient
                url={properties.webSocketUrl}
                topics={['/update', '/update/'+(gameToJoin==null? '': gameToJoin.id)]}
                onConnect={onConnected}
                onDisconnect={console.log("Disconnected!")}
                onMessage={msg => onMessageReceived(msg)}
                debug={true}
            />


                <Button  onClick={startAGame.bind(this)}>Start a Game</Button>

             <Container fluid>
                <Row>
                    <Col sm={3}>
                        <TableScrollbar  rows={18}>
                            <Table striped bordered hover size="sm">
                                <thead>
                                <tr>
                                    <th>Game:</th>
                                    <th>By</th>
                                </tr>
                                </thead>
                                <tbody>
                                {listItems}
                                </tbody>
                            </Table>
                        </TableScrollbar>
                    </Col>
                    <Col sm={9}>
                        <Board gameToJoin={gameToJoin}></Board>
                    </Col>
                </Row>
            </Container>
        </div>
    );
}

const mapStateToProps = state => {
    return {
        games: state.games,
        gameToJoin: state.gameToJoin
    }
}

const mapDispatchToProps = dispatch => ({
    fetchGames: () => dispatch(fetchGames()),
    joinAGame: (gameId) => dispatch(joinAGame(gameId)),
    refreshGame: (game) => dispatch(refreshGame(game))
});
export default connect(mapStateToProps, mapDispatchToProps)(GameList);
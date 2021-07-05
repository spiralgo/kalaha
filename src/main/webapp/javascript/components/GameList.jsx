import React, {useEffect, useState} from 'react'
import {connect} from "react-redux";
import {fetchGames, joinAGame} from "../actions/games-action";
import SockJsClient from 'react-stomp';
import Button from 'react-bootstrap/Button';
import {properties} from "../config/properties";
import {showNotification} from "../config/notification";
import TableScrollbar from 'react-table-scrollbar';
import {Container, Col, Row, Table} from "react-bootstrap";
import Board from "./Board";

const GameList = ({fetchGames, games, joinAGame, gameToJoin}) => {

    let onConnected = () => {
        console.log("Connected!!")
    }

    let onMessageReceived = (msg) => {
        switch (msg.action) {
            case "refresh_game_list":
                fetchGames();
                showNotification("Game Message", "success", msg.message) ;

                break;

            case "refresh_board":
                joinAGame(gameToJoin.id);
                showNotification("Game Message", "default", msg.message) ;

                break;

            case "end":
                joinAGame(gameToJoin.id);
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


    function handleSubmit(evt) {

        evt.preventDefault();
        const playerId = localStorage.getItem("playerId");
        const playerName = localStorage.getItem("playerName");

        if (playerId == null) {
            showNotification("Warning", "warning", "You need to create player first.") ;
            return;
        }
        fetch("/game/create", {
            method: "POST",
            headers: {
                'Content-Type': 'application/json',
                'Authorization': 'Bearer my-token',
                'My-Custom-Header': 'foobar'
            },
            body: JSON.stringify({"id": playerId, "name": playerName})
        }).then((response) => {
                if (!response.ok) {

                    console.log("Failed to create a game.", "danger");

                }
            }
        ).catch((error) => {
            console.log(error, "danger");

        });
        evt.target.reset();
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
                topics={['/update']}
                onConnect={onConnected}
                onDisconnect={console.log("Disconnected!")}
                onMessage={msg => onMessageReceived(msg)}
                debug={true}
            />

            <form onSubmit={handleSubmit.bind(this)}>
                <Button type={"submit"}>Start a Game</Button>
            </form>
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

});
export default connect(mapStateToProps, mapDispatchToProps)(GameList);
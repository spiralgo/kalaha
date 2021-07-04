import React, {useEffect, useState} from 'react'
import {connect} from "react-redux";
import {fetchGames, joinAGame} from "../actions/games-action";
import SockJsClient from 'react-stomp';
import Board from "./Board";
import Button from 'react-bootstrap/Button';
import {Alert} from "react-bootstrap";
import {properties} from "../config/properties";

const GameList = ({fetchGames, games, joinAGame, gameToJoin}) => {

    let onConnected = () => {
        console.log("Connected!!")
    }

    let onMessageReceived = (msg) => {
        switch (msg.action) {
            case "refresh_game_list":
                fetchGames();
                break;

            case "refresh_board":
                joinAGame(gameToJoin.id);
                break;

            case "end":
                joinAGame(gameToJoin.id);
                break;
        }
        showMessage(msg.message, "primary");
    }
    const [selectedGame, setSelectedGame] = React.useState({})
    const [type, setType] = useState("");
    const [message, setMessage] = useState("");

    function handleSelectChange(event) {
        setSelectedGame(event.target.value);
        joinAGame(event.target.value);
    }

    useEffect(() => {
        fetchGames();
    }, []);

    function showMessage(message, type) {
        setMessage(message);
        setType(type);
    }

    function handleSubmit(evt) {

        evt.preventDefault();
        const playerId = localStorage.getItem("playerId");
        const playerName = localStorage.getItem("playerName");

        if (playerId == null) {
            showMessage("You need to create player first.", "warning");
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
                    showMessage("Failed to create a game.", "danger");

                }
            }
        ).catch((error) => {
            showMessage(error, "danger");

        });
        evt.target.reset();
        return false;
    }

    const listItems = games.map((game, i) =>

        <option value={game.id}>#{game.id} by {game.playerOne.name}</option>
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

            <Alert variant={type}>
                {message}
            </Alert>

            <p>You can either start a new game...</p>
            <form onSubmit={handleSubmit.bind(this)}>
                <Button type={"submit"}>Start a Game</Button>
            </form>
            <p>or join am existing game:</p>

            <select value={selectedGame} onChange={handleSelectChange}> //set value here
                {listItems}
            </select>


            <Board gameToJoin={gameToJoin} player1Score="0" player2Score="0"/>

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
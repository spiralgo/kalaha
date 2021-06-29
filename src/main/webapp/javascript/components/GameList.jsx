import React, {useEffect, useState} from 'react'
import {connect} from "react-redux";
import {fetchGames, getBoard, joinAGame} from "../actions/games-action";
import SockJsClient from 'react-stomp';
import Board from "./Board";
const SOCKET_URL = "http://localhost:8080/websocket";
import Button from 'react-bootstrap/Button';

const GameList = ({ fetchGames, games, joinAGame, gameToJoin, board, getBoard}) =>  {

    let onConnected = () => {
        console.log("Connected!!")
    }

    let onMessageReceived = (msg) => {
         if(msg="refresh_game_list") fetchGames();
    }
    const [selectedGame,setSelectedGame] = React.useState({})
    function handleSelectChange(event) {
        setSelectedGame(event.target.value);
        joinAGame(event.target.value);
        getBoard(event.target.value);
    }
    useEffect(() => {
        fetchGames();
     }, []);


    function handleSubmit(evt) {

        evt.preventDefault();
        const playerId = localStorage.getItem("playerId");
        if(playerId==null) {
            alert("You need to create player first.");
            return;
        }
        fetch("/game/create", {
            method: "POST",
            headers: {
                'Content-Type': 'application/json',
                'Authorization': 'Bearer my-token',
                'My-Custom-Header': 'foobar'
            },
            body:  JSON.stringify({"id": playerId})
        }).then((response) => {
                if (!response.ok) {
                    alert("Failed to create a game.");
                }
            }
        ).catch((error) => {
            // Network errors
            alert(error);
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
                url={SOCKET_URL}
                topics={['/update']}
                onConnect={onConnected}
                onDisconnect={console.log("Disconnected!")}
                onMessage={msg => onMessageReceived(msg)}
                debug={true}
            />
            <p>You can either start a new game...</p>
            <form onSubmit={handleSubmit.bind(this)}>
                <Button type={"submit"}>Start a Game</Button>
            </form>
            <p>or join am existing game:</p>

            <select value={selectedGame} onChange={handleSelectChange}> //set value here
                {listItems}
            </select>


            <Board gameBoard={board} player1Score="0" player2Score="0" />

        </div>
    );
}

const mapStateToProps = state => {
    return {
        board: state.board,
        games: state.games,
        gameToJoin: state.gameToJoin
    }
}

const mapDispatchToProps = dispatch => ({
    fetchGames: () => dispatch(fetchGames()),
    joinAGame: (gameId) => dispatch(joinAGame(gameId)),
    getBoard: (gameId) => dispatch(getBoard(gameId)),
});
export default connect(mapStateToProps, mapDispatchToProps)(GameList);
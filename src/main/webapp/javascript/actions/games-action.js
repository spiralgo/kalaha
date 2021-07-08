import axios from "axios";
import { store } from 'react-notifications-component';
import {showNotification} from "../config/notification";

const API = '';
const GAME_API = API + "/game";
const MOVE_API = API + "/play";
const GAME_GET_BOARD = MOVE_API + "/board/";
const GAME_CREATE_API = GAME_API + "/create";
const GAME_GET_ALL = API;
const GAME_GET_ALL_AVAILABLE_GAMES = GAME_API + "/gameslist";
const GAME_JOIN = GAME_API + "/join";

export const fetchGames = () => {
    return dispatch => {

        axios
            .get(GAME_API)
            .then(response => dispatch(updateGameList(response.data)))
            .catch(error => console.log(error));
    };
};

export const joinAGame = (gameId) => {
    return dispatch => {
        axios
            .patch(GAME_JOIN + "/" + gameId,
                {"id": localStorage.getItem("playerId"), "name": localStorage.getItem("playerName")})
            .then(response => {
                dispatch(refreshTheGame(response.data))
            })
            .catch(error => showNotification("Game warning", "warning", error.response.data));
    };
};
export const refreshGame = (game) => {
    return dispatch => {
        dispatch(refreshTheGame(game));
    };
};
export const move = (gameId, pitPosition) => {
    return dispatch => {
        axios
            .post(MOVE_API + "/" + gameId + "/" + localStorage.getItem("playerId") + "/" + pitPosition, {})
            .then(response => console.log("data received."))
            .catch(error => showNotification("Move warning", "warning", error.response.data));
    };
};

function refreshTheGame(game) {
    return {
        type: "REFRESH_THE_GAME",
        game: game
    }
}

function updateGameList(games) {
    return {
        type: "UPDATE_GAME_LIST",
        games: games
    }
}


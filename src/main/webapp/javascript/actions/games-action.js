import axios from "axios";
import {showNotification} from "../config/notification";

const GAME_API = "/game";
const MOVE_API = "/move";
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


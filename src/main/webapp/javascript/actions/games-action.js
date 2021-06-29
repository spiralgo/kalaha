import axios from "axios";

const API = '';
const GAME_API = API+"/game";
const PLAY_API = API+"/play";
const GAME_GET_BOARD = PLAY_API+"/board/";
const GAME_CREATE_API = GAME_API+"/create";
const GAME_GET_ALL =  API;
const GAME_GET_ALL_AVAILABLE_GAMES = GAME_API+"/gameslist";
const GAME_JOIN = GAME_API+"/join";

export const fetchGames = () => {
    return dispatch => {

        axios
            .get(GAME_API)
            .then(response => dispatch(updateGameList(response.data)))
            .catch(error => alert(error));
    };
};

export const joinAGame = (gameId) => {
    return dispatch => {
        axios
            .patch(GAME_JOIN + "/" +  gameId,
                {"id":localStorage.getItem ("playerId")})
            .then(response => dispatch(getBoard(response.data.id)))
            .catch(error => console.log(error) ); // alert("Game is not available.")
    };
};
export const getBoard = (gameId) => {
    return dispatch => {
        axios
            .get(GAME_GET_BOARD + "/" +  gameId)
            .then(response => dispatch(updateJoinAGame(response.data)))
            .catch(error => console.log(error) ); // alert("Game is not available.")
    };
};


function updateJoinAGame(game) {
    return   {
        type: "UPDATE_JOIN_A_GAME",
        game: game
    }
}

function updateGameList(games) {
    return   {
        type: "UPDATE_GAME_LIST",
        games: games
    }
}


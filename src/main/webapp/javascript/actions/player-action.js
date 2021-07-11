import axios from "axios";
import {showNotification} from "../config/notification";

export const createAPlayer = (name) => {
    return dispatch => {
        const headers = {
            'Content-Type': 'application/json'
        }
        axios.post("/player/create"
            , JSON.stringify({"name": name})
            , {
                headers: headers
            })
            .then((response) => {

                    localStorage.setItem("playerId", response.data.body.id);
                    localStorage.setItem("playerName", response.data.body.name);
                    dispatch(playerCreated(response.data.body));
                    showNotification("Player info:", "success", response.data.message);
                }
            ).catch((error) => {
            showNotification("Player warning:", "warning", error.response.data)

        });
    };
};
function playerCreated(player) {
    return {
        type: "PLAYER_CREATED",
        player: player
    }
}
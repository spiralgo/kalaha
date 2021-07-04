import React from 'react'
import "regenerator-runtime/runtime";

import {connect} from "react-redux";

function Player(props) {
    const [state, setState] = React.useState({
        name: "",
        password: ""
    })

    function handleChange(evt) {
        const value = evt.target.value;
        setState({
            ...state,
            [evt.target.name]: value
        });
    }


    function handleSubmit(evt) {
        evt.preventDefault();

        fetch("/player/create", {
            method: "POST",
            headers: {
                'Content-Type': 'application/json',
                'Authorization': 'Bearer my-token'
            },
            body: JSON.stringify({"name": state.name, "password": state.password})
        }).then(async (response) => {
                if (response.ok) {
                    const body = await response.json();

                    localStorage.setItem("playerId", body.id);
                    localStorage.setItem("playerName", body.name);

                } else {
                    alert("Failed to create a user.");
                }
            }
        ).catch((error) => {
            // Network errors
            alert(error);
        });
        evt.target.reset();
        return false;
    }


    return (
        <div>

            <form onSubmit={handleSubmit.bind(this)}>
                <input id="name" name="name"
                       value={state.name}
                       onChange={handleChange}
                       type="text" placeholder="Enter a nickname"/>

                <button type='submit'>Create a player</button>
            </form>
        </div>
    );
}

const mapStateToProps = state => {
    return {
        player: state.player
    }
}
export default connect(mapStateToProps)(Player);
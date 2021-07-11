import React from 'react'
import "regenerator-runtime/runtime";
import {connect} from "react-redux";
import {createAPlayer} from "../actions/player-action";

function Player({createAPlayer}) {
    const [state, setState] = React.useState({
        name: ""
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
        createAPlayer(state.name);
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

const mapDispatchToProps = dispatch => ({
    createAPlayer: (name) => dispatch(createAPlayer(name))
});
export default connect(mapStateToProps, mapDispatchToProps)(Player);
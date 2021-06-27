import React from 'react'
import Game from './Game'
import Player from "./Player";
import {
    Switch,
    Route,
    Link
} from "react-router-dom";
import GameList from "./GameList";

function App(){
    return (
    <div>
        <ul>
            <li>
                <Link to="/">Create Player</Link>
            </li>
            <li>
                <Link to="/games">Game List</Link>
            </li>
        </ul>

        <hr />

        <Switch>
            <Route exact path="/">
                <Player />
            </Route>

            <Route path="/games">
                <GameList></GameList>
            </Route>
        </Switch>
    </div>
    )
}
export default App
import React from 'react'
import '../../css/Main.css'
import Player from "./Player";
import {
    Switch,
    Route,
    Link
} from "react-router-dom";
import GameList from "./GameList";
import NavbarComponent from "./Navbar";

function App(){
    return (
    <div>
        <NavbarComponent></NavbarComponent>

        <Switch>
            <Route exact path="/">
                <Player />
            </Route>

            <Route exact path="/games">
                <GameList></GameList>
            </Route>
        </Switch>
    </div>
    )
}
export default App
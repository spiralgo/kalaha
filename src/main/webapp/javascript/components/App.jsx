import React from 'react'
import '../../css/Main.css'
import Player from "./Player";
import {Route, Switch} from "react-router-dom";
import GameList from "./GameList";
import NavbarComponent from "./Navbar";
import ReactNotification from 'react-notifications-component'
import 'react-notifications-component/dist/theme.css'

function App() {
    return (
        <div>
            <ReactNotification />

            <NavbarComponent></NavbarComponent>

            <Switch>
                <Route exact path="/">
                    <GameList/>
                </Route>

                <Route exact path="/createPlayer">
                    <Player/>
                </Route>
            </Switch>
        </div>
    )
}

export default App
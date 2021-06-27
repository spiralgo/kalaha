import React, {useEffect} from 'react'
import {connect} from "react-redux";
function GameList(props) {
    const [selectedClient,setSelectedClient] = React.useState({
        value: "havuç"
    })
    function handleSelectChange(event) {
        setSelectedClient(event.target.value);
    }
    useEffect(() => {
        fetchGames();
    }, []);
    function updateGameList(games) {
        const { dispatch } = props
        const action = {
            type: "UPDATE_GAME_LIST",
            games: games
        }
        dispatch(action)
    }
    function fetchGames() {
        fetch("/game")
            .then(res => res.json())
            .then(
                (response) => {
                   updateGameList(response);
                },
                (error) => {
                    alert(error);
                }
            )
    }

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
                if (response.ok) {
                    fetchGames();
                } else {
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

    const games = props.games;
    const listItems = games.map((game, i) =>
        <li key={i}>{game.id}</li>
    );
    return (
        <div>
        <ul>{listItems}</ul>
        <form onSubmit={handleSubmit.bind(this)}>
            <input id="name" name="name" type="text" placeholder="Enter name"/>
            <button type='submit'>Create Game</button>
        </form>

            <select value={selectedClient} onChange={handleSelectChange}> //set value here
                <option value="one">One</option>
                <option value="two">Two</option>
                <option value="three">Three</option>
            </select>
        </div>
    );
}

const mapStateToProps = state => {
    return {
        games: state.games,
        player: state.player
    }
}
export default connect(mapStateToProps)(GameList);
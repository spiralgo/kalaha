import React, {useEffect} from 'react'
import {connect} from "react-redux";
import {fetchGames} from "../actions/games-action";
const GameList = ({ fetchGames, games, player}) =>  {
    const [selectedClient,setSelectedClient] = React.useState({
        value: "havuç"
    })
    function handleSelectChange(event) {
        setSelectedClient(event.target.value);
    }
    useEffect(() => {
        fetchGames();
    }, []);



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
                if (!response.ok) {
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
    const listItems = games.map((game, i) =>
        <li key={i}>{game.id}</li>
    );
    return (
        <div>
        <ul>{listItems}</ul>
        <form onSubmit={handleSubmit.bind(this)}>
            <input id="name" name="name" type="text" placeholder="Enter Name"/>
            <button type='submit'>Start a Game</button>
        </form>
            <p>Games you can join:</p>
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

const mapDispatchToProps = dispatch => ({
    fetchGames: () => dispatch(fetchGames())
});
export default connect(mapStateToProps, mapDispatchToProps)(GameList);
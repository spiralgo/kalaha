import React from 'react';
import {Nav, Navbar} from "react-bootstrap";
import Button from "react-bootstrap/Button";
import {connect} from "react-redux";

function NavbarComponent(props) {
    let endPart = '';
    let playerLink = '';
    if(localStorage.getItem("playerName")!=null || props.player.name!=null){
        endPart =  <Navbar.Collapse className="justify-content-end">
            <Navbar.Text>
                { "Welcome " +localStorage.getItem("playerName")+"!"}
            </Navbar.Text>
            <Button onClick={logout.bind(this)}>Logout</Button>
        </Navbar.Collapse>;
    }else {
        playerLink = <Nav.Link href="#/createPlayer">Create Player</Nav.Link>;
    }


    function logout() {
        localStorage.removeItem("playerId");
        localStorage.removeItem("playerName");
        location.reload();
    }
    return (
        <Navbar bg="primary" variant="dark">
            <Navbar.Brand>Kalaha</Navbar.Brand>
            <Nav className="mr-auto">
                {playerLink}
                <Nav.Link href="#/">Games</Nav.Link>
            </Nav>
            {endPart}
        </Navbar>
    )

}


const mapStateToProps = state => {
    return {
        player: state.player
    }
}

export default connect(mapStateToProps)(NavbarComponent);
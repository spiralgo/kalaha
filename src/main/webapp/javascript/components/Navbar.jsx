import React from 'react';
import {Nav, Navbar} from "react-bootstrap";
import Button from "react-bootstrap/Button";

function NavbarComponent(props) {
    let endPart = '';

    if(localStorage.getItem("playerName")!=null)
    endPart =  <Navbar.Collapse className="justify-content-end">
        <Navbar.Text>
            { "Welcome " +localStorage.getItem("playerName")+"!"}
        </Navbar.Text>
        <Button onClick={logout.bind(this)}>Logout</Button>

    </Navbar.Collapse>;

    function logout() {
        localStorage.removeItem("playerId");
        localStorage.removeItem("playerName");
        location.reload();
    }
    return (
        <Navbar bg="primary" variant="dark">
            <Navbar.Brand>Kalaha</Navbar.Brand>
            <Nav className="mr-auto">
                <Nav.Link href="#/">Create Player</Nav.Link>
                <Nav.Link href="#/games">Games</Nav.Link>
            </Nav>
            {endPart}
        </Navbar>
    )

}

export default NavbarComponent;
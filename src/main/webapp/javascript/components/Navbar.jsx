import React from 'react';
import {Nav, Navbar} from "react-bootstrap";
import {Link} from "react-router-dom";

function NavbarComponent(props){

    return (
        <Navbar bg="primary" variant="dark">
            <Navbar.Brand>Kalaha</Navbar.Brand>
            <Nav className="mr-auto">
                <Nav.Link href="#/">Create Player</Nav.Link>
                <Nav.Link href="#/games">Games</Nav.Link>
            </Nav>
        </Navbar>
    )

}

export default NavbarComponent;
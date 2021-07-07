import React from 'react';
import {Nav, Navbar} from "react-bootstrap";

function NavbarComponent(props) {

    return (
        <Navbar bg="primary" variant="dark">
            <Navbar.Brand>Kalaha</Navbar.Brand>
            <Nav className="mr-auto">
                <Nav.Link href="#/">Create Player</Nav.Link>
                <Nav.Link href="#/games">Games</Nav.Link>
            </Nav>
            <Navbar.Collapse className="justify-content-end">
                <Navbar.Text>
                    {(localStorage.getItem("playerName")==null? '':   "Welcome " +localStorage.getItem("playerName")+"!")}
                </Navbar.Text>
            </Navbar.Collapse>


        </Navbar>
    )

}

export default NavbarComponent;
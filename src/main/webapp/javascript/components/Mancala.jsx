import React from 'react';
import {Card} from "react-bootstrap";

function Mancala(props) {

    return (

    <Card style={{ width: '120px', height: '270px' }} className="text-center bg-dark text-white">
        <Card.Header> {props.label}</Card.Header>
        <Card.Body>
            <Card.Title>{props.score}</Card.Title>
            <Card.Text>
                {props.playerName}
            </Card.Text>
            <Card.Footer>
                {props.isTurn==true? '🟢' : ''}

            </Card.Footer>

        </Card.Body>
     </Card>

    )

}

export default Mancala;
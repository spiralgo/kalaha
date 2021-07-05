import React from 'react';
import {move} from "../actions/games-action";
import {connect} from "react-redux";
import {Card} from "react-bootstrap";

function Pit(props) {

    function onPitClick() {
        props.move(props.gameToJoin.id, props.content.position);
    }

    return (

        <Card style={{ width: '90px', height: '120px' }}  onClick={() => onPitClick()} className="text-center bg-dark text-white">
            <Card.Header>  Pit {props.content.position % 7} </Card.Header>
            <Card.Body>
                <Card.Title>{props.content.value}</Card.Title>
            </Card.Body>
        </Card>
    )

}

const mapStateToProps = state => {
    return {};
}

const mapDispatchToProps = dispatch => ({
    move: (gameId, pitPosition) => dispatch(move(gameId, pitPosition))
});
export default connect(mapStateToProps, mapDispatchToProps)(Pit);
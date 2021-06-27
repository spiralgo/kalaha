import MancalaBoard from './board-reducer';
import scoreReducer from './score-reducer';
import gameReducer from './game-reducer';
import { combineReducers } from 'redux';

const rootReducer = combineReducers({
    board: MancalaBoard,
    scores: scoreReducer,
    games: gameReducer
});

export default rootReducer;
import gameReducer from './game-reducer';
import gameToJoinReducer from './game-join-reducer';
import { combineReducers } from 'redux';

const rootReducer = combineReducers({
    games: gameReducer,
    gameToJoin: gameToJoinReducer
});

export default rootReducer;
import gameReducer from './game-reducer';
import gameToJoinReducer from './game-join-reducer'
import playerReducer from './player-reducer';

import {combineReducers} from 'redux';

const rootReducer = combineReducers({
    games: gameReducer,
    gameToJoin: gameToJoinReducer,
    player: playerReducer
});

export default rootReducer;
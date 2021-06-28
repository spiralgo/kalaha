export default (state = [], action) => {
    switch(action.type){
        case 'FETCH_GAMES_START':
            return state;

        case 'UPDATE_GAME_LIST':

        return action.games;
        
        default:
        return state;
    }
}
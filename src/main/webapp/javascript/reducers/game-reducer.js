export default (state = [], action) => {
    switch (action.type) {
        case 'UPDATE_GAME_LIST':
            return action.games;

        default:
            return state;
    }
}
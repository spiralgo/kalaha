export default (state = null, action) => {
    switch (action.type) {
        case 'UPDATE_JOIN_A_GAME':
            return action.game;

        default:
            return state;
    }
}
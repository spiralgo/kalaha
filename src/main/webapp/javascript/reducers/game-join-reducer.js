export default (state = null, action) => {
    switch (action.type) {
        case 'REFRESH_THE_GAME':
            return action.game;

        default:
            return state;
    }
}
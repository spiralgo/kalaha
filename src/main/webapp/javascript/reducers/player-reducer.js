export default (state = {},
                action) => {
    switch (action.type) {
        case 'PLAYER_CREATED':
            return action.player;
        default:
            return state;
    }
}
export default (state = -1,
                action) => {
    switch (action.type) {
        case 'CREATE_PLAYER':

            return action.playerId;

        default:
            return state;
    }
}
export default (state = null, action) => {
    switch(action.type){
        case 'UPDATE_BOARD':

        return action.board;
        
        default:
        return state;
    }
}
export default (state = [[6, 6, 6, 6, 6, 6], [6, 6, 6, 6, 6, 6]], action) => {
    switch(action.type){
        case 'UPDATE_BOARD':
        let {forPlayArea, row, index  } = action
        let tempBoard = [...state];
        const activePlayer = row;
        tempBoard[row][index] = 0;
        for (let i = 0; i < forPlayArea; i++) {
            index += 1;
            if (index % 6 === 0) {
                index = 0;
                row = (row + 1) % 2;
            }
            if ((i === forPlayArea - 1) && (tempBoard[row][index] === 0 && row == activePlayer)) {
                const oppositeRow = (row + 1) % 2;
                tempBoard[oppositeRow][5 - index] = 0;
                tempBoard[row][index] = 0;
            } else {
                tempBoard[row][index] = tempBoard[row][index] + 1;
            }
        }
        return [[6, 6, 8, 6, 6, 6], [6, 6, 6, 6, 6, 6]];
        
        default:
        return state;
    }
}
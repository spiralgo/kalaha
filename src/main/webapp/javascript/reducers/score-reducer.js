export default (state = { player1Score: 0, player2Score: 0 }, action) => {
    switch(action.type){
        case 'UPDATE_SCORE':
        let { tempBoard, forPlayArea, row, index } = action
        const activePlayer = row;
        const pocketValue = tempBoard[row][index];
        let scoreValue = Math.floor((index + pocketValue + 6) / 12);
        let {player1Score, player2Score} = state;
        console.log(pocketValue, row, index, tempBoard, tempBoard[row][index]);

        for (let i = 0; i < forPlayArea; i++) {
            index += 1;
            if (index % 6 === 0) {
                index = 0;
                row = (row + 1) % 2;
            }
            if ((i === forPlayArea - 1) && (tempBoard[row][index] === 0 && row == activePlayer)) {
                const oppositeRow = (row + 1) % 2;
                const valueToSteal = tempBoard[oppositeRow][5 - index];
                scoreValue += valueToSteal + 1;
            }
        }
        
        if (activePlayer === 0) {
            player2Score = player2Score + scoreValue;
        } else {
            player1Score = player1Score + scoreValue;
        }
        return { player1Score: player1Score, player2Score: player2Score };
        
        default:
        return state;
    }
}
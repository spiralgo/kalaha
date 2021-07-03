package com.bol.kalaha.util;

import com.bol.kalaha.model.Board;
import com.bol.kalaha.model.Pit;

import java.util.List;

public class GameRules {
    public static final Integer KALAHA_PLAYER_ONE = 7;
    public static final Integer KALAHA_PLAYER_TWO = 14;

    public int sowPit(Board board, int positionOfPitToPlay) {
        List<Pit> pits = board.getPits();
        int currentNumberOfSeeds = pits.get(positionOfPitToPlay-1).getValue();
        pits.get(positionOfPitToPlay-1).setValue(0);
            int indexToSow = positionOfPitToPlay -1;
            while (currentNumberOfSeeds>0){
                indexToSow = (++positionOfPitToPlay-1) % KALAHA_PLAYER_TWO;
                Pit pit = pits.get(indexToSow);
                pits.get(indexToSow).setValue(pit.getValue()+1);
                currentNumberOfSeeds--;
            }
         board.setPits(pits);

         return indexToSow + 1;
    }

    public void checkAndCapture(boolean isPlayerOne, int theLastPosition, Board board) {

        int theLastIndex = theLastPosition -1;
        List<Pit> pits = board.getPits();

        if(pits.get(theLastIndex).getValue() != 1) return;

        int opponentIndex = getOpponentIndex(theLastPosition);
        int opponentsPitValue = pits.get(opponentIndex).getValue();

            if(isPlayerOne && theLastPosition>=1 && theLastPosition < KALAHA_PLAYER_ONE) {
                pits.get(theLastIndex).setValue(0);
                pits.get(opponentIndex).setValue(0);
                int currentKalahaValue = pits.get(KALAHA_PLAYER_ONE-1).getValue();
                pits.get(KALAHA_PLAYER_ONE-1).setValue(currentKalahaValue + opponentsPitValue+ 1);
            }else if(theLastPosition>KALAHA_PLAYER_ONE && theLastPosition < KALAHA_PLAYER_TWO) {
                pits.get(theLastIndex).setValue(0);
                pits.get(opponentIndex).setValue(0);
                int currentKalahaValue = pits.get(KALAHA_PLAYER_TWO-1).getValue();
                pits.get(KALAHA_PLAYER_TWO-1).setValue(currentKalahaValue+ opponentsPitValue + 1);
            }

     }

    public boolean checkExtraMove(boolean isPlayerOne, List<Pit> pits) {

        return (isPlayerOne && pits.get(KALAHA_PLAYER_ONE-1).getValue() == 1)
             || (!isPlayerOne && pits.get(KALAHA_PLAYER_TWO-1).getValue() == 1);

    }
    public int getOpponentIndex(int position){
        return KALAHA_PLAYER_TWO - position-1;
    }
}

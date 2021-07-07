package com.bol.kalaha.util;

import com.bol.kalaha.model.Board;
import com.bol.kalaha.model.Game;
import com.bol.kalaha.model.Pit;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import java.util.stream.Stream;

public class GameRules {

    public static final Integer KALAHA_PLAYER_ONE = 7;
    public static final Integer KALAHA_PLAYER_TWO = 14;


    public int sowPit(Board board, int positionOfPitToPlay, boolean isPlayerOne) {
        List<Pit> pits = board.getPits();
        int currentNumberOfSeeds = pits.get(positionOfPitToPlay - 1).getValue();
        pits.get(positionOfPitToPlay - 1).setValue(0);
        int indexToSow = positionOfPitToPlay - 1;
        int indexOfOpponentsKahala = -1;
        if(isPlayerOne){
            indexOfOpponentsKahala = KALAHA_PLAYER_TWO-1;
        }else{
            indexOfOpponentsKahala = KALAHA_PLAYER_ONE-1;
        }
        while (currentNumberOfSeeds > 0) {
            indexToSow = (++positionOfPitToPlay - 1) % KALAHA_PLAYER_TWO;

            if(indexOfOpponentsKahala==indexToSow){
                continue;
            }
            Pit pit = pits.get(indexToSow);
            pits.get(indexToSow).setValue(pit.getValue() + 1);
            currentNumberOfSeeds--;
        }
        board.setPits(pits);

        return indexToSow + 1;
    }

    public void checkAndCapture(boolean isPlayerOne, int theLastPosition, Board board) {

        int theLastIndex = theLastPosition - 1;
        List<Pit> pits = board.getPits();

        if (pits.get(theLastIndex).getValue() != 1) return;

        int opponentIndex = getOpponentIndex(theLastPosition);
        int opponentsPitValue = pits.get(opponentIndex).getValue();

        if (isPlayerOne && theLastPosition >= 1 && theLastPosition < KALAHA_PLAYER_ONE) {
            pits.get(theLastIndex).setValue(0);
            pits.get(opponentIndex).setValue(0);
            int currentKalahaValue = pits.get(KALAHA_PLAYER_ONE - 1).getValue();
            pits.get(KALAHA_PLAYER_ONE - 1).setValue(currentKalahaValue + opponentsPitValue + 1);
        } else if (theLastPosition > KALAHA_PLAYER_ONE && theLastPosition < KALAHA_PLAYER_TWO) {
            pits.get(theLastIndex).setValue(0);
            pits.get(opponentIndex).setValue(0);
            int currentKalahaValue = pits.get(KALAHA_PLAYER_TWO - 1).getValue();
            pits.get(KALAHA_PLAYER_TWO - 1).setValue(currentKalahaValue + opponentsPitValue + 1);
        }

    }

    public boolean checkExtraMove(boolean isPlayerOne, int theLastPosition) {

        return (isPlayerOne && (KALAHA_PLAYER_ONE == theLastPosition))
                ||
                (!isPlayerOne && (KALAHA_PLAYER_TWO == theLastPosition));

    }

    public int getOpponentIndex(int position) {
        return KALAHA_PLAYER_TWO - position - 1;
    }
    public boolean checkGameOver(Board board) {
        int pitCountPlayer1 = 0;
        int pitCountPlayer2 = 0;

        List<Pit> pits = board.getPits();
        for (int i = 0; i < KALAHA_PLAYER_ONE-1; i++) {
            pitCountPlayer1 +=  Optional.ofNullable(pits.get(i).getValue()).orElse(0);

        }
       for (int i = KALAHA_PLAYER_ONE; i < KALAHA_PLAYER_TWO-1; i++) {
           pitCountPlayer2 +=  Optional.ofNullable(pits.get(i).getValue()).orElse(0);

       }

        boolean isGameOver = true;
            if(pitCountPlayer2 == 0){
                pitCountPlayer1+= pits.get(KALAHA_PLAYER_ONE-1).getValue();
                pits.get(KALAHA_PLAYER_ONE-1).setValue(pitCountPlayer1);
              }else if (pitCountPlayer1 == 0){
                pitCountPlayer2+= pits.get(KALAHA_PLAYER_TWO-1).getValue();
                pits.get(KALAHA_PLAYER_TWO-1).setValue(pitCountPlayer2);

            }else{
                isGameOver = false;
            }
        if(isGameOver) {

            for (int i = 0; i < KALAHA_PLAYER_TWO; i++) {
                    if(i !=KALAHA_PLAYER_ONE-1 && i!=KALAHA_PLAYER_TWO-1)
                    pits.get(i).setValue(0);
            }
            board.setPits(pits);
        }
        return isGameOver;

    }

    public Game changeTurn(Game game) {
        if (game.getTurnOfWithId().equals(game.getPlayerOne()))
            game.setTurnOfWithId(game.getPlayerTwo());
        else
            game.setTurnOfWithId(game.getPlayerOne());
        return game;
    }
}

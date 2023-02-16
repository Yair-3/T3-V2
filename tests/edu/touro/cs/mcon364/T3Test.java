package edu.touro.cs.mcon364;

import org.junit.jupiter.api.Test;

import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.*;

class T3Test {

    T3 newGame = new T3();

    @Test
    void reset() {
        newGame.makeMove(0, 0);  //X
        newGame.makeMove(1, 1);

        newGame.reset();

        newGame.makeMove(1, 1); // X in the old O location
        assertEquals(T3.CellValue.X, newGame.getBoard()[1][1]);  // X would not be allowed if a not new game

    }

    @Test
    void makeMove() {
        newGame.makeMove(0, 0);  //X
        newGame.makeMove(1, 1); //O
        newGame.makeMove(1, 1); // Attempts to put X in O's spot. Both asserts will still be true.
        assertEquals(T3.CellValue.X, newGame.getBoard()[0][0]);
        assertEquals(T3.CellValue.O, newGame.getBoard()[1][1]);
    }


    @Test
    void winner() {
        newGame.makeMove(0, 0); //X
        newGame.makeMove(1, 0); //O

        newGame.makeMove(1, 1); //X
        newGame.makeMove(1, 2); //O

        newGame.makeMove(2, 2); //X
        // X wins on a diagonal
        assertEquals(T3.CellValue.X, newGame.winner());
    }

    @Test
    void nextPlayer() {
        newGame.makeMove(0, 0); //X
        assertEquals(T3.CellValue.O, newGame.nextPlayer());

    }

    @Test
    void currentPlayer() {
        newGame.makeMove(0, 0); //X
        newGame.makeMove(1, 1); // 0
        assertEquals(T3.CellValue.O, newGame.currentPlayer()); // model only changes the player once a new move was made

    }


    @Test
    void getWinnerRow() {      //X wins in row
        newGame.makeMove(0, 0); //X
        newGame.makeMove(1, 0); //O

        newGame.makeMove(0, 1); //X
        newGame.makeMove(1, 1); //O

        newGame.makeMove(0, 2); //X

        assertEquals(T3.CellValue.X, newGame.winner());
    }

    @Test
    void getWinnerColumn() {
        newGame.makeMove(0, 0); //X
        newGame.makeMove(1, 2); //O

        newGame.makeMove(0, 1); //X
        newGame.makeMove(2, 1); //O

        newGame.makeMove(0, 2); //X


        assertEquals(T3.CellValue.X, newGame.winner());
    }

    @Test
    void getWinnerDiagonal() {
        newGame.makeMove(1, 1); //X
        newGame.makeMove(1, 2); //O

        newGame.makeMove(0, 0); //X
        newGame.makeMove(2, 1); //O

        newGame.makeMove(2, 2); //X


        assertEquals(T3.CellValue.X, newGame.winner());
    }

    @Test
    void isGameOver() {
        newGame.makeMove(1, 1); //X
        newGame.makeMove(1, 2); //O

        assertFalse(newGame.isGameOver());

        newGame.makeMove(0, 0); //X
        newGame.makeMove(2, 1); //O

        newGame.makeMove(2, 2); //X


        assertTrue(newGame.isGameOver());


    }

    @Test
    void getWinningLocation() {
        newGame.makeMove(1, 1); //X
        newGame.makeMove(1, 2); //O

        newGame.makeMove(0, 0); //X
        newGame.makeMove(2, 1); //O

        newGame.makeMove(2, 2); //X

        ArrayList<Integer> winningSpot;
        winningSpot = newGame.getWinningLocation();

        assertEquals(0, winningSpot.get(0));
        assertEquals(0, winningSpot.get(1));
        assertEquals(1, winningSpot.get(2));
        assertEquals(1, winningSpot.get(3));
        assertEquals(2, winningSpot.get(4));
        assertEquals(2, winningSpot.get(5)); // The winning locations are loaded into an alist;


    }

    @Test
    void isTie() {
        newGame.makeMove(1, 2);
        newGame.makeMove(1, 1);
        newGame.makeMove(0, 1);
        newGame.makeMove(1, 0);
        newGame.makeMove(0, 0);
        newGame.makeMove(0, 2);
        newGame.makeMove(2, 0);
        newGame.makeMove(2, 1);
        newGame.makeMove(2, 2);

        assertTrue(newGame.isTie());
    }
    @Test
    void makeMoveCpu(){
        newGame.makeMoveCpu();
        assertFalse(newGame.getCpuPosition().isEmpty()); // this array list stores the cpu's move.
                                                        // As long as there is contents that means the move was
                                                        // successful.
    }
}

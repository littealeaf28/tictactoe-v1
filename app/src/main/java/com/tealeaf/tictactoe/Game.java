package com.tealeaf.tictactoe;

import java.io.Serializable;

public class Game implements Serializable {
    private String[][] board;
    private int numTurns;
    private Player playerOne, playerTwo;

    public Game() {
        final int BOARD_WIDTH = 3;
        numTurns = 1;
        board = new String[BOARD_WIDTH][BOARD_WIDTH];
        for (int i = 0; i < board.length; i++) {
            for (int j = 0; j < board[i].length; j++) {
                board[i][j] = "";
            }
        }
        playerOne = new Player("Player One");
        playerTwo = new Player("Player Two");
    }

    //Assigns players token based on selected token passed in
    public void setPlayerTokens(String token) {
        if (token.equals("X")){
            playerOne.setMoveToken("X");
            playerTwo.setMoveToken("O");
        }
        else {
            playerOne.setMoveToken("O");
            playerTwo.setMoveToken("X");
        }
    }

    //Randomly determines which player starts first
    //1 is first, 0 is second
    public void setPlayerTurns() {
        int p1StartTurn = (int)(Math.random()*2);
        playerOne.setStartTurn(p1StartTurn);
        if (p1StartTurn == 1) {
            playerTwo.setStartTurn(0);
        }
        else {
            playerTwo.setStartTurn(1);
        }
    }

    //Translates the 2D array board into single array to be used by ArrayAdapter
    public String[] getSingleListBoard() {
        String[] singleListBoard = new String[9];
        int count = 0;
        for (int i = 0; i < board.length; i++) {
            for (int j = 0; j < board[i].length; j++) {
                singleListBoard[count] = board[i][j];
                count++;
            }
        }
        return singleListBoard;
    }

    public void setTokenOnBoard(int row, int col, String token) {
        board[row][col] = token;
    }

    public int checkGameConditions(int row, int col) {
        //Checks each of the four possible ways that could've been won from the most recently played token (in a row, in a column, in both diagonals)
        boolean gameIsOver = false;
        int[][][] xyCheck = { { {-1, -1}, {1, 1} }, { {-1, 0}, {1, 0} }, { {-1, 1}, {1, -1} }, { {0, -1}, {0, 1} } };
        for (int i = 0; i < xyCheck.length; i++) {
            int numInRow = 1;
            for (int j = 0; j < xyCheck[i].length; j++) {
                int focusRow = row + xyCheck[i][j][0];
                int focusCol = col + xyCheck[i][j][1];
                while (focusRow >= 0 && focusRow < board.length && focusCol >= 0 && focusCol < board[col].length && board[row][col].equals(board[focusRow][focusCol])) {
                    numInRow++;
                    focusRow += xyCheck[i][j][0];
                    focusCol += xyCheck[i][j][1];
                }
                if (numInRow >= 3) {
                    gameIsOver = true;
                    break;
                }
            }
        }

        if (gameIsOver && numTurns % 2 == playerTwo.getStartTurn()) { return 1; }
        else if (gameIsOver && numTurns % 2 == playerOne.getStartTurn()) { return 2; }
        else if (numTurns >= 10){ return 3; }
        else { return 0; }

    }

    public int getNumTurns() { return numTurns; }
    public void updateNumTurns() { numTurns++; }

    public Player playerOne() { return playerOne; }
    public Player playerTwo() { return playerTwo; }

}

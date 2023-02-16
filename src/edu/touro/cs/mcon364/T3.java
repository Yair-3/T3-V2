package edu.touro.cs.mcon364;


import java.io.Serializable;
import java.util.ArrayList;
import java.util.Objects;
import java.util.Random;


public class T3 implements Serializable {
    public enum CellValue {X, O, NONE}
    public enum GameType {CPU, ONE_V_ONE}
    private final CellValue[][] board = new CellValue[3][3];
    private CellValue currentPlayer = CellValue.X;
    private CellValue returnPlayer = CellValue.X;
    private CellValue statusPlayer = CellValue.X;
    private CellValue returnCpu;
    private ArrayList<Integer> winningLocation = new ArrayList<>();
    private ArrayList<Integer> cpuPosition = new ArrayList<>();
    private String gameState = "InProgress";
    private boolean isWinner;
    private int tie = 0;
    private Random rand = new Random();
    private boolean cpu;
    private boolean invalidMove;


    public T3() {
        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 3; j++) {
                board[i][j] = CellValue.NONE;
            }
        }
    }
    public void startGame(GameType gametype){
        cpu = gametype == GameType.CPU;
     }

    public void reset() {
        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 3; j++) {
                board[i][j] = CellValue.NONE;
            }
        }
        currentPlayer = CellValue.X;
        tie = 0;
        gameState = "InProgress";
        cpuPosition.clear();
    }



    public void makeMove(int row, int col) {
        if (isGameOver() || !isValidMove(row, col)) {
            invalidMove = true;
            return;
        }
        invalidMove = false;
        board[row][col] = currentPlayer;
        returnPlayer = currentPlayer;

        isWinner = gameCheck();
        gameState = changeGameState();
        currentPlayer = (currentPlayer == CellValue.X) ? CellValue.O : CellValue.X;
        statusPlayer = nextPlayer();
        printBoard();

    }

    public void makeMoveCpu() {
        int cRow = rand.nextInt(0, 3);
        int cCol = rand.nextInt(0, 3);
        if (isGameOver()) {
            return;
        }
        while (!isValidMove(cRow, cCol)) {
            cRow = rand.nextInt(0, 3);
            cCol = rand.nextInt(0, 3);
        }
        cpuPosition.clear();
        cpuPosition.add(cRow);
        cpuPosition.add(cCol);


        board[cRow][cCol] = currentPlayer;
        returnCpu = currentPlayer;

        isWinner = gameCheck();
        gameState = changeGameState();
        currentPlayer = (currentPlayer == CellValue.X) ? CellValue.O : CellValue.X;
        statusPlayer = nextPlayer();
        printBoard();
    }

    private boolean gameCheck() {
        for (int i = 0; i < 3; i++) {
            // Rows
            if (board[i][0] == board[i][1] && board[i][1] == board[i][2] && board[i][0] != CellValue.NONE) {
                winningLocation.add(i);
                winningLocation.add(0);
                winningLocation.add(i);
                winningLocation.add(1);
                winningLocation.add(i);
                winningLocation.add(2);
                return true;
            }
            // columns
            if (board[0][i] == board[1][i] && board[1][i] == board[2][i] && board[0][i] != CellValue.NONE) {
                winningLocation.add(0);
                winningLocation.add(i);
                winningLocation.add(1);
                winningLocation.add(i);
                winningLocation.add(2);
                winningLocation.add(i);
                return true;
            }
        }
        // diagonals
        if (board[0][0] == board[1][1] && board[1][1] == board[2][2] && board[0][0] != CellValue.NONE) {
            winningLocation.add(0);
            winningLocation.add(0);
            winningLocation.add(1);
            winningLocation.add(1);
            winningLocation.add(2);
            winningLocation.add(2);
            return true;
        }
        if (board[0][2] == board[1][1] && board[1][1] == board[2][0] && board[0][2] != CellValue.NONE) {
            winningLocation.add(0);
            winningLocation.add(2);
            winningLocation.add(1);
            winningLocation.add(1);
            winningLocation.add(2);
            winningLocation.add(0);
            return true;
        }
        // Check if the board is full
        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 3; j++) {
                if (board[i][j] == CellValue.NONE) {
                    return false;
                }
            }
        }
        tie = 1;
        return false;
    }
    private String changeGameState() {
        if (!isWinner && tie == 1) {
            gameState = "DRAW";
            return gameState;
        }
        if (isWinner && currentPlayer == CellValue.X) {
            gameState = "X";
            return gameState;
        }
        if (isWinner && currentPlayer == CellValue.O) {
            gameState = "O";
            return gameState;
        }

        gameState = "InProgress";

        return gameState;
    }

    public CellValue winner() {
        if (Objects.equals(gameState, "X")) {
            return CellValue.X;
        }
        if (Objects.equals(gameState, "O")) {
            return CellValue.O;
        }
        return CellValue.NONE;

    }
    public CellValue nextPlayer() {

        if (returnPlayer == CellValue.X) {
            return statusPlayer = CellValue.O;
        }
        return statusPlayer = CellValue.X;
    }
    public CellValue currentPlayer() {
        return returnPlayer;
    }
    public boolean isGameOver() {
        return !Objects.equals(gameState, "InProgress");
    }

    public boolean isTie() {
        return Objects.equals(gameState, "DRAW");
    }

    private boolean isValidMove(int row, int column) {
        if (row < 0 || row > 2 || column < 0 || column > 2) {
            return false;
        } else return board[row][column] == CellValue.NONE;
    }



    public ArrayList<Integer> getCpuPosition() {
        return cpuPosition;
    }

    public CellValue getCpu() {
        return returnCpu;
    }
    public ArrayList<Integer> getWinningLocation() {
        return winningLocation;
    }

    public CellValue[][] getBoard() {
        return this.board;
    }
    public String getGameState(){
        return gameState;
    }

    public Boolean getInvalidMove(){
        return invalidMove;
    }
    public GameType getGameType(){
        if (!cpu){
            return GameType.ONE_V_ONE;
        }
        return GameType.CPU;
    }
    public void printBoard() {
        for (int i = 0; i < 3; i++) {
            System.out.println();
            for (int j = 0; j < 3; j++) {
                System.out.print(" | ");
                System.out.print(board[i][j]);
            }
        }
        System.out.println();
    }
}

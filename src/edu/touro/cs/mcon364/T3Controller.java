package edu.touro.cs.mcon364;

import java.util.ArrayList;


public class T3Controller {
    T3 model;
    T3view view;


    T3Controller(T3 model, T3view view) {
        this.model = model;
        this.view = view;
    }

    public void setRequest(ArrayList<Integer> position) {
        model.makeMove(position.get(0), position.get(1));
    }
     public boolean getInvalidMove(){
        return model.getInvalidMove();
     }

     public void setGameCpu(T3.GameType gt){
        model.startGame(gt);
     }
     public T3.GameType getGameType() {
        return model.getGameType();
     }

    public String receiveInfo() {
        return model.currentPlayer().toString();
    }

    public String receiveStatus() {

        return model.nextPlayer().toString();
    }

    public boolean receiveTie() {
        return model.isTie();
    }

    public boolean receiveWinner() {
        if (receiveTie()) {
            return false;
        }
        return model.isGameOver();
    }

    public String winner() {
        return model.winner().toString();
    }

    public ArrayList<Integer> recWinnerLocal() {
        return model.getWinningLocation();
    }

    public void setRequest() {
        model.reset();
        view.resetView();
    }

    public void setRequestCpu() {
        model.makeMoveCpu();
    }

    public ArrayList<Integer> receiveCpuPosition() {
        return model.getCpuPosition();
    }


    public String receiveCpuInfo() {
        return model.getCpu().toString();
    }
    public T3.CellValue[][] getBoard(){
        return model.getBoard();
    }
    public void setModel(T3 model){
        this.model = model;
    }
    public T3 getModel(){
        return this.model;
    }
}

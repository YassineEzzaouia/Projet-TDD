package com.example;

public class TicTacToeSave {
    private GererDB dbManager;

    public TicTacToeSave(GererDB dbManager) {
        this.dbManager = dbManager;
    }

    public void checkDatabase() {
        try {
            dbManager.verify(); 
            dbManager.connect();
        } catch (Exception e) {
            throw new RuntimeException("Database does not exist or connection failed", e);
       }
    }

   public void createDatabaseIfNotExists() {
        try {
           dbManager.verify();
        } catch (Exception e) {
           dbManager.create();
        }
    }

    public Boolean saveMovement(Data movement) {
       try {
           dbManager.save(movement);
              return true;
        } catch (Exception e) {
        return false;
    }}
    
    public boolean resetGameState() {
        try {
            dbManager.drop();
            return true;
        } catch (Exception e) {
            return false;
        }
    }
    
    public void startNewGame() {
        try{
            createDatabaseIfNotExists();
        } catch (Exception e) {
        dbManager.drop();
    }}
    
    public boolean saveMoveWithExceptionMessage(Data move) {
        try {
            dbManager.save(move);
            return true;
        } catch (Exception e) {
            throw new RuntimeException("Failed to save move: " + e.getMessage());
        }
    }
    
}
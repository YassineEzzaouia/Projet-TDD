package com.example;

public class MockDatabaseManager implements GererDB {
    private boolean dbExists;
    private boolean throwExceptionOnSave; 
    private int savedMovesCount = 0;

    public MockDatabaseManager() {
        this(true); 
    }
    public MockDatabaseManager(boolean dbExists) {
        this.dbExists = dbExists;
        this.throwExceptionOnSave = false; 
    }
    public void setThrowExceptionOnSave(boolean throwException) {
        this.throwExceptionOnSave = throwException;
    }
    @Override
    public void connect() { }
    @Override
    public void verify() {
        if (!dbExists) throw new RuntimeException("Database not found");
    }
    @Override
    public void create() {
        dbExists = true;
    }
  
    @Override
    public void drop() { 
        savedMovesCount = 0;
    }
    @Override
    public void save(Data d) {
        if (throwExceptionOnSave) {
            throw new RuntimeException("Error while saving move");
        }
        savedMovesCount++;
    }
    public int getSavedMovesCount() {
        return savedMovesCount;
    }
}

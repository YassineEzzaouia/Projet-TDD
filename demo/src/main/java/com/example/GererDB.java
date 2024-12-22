package com.example;


public interface GererDB {
    void verify() throws RuntimeException;

    void connect() throws RuntimeException;

    void create() throws RuntimeException;

    void save(Data d) throws RuntimeException;

    void drop() throws RuntimeException;

    void setThrowExceptionOnSave(boolean throwException);

    int getSavedMovesCount( ) throws RuntimeException;
}



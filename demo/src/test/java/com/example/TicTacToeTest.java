package com.example;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.BeforeEach;


public class TicTacToeTest {
    private TicTacToe jeu;
    private GererDB mockDbManager;
    private TicTacToeSave save;

    @BeforeEach
    public void setUp() {
        jeu = new TicTacToe();
        mockDbManager = new MockDatabaseManager(true);
        save = new TicTacToeSave(mockDbManager);
    }

    @Test
    public void testPlacerPionHorsAxeX() {
        assertThrows(RuntimeException.class, () -> {
            jeu.placerPion(3, 0, "X");
        });
    }

    @Test
    public void testPlacerPionHorsAxeY() {
        assertThrows(RuntimeException.class, () -> {
            jeu.placerPion(0, 3, "X");
        });
    }

    @Test
    public void testPlacerPionCaseOccupee() {
        jeu.placerPion(1, 1, "X"); 
        assertThrows(RuntimeException.class, () -> {
            jeu.placerPion(1, 1, "O");
        });
    }

    @Test
    public void testPremierTourJoueurX() {
        assertEquals("X", jeu.getJoueurActuel(), "Le premier joueur doit être X");
    }

    @Test
    public void testTourSuivantJoueurO() {
        jeu.placerPion(0, 0, "X"); 
        assertEquals("O", jeu.getJoueurActuel(), "Après le tour de X, c'est O qui doit jouer");
    }

    @Test
    public void testTourSuivantJoueurX() {
        jeu.placerPion(0, 0, "X"); 
        jeu.placerPion(0, 1, "O");
        assertEquals("X", jeu.getJoueurActuel(), "Après le tour de O, c'est X qui doit jouer");
    }

    @Test
    public void testGagnerHorizontalement() {
        jeu.placerPion(0, 0, "X");
        jeu.placerPion(0, 1, "X");
        jeu.placerPion(0, 2, "X");
        assertEquals("X", jeu.getGagnant(), "Le joueur X devrait gagner horizontalement");
    }

    @Test
    public void testGagnerVerticalement() {
        jeu.placerPion(0, 0, "X");
        jeu.placerPion(1, 0, "X");
        jeu.placerPion(2, 0, "X"); 
        assertEquals("X", jeu.getGagnant(), "Le joueur X devrait gagner verticalement");
    }

    @Test
    public void testGagnerDiagonale() {
        jeu.placerPion(0, 0, "X");
        jeu.placerPion(1, 1, "X");
        jeu.placerPion(2, 2, "X");
        assertEquals("X", jeu.getGagnant(), "Le joueur X devrait gagner en diagonale");
    }

    @Test
    public void testMatchNul() {
        jeu.placerPion(0, 0, "X");
        jeu.placerPion(0, 1, "O");
        jeu.placerPion(0, 2, "x");
        jeu.placerPion(1, 0, "O");
        jeu.placerPion(1, 1, "X");
        jeu.placerPion(1, 2, "O");
        jeu.placerPion(2, 0, "O");
        jeu.placerPion(2, 1, "X");
        jeu.placerPion(2, 2, "O");
        assertNull(jeu.getGagnant(), "Il ne devrait y avoir aucun gagnant.");
        assertTrue(jeu.isMatchNul(), "Le match devrait être déclaré nul.");
    }

    @Test
    public void testDatabaseExistsAndConnect() {
        assertDoesNotThrow(() -> save.checkDatabase());
    }

    @Test
    public void testDatabaseCreationIfNotExists() {
        assertDoesNotThrow(() -> save.createDatabaseIfNotExists());
    }

    @Test
    public void testSaveMove() {
        Data move = new Data(1, 0, 1, 'X'); 
        boolean result = save.saveMovement(move);
        assertTrue(result); 
    }

    @Test
    public void testSaveMovementSuccess() {
        Data movement = new Data(1, 0, 1, 'X');
        assertTrue(save.saveMovement(movement));
    }

    @Test
    public void testSaveMoveReturnsFalseOnError() {
        mockDbManager.setThrowExceptionOnSave(true);
        Data move = new Data(3, 2, 2, 'X');
        boolean result = save.saveMovement(move);
        assertFalse(result); 
    }

    @Test
    public void testResetGameState() {
        boolean result = save.resetGameState();
        assertTrue(result);
    }

    @Test
    public void testDatabaseCreationOnNewGame() {
        assertDoesNotThrow(() -> save.startNewGame());
        assertDoesNotThrow(() -> mockDbManager.verify());
    }

    
    @Test
    public void testSaveMoveOnEachTurn() {
        Data move1 = new Data(1, 0, 0, 'X'); 
        Data move2 = new Data(2, 1, 1, 'O'); 

        assertTrue(save.saveMovement(move1)); 
        assertTrue(save.saveMovement(move2)); 

        assertEquals(2, mockDbManager.getSavedMovesCount());
    }

    @Test
    public void testSaveMoveThrowsExceptionOnError() {
        mockDbManager.setThrowExceptionOnSave(true);

        Data move = new Data(3, 2, 2, 'X');
        assertThrows(RuntimeException.class, () -> save.saveMoveWithExceptionMessage(move)); 
    }

}
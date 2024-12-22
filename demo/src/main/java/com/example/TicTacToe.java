package com.example;

public class TicTacToe {
    private String[][] grille;
    private String joueurActuel;

    public TicTacToe() {
        grille = new String[3][3];
        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 3; j++) {
                grille[i][j] = "";
            }
        }
        joueurActuel = "X";
    }

    public String getJoueurActuel() {
        return joueurActuel;
    }

    public void changerJoueur() {
        joueurActuel = joueurActuel.equals("X") ? "O" : "X";
    }

    public void placerPion(int x, int y, String joueur) {
        if (x < 0 || x >= 3) {
            throw new RuntimeException("Position hors de l'axe des X");
        }
        if (y < 0 || y >= 3) {
            throw new RuntimeException("Position hors de l'axe des ordonnées");
        }
        if (grille[x][y] != "") {
            throw new RuntimeException("La case est déjà occupée.");
        }
        grille[x][y] = joueur;
        if (verifierVictoire(x, y, joueur)) {
            joueurActuel = joueur + " (Gagnant)";
        } else if (isMatchNul()) {
        joueurActuel = "Match Nul";
        }else {
            changerJoueur();
    }
    }

    private boolean verifierVictoire(int x, int y, String joueur) {
        return verifierLigne(x, joueur) ||
               verifierColonne(y, joueur) ||
               verifierDiagonalePrincipale(joueur) ||
               verifierDiagonaleSecondaire(joueur);
    }
    
    private boolean verifierLigne(int x, String joueur) {
        return grille[x][0].equals(joueur) && grille[x][1].equals(joueur) && grille[x][2].equals(joueur);
    }

    private boolean verifierColonne(int y, String joueur) {
        return grille[0][y].equals(joueur) && grille[1][y].equals(joueur) && grille[2][y].equals(joueur);
    }
    
    private boolean verifierDiagonalePrincipale(String joueur) {
        return grille[0][0].equals(joueur) && grille[1][1].equals(joueur) && grille[2][2].equals(joueur);
    }
    
    private boolean verifierDiagonaleSecondaire(String joueur) {
        return grille[0][2].equals(joueur) && grille[1][1].equals(joueur) && grille[2][0].equals(joueur);
    }
    

    public String getGagnant() {
        return joueurActuel.contains("(Gagnant)") ? joueurActuel.replace(" (Gagnant)", "") : null;
    }

    public boolean isMatchNul() {
        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 3; j++) {
                if (grille[i][j].equals("")) { 
                    return false;
                }
            }
        }
        return getGagnant() == null;
    }
}

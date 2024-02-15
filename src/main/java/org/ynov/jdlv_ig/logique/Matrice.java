package org.ynov.jdlv_ig.logique;

import org.ynov.jdlv_ig.logique.Cellule;

import java.util.Random;

public class Matrice {

    Cellule[][] grille;
    Cellule[][] ancienneGrille;
    static int taille;
    double densite = 0.3;

    public Matrice() {
        grille = new Cellule[20][20];
        ancienneGrille = new Cellule[20][20];
        taille = 20;
        init();
    }

    public Matrice(int _taille, double densite) {
        taille = _taille;
        this.densite = densite;
        grille = new Cellule[taille][taille];
        ancienneGrille = new Cellule[taille][taille];
        init();
    }

    private void init() {
        for (int i = 0; i < taille; i++) {
            for (int j = 0; j < taille; j++) {
                Cellule c = new Cellule(ancienneGrille, i, j, false);
                grille[i][j] = c;
                ancienneGrille[i][j] = (Cellule) c.clone();
            }
        }
        initHasard();
    }

    public void initHasard() {
        Random r = new Random();
        for (int i = 0; i < taille; i++) {
            for (int j =0; j < taille; j++) {
                if( r.nextDouble() < densite) {
                    grille[i][j].estVivante = true;
                    ancienneGrille[i][j].estVivante = true;
                }
            }
        }
    }

    public void change(int i, int j) {
        grille[i][j].estVivante = !grille[i][j].estVivante;
        grille[i][j].changerCouleur();
    }

    public void copierGrille() {
        for(int i = 0; i < taille; i++) {
            for (int j = 0; j < taille; j++) {
                ancienneGrille[i][j].estVivante = grille[i][j].estVivante;
            }
        }
    }

    public void animGrille() {
        for (int i = 0; i < taille; i++) {
            for (int j = 0; j < taille; j++) {
                grille[i][j].evoluver();
            }
        }
    }

    public Cellule[][] getGrille() {
        return grille;
    }
}

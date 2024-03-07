package org.ynov.jdlv_ig.entity;

import java.util.Random;

/**
 * Classe qui permet de générer la simulation du jeu de la vie.
 */
public class Matrice {

    /**
     * Grille actuelle du jeu de la vie.
     */
    Cellule[][] grille;
    /**
     * Grille précédente du jeu de la vie.
     */
    Cellule[][] ancienneGrille;
    /**
     * Taille de la grille du jeu.
     */
    static int taille;
    /**
     * Densité des cellules dans la grille.
     */
    double densite = 0.3;

    /**
     * Constructeur prédéfini qui lance l'initialisation d'une grille.
     */
    public Matrice() {
        grille = new Cellule[20][20];
        ancienneGrille = new Cellule[20][20];
        taille = 20;
        init();
    }

    /**
     * Constructeur qui permet de construire une grille selon des valeurs définies par le joueur.
     * @param _taille
     * @param densite
     */
    public Matrice(int _taille, double densite) {
        taille = _taille;
        this.densite = densite;
        grille = new Cellule[taille][taille];
        ancienneGrille = new Cellule[taille][taille];
        init();
    }

    /**
     * Méthode qui lance l'initialisation de la grille du jeu.
     */
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

    /**
     * Méthode qui initialise les cellules de la grille en les positionnement aléatoirement.
     */
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

    /**
     * Méthode qui permet de changer la couleur des cellules en fonction de leur état.
     * @param i
     * @param j
     */
    public void change(int i, int j) {
        grille[i][j].estVivante = !grille[i][j].estVivante;
        grille[i][j].changerCouleur();
    }

    /**
     * Méthode qui permeet de copier la grille afin de gérer la transition d'état des cellules.
     */
    public void copierGrille() {
        for(int i = 0; i < taille; i++) {
            for (int j = 0; j < taille; j++) {
                ancienneGrille[i][j].estVivante = grille[i][j].estVivante;
            }
        }
    }

    /**
     * Méthode qui permet d'animer la grille via l'évolution des cellules.
     */
    public void animGrille() {
        for (int i = 0; i < taille; i++) {
            for (int j = 0; j < taille; j++) {
                grille[i][j].evoluver();
            }
        }
    }

    /**
     * Getter pour récupérer la grille actuelle.
     * @return
     */
    public Cellule[][] getGrille() {
        return grille;
    }
}

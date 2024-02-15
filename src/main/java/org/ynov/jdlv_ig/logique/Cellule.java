package org.ynov.jdlv_ig.logique;

import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;


public class Cellule implements Cloneable {

    static final int sousPopulation = 1;
    static final int surPopulation = 4;
    static int minPopulationRegeneratrice = 3;
    static int maxPopulationRegeneratrice = 3;
    boolean etatPrecedant;
    boolean estVivante;
    int x, y;
    Cellule[][] grille;
    static final public Color coulVersActive = Color.CYAN;
    static final public Color coulActive = Color.ALICEBLUE;
    static final public Color coulVersDesactive = Color.INDIANRED;
    static final public Color coulDesactive = Color.color(0.1, 0, 0);
    Circle circle;

    public Cellule() {
    }

    public Cellule(Cellule[][] grille, int x, int y, boolean estVivante) {
        this.grille = grille;
        this.estVivante = etatPrecedant = estVivante;
        this.x = x;
        this.y = y;
    }

    public void evoluver() {
        int taille = grille.length;
        int nbVivante = 0;
        for(int i = -1; i <2; i++) {
            int xx = ((x + i) + taille) % taille;
            for (int j = -1; j < 2; j++) {
                if(i == 0 && j == 0) continue;
                int yy = ((y+j) + taille) % taille;
                if (grille[xx][yy].estVivante) {
                    nbVivante++;
                }
            }
        }
        etatPrecedant = estVivante;
        if (estVivante && (nbVivante <= sousPopulation || nbVivante >= surPopulation)) {
            estVivante = false;
        } else if (nbVivante >= minPopulationRegeneratrice && nbVivante <= maxPopulationRegeneratrice){
            estVivante = true;
        }
        changerCouleur();
    }

    public void changerCouleur() {
        Color c = null;
        if(etatPrecedant != estVivante) {
            if (estVivante) {
                c = Cellule.coulActive;
            } else {
                c = Cellule.coulDesactive;
            }
            circle.setFill(c);
        }
    }

    public boolean isEstVivante() {
        return estVivante;
    }

    public void setCircle(Circle circle) {
        this.circle = circle;
    }

    public Object clone() {
        Object o = null;
        try {
            o = super.clone();
        } catch (CloneNotSupportedException e) {
            System.err.println("Cellule impossible a cloner...");
        }
        return o;
    }
}


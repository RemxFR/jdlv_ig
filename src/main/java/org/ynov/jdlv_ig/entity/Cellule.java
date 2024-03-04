package org.ynov.jdlv_ig.entity;

import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import lombok.extern.java.Log;
import org.ynov.jdlv_ig.utils.LoggerUtil;

public class Cellule implements Cloneable {
    LoggerUtil logger = new LoggerUtil();
    public static int sousPopulation = 1;
    public static int surPopulation = 4;
    public static int minPopulationRegeneratrice = 3;
    public static int maxPopulationRegeneratrice = minPopulationRegeneratrice + 1;
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
        } else if (nbVivante == minPopulationRegeneratrice){
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
            this.logger.log(System.Logger.Level.ERROR, "Cellule impossible a cloner...");
        }
        return o;
    }
}

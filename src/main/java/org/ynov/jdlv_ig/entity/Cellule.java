package org.ynov.jdlv_ig.entity;

import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import lombok.extern.java.Log;
import org.ynov.jdlv_ig.utils.LoggerUtil;

/**
 * Classe qui permet de générer les cellules qui composent la grille.
 */
public class Cellule implements Cloneable {
    /**
     * Logger pour afficher les logs relatives à cette classe.
     */
    LoggerUtil logger = new LoggerUtil();
    /**
     * Paramètre qui défini la sous-population.
     * Valeur mimimum qui détruit la cellule.
     */
    public static int sousPopulation = 1;
    /**
     * Paramètre qui défini la sur-population.
     * Valeur maximum qui ressucite la cellule.
     */
    public static int surPopulation = 4;
    /**
     * Paramètre qui défini la Reproduction.
     * Nombre de cellules autour de la cellule qui permettent de la ressusciter.
     */
    public static int minPopulationRegeneratrice = 3;
    /**
     * Ancien état de la cellule.
     */
    boolean etatPrecedant;
    /**
     * Valeur qui permet de définir/savoir si la cellule est vivante ou non.
     */
    boolean estVivante;
    /**
     * Position de la cellule dans la grille.
     */
    int x, y;
    /**
     * Grille du jeu.
     */
    Cellule[][] grille;
    /**
     * Couleur d'une cellule lorsqu'elle devient active.
     */
    static final public Color coulVersActive = Color.CYAN;
    /**
     * Couleur d'une cellule lorsqu'elle est active.
     */
    static final public Color coulActive = Color.ALICEBLUE;
    /**
     * Couleur d'une cellule lorsqu'elle devient desactivé.
     */
    static final public Color coulVersDesactive = Color.INDIANRED;
    /**
     * Couleur d'une cellule lorsqu'elle est desactivé.
     */
    static final public Color coulDesactive = Color.color(0.1, 0, 0);
    /**
     * Forme de la cellule.
     */
    Circle circle;

    /**
     * Constructeur vide
     */
    public Cellule() {
    }

    /**
     * Constructeur qui initialuse la cellule dans la grille.
     * @param grille
     * @param x
     * @param y
     * @param estVivante
     */
    public Cellule(Cellule[][] grille, int x, int y, boolean estVivante) {
        this.grille = grille;
        this.estVivante = etatPrecedant = estVivante;
        this.x = x;
        this.y = y;
    }

    /**
     * Méthode qui fait évoluer l'état de la cellule dans la grille.
     */
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

    /**
     * Méthode qui fait changer la couleur de la cellule.
     */
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

    /**
     * Getter du paramètre estVivante.
     * @return
     */
    public boolean isEstVivante() {
        return estVivante;
    }

    /**
     * Setter de la forme de la cellule.
     * @param circle
     */
    public void setCircle(Circle circle) {
        this.circle = circle;
    }

    /**
     * Méthode qui permet de cloner la cellule.
     * @return
     */
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


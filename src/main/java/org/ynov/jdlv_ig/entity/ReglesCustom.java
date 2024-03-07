package org.ynov.jdlv_ig.entity;

import lombok.Getter;
import lombok.Setter;

/**
 * Classe de l'Objet qui représente les règles customisées.
 */
@Getter
@Setter
public class ReglesCustom {
    /**
     * Id.
     */
    private Integer id;
    /**
     * Sur-population, au delà de laquelle les cellule se désactivent.
     */
    private int surPopulation;
    /**
     * Sous-population, en dessous de laquelle les cellule se désactivent.
     */
    private int sousPopulation;
    /**
     * Reproduction, Nombre de cellules nécessaires pour en activer une autre.
     */
    private int reproduction;
    /**
     * Taille de la grille.
     */
    private int tailleGrille;
    /**
     * Utilisateur.
     */
    private User user;

    /**
     * Constructeur sans l'id et l'utilisateur.
     * @param surPopulation
     * @param sousPopulation
     * @param reproduction
     * @param tailleGrille
     */
    public ReglesCustom(int surPopulation, int sousPopulation, int reproduction, int tailleGrille) {
        this.surPopulation = surPopulation;
        this.sousPopulation = sousPopulation;
        this.reproduction = reproduction;
        this.tailleGrille = tailleGrille;
    }

    /**
     * Constructeur sans l'id.
     * @param surPopulation
     * @param sousPopulation
     * @param reproduction
     * @param tailleGrille
     * @param user
     */
    public ReglesCustom(int surPopulation, int sousPopulation, int reproduction, int tailleGrille, User user) {
        this.surPopulation = surPopulation;
        this.sousPopulation = sousPopulation;
        this.reproduction = reproduction;
        this.tailleGrille = tailleGrille;
        this.user = user;
    }

    /**
     * Constructeur vide.
     */
    public ReglesCustom() {
    }
}

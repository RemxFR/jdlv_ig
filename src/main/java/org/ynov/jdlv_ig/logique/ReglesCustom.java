package org.ynov.jdlv_ig.logique;

public class ReglesCustom {

    private Integer id;
    private int surPopulation;
    private int sousPopulation;
    private int reproduction;
    private int tailleGrille;

    public ReglesCustom(Integer id, int surPopulation, int sousPopulation, int reproduction, int tailleGrille, User user) {
        this.id = id;
        this.surPopulation = surPopulation;
        this.sousPopulation = sousPopulation;
        this.reproduction = reproduction;
        this.tailleGrille = tailleGrille;
    }

    public ReglesCustom() {
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public int getSurPopulation() {
        return surPopulation;
    }

    public void setSurPopulation(int surPopulation) {
        this.surPopulation = surPopulation;
    }

    public int getSousPopulation() {
        return sousPopulation;
    }

    public void setSousPopulation(int sousPopulation) {
        this.sousPopulation = sousPopulation;
    }

    public int getReproduction() {
        return reproduction;
    }

    public void setReproduction(int reproduction) {
        this.reproduction = reproduction;
    }

    public int getTailleGrille() {
        return tailleGrille;
    }

    public void setTailleGrille(int tailleGrille) {
        this.tailleGrille = tailleGrille;
    }
}

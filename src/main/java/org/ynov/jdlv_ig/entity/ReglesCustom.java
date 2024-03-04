package org.ynov.jdlv_ig.entity;

public class ReglesCustom {

    private Integer id;
    private int surPopulation;
    private int sousPopulation;
    private int reproduction;
    private int tailleGrille;
    private User user;

    public ReglesCustom(int surPopulation, int sousPopulation, int reproduction, int tailleGrille) {
        this.surPopulation = surPopulation;
        this.sousPopulation = sousPopulation;
        this.reproduction = reproduction;
        this.tailleGrille = tailleGrille;
    }

    public ReglesCustom(int surPopulation, int sousPopulation, int reproduction, int tailleGrille, User user) {
        this.surPopulation = surPopulation;
        this.sousPopulation = sousPopulation;
        this.reproduction = reproduction;
        this.tailleGrille = tailleGrille;
        this.user = user;
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

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }
}

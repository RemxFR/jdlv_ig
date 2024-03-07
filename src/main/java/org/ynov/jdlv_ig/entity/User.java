package org.ynov.jdlv_ig.entity;

import lombok.*;

import java.util.ArrayList;
import java.util.List;

/**
 * Utilisateur
 */
@Getter
@Setter
@AllArgsConstructor
@Builder
public class User {
    /**
     * Login.
     */
    private String login;
    /**
     * Mot de passe.
     */
    private String mdp;
    /**
     * Règles customisées.
     */
    private List<ReglesCustom> reglesCustoms = new ArrayList<>();

    /**
     * Constructeur
     */
    public User() {
    }

    /**
     * Constructeur avec login et mot de passe.
     * @param login
     * @param mdp
     */
    public User(String login, String mdp) {
        this.login = login;
        this.mdp = mdp;
    }
}

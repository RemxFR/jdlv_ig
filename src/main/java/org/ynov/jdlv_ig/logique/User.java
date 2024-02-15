package org.ynov.jdlv_ig.logique;

import lombok.*;
import org.ynov.jdlv_ig.logique.ReglesCustom;

import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@Builder
public class User {

    private String login;
    private String mdp;

    private List<ReglesCustom> reglesCustoms = new ArrayList<>();

    public User() {
    }
    public User(String login, String mdp) {
        this.login = login;
        this.mdp = mdp;
    }

}

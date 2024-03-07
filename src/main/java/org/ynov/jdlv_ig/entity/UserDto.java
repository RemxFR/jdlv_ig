package org.ynov.jdlv_ig.entity;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

/**
 * UserDto.
 */
@Builder
@Getter
@Setter
public class UserDto {
    /**
     * Login.
     */
    private String login;
    /**
     * Mot de passe.
     */
    private String mdp;
}

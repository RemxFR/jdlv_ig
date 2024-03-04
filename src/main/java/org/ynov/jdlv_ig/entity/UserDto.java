package org.ynov.jdlv_ig.entity;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Builder
@Getter
@Setter
public class UserDto {
    private String login;
    private String mdp;
}

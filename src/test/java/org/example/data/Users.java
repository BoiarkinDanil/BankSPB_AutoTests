package org.example.data;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Builder
public class Users {
    private String firstName;
    private String lastName;
    private String email;
    private String password;
    private Integer age;
    private Integer gender;
    private Boolean active;
}
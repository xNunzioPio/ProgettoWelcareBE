package com.wellcare.controller.dto.password;

import lombok.Data;

@Data
public class PasswordIdentityChangeDTO {

    private String oldPassword;
    private String password;
    private String repeatPassword;

}
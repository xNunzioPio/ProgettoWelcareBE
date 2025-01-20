package com.wellcare.controller.dto.password;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class PasswordChangeDTO {
    private String email;
    private String oldPassword;
    private String password;
    private String repeatPassword;

}
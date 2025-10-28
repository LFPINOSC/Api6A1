package com.apiweb.pagina.DTOs;

import lombok.Data;

@Data
public class LoginRequest {
    private String username;
    private String password;
}

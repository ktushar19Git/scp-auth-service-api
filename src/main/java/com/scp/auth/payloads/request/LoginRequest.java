package com.scp.auth.payloads.request;

import lombok.Data;

@Data
public class LoginRequest {

    private String userName;
    private String password;
}

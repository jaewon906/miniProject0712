package com.project0712.Auth;

import lombok.Getter;

@Getter
public class AuthDTO {
    private final String secretKey = "salgbTJWujnsJejsfSLfghaSSjAUWQdjGFUCDOWdJFUAIOFGDGAsdldfojwahfdgFGsajhsdl";
    private final byte[] secretKeyToByte = secretKey.getBytes();

}

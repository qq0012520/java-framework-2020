package com.tudog.graphqldemo01.tools;

import org.springframework.security.crypto.factory.PasswordEncoderFactories;
import org.springframework.security.crypto.password.PasswordEncoder;

public class EncryptUtil {
    private EncryptUtil(){
        throw new RuntimeException("Not support initiating the Class");
    }

    private static final PasswordEncoder PASSWORD_ENCODER = PasswordEncoderFactories.createDelegatingPasswordEncoder();

    public static String encryptPassword(String rawPassword){
        return PASSWORD_ENCODER.encode(rawPassword);
    }
}
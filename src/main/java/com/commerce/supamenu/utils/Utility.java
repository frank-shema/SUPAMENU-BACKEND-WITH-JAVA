package com.commerce.supamenu.utils;

import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

public class Utility {
    private static final PasswordEncoder passwordEncoder = new BCryptPasswordEncoder();

    public static String hash(String raw){
        return passwordEncoder.encode(raw);
    }

    public static boolean matches(String raw, String hash){
        return passwordEncoder.matches(raw, hash);
    }
}

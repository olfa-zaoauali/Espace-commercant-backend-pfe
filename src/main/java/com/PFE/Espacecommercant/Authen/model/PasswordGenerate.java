package com.PFE.Espacecommercant.Authen.model;

import lombok.AllArgsConstructor;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

@AllArgsConstructor
public class PasswordGenerate {
    public static String generatepassword() {
        BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();
        String password = generateRandomPassword(10);
        String encodedPassword = encoder.encode(password);

        return password ;
    }

    private static String generateRandomPassword(int length) {
        String allowedChars = "abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ0123456789!@#$%^&*()_+-=[]{}|;:,.<>?";
        StringBuilder password = new StringBuilder(length);
        for (int i = 0; i < length; i++) {
            int index = (int) (Math.random() * allowedChars.length());
            password.append(allowedChars.charAt(index));
        }
        return password.toString();
    }
}

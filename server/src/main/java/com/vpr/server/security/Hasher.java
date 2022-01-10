package com.vpr.server.security;

import javax.crypto.SecretKeyFactory;
import javax.crypto.spec.PBEKeySpec;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import java.security.spec.InvalidKeySpecException;
import java.security.spec.KeySpec;

public class Hasher {

    public static byte[] HashPassword(String password, byte[] salt) throws NoSuchAlgorithmException, InvalidKeySpecException {
        // Credit: https://www.baeldung.com/java-password-hashing
        // Generate hash with PBKDF2
        KeySpec spec = new PBEKeySpec(password.toCharArray(), salt, 65536, 128);
        SecretKeyFactory factory = SecretKeyFactory.getInstance("PBKDF2WithHmacSHA1");
        return factory.generateSecret(spec).getEncoded();
    }

    public static byte[] GenerateSalt(){
        // Credit: https://www.baeldung.com/java-password-hashing
        // Create a salt
        SecureRandom random = new SecureRandom();
        byte[] salt = new byte[16];
        random.nextBytes(salt);
        return salt;
    }
}

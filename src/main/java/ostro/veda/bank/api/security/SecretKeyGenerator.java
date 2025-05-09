package ostro.veda.bank.api.security;

import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.security.Keys;

import java.security.Key;
import java.util.Base64;

public class SecretKeyGenerator {

    public static void main(String[] args) {
        Key key = Keys.secretKeyFor(SignatureAlgorithm.HS512); // Automatically generates a secure key
        System.out.println(Base64.getEncoder().encodeToString(key.getEncoded())); // Save this securely
    }
}

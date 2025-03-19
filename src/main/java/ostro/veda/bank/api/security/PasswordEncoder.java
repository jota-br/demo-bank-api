package ostro.veda.bank.api.security;

import org.springframework.security.crypto.bcrypt.BCrypt;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Component;

import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import java.util.Base64;

@Component
public class PasswordEncoder {

    public String encode(String password, String salt) throws NoSuchAlgorithmException {
        return getHash(password, salt);
    }

    public boolean matches(String password, String userPassword, String salt) throws NoSuchAlgorithmException {
        return encode(password, salt).equals(userPassword);
    }

    public String getEncodedSalt() throws NoSuchAlgorithmException {
        byte[] salt = getSalt();
        return Base64.getEncoder().encodeToString(getSalt());
    }

    private byte[] getSalt() throws NoSuchAlgorithmException {

        SecureRandom sr = SecureRandom.getInstance("SHA1PRNG");
        byte[] salt = new byte[32];
        sr.nextBytes(salt);
        return salt;

    }

    private String getHash(String password, byte[] salt) throws NoSuchAlgorithmException {

        MessageDigest md = MessageDigest.getInstance("SHA-256");
        md.update(salt);
        byte[] hashedPassword = md.digest(password.getBytes());
        return Base64.getEncoder().encodeToString(hashedPassword);

    }

    private String getHash(String password, String salt) throws NoSuchAlgorithmException {

        byte[] saltByte = salt.getBytes();
        MessageDigest md = MessageDigest.getInstance("SHA-256");
        md.update(saltByte);
        byte[] hashedPassword = md.digest(password.getBytes());
        return Base64.getEncoder().encodeToString(hashedPassword);

    }
}

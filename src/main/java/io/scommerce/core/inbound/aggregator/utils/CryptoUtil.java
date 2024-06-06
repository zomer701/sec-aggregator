package io.scommerce.core.inbound.aggregator.utils;

import lombok.extern.slf4j.Slf4j;

import javax.crypto.Cipher;
import javax.crypto.SecretKey;
import javax.crypto.SecretKeyFactory;
import javax.crypto.spec.PBEKeySpec;
import javax.crypto.spec.PBEParameterSpec;
import java.security.spec.AlgorithmParameterSpec;
import java.security.spec.KeySpec;
import java.util.Base64;

@Slf4j
public class CryptoUtil {
    private static final String SECRET_ORDER_KEY = "RY9Q8QMBAMPWTB4Z";
    private static final String CHAR_SET = "UTF-8";

    private static final byte[] salt = {
            (byte) 0xA9, (byte) 0x9B, (byte) 0xC8, (byte) 0x32,
            (byte) 0x56, (byte) 0x35, (byte) 0xE3, (byte) 0x03
    };
    // Iteration count
    private static final int iterationCount = 19;

    public static String encrypt(String plainText) {
        try {
            KeySpec keySpec = new PBEKeySpec(SECRET_ORDER_KEY.toCharArray(), salt, iterationCount);
            SecretKey key = SecretKeyFactory.getInstance("PBEWithMD5AndDES").generateSecret(keySpec);
            AlgorithmParameterSpec paramSpec = new PBEParameterSpec(salt, iterationCount);

            Cipher ecipher = Cipher.getInstance(key.getAlgorithm());
            ecipher.init(Cipher.ENCRYPT_MODE, key, paramSpec);
            byte[] out = ecipher.doFinal(plainText.getBytes(CHAR_SET));
            return Base64.getUrlEncoder().encodeToString(out);

        } catch (Exception e) {
            log.error("encrypt error = " + plainText);
        }

        return null;
    }

    public static String decrypt(String encryptedText) {
        try {
            KeySpec keySpec = new PBEKeySpec(SECRET_ORDER_KEY.toCharArray(), salt, iterationCount);
            SecretKey key = SecretKeyFactory.getInstance("PBEWithMD5AndDES").generateSecret(keySpec);
            AlgorithmParameterSpec paramSpec = new PBEParameterSpec(salt, iterationCount);
            Cipher dcipher = Cipher.getInstance(key.getAlgorithm());
            dcipher.init(Cipher.DECRYPT_MODE, key, paramSpec);
            byte[] utf8 = dcipher.doFinal(Base64.getUrlDecoder().decode(encryptedText));

            return new String(utf8, CHAR_SET);
        } catch (Exception e) {
            log.error("decrypt error = " + encryptedText);
        }

        return null;
    }
}

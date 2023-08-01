package ruby.loghistory.encrypt;

import jakarta.annotation.PostConstruct;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import javax.crypto.BadPaddingException;
import javax.crypto.Cipher;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.SecretKeySpec;
import java.nio.charset.StandardCharsets;
import java.security.InvalidAlgorithmParameterException;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.util.Base64;

/**
 * AES
 *  - 대칭키 암호화. 암호화 및 복호화에 동일한 키를 사용
 */
@Component
@RequiredArgsConstructor
public class AES256Util {

    private final AES256Properties aes256Properties;
    private Cipher encryptCipher;
    private Cipher decryptCipher;

    @PostConstruct
    private void post() throws
        NoSuchPaddingException, NoSuchAlgorithmException, InvalidAlgorithmParameterException, InvalidKeyException {
        SecretKeySpec keySpec = getKeySpec();
        IvParameterSpec ivParamSpec = getIvParameterSpec();
        this.encryptCipher = getCipher();
        encryptCipher.init(Cipher.ENCRYPT_MODE, keySpec, ivParamSpec);
        this.decryptCipher = getCipher();
        decryptCipher.init(Cipher.DECRYPT_MODE, keySpec, ivParamSpec);
    }

    /**
     * 암호화
     */
    public String encrypt(String text) {
        try {
            byte[] encrypted = encryptCipher.doFinal(text.getBytes(StandardCharsets.UTF_8));
            return Base64.getEncoder().encodeToString(encrypted);
        } catch (
                IllegalBlockSizeException |
                BadPaddingException e) {
            throw new AES256Exception();
        }
    }

    /**
     * 복호화
     */
    public String decrypt(String cipherText) {
        try {
            byte[] decodedBytes = Base64.getDecoder().decode(cipherText);
            byte[] decrypted = decryptCipher.doFinal(decodedBytes);
            return new String(decrypted, StandardCharsets.UTF_8);
        } catch (
                IllegalBlockSizeException |
                BadPaddingException e) {
            throw new AES256Exception();
        }
    }

    /**
     * Chiper
     *  - 암호화 및 복호화 기능을 제공하는 클래스
     */
    private Cipher getCipher() throws NoSuchPaddingException, NoSuchAlgorithmException {
        String alg = aes256Properties.alg();
        /** 지정한 알고리즘의 Chiper 인스턴스 생성 */
        return Cipher.getInstance(alg);
    }

    /**
     * SecretKeySpec
     *  - 암호화 및 복호화에 필요한 비밀키를 구성
     */
    private SecretKeySpec getKeySpec() {
        String key = aes256Properties.key();
        return new SecretKeySpec(key.getBytes(), "AES");
    }

    /**
     * IvParameterSpec
     *  - 암호화시 비밀 키와 함께 사용되는 난수 생성
     */
    private IvParameterSpec getIvParameterSpec() {
        String iv = aes256Properties.key().substring(0, 16);
        return new IvParameterSpec(iv.getBytes());
    }
}

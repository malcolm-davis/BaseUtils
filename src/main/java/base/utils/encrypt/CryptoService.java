package base.utils.encrypt;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.security.InvalidAlgorithmParameterException;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.security.spec.AlgorithmParameterSpec;
import java.security.spec.InvalidKeySpecException;
import java.security.spec.KeySpec;

import javax.crypto.BadPaddingException;
import javax.crypto.Cipher;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;
import javax.crypto.SecretKey;
import javax.crypto.SecretKeyFactory;
import javax.crypto.spec.PBEKeySpec;
import javax.crypto.spec.PBEParameterSpec;

/**
 * CryptoService is simply wrapper to encrypt/decrypt functionality.
 *
 * <b>Notes:</b>
 * <ul>
 *   <li>Do not use for passwords stored in a database.  1-way hash should be used for database passwords.</li>
 *   <li>For increased security, store the parameters passPhrase, salt and iterationCount on another system than were this is being used.</li>
 * </ul>
 *
 * @author Malcolm G. Davis
 * @version 1.0
 */
public class CryptoService implements Crypto  {

    /**
     * <p>newUtils.</p>
     *
     * @param passPhrase a {@link java.lang.String} object.
     * @param salt an array of byte.
     * @param iterationCount a int.
     * @return a {@link org.elasolutions.utils.encrypt.Crypto} object.
     * @throws java.security.spec.InvalidKeySpecException if any.
     * @throws java.security.NoSuchAlgorithmException if any.
     * @throws javax.crypto.NoSuchPaddingException if any.
     * @throws java.security.InvalidKeyException if any.
     * @throws java.security.InvalidAlgorithmParameterException if any.
     */
    public static Crypto newUtils(String passPhrase, byte[] salt, int iterationCount) throws InvalidKeySpecException, NoSuchAlgorithmException, NoSuchPaddingException, InvalidKeyException, InvalidAlgorithmParameterException {
        final KeySpec keySpec =
                new PBEKeySpec(passPhrase.toCharArray(), salt, iterationCount);
        final SecretKey key =
                SecretKeyFactory.getInstance("PBEWithMD5AndDES")
                .generateSecret(keySpec);

        Cipher dcipher = Cipher.getInstance(key.getAlgorithm());

        Cipher ecipher = Cipher.getInstance(key.getAlgorithm());

        // Prepare the parameter to the ciphers
        final AlgorithmParameterSpec paramSpec = new PBEParameterSpec(salt, iterationCount);

        // Create the ciphers
        ecipher.init(Cipher.ENCRYPT_MODE, key, paramSpec);
        dcipher.init(Cipher.DECRYPT_MODE, key, paramSpec);
        return new CryptoService(passPhrase, salt, iterationCount, ecipher, dcipher);
    }


    /** {@inheritDoc} */
    @Override
    public String decrypt(final String str) throws IOException, IllegalBlockSizeException, BadPaddingException {
        // Decode base64 to get bytes
        @SuppressWarnings("restriction")
        // final byte[] dec = new sun.misc.BASE64Decoder().decodeBuffer(str);
        final byte[] dec = java.util.Base64.getDecoder().decode(str);

        // Decrypt
        byte[] utf8 = null;

        // Cipher is not thread safe, need to use synchronized
        synchronized(m_dcipher){
            utf8 = m_dcipher.doFinal(dec);
        }

        // Decode using utf-8
        return new String(utf8, "UTF8");
    }

    /** {@inheritDoc} */
    @Override
    @SuppressWarnings("restriction")
    public String encrypt(final String str) throws UnsupportedEncodingException, IllegalBlockSizeException, BadPaddingException {
        // Encode the string into bytes using utf-8
        final byte[] utf8 = str.getBytes("UTF8");

        // Encrypt
        byte[] enc = null;

        // Cipher is not thread safe, need to use synchronized
        synchronized(m_ecipher){
            enc = m_ecipher.doFinal(utf8);
        }

        // Encode bytes to base64 to get a string
        // return new sun.misc.BASE64Encoder().encode(enc);
        return java.util.Base64.getEncoder().encodeToString(enc);
    }

    /**
     * <p>Constructor for CryptoService.</p>
     *
     * @param passPhrase a {@link java.lang.String} object.
     * @param salt an array of byte.
     * @param iterationCount a int.
     * @param ecipher a {@link javax.crypto.Cipher} object.
     * @param dcipher a {@link javax.crypto.Cipher} object.
     */
    public CryptoService(String passPhrase, byte[] salt, int iterationCount, Cipher ecipher, Cipher dcipher) {
        m_passPhrase = passPhrase;
        m_salt = salt;
        m_iterationCount = iterationCount;
        m_ecipher = ecipher;
        m_dcipher = dcipher;
    }

    Cipher m_dcipher;

    Cipher m_ecipher;

    int m_iterationCount = 19;

    String m_passPhrase = "YourPassPhrase";

    // 8-byte Salt
    byte[] m_salt =
        { (byte) 0xA9, (byte) 0x9B, (byte) 0xC8, (byte) 0x32, (byte) 0x56,
            (byte) 0x35, (byte) 0xE3, (byte) 0x03 };
}

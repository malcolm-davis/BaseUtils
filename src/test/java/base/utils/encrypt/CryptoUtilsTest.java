package base.utils.encrypt;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.security.InvalidAlgorithmParameterException;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.security.spec.InvalidKeySpecException;

import javax.crypto.BadPaddingException;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;

import base.utils.encrypt.Crypto;
import base.utils.encrypt.CryptoService;
import junit.framework.TestCase;


public class CryptoUtilsTest  extends TestCase {

    @Override
    protected void setUp() throws Exception {
        super.setUp();
    }

    @Override
    protected void tearDown() throws Exception {
        super.tearDown();
    }


    public void testCrypto() {
        int iterationCount = 19;
        String passPhrase = "YourPassPhrase";
        // 8-byte Salt
        byte[] salt =
            { (byte) 0xA9, (byte) 0x9B, (byte) 0xC8, (byte) 0x32, (byte) 0x56,
            (byte) 0x35, (byte) 0xE3, (byte) 0x03 };


        Crypto encrypter;

        try {
            encrypter = CryptoService.newUtils(passPhrase, salt, iterationCount);
            String COMPARE = "Don't tell anybody!";
            String encrypted = encrypter.encrypt(COMPARE);
            System.out.println("encrypted=" + encrypted);

            assertEquals(COMPARE,encrypter.decrypt(encrypted));

        } catch (InvalidKeyException excep) {
            assertTrue(false);
        } catch (InvalidKeySpecException excep) {
            assertTrue(false);
        } catch (NoSuchAlgorithmException excep) {
            assertTrue(false);
        } catch (NoSuchPaddingException excep) {
            assertTrue(false);
        } catch (InvalidAlgorithmParameterException excep) {
            assertTrue(false);
        } catch (UnsupportedEncodingException excep) {
            assertTrue(false);
        } catch (IllegalBlockSizeException excep) {
            assertTrue(false);
        } catch (BadPaddingException excep) {
            assertTrue(false);
        } catch (IOException excep) {
            assertTrue(false);
        }

    }

}

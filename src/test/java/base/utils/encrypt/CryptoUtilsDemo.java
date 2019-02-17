package base.utils.encrypt;

import java.io.IOException;
import java.security.InvalidAlgorithmParameterException;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.security.spec.InvalidKeySpecException;

import javax.crypto.BadPaddingException;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;

import base.utils.encrypt.Crypto;
import base.utils.encrypt.CryptoService;



public class CryptoUtilsDemo {

    public static void main(String[] args) {
        System.out.println("Start: CryptoUtilsDemo ");
        System.out.println("---------------------------------------------");

        long start = System.currentTimeMillis();

        CryptoUtilsDemo test = new CryptoUtilsDemo();
        try {
            test.run();
        } catch (Exception excep) {
            excep.printStackTrace();
        }

        System.out.println("---------------------------------------------");
        System.out.println("Runtime: " + (System.currentTimeMillis() - start));
        System.out.println("Finished.");
        System.exit(0);
    }

    private void run() throws InvalidKeyException, InvalidKeySpecException, NoSuchAlgorithmException, NoSuchPaddingException, InvalidAlgorithmParameterException, IllegalBlockSizeException, BadPaddingException, IOException {
        int iterationCount = 19;

        String passPhrase = "YourPassPhrase";

        // 8-byte Salt
        byte[] salt =
            { (byte) 0xA9, (byte) 0x9B, (byte) 0xC8, (byte) 0x32, (byte) 0x56,
            (byte) 0x35, (byte) 0xE3, (byte) 0x03 };


        Crypto encrypter = CryptoService.newUtils(passPhrase, salt, iterationCount);

        final String encrypted = encrypter.encrypt("Don't tell anybody!");
        System.out.println("encrypted=" + encrypted);
        System.out.println("decrypted="+encrypter.decrypt(encrypted));
    }

}

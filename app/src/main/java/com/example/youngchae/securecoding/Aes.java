package com.example.youngchae.securecoding;

import java.io.UnsupportedEncodingException;
import java.security.InvalidAlgorithmParameterException;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;

import javax.crypto.BadPaddingException;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;

/**
 * Created by youngchae on 2016-08-14.
 */
public class Aes {
    private static Aes256Util aes;
    public Aes() throws UnsupportedEncodingException {
        aes = new Aes256Util("1kh3k2429u34l1j234");
    }
    public static String LoginEncPass(String str) throws NoSuchPaddingException, InvalidAlgorithmParameterException, UnsupportedEncodingException, IllegalBlockSizeException, BadPaddingException, NoSuchAlgorithmException, InvalidKeyException {
        return aes.aesEncode(str);
    }
    public static String FindpwDecPass(String str) throws NoSuchPaddingException, InvalidAlgorithmParameterException, UnsupportedEncodingException, IllegalBlockSizeException, BadPaddingException, NoSuchAlgorithmException, InvalidKeyException {
        return aes.aesDecode(str);
    }
    public static String RegEncPass(String str) throws NoSuchPaddingException, InvalidAlgorithmParameterException, UnsupportedEncodingException, IllegalBlockSizeException, BadPaddingException, NoSuchAlgorithmException, InvalidKeyException {
        return aes.aesEncode(str);
    }
}

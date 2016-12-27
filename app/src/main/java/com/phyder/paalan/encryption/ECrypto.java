
package com.phyder.paalan.encryption;

import android.annotation.SuppressLint;
import android.app.Application;
import android.util.Log;

import java.io.IOException;
import java.io.InputStream;
import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.net.URLEncoder;
import java.security.InvalidAlgorithmParameterException;
import java.security.InvalidKeyException;
import java.security.Key;
import java.security.KeyFactory;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.security.PublicKey;
import java.security.SecureRandom;
import java.security.spec.InvalidKeySpecException;
import java.security.spec.X509EncodedKeySpec;

import javax.crypto.BadPaddingException;
import javax.crypto.Cipher;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.SecretKeySpec;

public class ECrypto {

    private static ECrypto instance;

    private static final String TAG = "ECrypto";

    // AES
    private static final int KEY_SIZE = 16;

    private Key mKeyAES;

    private String mStrAESKey;

    private String mStrAESIV;

    // RSA
    private String mStrRSAPublicKey;

    private PublicKey mKeyRSAPublic;

    private ECrypto() {
    }

    public static ECrypto getInstance() {
        if (instance == null) {
            instance = new ECrypto();
        }
        return instance;
    }

    public Key getAESKey() {
        return mKeyAES;
    }

    public void setAESKey(Key AESKey) {
        this.mKeyAES = AESKey;
    }

    public String getAESStringKey() {
        return mStrAESKey;
    }

    public void setAESStringKey(String mStrAESKey) {
        this.mStrAESKey = mStrAESKey;
    }

    public String getAESIV() {
        return mStrAESIV;
    }

    public void setAESIV(String AESIV) {
        this.mStrAESIV = AESIV;
    }

    public String getRSAPublicStringKey() {
        return mStrRSAPublicKey;
    }

    public void setRSAPublicStringKey(String mStrRSAPublicKey) {
        // RSA Public key is initialized in the first Activity in this case
        // com.phyer.engage.Splash
        this.mStrRSAPublicKey = mStrRSAPublicKey;
    }

    public PublicKey getRSAPublicKey() {
        // RSA Public key is initialized in the first Activity in this case
        // com.phyer.engage.Splash
        return mKeyRSAPublic;
    }

    public void setRSAPublicKey(PublicKey RSAPublicKey) {
        this.mKeyRSAPublic = RSAPublicKey;
    }

    public void generateKey(Application application) {
        // Every time new AES Key is generated
        try {
            generateAESKey();
        } catch (UnsupportedEncodingException eueEx) {
            Log.d(getClass().getSimpleName(),
                    getClass().getSimpleName() + ": " + eueEx.getMessage());
//            Log.stack(eueEx);
        }
        // Same RSA Public key is initialized every if not already initialized
        // initiateRSAPublicKey(application);
    }

    @SuppressLint("TrulyRandom")
    public Key generateAESKey() throws UnsupportedEncodingException {

        byte[] bytes = new byte[KEY_SIZE];
        SecureRandom secureRandom = new SecureRandom();
        secureRandom.nextBytes(bytes);
        String encodedKey = new String(Base64Coder.encode(bytes));
        mStrAESKey = encodedKey.substring(0, 16);
        mKeyAES = new SecretKeySpec(mStrAESKey.getBytes("UTF-8"), "AES");

        bytes = new byte[KEY_SIZE];
        secureRandom = new SecureRandom();
        secureRandom.nextBytes(bytes);
        String encodedIV = new String(Base64Coder.encode(bytes));
        mStrAESIV = encodedIV.substring(0, 16);

        return mKeyAES;
    }

    public void initiateRSAPublicKey(Application application) {
        try {

            if (mKeyRSAPublic == null) {
                InputStream instream = null;
                byte[] encodedKey = new byte[instream.available()];
                instream.read(encodedKey);
                mStrRSAPublicKey = new String(encodedKey);
                X509EncodedKeySpec publicKeySpec = new X509EncodedKeySpec(encodedKey);
                KeyFactory kf = KeyFactory.getInstance("RSA");
                PublicKey pkPublic = kf.generatePublic(publicKeySpec);
                mKeyRSAPublic = pkPublic;
                instream.close();

//                if (BuildConfig.DEBUG) {
//                    ELog.d(TAG, "RSA Public Key String: " + mStrRSAPublicKey + " RSA KEY: "
//                            + mKeyRSAPublic);
//                }
            }
        } catch (NoSuchAlgorithmException | IOException | InvalidKeySpecException nsaEx) {
//            ELog.d(TAG + " RSA Public Key genration",
//                    "NoSuchAlgorithmException: " + nsaEx.getMessage());
        }
    }

    public String encryptAES(String plainText, boolean isToBeEncoded) {
        String encodedCipherParam = null;
        try {
            Cipher cipher = Cipher.getInstance("AES/CBC/PKCS7PADDING");
            IvParameterSpec ivParameterSpec = new IvParameterSpec(mStrAESIV.getBytes("UTF-8"));

            cipher.init(Cipher.ENCRYPT_MODE, mKeyAES, ivParameterSpec);
            byte[] encryptedInByte = cipher.doFinal(plainText.getBytes("UTF-8"));

            String encryptedParam = new String(Base64Coder.encode(encryptedInByte));
            encodedCipherParam = encryptedParam;

            // URL encode the data
            if (isToBeEncoded) {
                encodedCipherParam = URLEncoder.encode(encryptedParam, "UTF-8");
            }

//            if (BuildConfig.DEBUG) {
//                Log.d(TAG, "Base64Encoded Encrypted Param: " + encodedCipherParam);
//                Log.d(TAG, "URLEncoded Encrypted Param: " + encodedCipherParam);
//            }

        } catch (UnsupportedEncodingException | InvalidKeyException | NoSuchPaddingException | NoSuchAlgorithmException | IllegalBlockSizeException | InvalidAlgorithmParameterException | BadPaddingException ueEx) {
            Log.e(TAG, "UnsupportedEncodingException: " + ueEx.getMessage());
//            Log.stack(ueEx);
        }

        return encodedCipherParam;
    }

    public String decryptAES(String cipherText, boolean isToBeDecoded) {
        String plainText = null;
        try {
            if (isToBeDecoded) {
                cipherText = URLDecoder.decode(cipherText, "UTF-8");
            }
            byte[] bytEncrypted = Base64Coder.decode(cipherText);
            Cipher cipher = Cipher.getInstance("AES/CBC/PKCS7PADDING");
            IvParameterSpec ivParameterSpec = new IvParameterSpec(mStrAESIV.getBytes("UTF-8"));

            cipher.init(Cipher.DECRYPT_MODE, mKeyAES, ivParameterSpec);
            byte[] encryptedInByte = cipher.doFinal(bytEncrypted);
            plainText = new String(encryptedInByte, "UTF-8");

//            if (BuildConfig.DEBUG) {
////                ELog.d(TAG, "Encrypted Cipher: " + cipherText);
////                ELog.d(TAG, "Decryption Key: " + mStrAESKey);
////                ELog.d(TAG, "Decrypted Text: " + plainText);
//            }
        } catch (UnsupportedEncodingException | InvalidKeyException | NoSuchPaddingException | NoSuchAlgorithmException | IllegalBlockSizeException | BadPaddingException | InvalidAlgorithmParameterException ueEx) {
//            ELog.e(TAG, "UnsupportedEncodingException: " + ueEx.getMessage());
//            ELog.stack(ueEx);
        }
        return plainText;
    }

    public String encryptRSA(String plainText, boolean isToBeEncoded) {
        String encodedCipherText = null;
        try {
            PublicKey pkPublic = getRSAPublicKey();

            Cipher pkCipher = Cipher.getInstance("RSA/None/PKCS1PADDING");
            pkCipher.init(Cipher.ENCRYPT_MODE, pkPublic);
            byte[] encryptedInByte = pkCipher.doFinal(plainText.getBytes("UTF-8"));

            String encryptedParam = new String(Base64Coder.encode(encryptedInByte));
            encodedCipherText = encryptedParam;

            if (isToBeEncoded) {
                encodedCipherText = URLEncoder.encode(encryptedParam, "UTF-8");
            }
        } catch (InvalidKeyException | NoSuchAlgorithmException | IllegalBlockSizeException | NoSuchPaddingException | BadPaddingException | IOException ikEx) {
//            ELog.e(TAG, "InvalidKeyException: " + ikEx.getMessage());
//            ELog.stack(ikEx);
        }
        return encodedCipherText;
    }

    public String sha512(String s) {
        StringBuilder builder = null;
        try {
            MessageDigest md = MessageDigest.getInstance("SHA-512");
            md.update(s.getBytes("UTF-8"));
            byte[] bytes = md.digest();
            builder = new StringBuilder();
            for (int i = 0; i < bytes.length; i++) {
                String tmp = Integer.toString((bytes[i] & 0xff) + 0x100, 16).substring(1);
                builder.append(tmp);
            }
        } catch (NoSuchAlgorithmException | UnsupportedEncodingException nsaEx) {
//            ELog.e(TAG, "NoSuchAlgorithmException: " + nsaEx.getMessage());
//            ELog.stack(nsaEx);
        }

        return builder.toString();
    }

}

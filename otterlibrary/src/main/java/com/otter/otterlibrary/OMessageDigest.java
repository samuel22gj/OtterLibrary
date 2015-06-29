package com.otter.otterlibrary;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.math.BigInteger;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

/**
 * OMessageDigest is a wrapper of {@link java.security.MessageDigest}.
 */
public class OMessageDigest {

    /**
     * Based on specific algorithm, computes and returns the hash value.
     *
     * @param algorithm The cryptographic hash function(e.g., MD5, SHA-1, SHA-256)
     * @param data The byte array.
     *
     * @return Hash value or null.
     */
    public static String calculate(String algorithm, byte[] data) {
        String hashValue = null;

        try{
            MessageDigest digester = MessageDigest.getInstance(algorithm);
            digester.update(data);
            byte[] digest = digester.digest();

            // Using BigInteger to convert byte to hex string
            hashValue = new BigInteger(1, digest).toString(16).toUpperCase();
            while (hashValue.length() < 32) {
                hashValue = "0" + hashValue;
            }
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        }

        return hashValue;
    }

    /**
     * Based on specific algorithm, computes and returns the hash value.
     *
     * @param algorithm The cryptographic hash function(e.g., MD5, SHA-1, SHA-256)
     * @param file The target file.
     *
     * @return Hash value or null.
     */
    public static String calculate(String algorithm, File file) {
        String hashValue = null;

        try{
            InputStream input = new FileInputStream(file);
            hashValue = calculate(algorithm, input);
            input.close();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }

        return hashValue;
    }

    /**
     * Based on specific algorithm, computes and returns the hash value.
     *
     * @param algorithm The cryptographic hash function(e.g., MD5, SHA-1, SHA-256)
     * @param inputStream The data input stream.
     *
     * @return Hash value or null.
     */
    public static String calculate(String algorithm, InputStream inputStream) {
        String hashValue = null;

        try{
            MessageDigest digester = MessageDigest.getInstance(algorithm);
            byte[] bytes = new byte[8192];
            int byteCount;
            while ((byteCount = inputStream.read(bytes)) > 0) {
                digester.update(bytes, 0, byteCount);
            }
            byte[] digest = digester.digest();

            // Using BigInteger to convert byte to hex string
            hashValue = new BigInteger(1, digest).toString(16).toUpperCase();
            while (hashValue.length() < 32) {
                hashValue = "0" + hashValue;
            }
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }

        return hashValue;
    }
}

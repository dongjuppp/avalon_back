package web.game.avalon.utils;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;


public class Encryption {

    public static String getSha256(String id,String pwd) throws NoSuchAlgorithmException {
        String code=id+pwd;

        MessageDigest md = MessageDigest.getInstance("SHA-256");
        md.update(code.getBytes());

        StringBuilder stringBuilder = new StringBuilder();
        byte[] bytes=md.digest();
        for(byte b:bytes){
            stringBuilder.append(String.format("%02x",b));
        }
        return stringBuilder.toString();

    }
}

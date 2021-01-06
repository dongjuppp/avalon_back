package web.game.avalon.utils;

import java.security.SecureRandom;

public class SirialNumber {
    private static SecureRandom random = new SecureRandom();
    static String ENGLISH_LOWER = "abcdefghijklmnopqrstuvwxyz";
    static String ENGLISH_UPPER = ENGLISH_LOWER.toUpperCase();
    static String NUMBER = "0123456789";

    /** 랜덤을 생성할 대상 문자열 **/
    static String DATA_FOR_RANDOM_STRING = ENGLISH_LOWER + ENGLISH_UPPER + NUMBER;

    /** 랜덤 문자열 길이 **/
    static int random_string_length=10;


    private static String generate(String DATA, int length) {
        if (length < 1) throw new IllegalArgumentException("length must be a positive number.");
        StringBuilder sb = new StringBuilder(length);
        for (int i = 0; i < length; i++) {
            sb.append( DATA.charAt(
                    random.nextInt(DATA.length())
            ));
        }
        return sb.toString();
    }

    public static String getSirialNumber(){
        return generate(DATA_FOR_RANDOM_STRING,random_string_length);
    }
}

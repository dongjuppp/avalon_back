package web.game.avalon.utils;

import java.util.Random;

public class ValidationCode {


    public static String excuteGenerate() {
        int length = 8;
        char[] characterTable = { 'A', 'B', 'C', 'D', 'E', 'F', 'G', 'H', 'I', 'J', 'K', 'L',
                'M', 'N', 'O', 'P', 'Q', 'R', 'S', 'T', 'U', 'V', 'W', 'X',
                'Y', 'Z', '1', '2', '3', '4', '5', '6', '7', '8', '9', '0' };
        Random random = new Random(System.currentTimeMillis());
        int tableLength = characterTable.length;
        StringBuffer buf = new StringBuffer();

        for(int i = 0; i < length; i++) {
            buf.append(characterTable[random.nextInt(tableLength)]);
        }

        return buf.toString();
    }

}

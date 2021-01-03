package web.game.avalon.game;
import java.security.SecureRandom;


import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class Room {
    private String roomName;
    private int rule;
    //private int NumberOfPeople;
    private Game game;
    private String roomId;
    private static SecureRandom random = new SecureRandom();
    String ENGLISH_LOWER = "abcdefghijklmnopqrstuvwxyz";
    String ENGLISH_UPPER = ENGLISH_LOWER.toUpperCase();
    String NUMBER = "0123456789";

    /** 랜덤을 생성할 대상 문자열 **/
    String DATA_FOR_RANDOM_STRING = ENGLISH_LOWER + ENGLISH_UPPER + NUMBER;

    /** 랜덤 문자열 길이 **/
    int random_string_length=10;

    public void makeGame(){
        makeId();
        this.game=new Game();
    }

    public String getRoomId(){
        return roomId;
    }



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

    private void makeId(){
        roomId=generate(DATA_FOR_RANDOM_STRING,random_string_length);
    }

}

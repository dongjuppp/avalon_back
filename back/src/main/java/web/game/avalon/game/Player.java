package web.game.avalon.game;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import web.game.avalon.game.character.GameCharacter;

import java.util.ArrayList;

@Setter
@Getter
@ToString
public class Player {
    private String userId;
    private int turnNumber;
    private GameCharacter gameCharacter;
    private ArrayList<String> images;
    private Boolean checked; //게임에서 선택되었는지 화면에 표시하기 위함
    private Boolean proAndCons; //찬성 반대, 성공 실패를 위함


    public Player(){}

    public Player(int turnNumber,String userId){
        this.turnNumber=turnNumber;
        this.userId=userId;
        this.checked=false;
    }
}

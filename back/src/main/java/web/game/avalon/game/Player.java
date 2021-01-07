package web.game.avalon.game;

import lombok.Getter;
import lombok.Setter;
import web.game.avalon.game.character.GameCharacter;

import java.util.ArrayList;

@Setter
@Getter
public class Player {
    private String userId;
    private int turnNumber;
    private GameCharacter gameCharacter;
    private ArrayList<String> images;

    public Player(){}

    public Player(int turnNumber,String userId){
        this.turnNumber=turnNumber;
        this.userId=userId;
    }
}

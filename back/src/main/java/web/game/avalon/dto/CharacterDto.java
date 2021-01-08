package web.game.avalon.dto;

import lombok.Getter;
import lombok.Setter;

import java.util.ArrayList;

@Getter
@Setter
public class CharacterDto {
    private ArrayList<String> users;
    private ArrayList<String> images;
    private ArrayList<Boolean> checked;
    private Boolean isTurn;
    private String msg;

    public CharacterDto(){
        users=new ArrayList<>();

    }
}

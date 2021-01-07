package web.game.avalon.game;

import com.sun.istack.NotNull;
import web.game.avalon.dto.UserDto;
import web.game.avalon.game.character.CharacterFactory;
import web.game.avalon.game.character.GameCharacter;
import web.game.avalon.game.state.StateEnum;
import web.game.avalon.table.User;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Hashtable;
import java.util.List;

public class Game {
    private StateEnum stateEnum;
    private Hashtable<String,Player> table;
    private int rule;

    public Game(){
        stateEnum=StateEnum.Init;
        table=new Hashtable<>();
    }

    public StateEnum getStateEnum(){
        return stateEnum;
    }

    public void setStateEnum(StateEnum stateEnum){
        this.stateEnum=stateEnum;
    }

    public void makeRole(ArrayList<UserDto> users,int rule){
        Collections.shuffle(users);
        this.rule=rule;
        ArrayList<GameCharacter> characters=CharacterFactory.getCharacters(this.rule);
        for(int i=0;i<users.size();i++){
            Player player=new Player(i+1,users.get(i).getUserId());
            player.setGameCharacter(characters.get(i));
            table.put(users.get(i).getUserId(),player);
        }

    }


}

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
    private ArrayList<Player> playerList;
    //private ArrayList<String> image;
    private ArrayList<String> userStrings;
    private ArrayList<Boolean> checked;
    private int turn;
    private int rule;

    public Game(){
        stateEnum=StateEnum.Init;
        table=new Hashtable<>();
        checked=new ArrayList<>();
        userStrings=new ArrayList<>();
        //playerList=new ArrayList<>();
    }
    public ArrayList<String> getUserStrings(){return userStrings;}
    //public ArrayList<String> getImage(){return image;}

    public StateEnum getStateEnum(){
        return stateEnum;
    }

    public void setStateEnum(StateEnum stateEnum){
        this.stateEnum=stateEnum;
    }
    public ArrayList<Boolean> getChecked(){return checked;}
    public void changeCheck(int num){
        checked.set(num,!checked.get(num));
    }

    // 게임 룰에 맞춰 캐릭터를 정하고 사용자에게 역할을 랜덤하게 부여하고 이미지도 준다
    public ArrayList<Player> makeRole(ArrayList<UserDto> users,int rule){
        turn=1;
        Collections.shuffle(users);
        this.rule=rule;
        ArrayList<GameCharacter> characters=CharacterFactory.getCharacters(this.rule);
        for(int i=0;i<users.size();i++){
            Player player=new Player(i+1,users.get(i).getUserId());
            player.setGameCharacter(characters.get(i));
            table.put(users.get(i).getUserId(),player);
            checked.add(false);
        }

        ArrayList<Player> players=new ArrayList<>();
        for(UserDto userDto:users){
            players.add(table.get(userDto.getUserId()));
            userStrings.add(userDto.getUserId());
        }

        for(int i=0;i<users.size();i++){
            ArrayList<String> images=new ArrayList<>();
            for(int j=0;j<users.size();j++){
                if(players.get(i).getGameCharacter().getName().equals("일반선")){
                    if(i==j){
                        images.add("normalgood1");
                    }
                    else{
                        images.add("que");
                    }
                }
                else{
                    images.add(
                            CharacterFactory.getPlayerInImage(players.get(i).getGameCharacter()
                                    ,players.get(j).getGameCharacter()));
                }
            }
            players.get(i).setImages(images);
            //this.image=images;
        }
        playerList=players;
        return playerList;
    }

    public int getNowTurn(){
        return turn;
    }

    public ArrayList<Player> getPlayerList(){
        return playerList;
    }

}

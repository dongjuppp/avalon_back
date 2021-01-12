package web.game.avalon.game;

import web.game.avalon.dto.UserDto;
import web.game.avalon.game.character.CharacterFactory;
import web.game.avalon.game.character.GameCharacter;
import web.game.avalon.game.state.StateEnum;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Hashtable;

public class Game {
    private StateEnum stateEnum;
    private ArrayList<Player> playerList;
    private Hashtable<String,Player> playerTable;
    private int turn;
    private Round round;
    private int rule;


    public Game(){
        stateEnum=StateEnum.Init;
        playerList=new ArrayList<>();
        round=new Round();
        playerTable=new Hashtable<>();

    }

    //
    public void voteExpedition(String userId,boolean isWin){
        round.getPlayerById(userId).setProAndCons(isWin);
    }

    public void initRoundUser(){round.initTable();}



    public boolean isMemberFull(){return round.isMemberFull();}

    public ArrayList<String> getUserListString(){
        ArrayList<String> tmp=new ArrayList<>();
        playerList.forEach(player -> tmp.add(player.getUserId()));
        return tmp;
    }

    public void setPlayerProsAndCons(String player,boolean b){
        playerTable.get(player).setProAndCons(b);
    }

    public StateEnum getStateEnum(){
        return stateEnum;
    }

    public void setStateEnum(StateEnum stateEnum){
        this.stateEnum=stateEnum;
    }

    public boolean isExpeditionMax(){
        return round.isMemberFull();
    }

    public boolean isDuplicationMember(Player player){
        return round.isDuplicationMember(player);
    }

    public boolean isExpeditionMember(String userId){
        return round.isMember(playerTable.get(userId));
    }

    public boolean isWinRound(){
        int count=0;
        for(Player player:playerList){
            if(round.getNowRound()!=4){
                if(round.isMember(player)){
                    if(!player.getProAndCons()) return false;
                }
            }
            else{
                if(round.isMember(player)){
                    if(!player.getProAndCons()) count++;
                }
            }
        }
        return count<2;
    }

    public int getNowRound(){
        return round.getNowRound();
    }

    public void addRoundUser(Player player){
        round.addUser(player);
    }
    public void deleteRoundUser(Player player){
        round.deleteUser(player);
    }

    public Boolean isUserChecked(int num){
        return playerList.get(num).getChecked();
    }

    public ArrayList<Boolean> getCheckedList(){
        ArrayList<Boolean> tmp=new ArrayList<>();
        playerList.forEach((player -> tmp.add(player.getChecked())));
        return tmp;
    }

    public void changeCheck(int num){
        playerList.get(num).setChecked(!playerList.get(num).getChecked());
    }

    public void initCheck(){
        playerList.forEach(player -> player.setChecked(false));
    }

    // 게임 룰에 맞춰 캐릭터를 정하고 사용자에게 역할을 랜덤하게 부여하고 이미지도 준다
    public ArrayList<Player> makeRole(ArrayList<UserDto> users,int rule){
        turn=0;
        Collections.shuffle(users);
        this.rule=rule;
        round.setRule(rule);
        // 역할 부여
        ArrayList<GameCharacter> characters=CharacterFactory.getCharacters(this.rule);
        for(int i=0;i<users.size();i++){
            Player player=new Player(i+1,users.get(i).getUserId());
            player.setGameCharacter(characters.get(i));
            playerList.add(player);
            playerTable.put(player.getUserId(),player);
        }

        //역할에 따른 이미지 부여
        CharacterFactory.setPersonalImages(users,playerList);
        return playerList;
    }

    public ArrayList<Player> getExpeditionMember(){
        ArrayList<Player> tmp=new ArrayList<>();
        playerList.forEach(player -> {if(round.isMember(player)) tmp.add(player);});
        return tmp;
    }

    public void setNextTurn(){
        int size=playerList.size();
        if(turn+1==size) turn=0;
        else turn++;
    }

    public int getNowTurn(){
        return turn;
    }

    public ArrayList<Player> getPlayerList(){
        return playerList;
    }

    public String getNowTurnId(){
        return playerList.get(turn).getUserId();
    }

}

package web.game.avalon.game;

import web.game.avalon.dto.KillDto;
import web.game.avalon.dto.MessageDto;
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
    private Hashtable<String, Player> playerTable;
    private int turn;
    private Round round;
    private int rule;


    public Game() {
        stateEnum = StateEnum.Init;
        playerList = new ArrayList<>();
        round = new Round();
        playerTable = new Hashtable<>();
    }

    public KillDto killMerlin(MessageDto messageDto){
        KillDto killDto=new KillDto();
        boolean result=false;
        String name = "";
        String job = "";
        for (Player player : getPlayerList()) {
            if (player.getUserId().equals(messageDto.getChoiceId())) {
                if (player.getGameCharacter().getName().equals("멀린")) {
                    result = true;
                }
                killDto.setName(player.getUserId());
                killDto.setJob(player.getGameCharacter().getName());
            }
        }


        return killDto;
    }

    public String choice(MessageDto messageDto){
        String msg = String.format("%s가 선택되었습니다", messageDto.getChoiceId());
        for(Player player:playerList){
            if(messageDto.getChoiceId().equals(player.getUserId())){
                if(!isDuplicationMember(player)){
                    if(isExpeditionMax()){
                        msg = "원정대가 인원수를 초과 하였습니다";
                    }
                    else{
                        changeCheck(playerList.indexOf(player));
                        addRoundUser(player);
                        if (isExpeditionMax()) {
                            msg += "\n원정대를 모두 선정하였습니다";
                        }
                    }
                }
                else{
                    changeCheck(playerList.indexOf(player));
                    deleteRoundUser(player);
                    msg = String.format("%s가 제외되었습니다", messageDto.getChoiceId());
                }
                break;
            }
        }
        return msg;
    }

    public MessageDto checkEndGame(StateEnum stateEnum, MessageDto messageDto){
        if(stateEnum!=null){
            MessageDto message=new MessageDto();
            if(stateEnum==StateEnum.GoodWin){
                message.setMsg("선의 승리입니다.<br/> 암살자는 멀린을 찾으세요!<br/>");
                message.setUserId(getAssassinId());
            }
            else if(stateEnum==StateEnum.EvilWin){
                message.setMsg("악의 승리입니다<br/>게임이 종료되었습니다<br/>");
            }
            else return null;
            return message;
        }
        return null;
    }

    public String getAssassinId() {
        for (Player player : playerList) {
            if (player.getGameCharacter().getName().equals("암살자")) {
                return player.getUserId();
            }
        }
        return "";
    }

    //
    public void voteExpedition(String userId, boolean isWin) {
        round.getPlayerById(userId).setProAndCons(isWin);
    }

    public void initRoundUser() {
        round.initTable();
    }

    public ArrayList<Integer> getMainRound() {
        return round.getMainRound();
    }

    public ArrayList<Integer> getSubRound() {
        return round.getSubRound();
    }

    public boolean isMemberFull() {
        return round.isMemberFull();
    }

    public ArrayList<String> getUserListString() {
        ArrayList<String> tmp = new ArrayList<>();
        playerList.forEach(player -> tmp.add(player.getUserId()));
        return tmp;
    }

    public StateEnum checkEndGame() {
        return round.checkEndGame();
    }

    public void changeSubRound() {
        round.changeSubRound();
    }

    public void initSubRound() {
        round.initSubRound();
    }

    public void changeMainRound(boolean isWin) {
        round.changeMainRound(isWin);
    }

    public void setPlayerProsAndCons(String player, boolean b) {
        playerTable.get(player).setProAndCons(b);
    }

    public StateEnum getStateEnum() {
        return stateEnum;
    }

    public void setStateEnum(StateEnum stateEnum) {
        this.stateEnum = stateEnum;
    }

    public boolean isExpeditionMax() {
        return round.isMemberFull();
    }

    public boolean isDuplicationMember(Player player) {
        return round.isDuplicationMember(player);
    }

    public boolean isExpeditionMember(String userId) {
        return round.isMember(playerTable.get(userId));
    }

    public boolean isWinRound() {
        int count = 0;
        for (Player player : playerList) {
            if (round.getNowRound() != 3) {
                if (round.isMember(player)) {
                    if (!player.getProAndCons()) return false;
                }
            } else {
                if (round.isMember(player)) {
                    if (!player.getProAndCons()) count++;
                }
            }
        }
        return count < 2;
    }

    public String getExpeditionVote() {
        int win = 0;
        int lose = 0;
        for (Player player : playerList) {
            if (round.isMember(player)) {
                if (player.getProAndCons()) win++;
                else lose++;
            }
        }
        return String.format("성공:%d 실패:%d<br/>", win, lose);
    }

    public int getNowRound() {
        return round.getNowRound();
    }

    public void addRoundUser(Player player) {
        round.addUser(player);
    }

    public void deleteRoundUser(Player player) {
        round.deleteUser(player);
    }

    public Boolean isUserChecked(int num) {
        return playerList.get(num).getChecked();
    }

    public ArrayList<Boolean> getCheckedList() {
        ArrayList<Boolean> tmp = new ArrayList<>();
        playerList.forEach((player -> tmp.add(player.getChecked())));
        return tmp;
    }

    public void changeCheck(int num) {
        playerList.get(num).setChecked(!playerList.get(num).getChecked());
    }

    public void initCheck() {
        playerList.forEach(player -> player.setChecked(false));
    }

    // 게임 룰에 맞춰 캐릭터를 정하고 사용자에게 역할을 랜덤하게 부여하고 이미지도 준다
    public ArrayList<Player> makeRole(ArrayList<UserDto> users, int rule) {
        turn = 0;
        Collections.shuffle(users);
        this.rule = rule;
        round.setRule(rule);
        // 역할 부여
        ArrayList<GameCharacter> characters = CharacterFactory.getCharacters(this.rule);
        for (int i = 0; i < users.size(); i++) {
            Player player = new Player(i + 1, users.get(i).getUserId());
            player.setGameCharacter(characters.get(i));
            playerList.add(player);
            playerTable.put(player.getUserId(), player);
        }

        //역할에 따른 이미지 부여
        CharacterFactory.setPersonalImages(users, playerList);
        return playerList;
    }

    public ArrayList<Player> getExpeditionMember() {
        ArrayList<Player> tmp = new ArrayList<>();
        playerList.forEach(player -> {
            if (round.isMember(player)) tmp.add(player);
        });
        return tmp;
    }

    public void setNextTurn() {
        int size = playerList.size();
        if (turn + 1 == size) turn = 0;
        else turn++;
    }

    public int getNowTurn() {
        return turn;
    }

    public ArrayList<Player> getPlayerList() {
        return playerList;
    }

    public String getNowTurnId() {
        return playerList.get(turn).getUserId();
    }

}

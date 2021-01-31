package web.game.avalon.game.state;

import lombok.Getter;

@Getter
public enum StateEnum {

    Init(1,"게임시작전"),
    Choice(2,"원정대선택중"),
    ChoiceComplete(3,"원정대선택완료"),
    VoteOne(4,"원정대 보내기 찬성반대 투표"),
    Expedition(5,"원정대 출발"),
    ExpeditionVoteAll(6,"원정대 투표 완료"),
    GoodWin(7,"선 승리"),
    EvilWin(8,"악승리"),
    AssassinChoice(9,"암살자 선택중"),
    GameEnd(10,"게임 종료");

    private int stateNum;
    private String stateStr;

    StateEnum(int stateNum,String stateStr){
        this.stateNum = stateNum;
        this.stateStr = stateStr;
    }
}
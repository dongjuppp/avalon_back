package web.game.avalon.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.messaging.handler.annotation.MessageExceptionHandler;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import web.game.avalon.dto.CharacterDto;
import web.game.avalon.dto.MessageDto;
import web.game.avalon.dto.UserDto;
import web.game.avalon.game.Game;
import web.game.avalon.game.Player;
import web.game.avalon.game.Room;
import web.game.avalon.game.RoomManager;
import web.game.avalon.game.state.StateEnum;

import java.util.ArrayList;

@RestController
public class SocketController {

    private Logger logger=LoggerFactory.getLogger(SocketController.class);
    private SimpMessagingTemplate template;
    private RoomManager manager;

    public SocketController(SimpMessagingTemplate template,RoomManager manager){
        this.template=template;
        this.manager=manager;
    }

    @MessageMapping("/chatting")
    public void test(MessageDto messageDto){
        //Room room=manager.getRoomById(messageDto.getRoomId());
        String msg=String.format("%s:%s",messageDto.getUserId(),messageDto.getMsg());
        //System.out.println(messageDto.getRoomId());
        template.convertAndSend("/topic/chatting/"+messageDto.getRoomId(),msg);
    }

    @MessageMapping("/whoEntry")
    public void whoEnry(MessageDto messageDto){

        UserDto userDto=new UserDto();
        userDto.setUserId(messageDto.getUserId());
        Room room=manager.getRoomById(messageDto.getRoomId());
        room.addUser(userDto);

        String msg=String.format("%s 입장",messageDto.getUserId());
        logger.info(String.format("%s가 %s방에 입장",messageDto.getUserId(),messageDto.getRoomId()));
        messageDto.setMsg(msg);
        messageDto.setImage("que");
        messageDto.setUsers(room.getUserList());

        template.convertAndSend("/topic/whoEntry"+messageDto.getRoomId(),messageDto);
    }

    @MessageMapping("/exit")
    public void exitRoom(MessageDto messageDto){
        if(manager.deleteRoom(messageDto.getRoomId())){
            messageDto.setMsg("success");
            template.convertAndSend("/topic/exit"+messageDto.getRoomId(),messageDto);
        }
        else{
            template.convertAndSend("/topic/exitFail/"+messageDto.getRoomId()+"/"+messageDto.getUserId(),
                    "방을 삭제하는데 실패 했습니다");
        }
    }

    @MessageMapping("/choice")
    public void choice(MessageDto messageDto){
        Room room=manager.getRoomById(messageDto.getRoomId());
        Game game=room.getGame();

        ArrayList<String> users=game.getUserListString();
        ArrayList<Player> players=game.getPlayerList();
        String msg=String.format("%s가 선택되었습니다",messageDto.getChoiceId());
        for(int i=0;i<users.size();i++){
            if(messageDto.getChoiceId().equals(users.get(i))){
                //중복 X
                if(!game.isDuplicationMember(players.get(i))){ //Round 를 통해 확인
                    //Full
                    if(game.isExpeditionMax()){
                        msg="원정대가 인원수를 초과 하였습니다";
                    }
                    else{ //추가
                        game.changeCheck(i);

                        game.addRoundUser(players.get(i));
                        if(game.isExpeditionMax()){
                            msg+="\n원정대를 모두 선정하였습니다";
                        }
                    }
                }//중복 O
                else{
                    game.changeCheck(i);
                    game.deleteRoundUser(players.get(i));

                    msg=String.format("%s가 제외되었습니다",messageDto.getChoiceId());
                }
                break;
            }
        }

        for(Player player:players){
            CharacterDto characterDto=new CharacterDto();
            characterDto.setMsg(msg);
            characterDto.setImages(player.getImages());
            characterDto.setUsers(users);
            characterDto.setChecked(game.getCheckedList());
            template.convertAndSend("/topic/choice/"+messageDto.getRoomId()
                    +"/"+player.getUserId(),characterDto);
            //characterDto.setNowTurnId();
        }
    }

    @MessageMapping("/expeditionEnd")
    public void expeditionEnd(MessageDto messageDto){
        Game game=manager.getRoomById(messageDto.getRoomId()).getGame();
        if(game.getStateEnum()!=StateEnum.Expedition)return;
        if(!game.getPlayerList().get(game.getNowTurn()).getUserId().equals(messageDto.getUserId()))return;
        String msg="";
        if(game.isWinRound()){
            msg=String.format("%d 라운드는 선의 승리입니다<br/>",game.getNowRound());
        }
        else{
            msg=String.format("%d 라운드는 선의 패배입니다<br/>",game.getNowRound());
        }
        game.setNextTurn();
        game.initCheck();
        game.initRoundUser();
        game.setStateEnum(StateEnum.Choice);
        msg+=String.format("<br/>다음 왕관은 %s 입니다",game.getPlayerList().get(game.getNowTurn()).getUserId());
        template.convertAndSend("/topic/expeditionEnd/"+messageDto.getRoomId(),msg);
        sendInitImage(messageDto);
    }

    @MessageMapping("/expeditionWin")
    public void expeditionWin(MessageDto messageDto){
        Game game=manager.getRoomById(messageDto.getRoomId()).getGame();
        if(game.getStateEnum()!=StateEnum.Expedition)return;
        if(!game.isExpeditionMember(messageDto.getUserId())) return;
        game.voteExpedition(messageDto.getUserId(),true);
        template.convertAndSend("/topic/voteWinLose/"+messageDto.getRoomId(),
                String.format("%s가 성공/실패 투표를 하였습니다",messageDto.getUserId()));
    }

    @MessageMapping("/expeditionLose")
    public void expeditionLose(MessageDto messageDto){
        Game game=manager.getRoomById(messageDto.getRoomId()).getGame();
        if(!game.isExpeditionMember(messageDto.getUserId())) return;
        if(game.getStateEnum()!=StateEnum.Expedition)return;
        game.voteExpedition(messageDto.getUserId(),false);
        template.convertAndSend("/topic/voteWinLose/"+messageDto.getRoomId(),
                String.format("%s가 성공/실패 투표를 하였습니다",messageDto.getUserId()));
    }

    @MessageMapping("/expeditionMemberFull")
    public void expeditionMemberFull(MessageDto messageDto){
        Game game=getGame(messageDto);
        if(game==null) return;
        if(game.getStateEnum()!=StateEnum.Choice) return;
        if(!game.isMemberFull()) return;
        game.setStateEnum(StateEnum.ChoiceComplete);


        template.convertAndSend("/topic/expeditionMemberFull/"+messageDto.getRoomId(),
                "원정대 멤버선정이 완료 되었습니다 찬성/반대 투표를 하세요");
    }

    @MessageMapping("/prosAndConsEnd")
    public void prosAndConsEnd(MessageDto messageDto){
        Game game=getGame(messageDto);
        if(game==null) return;
        if(game.getStateEnum()!=StateEnum.ChoiceComplete) return;
        String msg="";
        int count=0;
        for(Player player:game.getPlayerList()){
            if(player.getProAndCons()){
                msg+=String.format("%s: 찬성<br/>",player.getUserId());
                count++;
            }
            else msg+=String.format("%s: 반대<br/>",player.getUserId());
        }

        game.initCheck(); //원정대 선택되었음을 초기화?

        if(count>game.getPlayerList().size()/2){
            msg+="<br/>결과: 원정대 출발<br/>원정대는 성공/실패 투표를하세요<br/>";
            game.setStateEnum(StateEnum.Expedition);
            messageDto.setResult(1);
        }
        else{
            game.setNextTurn();
            msg+="<br/>결과: 원정대 출발 불가<br/>";
            messageDto.setRule(0);
            game.setStateEnum(StateEnum.Choice);
            sendInitImage(messageDto);
            game.initRoundUser();
            msg+=String.format("<br/>다음 왕관은 %s 입니다",game.getPlayerList().get(game.getNowTurn()).getUserId());
        }

        messageDto.setMsg(msg);
        template.convertAndSend("/topic/prosAndConsResult/"+messageDto.getRoomId(),
                messageDto);
    }

    @MessageMapping("/expeditionAgree")
    public void expeditionAgree(MessageDto messageDto){
        Room room=manager.getRoomById(messageDto.getRoomId());
        Game game=room.getGame();
        String msg=String.format("%s가 찬성/반대 투표를 하였습니다",messageDto.getUserId());
        logger.info(msg);
        game.setPlayerProsAndCons(messageDto.getUserId(),true);
        template.convertAndSend("/topic/expeditionMsg/"+messageDto.getRoomId(),
                msg);
    }

    @MessageMapping("/expeditionDisAgree")
    public void expeditionDisAgree(MessageDto messageDto){
        Room room=manager.getRoomById(messageDto.getRoomId());
        Game game=room.getGame();
        String msg=String.format("%s가 찬성/반대 투표를 하였습니다",messageDto.getUserId());
        game.setPlayerProsAndCons(messageDto.getUserId(),false);
        logger.info(msg);
        template.convertAndSend("/topic/expeditionMsg/"+messageDto.getRoomId(),
                msg);
    }

    @MessageMapping("/start")
    public void start(MessageDto messageDto){
        Room room=manager.getRoomById(messageDto.getRoomId());
        Game game=room.getGame();
        if(game.getStateEnum()!=StateEnum.Init) return;
        game.setStateEnum(StateEnum.Choice);
        ArrayList<Player> playerList=game.makeRole(room.getUserList(),room.getRule());

        //System.out.println("플에이어 사이즈"+playerList.size());
        String msg="턴순서는: ";
        ArrayList<String> userIds=new ArrayList<>();
        for(Player player:playerList){
            msg+=player.getUserId()+"->";
            userIds.add(player.getUserId());
        }

        String nowTurnId=playerList.get(0).getUserId();
        for(Player player:playerList){
            CharacterDto characterDto=new CharacterDto();
            characterDto.setMsg(msg);
            characterDto.setUsers(userIds);
            characterDto.setNowTurnId(nowTurnId);
            characterDto.setImages(player.getImages());
            String userId=player.getUserId();
            template.convertAndSend("/topic/start"+messageDto.getRoomId()+"/"+userId
                    ,characterDto);
        }

    }

    public void sendInitImage(MessageDto messageDto){
        Room room=manager.getRoomById(messageDto.getRoomId());
        Game game=room.getGame();
        //ArrayList<Player> players=game.getPlayerList();
        String nowTurnId=game.getNowTurnId();


        for(Player player:game.getPlayerList()){
            CharacterDto characterDto=new CharacterDto();
            characterDto.setNowTurnId(nowTurnId);
            characterDto.setImages(player.getImages());
            characterDto.setUsers(game.getUserListString());
            template.convertAndSend("/topic/initImage/"+messageDto.getRoomId()+"/"+player.getUserId(),
                    characterDto);
        }

    }

    private Game getGame(MessageDto messageDto){
        Room room=manager.getRoomById(messageDto.getRoomId());
        Game game=room.getGame();
        if(!game.getPlayerList().get(game.getNowTurn()).getUserId()
                .equals(messageDto.getUserId())) return null;
        return game;
    }


}

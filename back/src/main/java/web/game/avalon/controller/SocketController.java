package web.game.avalon.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
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
        logger.debug(String.format("%s가 %s방에 입장",messageDto.getUserId(),messageDto.getRoomId()));
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
        String userId=messageDto.getUserId();
        Room room=manager.getRoomById(messageDto.getRoomId());
        Game game=room.getGame();
        int turn=game.getNowTurn();
        ArrayList<String> users=game.getUserStrings();
        ArrayList<Player> players=game.getPlayerList();
        String msg=String.format("%s가 선택되었습니다",messageDto.getChoiceId());
        for(int i=0;i<users.size();i++){
            if(messageDto.getChoiceId().equals(users.get(i))){
                game.changeCheck(i);
                if(!game.getChecked().get(i)){
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
            characterDto.setChecked(game.getChecked());
            template.convertAndSend("/topic/choice/"+messageDto.getRoomId()
                    +"/"+player.getUserId(),characterDto);
            //characterDto.setNowTurnId();
        }
    }

    @MessageMapping("/start")
    public void start(MessageDto messageDto){
        Room room=manager.getRoomById(messageDto.getRoomId());
        Game game=room.getGame();
        if(game.getStateEnum()== StateEnum.Init){
            game.setStateEnum(StateEnum.Choice);
        }
        else{
            return;
        }
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


}

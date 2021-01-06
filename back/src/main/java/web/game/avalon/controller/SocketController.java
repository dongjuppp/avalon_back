package web.game.avalon.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import web.game.avalon.dto.MessageDto;
import web.game.avalon.dto.UserDto;
import web.game.avalon.game.Room;
import web.game.avalon.game.RoomManager;

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
}

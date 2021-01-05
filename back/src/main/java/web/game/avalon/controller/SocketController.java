package web.game.avalon.controller;

import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import web.game.avalon.dto.MessageDto;
import web.game.avalon.game.Room;
import web.game.avalon.game.RoomManager;

@RestController
public class SocketController {

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
        template.convertAndSend("/topic/chatting/"+messageDto.getRoomId(),msg);
    }
}

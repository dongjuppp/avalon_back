package web.game.avalon.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import web.game.avalon.dto.RoomDto;
import web.game.avalon.game.Room;
import web.game.avalon.game.RoomManager;

import java.util.ArrayList;

@RestController
public class RoomController {
    private RoomManager roomManager;

    @Autowired
    public RoomController(RoomManager roomManager){
        this.roomManager=roomManager;
    }

    @GetMapping("/getRoom")
    public ArrayList<RoomDto> getRoom(){

        ArrayList<RoomDto> dtos=new ArrayList<>();
        for(Room room:roomManager.getRooms()){
            dtos.add(new RoomDto(room.getRoomName(),room.getRule(),room.getRoomId()));
        }
        return dtos;
    }

    @PostMapping("/makeRoom")
    public String makeRoom(@RequestBody Room room){
        room.makeGame();
        if(roomManager.insertRoom(room)){
            return room.getRoomId();
        }
        return "fail";
    }

    @GetMapping("/deleteRoom")
    public String deleteRoom(@PathVariable String roomId){
        System.out.println(roomId);
        if(roomManager.deleteRoom(roomId)) return "success";
        return "fail";
    }
}

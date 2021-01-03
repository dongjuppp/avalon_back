package web.game.avalon.game;

import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.Hashtable;
import java.util.List;


/*
* 방 전체 전달
* 방 추가
* 방 삭제
* 방 1개 가져오기
* */
@Component
public class RoomManager {
    private Hashtable<String,Room> rooms;

    public RoomManager(){
        rooms=new Hashtable<>();
    }

    public boolean insertRoom(Room room){
        room.makeGame();
        if(rooms.containsKey(room.getRoomName())) return false;
        rooms.put(room.getRoomId(),room);
        return true;
    }

    public ArrayList<Room> getRooms(){
        return new ArrayList<>(rooms.values());
    }

    public boolean deleteRoom(String roomId){
        Room room= rooms.get(roomId);

        return rooms.remove(roomId,room);
    }


}

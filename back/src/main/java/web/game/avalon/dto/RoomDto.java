package web.game.avalon.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class RoomDto {
    private String roomName;
    private int rule;
    private String roomId;

    public RoomDto(String roomName,int rule,String roomId){
        this.roomId=roomId;
        this.roomName=roomName;
        this.rule=rule;
    }
}

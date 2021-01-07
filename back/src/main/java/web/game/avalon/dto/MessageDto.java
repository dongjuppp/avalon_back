package web.game.avalon.dto;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.util.ArrayList;

@Getter
@Setter
@ToString
public class MessageDto {
    private String roomId;
    private String userId;
    private String image;
    private String msg;
    private int rule;
    private ArrayList<UserDto> users;
}

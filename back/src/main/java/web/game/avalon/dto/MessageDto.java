package web.game.avalon.dto;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
public class MessageDto {
    private String roomId;
    private String userId;

    private String msg;
}

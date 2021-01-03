package web.game.avalon.game;

import lombok.Getter;
import lombok.Setter;
import web.game.avalon.game.character.GameCharacter;

@Setter
@Getter
public class Player {
    private String id;
    private GameCharacter gameCharacter;
}

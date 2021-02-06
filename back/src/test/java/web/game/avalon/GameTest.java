package web.game.avalon;

import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import web.game.avalon.dto.KillDto;
import web.game.avalon.dto.MessageDto;
import web.game.avalon.game.Game;
import web.game.avalon.game.Player;
import web.game.avalon.game.Room;
import web.game.avalon.game.RoomManager;
import web.game.avalon.game.character.Merlin;

import java.util.ArrayList;
import java.util.Arrays;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.AssertionsForClassTypes.tuple;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.when;

//@SpringBootTest
public class GameTest {

    @Test
    void killMerlinTest(){
        Game game=new Game();
        for(int i=0;i<5;i++){
            Player player=new Player();
            player.setUserId(Integer.toString(i));
            game.addUser(player);
        }

        game.getPlayerList().get(2).setGameCharacter(new Merlin());

        MessageDto messageDto=new MessageDto();
        messageDto.setChoiceId("2");
        assertThat(game.killMerlin(messageDto))
                .extracting(KillDto::getName,KillDto::isResult,KillDto::getJob)
                .contains("2")
                .contains("멀린")
                .contains(true);

        MessageDto fail=new MessageDto();
        fail.setChoiceId("33");
        assertThat(game.killMerlin(fail))
                .extracting(KillDto::isResult)
                .isEqualTo(false);
    }

    @Test
    void choiceTest(){
        Game game=new Game();
        //game.initRoundUser();
        for(int i=0;i<5;i++){
            Player player=new Player();
            player.setUserId(Integer.toString(i));
            player.setChecked(true);
            game.addUser(player);
            game.addRoundUser(player);
        }

        MessageDto msg=new MessageDto();
        msg.setChoiceId("2");
        assertThat(game.choice(msg))
                .isEqualTo("2가 제외되었습니다");
    }


}

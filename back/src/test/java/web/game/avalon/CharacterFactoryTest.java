package web.game.avalon;

import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.springframework.boot.test.context.SpringBootTest;
import web.game.avalon.game.character.*;

import java.util.ArrayList;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.BDDMockito.given;

@SpringBootTest
public class CharacterFactoryTest {

    @Mock
    GameCharacter gc1;

    @Mock
    GameCharacter gc2;

    @Test
    void testGetCharacters(){
        // (선)멀린+퍼시벌+신하4 (악)암살자+모르가나+모드레드+오베론
        ArrayList<GameCharacter> tmp=CharacterFactory.getCharacters(12);
        ArrayList<String> names=new ArrayList<>();
        names.add("멀린");
        names.add("파시발");
        names.add("암살자");
        names.add("모르가나");
        names.add("모드레드");
        names.add("오베론");

        assertThat(tmp).hasSize(10);

        assertThat(tmp).filteredOn(character->names.contains(character.getName()));
    }

    @Test
    void getPlayerImage(){
        given(gc1.getName()).willReturn("멀린");
        given(gc2.getName()).willReturn("오베론");

        assertThat(CharacterFactory.getPlayerInImage(gc1,gc2)).isEqualTo("normalevil2");
    }
}

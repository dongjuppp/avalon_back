package web.game.avalon;

import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.springframework.boot.test.context.SpringBootTest;
import web.game.avalon.dto.UserDto;
import web.game.avalon.game.Room;
import web.game.avalon.game.RoomManager;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.BDDMockito.given;

@SpringBootTest
class AvalonApplicationTests {

    @Mock
    RoomManager roomManager;

    @Test
    void testMakeGame() {
        Room testRoom=new Room();
        testRoom.makeGame();
        given(roomManager.getRoomById("test")).willReturn(testRoom);

        assertThat(roomManager.getRoomById("test").getRoomId()).isNotNull();
    }

    @Test
    void testEnterRoom(){
        Room testRoom =new Room();
        testRoom.makeGame();
        testRoom.setRule(1);

        testRoom.addUser(new UserDto());
        testRoom.addUser(new UserDto());
        testRoom.addUser(new UserDto());
        testRoom.addUser(new UserDto());

        assertThat(testRoom.isEnter()).isEqualTo("success");

        testRoom.addUser(new UserDto());
        assertThat(testRoom.isEnter()).isEqualTo("max");
    }

}

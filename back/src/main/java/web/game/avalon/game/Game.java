package web.game.avalon.game;

import com.sun.istack.NotNull;
import web.game.avalon.table.User;

import java.util.ArrayList;
import java.util.List;

public class Game {
    private List<Player> playerList;


    private void setPlayerList(@NotNull List<User> users){
        playerList=new ArrayList<>();
        for(User user:users){
            Player player=new Player();
            player.setId(user.getId());
        }
    }
}

package web.game.avalon.game;
import java.security.SecureRandom;
import java.util.ArrayList;


import lombok.Getter;
import lombok.Setter;
import web.game.avalon.dto.UserDto;
import web.game.avalon.utils.SirialNumber;

@Getter
@Setter
public class Room {
    private String roomName;
    private int rule;
    //private int NumberOfPeople;
    private Game game;
    private String roomId;
    private ArrayList<UserDto> userList;
    private String roomMaker;


    private void makeId(){
        roomId= SirialNumber.getSirialNumber();
    }

    public void makeGame(){
        makeId();
        userList=new ArrayList<>();
        this.game=new Game();
    }

    public String getRoomId(){
        return roomId;
    }

    public void addUser(UserDto userDto){
        userList.add(userDto);
    }

    public ArrayList<UserDto> getUserList(){
        return userList;
    }


    //입장 가능한지
    public String isEnter(){
        int size=0;
        if(rule==1){
            size=5;
        }
        else if(rule==2 || rule==3){
            size=6;
        }
        else if(rule==4 || rule==5) size=7;
        else if(rule==6 || rule==7) size=8;
        else if(rule==8 || rule==9) size=9;
        else size=10;

        //System.out.println(size+" "+userList.size());
        if(userList.size()<size) return "success";
        else return "max";
        //return "max";
    }

    public boolean isDuplicationMember(String userId){
        for(UserDto userDto:userList){
            if(userDto.getUserId().equals(userId)){
                return false;
            }
        }
        return true;
    }

}

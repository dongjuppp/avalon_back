package web.game.avalon.game.character;

import web.game.avalon.game.Player;

import java.util.ArrayList;
import java.util.Collections;

public class CharacterFactory {

    public static ArrayList<GameCharacter> getCharacters(int rule){
        ArrayList<GameCharacter> list = new ArrayList<>();

        list.add(new Merlin());
        list.add(new Assassin());
        list.add(new NormalGood());
        list.add(new NormalGood());


        if(rule==1){
            list.add(new NormalEvil());
        }
        else if(rule==2){
            list.add(new NormalGood());
            list.add(new NormalEvil());
        }
        else if(rule==3){
            list.add(new Parsibal());
            list.add(new Morgana());
        }
        else if(rule==4){
            list.add(new Parsibal());
            list.add(new NormalEvil());
            list.add(new NormalEvil());
        }
        else if(rule==5){
            list.add(new Parsibal());
            list.add(new Morgana());
            list.add(new Oberon());
        }
        else if(rule==6){
            list.add(new Parsibal());
            list.add(new NormalGood());
            list.add(new NormalEvil());
            list.add(new NormalEvil());
        }
        else if(rule==7){
            list.add(new Parsibal());
            list.add(new NormalGood());
            list.add(new Morgana());
            list.add(new Oberon());
        }
        else if(rule==8){
            list.add(new Parsibal());
            list.add(new NormalGood());
            list.add(new NormalGood());
            list.add(new Morgana());
            list.add(new Mordred());
        }
        else if(rule==9){
            list.add(new Parsibal());
            list.add(new NormalGood());
            list.add(new NormalGood());
            list.add(new Morgana());
            list.add(new NormalEvil());
        }
        else if(rule==10){
            list.add(new Parsibal());
            list.add(new NormalGood());
            list.add(new NormalGood());
            list.add(new NormalEvil());
            list.add(new Morgana());
            list.add(new Mordred());
        }
        else if(rule==11){
            list.add(new Parsibal());
            list.add(new NormalGood());
            list.add(new NormalGood());
            list.add(new Oberon());
            list.add(new Morgana());
            list.add(new NormalEvil());
        }
        else if(rule==12){
            list.add(new Parsibal());
            list.add(new NormalGood());
            list.add(new NormalGood());
            list.add(new Mordred());
            list.add(new Oberon());
            list.add(new Morgana());

        }
        Collections.shuffle(list);
        return list;
    }

    public static void getPlayerInImage(ArrayList<Player> list,Player player){
        ArrayList<String> images=new ArrayList<>();
        String myName=player.getGameCharacter().getName();
        if(myName.equals("멀린")){
            for(Player other:list){
                String otherName=other.getGameCharacter().getName();
                if(!otherName.equals("멀린")){
                    if(otherName.equals("일반선")||otherName.equals("파시발")){
                        
                    }
                }
            }
        }
    }
}

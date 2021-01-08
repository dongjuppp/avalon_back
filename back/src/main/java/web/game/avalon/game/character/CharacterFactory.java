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

    public static String getPlayerInImage(GameCharacter me,GameCharacter other){
        String myName=me.getName();
        String otherName=other.getName();

        if(myName.equals("멀린")){
            if(otherName.equals("멀린")){
                return "merlin";
            }
            else if(otherName.equals("암살자")||otherName.equals("일반악") || otherName.equals("오베론")){
                return "normalevil2";
            }
        }
        else if(myName.equals("파시발")){
            if(otherName.equals("파시발")){
                return "par";
            }
            else if(otherName.equals("멀린")||otherName.equals("모르가나")){
                return "merlin";
            }
        }
        else if(myName.equals("암살자")){
            if(otherName.equals("암살자")){
                return "assassin";
            }
            else if(otherName.equals("일반악") || otherName.equals("모드레드") || otherName.equals("모르가나")){
                return "normalevil1";
            }
        }
        else if(myName.equals("일반악")){
            if(otherName.equals("일반악")){
                return "normalevil2";
            }
            else if(otherName.equals("암살자") || otherName.equals("모드레드") || otherName.equals("모르가나")){
                return "normalevil1";
            }
        }
        else if(myName.equals("모드레드")){
            if(otherName.equals("모드레드")){
                return "mordred";
            }
            else if(otherName.equals("일반악") || otherName.equals("모르가나") || otherName.equals("암살자")){
                return "normalevil1";
            }
        }
        else if(myName.equals("모르가나")){
            if(otherName.equals("모르가나")){
                return "mor";
            }
            else if(otherName.equals("일반악")||otherName.equals("모드레드") || otherName.equals("암살자")){
                return "normalevil2";
            }
        }
        else if(myName.equals("오베론")){
            if(otherName.equals("오베론")){
                return "oberon";
            }
        }
        return "que";
    }
}

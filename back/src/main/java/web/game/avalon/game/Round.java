package web.game.avalon.game;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Hashtable;

//1, 23, 45, 6789, 10 11 12
//5 6 7 89 10
public class Round {
    private Hashtable<String,Player> table;
    private int nowRound;
    private int rule;
    private ArrayList<Integer> max;

    Round(){
        this.table=new Hashtable<>();
        max=new ArrayList<>();
    }

    void initTable(){table=new Hashtable<>();}

    boolean isMember(Player player){
        return table.containsKey(player.getUserId());
    }

    boolean isMemberFull(){
        return table.size()==max.get(nowRound);
    }

    void setRule(int rule){
        this.rule=rule;
        Integer[] one={2,3,2,3,3};
        Integer[] two={2,3,4,3,4};
        Integer[] three={2,3,3,4,4};
        Integer[] four={3,4,4,5,5};

        switch (rule){
            case 1:
                max=new ArrayList<>(Arrays.asList(one));
                break;
            case 2:
            case 3:
                max=new ArrayList<>(Arrays.asList(two));
                break;
            case 4:
            case 5:
                max=new ArrayList<>(Arrays.asList(three));
                break;
            default:
                max=new ArrayList<>(Arrays.asList(four));

        }
    }

    boolean isDuplicationMember(Player player){
        return table.get(player.getUserId())!=null;
    }

    public void addUser(Player player){
        table.put(player.getUserId(),player);
    }

    boolean deleteUser(Player player){
        return table.remove(player.getUserId(),player);
    }


}

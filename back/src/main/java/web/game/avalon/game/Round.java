package web.game.avalon.game;

import web.game.avalon.game.state.StateEnum;

import javax.persistence.criteria.CriteriaBuilder;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Hashtable;

//1, 23, 45, 6789, 10 11 12
//5 6 7 89 10
public class Round {
    private Hashtable<String,Player> table;
    private int nowRound;
    private int nowSubRound;
    private int rule;
    private ArrayList<Integer> max;
    private ArrayList<Integer> subRound;
    private ArrayList<Integer> mainRound;

    Round(){
        this.table=new Hashtable<>();
        max=new ArrayList<>();
        Integer[] tmp={1,1,1,1,1};
        subRound=new ArrayList<>(Arrays.asList(tmp));
        Integer[] mainTmp={2,2,2,2,2};
        mainRound=new ArrayList<>(Arrays.asList(mainTmp));
        nowSubRound=0;
    }

    void changeMainRound(boolean isWin){
        if(isWin) mainRound.set(nowRound,1);
        else mainRound.set(nowRound,0);
        nowRound++;
    }

    void changeSubRound(){
        if(nowSubRound<5){
            subRound.set(nowSubRound,0);
            nowSubRound++;
        }
    }

    StateEnum checkEndGame(){
        /*
        * 서브라운드 5초과 악승리
        * 메인 라운드 3승
        * */
        if(nowSubRound>=5) return StateEnum.EvilWin;
        int good=0;
        int evil=0;
        for(int num:mainRound){
            if(num==1) good++;
            else if(num==0) evil++;
        }
        if(good>2) return StateEnum.GoodWin;
        if(evil>2) return StateEnum.EvilWin;
        return null;
    }

    void initSubRound(){
        Integer[] tmp={1,1,1,1,1};
        subRound=new ArrayList<>(Arrays.asList(tmp));
    }

    ArrayList<Integer> getMainRound(){return mainRound;}
    ArrayList<Integer> getSubRound(){return subRound;}

    void initTable(){table=new Hashtable<>();}

    boolean isMember(Player player){
        return table.containsKey(player.getUserId());
    }

    boolean isMemberFull(){
        return table.size()==max.get(nowRound);
    }

    int getNowRound(){return nowRound;}

    Player getPlayerById(String userId){
        return table.get(userId);
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

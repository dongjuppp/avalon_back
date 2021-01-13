package web.game.avalon.dto;

import lombok.Getter;
import lombok.Setter;

import java.util.ArrayList;

@Getter
@Setter
public class RoundDto {
    private ArrayList<Integer> mainRound;
    private ArrayList<Integer> subRound;
}

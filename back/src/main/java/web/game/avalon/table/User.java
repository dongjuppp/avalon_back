package web.game.avalon.table;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import javax.persistence.Entity;
import javax.persistence.Id;

@Getter
@Setter
@ToString
@Entity(name = "User")
public class User {

    @Id
    private String id;

    private String pwd;

    private String name;

}

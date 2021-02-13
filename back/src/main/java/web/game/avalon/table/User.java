package web.game.avalon.table;

import com.sun.istack.NotNull;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.validation.constraints.NotEmpty;

@Getter
@Setter
@ToString
@Entity(name = "User")
public class User {

    @Id @NotEmpty
    private String id;

    @NotEmpty
    private String pwd;

    @NotEmpty
    private String name;

}

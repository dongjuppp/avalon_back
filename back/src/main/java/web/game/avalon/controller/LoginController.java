package web.game.avalon.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import web.game.avalon.table.User;
import web.game.avalon.service.UserService;
import web.game.avalon.utils.Encryption;

import java.security.NoSuchAlgorithmException;

@RestController
public class LoginController {

    private UserService userService;

    @Autowired
    public LoginController(UserService userService){
        this.userService=userService;
    }

    @PostMapping("/login")
    public String login(@RequestBody User user) throws NoSuchAlgorithmException {

        String hash= Encryption.getSha256(user.getId(), user.getPwd());
        User tmp = userService.findUserById(user.getId());

        if(tmp==null) return "fail";

        if(tmp.getPwd().equals(hash)){
            return "success";
        }

        return "fail";
    }

    @PostMapping("/signup")
    public String signUp(@RequestBody User user) throws NoSuchAlgorithmException {
        User name = userService.findUserByName(user.getName());
        User id=userService.findUserById(user.getId());

        if(name!=null){
            return "name_duplication";
        }

        if(id!=null){
            return "id_duplication";
        }
        String hash= Encryption.getSha256(user.getId(), user.getPwd());
        user.setPwd(hash);
        User saveUser=userService.saveUser(user);
        if(saveUser!=null) return "success";
        return "fail";
    }
}

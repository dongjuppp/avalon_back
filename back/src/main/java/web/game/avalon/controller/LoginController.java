package web.game.avalon.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import web.game.avalon.service.EmailService;
import web.game.avalon.table.User;
import web.game.avalon.service.UserService;
import web.game.avalon.utils.Encryption;
import web.game.avalon.utils.ValidationCode;

import java.security.NoSuchAlgorithmException;

@RestController
public class LoginController {

    private UserService userService;
    private EmailService emailService;

    @Autowired
    public LoginController(UserService userService,EmailService emailService){
        this.userService=userService;
        this.emailService=emailService;
    }

    @PostMapping("/login")
    public String login(@RequestBody User user) throws NoSuchAlgorithmException {

        String hash= Encryption.getSha256(user.getId(), user.getPwd());
        User tmp = userService.findUserById(user.getId());

        if(tmp==null) return "fail";

        if(tmp.getPwd().equals(hash)){
            return tmp.getName();
        }

        return "fail";
    }

    @PostMapping("/signup")
    public String signUp(@RequestBody User user){
        User name = userService.findUserByName(user.getName());
        User id=userService.findUserById(user.getId());

        if(name!=null){
            return "name_duplication";
        }

        if(id!=null){
            return "id_duplication";
        }
        String validationCode = ValidationCode.excuteGenerate();

        try{
            new Thread(()->{
                emailService.sendMail(validationCode,user.getId());
            }).start();
            return "success/"+validationCode;
        }catch(Exception e){
            e.printStackTrace();
            return "emailFail";
        }
        //System.out.println(user.getId());

    }

    @PostMapping("/validation")
    public String validation(@RequestBody User user) throws NoSuchAlgorithmException{
        //System.out.println(user);
        String hash=Encryption.getSha256(user.getId(),user.getPwd());
        user.setPwd(hash);
        User saveUser=userService.saveUser(user);
        //System.out.println(saveUser);
        if(saveUser!=null) return "success";
        return "fail";
    }
}

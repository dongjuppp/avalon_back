package web.game.avalon.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import web.game.avalon.table.User;
import web.game.avalon.repository.UserRepository;

@Service
public class UserService {

    private UserRepository userRepository;

    @Autowired
    public UserService(UserRepository userRepository){
        this.userRepository=userRepository;
    }

    public User findUserById(String id){
        return userRepository.findUserById(id);
    }

    public User findUserByName(String name){
        return userRepository.findUserByName(name);
    }

    public User saveUser(User user){
        return userRepository.save(user);
    }
}

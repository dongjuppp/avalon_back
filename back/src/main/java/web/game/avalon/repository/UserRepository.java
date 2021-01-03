package web.game.avalon.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import web.game.avalon.table.User;

@Repository
public interface UserRepository extends JpaRepository<User,String> {
    User findUserById(String id);
    User findUserByName(String name);

}

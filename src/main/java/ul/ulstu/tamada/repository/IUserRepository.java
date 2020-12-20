package ul.ulstu.tamada.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import ul.ulstu.tamada.model.User;

import java.util.Optional;

public interface IUserRepository extends JpaRepository<User, String> {

    @Query("SELECT user FROM User user WHERE user.login = :login AND TYPE(user) = :userClass")
    Optional<User> findByLogin(String login, Class<? extends User> userClass);

    @Query("SELECT COUNT(user) > 0 FROM User user WHERE user.login = :login AND TYPE(user) = :userClass")
    boolean existsByLogin(String login, Class<? extends User> userClass);
}

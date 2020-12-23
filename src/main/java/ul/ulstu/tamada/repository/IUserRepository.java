package ul.ulstu.tamada.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import ul.ulstu.tamada.model.User;
import ul.ulstu.tamada.model.UserType;

import java.util.Optional;

@Repository
public interface IUserRepository extends JpaRepository<User, String> {

    @Query("SELECT user FROM User user WHERE user.login = :login AND TYPE(user) = :#{#type.getUserType()}")
    Optional<User> findByLogin(String login, UserType type);

    @Query("SELECT COUNT(user) > 0 FROM User user WHERE user.login = :login AND TYPE(user) = :userClass")
    boolean existsByLogin(String login, Class<? extends User> userClass);
}

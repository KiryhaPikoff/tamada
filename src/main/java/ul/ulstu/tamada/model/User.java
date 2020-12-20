package ul.ulstu.tamada.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import ul.ulstu.tamada.repository.IUserRepository;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Inheritance;
import javax.persistence.InheritanceType;

@Data
@Entity
@NoArgsConstructor
@AllArgsConstructor
@Inheritance(strategy = InheritanceType.TABLE_PER_CLASS)
public abstract class User {

    @Id
    private String login;

    private String password;

    public boolean isAlreadyExists(IUserRepository userRepository) {
        return userRepository.existsByLogin(this.login, this.getClass());
    }
}

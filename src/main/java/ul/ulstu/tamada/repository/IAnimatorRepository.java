package ul.ulstu.tamada.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ul.ulstu.tamada.model.Animator;

@Repository
public interface IAnimatorRepository extends JpaRepository<Animator, Long> {

}

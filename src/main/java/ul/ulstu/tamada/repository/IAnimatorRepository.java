package ul.ulstu.tamada.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ul.ulstu.tamada.model.Animator;
import ul.ulstu.tamada.model.enums.AnimatorStatus;

import java.util.List;

@Repository
public interface IAnimatorRepository extends JpaRepository<Animator, Long> {

    List<Animator> findAllByStatusIn(List<AnimatorStatus> statuses);
}

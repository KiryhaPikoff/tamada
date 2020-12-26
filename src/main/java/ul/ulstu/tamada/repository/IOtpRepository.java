package ul.ulstu.tamada.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import ul.ulstu.tamada.model.Otp;

@Repository
public interface IOtpRepository extends JpaRepository<Otp, Long> {

}

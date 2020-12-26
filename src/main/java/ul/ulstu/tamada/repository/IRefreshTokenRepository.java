package ul.ulstu.tamada.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ul.ulstu.tamada.model.RefreshToken;

import java.util.List;
import java.util.UUID;

@Repository
public interface IRefreshTokenRepository extends JpaRepository<RefreshToken, UUID> {

    List<RefreshToken> findAllBySub(String sub);
}

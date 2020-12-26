package ul.ulstu.tamada.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ul.ulstu.tamada.model.Customer;
import ul.ulstu.tamada.model.enums.CustomerStatus;

import java.util.List;

@Repository
public interface ICustomerRepository extends JpaRepository<Customer, String> {

    List<Customer> getAllByStatusIn(List<CustomerStatus> statusIn);
}

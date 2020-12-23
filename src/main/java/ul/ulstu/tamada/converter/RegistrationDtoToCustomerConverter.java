package ul.ulstu.tamada.converter;

import org.springframework.stereotype.Component;
import ul.ulstu.tamada.model.Customer;
import ul.ulstu.tamada.rest.dto.auth.RegistrationRequest;

@Component
public class RegistrationDtoToCustomerConverter extends BaseConverter<RegistrationRequest, Customer> {

    @Override
    public Customer convert(RegistrationRequest registrationRequest) {
        var customer = new Customer();

        customer.setLogin(registrationRequest.getPhone());
        customer.setPassword(registrationRequest.getPassword());
        customer.setName(registrationRequest.getName());

        return customer;
    }
}

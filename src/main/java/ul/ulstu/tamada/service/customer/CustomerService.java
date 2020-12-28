package ul.ulstu.tamada.service.customer;

import org.springframework.core.convert.ConversionService;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import ul.ulstu.tamada.exception.CustomerNotFoundException;
import ul.ulstu.tamada.model.Customer;
import ul.ulstu.tamada.model.UserType;
import ul.ulstu.tamada.repository.IUserRepository;
import ul.ulstu.tamada.rest.dto.customer.CustomerInfoDto;
import ul.ulstu.tamada.rest.dto.customer.UpdateCustomerDto;

@Service
public class CustomerService implements ICustomerService {

    private final ConversionService conversionService;
    private final IUserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    public CustomerService(
            ConversionService conversionService,
            IUserRepository userRepository,
            PasswordEncoder passwordEncoder
    ) {
        this.conversionService = conversionService;
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
    }

    @Override
    public CustomerInfoDto getCustomerInfo(String phone) {
        var customer = userRepository.findByLogin(phone, UserType.CUSTOMER)
                .orElseThrow(() -> new CustomerNotFoundException(phone));

        return conversionService.convert(customer, CustomerInfoDto.class);
    }

    @Override
    public void createOrUpdate(String phone, UpdateCustomerDto customerDto) {
        var customer = (Customer) userRepository.findByLogin(phone, UserType.CUSTOMER)
                .orElseThrow(() -> new CustomerNotFoundException(phone));

        customer.setName(customerDto.getName());
        customer.setPassword(
                passwordEncoder.encode(customerDto.getPassword())
        );

        userRepository.save(customer);
    }
}

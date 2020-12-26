package ul.ulstu.tamada.service.customer;

import ul.ulstu.tamada.rest.dto.customer.CustomerInfoDto;
import ul.ulstu.tamada.rest.dto.customer.UpdateCustomerDto;

public interface ICustomerService {

    CustomerInfoDto getCustomerInfo(String phone);

    void updateCustomer(String phone, UpdateCustomerDto customerDto);
}

package ul.ulstu.tamada.scheduler;

import lombok.extern.log4j.Log4j2;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import ul.ulstu.tamada.model.enums.CustomerStatus;
import ul.ulstu.tamada.repository.ICustomerRepository;
import ul.ulstu.tamada.repository.IOtpRepository;

import java.util.List;

@Log4j2
@Component
public class RegistrationResetScheduler {

    private final ICustomerRepository customerRepository;
    private final IOtpRepository otpRepository;

    public RegistrationResetScheduler(
            ICustomerRepository customerRepository,
            IOtpRepository otpRepository
    ) {
        this.customerRepository = customerRepository;
        this.otpRepository = otpRepository;
    }

    @Scheduled(cron = "${users.registration-reset-schedule}")
    public void handle() {
        deleteRegisterReqCustomers();
    }

    private void deleteRegisterReqCustomers() {
        customerRepository.getAllByStatusIn(List.of(CustomerStatus.REGISTER_REQUEST))
                .forEach(customerRepository::delete);
        otpRepository.findAll()
            .forEach((otp -> {
                if (otp.getAvailableAttempts() == 0) {
                    otpRepository.delete(otp);
                }
            }));
    }
}

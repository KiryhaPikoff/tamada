package ul.ulstu.tamada.rest.controller;

import io.swagger.annotations.ApiOperation;
import lombok.extern.log4j.Log4j2;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.web.bind.annotation.*;
import ul.ulstu.tamada.authorization.AllowedUserRoles;
import ul.ulstu.tamada.configuration.enums.UserRole;
import ul.ulstu.tamada.rest.dto.customer.CustomerInfoDto;
import ul.ulstu.tamada.rest.dto.customer.UpdateCustomerDto;
import ul.ulstu.tamada.service.customer.ICustomerService;

import javax.validation.Valid;

@Log4j2
@RestController
@RequestMapping("${base_uri}/users/v1")
public class UserController {

    private final ICustomerService customerService;

    public UserController(ICustomerService customerService) {
        this.customerService = customerService;
    }

    @GetMapping("/info")
    @ApiOperation("Получение детальной информации о пользователе")
    @AllowedUserRoles(roles = {UserRole.CUSTOMER})
    public ResponseEntity<CustomerInfoDto> getCustomerInfo(
            @AuthenticationPrincipal Jwt jwt
    ) {
        var customerInfo = customerService.getCustomerInfo(jwt.getSubject());
        return ResponseEntity.ok(customerInfo);
    }

    @PutMapping
    @ApiOperation("Запрос на изменение информации о пользователе")
    @AllowedUserRoles(roles = {UserRole.CUSTOMER})
    public ResponseEntity<CustomerInfoDto> updateCustomer(
            @AuthenticationPrincipal Jwt jwt,
            @RequestBody @Valid UpdateCustomerDto customerDto
            ) {
        customerService.createOrUpdate(jwt.getSubject(), customerDto);
        return ResponseEntity.ok().build();
    }
}

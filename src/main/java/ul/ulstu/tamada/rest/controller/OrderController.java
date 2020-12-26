package ul.ulstu.tamada.rest.controller;

import io.swagger.annotations.ApiOperation;
import lombok.extern.log4j.Log4j2;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.web.bind.annotation.*;
import ul.ulstu.tamada.authorization.AllowedUserRoles;
import ul.ulstu.tamada.configuration.enums.UserRole;
import ul.ulstu.tamada.rest.dto.order.CreateOrderRequest;
import ul.ulstu.tamada.rest.dto.order.DetailedOrdersResponse;
import ul.ulstu.tamada.service.order.IOrderService;

import javax.validation.Valid;

@Log4j2
@RestController
@RequestMapping("${base_uri}/orders/v1")
public class OrderController {

    private final IOrderService orderService;

    public OrderController(IOrderService orderService) {
        this.orderService = orderService;
    }

    @GetMapping
    @ApiOperation("Получение списка всех заказов в системе")
    @AllowedUserRoles(roles = {UserRole.ADMIN})
    public ResponseEntity<DetailedOrdersResponse> getAllOrders() {
        var orders = orderService.findAllOrders();
        return ResponseEntity.ok(orders);
    }

    @PostMapping
    @ApiOperation("Запрос на создание заказа")
    @AllowedUserRoles(roles = {UserRole.CUSTOMER})
    public ResponseEntity<Void> createOrder(
            @RequestBody @Valid CreateOrderRequest orderRequest,
            @AuthenticationPrincipal Jwt jwt
    ) {
        orderService.createOrder(jwt.getSubject(), orderRequest);
        return ResponseEntity.ok().build();
    }

    @PostMapping("/{orderId}/approve")
    @ApiOperation("Подтвердить заказ")
    @AllowedUserRoles(roles = {UserRole.ADMIN})
    public ResponseEntity<Void> approveOrder(
            @PathVariable Long orderId
    ) {
        orderService.approveOrder(orderId);
        return ResponseEntity.ok().build();
    }

    @PostMapping("/{orderId}/cancel")
    @ApiOperation("Отменить заказ")
    @AllowedUserRoles(roles = {UserRole.ADMIN, UserRole.CUSTOMER})
    public ResponseEntity<Void> cancelOrder(
            @PathVariable Long orderId
    ) {
        orderService.cancelOrder(orderId);
        return ResponseEntity.ok().build();
    }
}

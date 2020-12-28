package ul.ulstu.tamada.rest.controller;

import io.swagger.annotations.ApiOperation;
import lombok.extern.log4j.Log4j2;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import ul.ulstu.tamada.rest.dto.statistic.OrdersStatisticResponse;
import ul.ulstu.tamada.service.statistic.IStatisticService;

@Log4j2
@RestController
@RequestMapping("${base_uri}/statistics/v1")
public class StatisticController {

    private final IStatisticService statiscticService;

    public StatisticController(IStatisticService statiscticService) {
        this.statiscticService = statiscticService;
    }

    @GetMapping("/events")
    @ApiOperation("Получение статистики о преведенных мероприятиях")
    public ResponseEntity<OrdersStatisticResponse> getOrdersStatistic() {
        var ordersStatistic = statiscticService.getOrdersStatistic();
        return ResponseEntity.ok(ordersStatistic);
    }
}

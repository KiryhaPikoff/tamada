package ul.ulstu.tamada.rest.dto.statistic;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.util.List;

@Data
public class OrdersStatisticResponse {

    @ApiModelProperty("Статистика по проведенным мероприятиям сгрупперованая по типу")
    private List<OrderStatisticResponse> statistics;

    @ApiModelProperty("Общее количество проведенных заказов")
    private Integer total;
}

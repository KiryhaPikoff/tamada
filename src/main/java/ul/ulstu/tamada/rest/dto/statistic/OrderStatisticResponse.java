package ul.ulstu.tamada.rest.dto.statistic;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import ul.ulstu.tamada.model.enums.EventType;

@Data
public class OrderStatisticResponse {

    @ApiModelProperty("Тип мероприятия")
    private EventType event;

    @ApiModelProperty("Количество проведенных мероприятий")
    private Integer count;
}

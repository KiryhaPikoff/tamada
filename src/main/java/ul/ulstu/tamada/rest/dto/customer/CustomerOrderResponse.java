package ul.ulstu.tamada.rest.dto.customer;

import com.fasterxml.jackson.annotation.JsonFormat;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import ul.ulstu.tamada.model.enums.EventType;
import ul.ulstu.tamada.model.enums.OrderStatus;

import java.time.LocalDateTime;
import java.util.Date;

@Data
public class CustomerOrderResponse {

    @ApiModelProperty("Идентификатор заказа")
    private Long id;

    @JsonFormat(
            shape = JsonFormat.Shape.STRING,
            pattern = "dd.MM.yyyy"
    )
    @ApiModelProperty("Дата проведения мероприятия")
    private Date date;

    @ApiModelProperty("Краткая информация об аниматоре")
    private CustomerAnimatorResponse animator;

    @ApiModelProperty("Тип мероприятия")
    private EventType event;

    @JsonFormat(
            shape = JsonFormat.Shape.STRING,
            pattern = "yyyy-MM-dd HH:mm:ss"
    )
    @ApiModelProperty("Время заказа мероприятия")
    private LocalDateTime createdAt;

    @ApiModelProperty("Адрес по которому будет проводится мероприятие")
    private String address;

    @ApiModelProperty("Статус заказа")
    private OrderStatus status;

}

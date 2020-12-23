package ul.ulstu.tamada.rest.dto.order;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;
import ul.ulstu.tamada.model.enums.EventType;

import java.util.Date;

@Data
public class CreateOrderRequest {

    @JsonFormat(
            shape = JsonFormat.Shape.STRING,
            pattern = "dd.MM.yyyy"
    )
    private Date date;

    private Long animatorId;

    private EventType event;

    private String address;
}

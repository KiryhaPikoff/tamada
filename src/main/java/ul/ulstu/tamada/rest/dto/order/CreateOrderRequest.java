package ul.ulstu.tamada.rest.dto.order;

import com.fasterxml.jackson.annotation.JsonFormat;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import ul.ulstu.tamada.model.enums.EventType;

import javax.validation.constraints.Future;
import javax.validation.constraints.FutureOrPresent;
import javax.validation.constraints.NotNull;
import java.util.Date;

@Data
public class CreateOrderRequest {

    @NotNull
    @JsonFormat(
            shape = JsonFormat.Shape.STRING,
            pattern = "dd.MM.yyyy"
    )
    @ApiModelProperty("Планируемая дата мероприятия")
    private Date date;

    @NotNull
    @ApiModelProperty("ID аниматора мероприятия")
    private Long animatorId;

    @NotNull
    @ApiModelProperty("Тип мероприятия")
    private EventType event;

    @NotNull
    @ApiModelProperty("Адрес по которому будет проводится мероприятие")
    private String address;
}

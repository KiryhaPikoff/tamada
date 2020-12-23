package ul.ulstu.tamada.rest.dto.animator;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.util.List;

@Data
public class AnimatorWithScheduleResponse {

    @ApiModelProperty("Идентификатор аниматора")
    private Long id;

    @ApiModelProperty("ФИО аниматора")
    private String name;

    @ApiModelProperty("Свободные для заказа даты аниматора в UNIX формате")
    private List<Long> dates;
}

package ul.ulstu.tamada.rest.dto;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.util.List;

@Data
public class AnimatorsWithScheduleResponse {

    @ApiModelProperty("Массив аниматоров с краткой информацией и массивом свободных дней для заказа мероприятия")
    private List<AnimatorWithScheduleResponse> animators;
}

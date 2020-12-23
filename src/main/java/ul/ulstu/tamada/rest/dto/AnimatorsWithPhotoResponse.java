package ul.ulstu.tamada.rest.dto;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.util.List;

@Data
public class AnimatorsWithPhotoResponse {

    @ApiModelProperty("Массив аниматоров с детальной информацией и фотографией в base64")
    private List<AnimatorWithPhotoResponse> animators;
}

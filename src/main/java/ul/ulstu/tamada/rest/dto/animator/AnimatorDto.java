package ul.ulstu.tamada.rest.dto.animator;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.NotNull;

@Data
public class AnimatorDto {

    @NotNull
    @ApiModelProperty("ФИО аниматора")
    private String name;

    @NotNull
    @ApiModelProperty("Возраст аниматора")
    private Integer age;

    @NotNull
    @ApiModelProperty("Краткое описание аниматора")
    private String description;

    @NotNull
    @ApiModelProperty("Девиз аниматора")
    private String motto;

    @NotNull
    @ApiModelProperty("Фотография аниматора в base64")
    private byte[] image;

    @NotNull
    @ApiModelProperty("Цена аниматора за мероприятие")
    private Integer price;
}

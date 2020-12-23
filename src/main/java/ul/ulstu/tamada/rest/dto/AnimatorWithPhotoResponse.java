package ul.ulstu.tamada.rest.dto;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@Data
public class AnimatorWithPhotoResponse {

    @ApiModelProperty("Идентификатор аниматора")
    private Long id;

    @ApiModelProperty("ФИО аниматора")
    private String name;

    @ApiModelProperty("Возраст аниматора")
    private Integer age;

    @ApiModelProperty("Краткое описание аниматора")
    private String description;

    @ApiModelProperty("Девиз аниматора")
    private String motto;

    @ApiModelProperty("Фотография аниматора в base64")
    private String image;

    @ApiModelProperty("Цена аниматора за мероприятие")
    private Integer price;
}

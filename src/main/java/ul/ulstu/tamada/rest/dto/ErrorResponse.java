package ul.ulstu.tamada.rest.dto;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class ErrorResponse {

    @ApiModelProperty(notes = "Cтроковый код ошибки")
    private String code;

    @ApiModelProperty(notes = "Текстовое описание")
    private String message;
}

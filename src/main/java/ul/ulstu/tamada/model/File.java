package ul.ulstu.tamada.model;

import lombok.Data;
import ul.ulstu.tamada.model.enums.FileType;

@Data
public class File {

    private String ident;

    private byte[] data;

    private FileType type;

    private String extension;
}

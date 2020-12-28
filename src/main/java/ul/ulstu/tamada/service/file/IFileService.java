package ul.ulstu.tamada.service.file;

import ul.ulstu.tamada.model.File;
import ul.ulstu.tamada.model.enums.FileType;

public interface IFileService {

    void save(File file);

    byte[] findByName(String fileName, FileType type);
}

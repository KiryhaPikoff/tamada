package ul.ulstu.tamada.service.file;

import ul.ulstu.tamada.model.File;
import ul.ulstu.tamada.model.enums.FileType;

public interface IFileService {

    void uploadFile(File file);

    byte[] downloadFile(String fileName, FileType type);
}

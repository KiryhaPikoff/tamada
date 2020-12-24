package ul.ulstu.tamada.repository;

import ul.ulstu.tamada.model.File;

public interface IFileRepository {

    void uploadFile(File file);

    byte[] downloadFile(String fileName);
}

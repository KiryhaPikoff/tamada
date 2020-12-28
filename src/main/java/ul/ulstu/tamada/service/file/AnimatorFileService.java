package ul.ulstu.tamada.service.file;

import lombok.extern.log4j.Log4j2;
import org.apache.tika.Tika;
import org.apache.tika.config.TikaConfig;
import org.apache.tika.mime.MimeType;
import org.springframework.stereotype.Service;
import ul.ulstu.tamada.configuration.properties.FileServiceProperties;
import ul.ulstu.tamada.exception.ExtensionNotAllowedException;
import ul.ulstu.tamada.model.File;
import ul.ulstu.tamada.model.enums.FileType;
import ul.ulstu.tamada.repository.IFileRepository;

@Log4j2
@Service
public class AnimatorFileService implements IFileService {

    private final IFileRepository fileRepository;
    private final FileServiceProperties fileServiceProperties;

    public AnimatorFileService(
            IFileRepository fileRepository,
            FileServiceProperties fileServiceProperties
    ) {
        this.fileRepository = fileRepository;
        this.fileServiceProperties = fileServiceProperties;
    }

    @Override
    public void save(File file) {
        file.setIdent(
                file.getIdent() + "_" + file.getType()
        );

        String extension = "";

        try {
            var fileMimeType = new Tika().detect(file.getData());
            var foundType = TikaConfig.getDefaultConfig().getMimeRepository().forName(fileMimeType);
            extension = foundType.getExtension();
        } catch (Exception exception) {
            log.error(extension);
        }

        if (!fileServiceProperties.getAllowedFileTypes().contains(extension)) {
            throw new ExtensionNotAllowedException(extension);
        }

        file.setExtension(extension);

        fileRepository.uploadFile(file);
    }

    @Override
    public byte[] findByName(String fileName, FileType type) {
        return fileRepository.downloadFile(fileName + "_" + type);
    }
}

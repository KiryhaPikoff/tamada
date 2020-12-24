package ul.ulstu.tamada.repository;

import lombok.SneakyThrows;
import org.springframework.stereotype.Repository;
import ul.ulstu.tamada.configuration.properties.FileServiceProperties;
import ul.ulstu.tamada.exception.FileLoadingException;
import ul.ulstu.tamada.model.File;

import java.io.ByteArrayInputStream;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.nio.file.Files;
import java.nio.file.NoSuchFileException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Repository
public class FileRepository implements IFileRepository {

    private final static Integer BUFFER_SIZE = 4096;
    private final FileServiceProperties fileServiceProperties;

    public FileRepository(FileServiceProperties fileServiceProperties) {
        this.fileServiceProperties = fileServiceProperties;
    }

    @Override
    public void uploadFile(File file) {
        var filePath = getFullFilePath(file.getIdent(), file.getExtension());
        deleteFileWithSameName(file.getIdent(), file.getExtension());
        var fileStream = new ByteArrayInputStream(file.getData());

        try (var stream = new FileOutputStream(filePath)) {

            byte[] bufferArray = new byte[BUFFER_SIZE];

            var readRes = 0;
            while ((readRes = fileStream.read(bufferArray)) != -1) {
                stream.write(bufferArray, 0, readRes);
            }

        } catch (Exception exc) {
            throw new FileLoadingException();
        }
    }

    @Override
    @SneakyThrows
    public byte[] downloadFile(String fileName) {
        var fullFilePath = searchFile(fileName);

        if (fullFilePath.isEmpty()) {
            throw new FileLoadingException();
        }

        if (!Files.exists(Path.of(fullFilePath.get()))) {
            throw new FileLoadingException();
        };

        return new FileInputStream(fullFilePath.get()).readAllBytes();
    }

    private Optional<String> searchFile(String fileName) {
        var folder = new java.io.File(fileServiceProperties.getFilesStoragePath());
        if (!folder.exists()) {
            folder.mkdir();
        }

        var listOfFiles = List.of(folder.listFiles());

        if (listOfFiles.isEmpty()) {
            return Optional.empty();
        }

        var searchedFile = listOfFiles.stream()
                .filter(java.io.File::isFile)
                .filter(file -> file.getName().split("\\.")[0].equalsIgnoreCase(fileName))
                .collect(Collectors.toList());

        if (searchedFile.isEmpty()) {
            return Optional.empty();
        } else {
            return Optional.of(searchedFile.get(0).getAbsolutePath());
        }
    }

    private String getFullFilePath(String fileName, String fileExtension) {
        var folder = new java.io.File(fileServiceProperties.getFilesStoragePath());
        if (!folder.exists()) {
            folder.mkdir();
        }
        var path = fileServiceProperties.getFilesStoragePath();
        return path + fileName + fileExtension;
    }

    @SneakyThrows
    private void deleteFileWithSameName(String fileName, String fileExtension) {
        var fullFilePath = getFullFilePath(fileName, fileExtension);

        try {
            Files.delete(Paths.get(fullFilePath));
        } catch (NoSuchFileException ex) {
            var filePath = getFullFilePath(fileName, fileExtension);
            if (Files.exists(Path.of(filePath))) {
                throw new FileLoadingException();
            }
        }
    }
}

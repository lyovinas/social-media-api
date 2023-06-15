package ru.pet.socialnetwork.util;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.StandardCopyOption;
import java.util.Objects;
import java.util.UUID;

@Component
public class FileHelper {

    @Value("${post.image.path}")
    private String postImagePath;


    public String createFile(MultipartFile file) {
        String fileName = UUID.randomUUID().toString();
        String fileExtension = Objects.requireNonNull(file.getOriginalFilename())
                .split("\\.")[file.getOriginalFilename().split("\\.").length - 1];
        String fullFileName = fileName + "." + fileExtension;

        try (InputStream inputStream = file.getInputStream()) {
            Path path = Path.of(postImagePath + "/" + fullFileName).toAbsolutePath();
            if (!path.getParent().toFile().exists()) {
                Files.createDirectories(path.getParent());
            }
            Files.copy(inputStream, path);
        } catch (IOException e) {
            return null;
        }

        return fullFileName;
    }

    public void updateFile(String imageFileName, MultipartFile newImageFile) {
        Path path = Path.of(postImagePath + "/" + imageFileName).toAbsolutePath();
        if (path.toFile().exists()) {
            try (InputStream inputStream = newImageFile.getInputStream()) {
                Files.copy(inputStream, path, StandardCopyOption.REPLACE_EXISTING);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    public boolean deleteFile(String imageFileName) {
        Path path = Path.of(postImagePath + "/" + imageFileName).toAbsolutePath();
        try {
            Files.deleteIfExists(path);
            return true;
        } catch (IOException e) {
            e.printStackTrace();
            return false;
        }
    }

    public File getFile(String imageFileName) {
        Path path = Path.of(postImagePath + "/" + imageFileName).toAbsolutePath();
//        if (file.exists()) {
//            try (InputStream inputStream = new FileInputStream(file)) {
//                return new MockMultipartFile(file.getName(), Files.readAllBytes(path));
//            } catch (IOException e) {
//                throw new RuntimeException(e);
//            }
//        }
        return new File(path.toString());
    }
}

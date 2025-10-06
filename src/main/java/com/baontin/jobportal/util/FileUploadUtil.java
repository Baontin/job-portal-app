package com.baontin.jobportal.util;

import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;

public class FileUploadUtil {

    public static void saveFile(String uploadDir, String fileName, MultipartFile multipartFile)
        throws Exception {
        Path uploadPath = Paths.get(uploadDir);

        if (!Files.exists(uploadPath)) {
            Files.createDirectories(uploadPath);
        }

        /*
        InputStream:
        - An abstraction class.
        - It represents an input stream of byte (file on disk, data over the network, data inside memory)
        - It helps the program read a file (or any date source) in raw bytes
        * */
        try (InputStream inputStream = multipartFile.getInputStream()) {
            Path path = uploadPath.resolve(fileName);
            System.out.println("FilePath " + path);
            System.out.println("Filename " + fileName);
            Files.copy(inputStream, path, StandardCopyOption.REPLACE_EXISTING);
        } catch (IOException ioe) {
            // If anything goes wrong (e.g., disk is full, folder can’t be created, permission denied, etc.),
            // it catches the error.
            throw new IOException("Could not save image file: " + fileName, ioe);
        }
    }
}

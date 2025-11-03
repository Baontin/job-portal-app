package com.baontin.jobportal.util;

import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

public class FileDownloadUtil {

    private Path foundFile;

    /* Resource:
    * It represents a source of data that you can read from — like a file, a URL,
    * a classpath resource, or even something from a JAR.
    *
    * Here UrlResource is a concrete class (a subclass of Resource).
      It represents a resource located at a specific URL or URI,
      such as file:///C:/photos/candidate/1/resume.pdf.

    * */


    public Resource getFileAsResource(String downloadDir, String fileName) throws IOException {

        /*
        * get() method creates a Path object
        -> representing a folder or file in your computer’s filesystem.
            ex: downloadDir: "photos/candidate/12"

        * list(): lists all files inside that directory (a stream of Path objects)
        * */
        Path path = Paths.get(downloadDir);
        Files.list(path).forEach(file -> {
            if (file.getFileName().toString().equals(fileName)) {
                foundFile = file;
            }
        });

        /*
        * foundFile.toUri() convert path to URI
        * A URI (Uniform Resource Identifier) is a standard way
            to locate something on your computer or on the web.
        * EX: photos/candidate/12/resume.pdf -> file:///D:/MyProject/photos/candidate/12/resume.pdf

        * new UrlResource(foundFile.toUri())
            → creates a UrlResource that points to that physical file.
            This lets Spring handle it generically — whether it’s a file,
            or something from the internet, it can be returned as a Resource in your HTTP response.
        * */
        if (foundFile != null) {
            return new UrlResource(foundFile.toUri());
        }

        return null;
    }
}

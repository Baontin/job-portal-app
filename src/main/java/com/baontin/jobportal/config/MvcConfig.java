package com.baontin.jobportal.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import java.nio.file.Path;
import java.nio.file.Paths;

/*
This Configuration class will map requests for /photos to serve files from a dir on your file system.

implements WebMvcConfigurer: Gives you hooks to override MVC behavior.
* */
@Configuration
public class MvcConfig implements WebMvcConfigurer {

    private static final String UPLOAD_DIR = "photos";

    // This method registers custom resource handlers (URL → file system mappings).
    // It’s like creating a shortcut between an internet address and a folder in your disk.
    // it will know where to take a picture requested (/photos/recruiter/5/avatar.png)
    // it check photos/... (photo/.. is configured) it will go in there to take.
    @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry) {
        exposeDirectory(UPLOAD_DIR, registry);
    }

    /*
    Converts the uploadDir to a Path
    Map request starting with "/photos/**" to a file system location file:<absolute path to photos dir>
    the ** will match on all sub-dirs

    Absolute path: D:/Code/jobportal/photos (for example)
    * */
    private void exposeDirectory(String uploadDir, ResourceHandlerRegistry registry) {
        Path path = Paths.get(uploadDir);
        registry.addResourceHandler("/" + uploadDir + "/**")
                .addResourceLocations("file:" + path.toAbsolutePath() + "/");
    }
}

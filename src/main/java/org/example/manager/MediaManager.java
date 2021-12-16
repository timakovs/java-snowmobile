package org.example.manager;


import org.example.dto.UploadSingleMediaResponceDTO;
import org.example.exception.UnsupportedContentTypeException;
import org.example.exception.UploadException;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Map;
import java.util.UUID;

@Component
public class MediaManager {
    private final Path path = Path.of("media");
    private final Map<String, String> types = Map.of(
            "image/jpeg", ".jpg",
            "image/png", ".png"
    );

    public MediaManager() throws IOException {
        Files.createDirectories(path);
    }

    public UploadSingleMediaResponceDTO save(byte[] bytes, String contentType) {
        try {
            final String name = UUID.randomUUID() + getExtension(contentType);
            Files.write(path.resolve(name), bytes);
            return new UploadSingleMediaResponceDTO(name);
        } catch (IOException e) {
            throw new UploadException(e);
        }

    }

    private String getExtension(String contentType) {
        final String extencion = types.get(contentType);
        if (extencion == null) {
            throw new UnsupportedContentTypeException(contentType + "not alowed for upload");
        }
        return extencion;
    }
}

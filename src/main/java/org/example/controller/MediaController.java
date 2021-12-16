package org.example.controller;

import lombok.RequiredArgsConstructor;
import org.example.dto.UploadSingleMediaResponceDTO;
import org.example.manager.MediaManager;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/media")
@RequiredArgsConstructor
public class MediaController {
    private final MediaManager manager;

    @PostMapping("/bytes")
    public UploadSingleMediaResponceDTO upload (@RequestBody byte[] bytes, @RequestHeader("Content-type") String contentType) {
        return manager.save(bytes, contentType);

    }
}

package org.example.controller;

import lombok.RequiredArgsConstructor;
import org.example.dto.SnowmobileGetAllResponseDTO;
import org.example.dto.SnowmobileGetByIdResponseDTO;
import org.example.dto.SnowmobileSaveRequestDTO;
import org.example.dto.SnowmobileSaveResponseDTO;
import org.example.manager.SnowmobileManager;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/snowmobiles")
@RequiredArgsConstructor
public class SnowmobileController {
    private final SnowmobileManager manager;

    @GetMapping("/getAll")
    public SnowmobileGetAllResponseDTO getAll() {
        return manager.getAll();
    }

    @GetMapping("/getById")
    public SnowmobileGetByIdResponseDTO getById(@RequestParam long id) {
        return manager.getById(id);
    }

    @PostMapping("/save")
    public SnowmobileSaveResponseDTO save(@RequestBody SnowmobileSaveRequestDTO requestDTO) {
        return manager.save(requestDTO);
    }


    @PostMapping("/removeById")
    public void removeById(@RequestParam long id) {
        manager.removeById(id);
    }

    @PostMapping("/restoreById")
    public void restoreById(@RequestParam long id) {
        manager.restoreById(id);
    }
}


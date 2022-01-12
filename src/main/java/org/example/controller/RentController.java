package org.example.controller;

import lombok.RequiredArgsConstructor;
import org.example.dto.RentGetByIdResponseDTO;
import org.example.dto.RentRegisterRequestDTO;
import org.example.dto.RentRegisterResponseDTO;
import org.example.manager.RentManager;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/rents")
@RequiredArgsConstructor
public class RentController {
    private final RentManager manager;

    @GetMapping("/getById")
    public RentGetByIdResponseDTO getById(@RequestParam long id) {
        return manager.getById(id);
    }

    @PostMapping("/register")
    public RentRegisterResponseDTO register(@RequestBody RentRegisterRequestDTO requestDTO) {
         return manager.register(requestDTO);
    }
}

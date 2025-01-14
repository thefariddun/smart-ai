package uz.smart_ai.smart_ai.controller;

import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import uz.smart_ai.smart_ai.dto.RegistrationDTO;
import uz.smart_ai.smart_ai.service.AuthService;

@RestController
@RequestMapping("/auth")
public class AuthController {

    @Autowired
    private AuthService authService;

    @PostMapping("/registration")
    public ResponseEntity<String> registration(@Valid @RequestBody RegistrationDTO registrationDTO) {
        return ResponseEntity.ok().body(authService.registration(registrationDTO));
    }

    @GetMapping("/registration/verification/{id}")
    public ResponseEntity<String> regVerification(@PathVariable("id") Long id) {
        return ResponseEntity.ok().body(authService.regVerification(id));
    }
}

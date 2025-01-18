package uz.smart_ai.smart_ai.controller;

import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import uz.smart_ai.smart_ai.dto.LoginDTO;
import uz.smart_ai.smart_ai.dto.RegistrationDTO;
import uz.smart_ai.smart_ai.service.AuthService;

import java.util.Map;

@RestController
@RequestMapping("/auth")
public class AuthController {

    @Autowired
    private AuthService authService;

    @PostMapping("/registration")
    public ResponseEntity<String> registration(@Valid @RequestBody RegistrationDTO registrationDTO) {
        return ResponseEntity.ok().body(authService.registration(registrationDTO));
    }


    @PostMapping("/registration/verify")
    public ResponseEntity<String> verifyRegistration(@RequestBody Map<String, String> requestBody) {
        String phoneNumber = requestBody.get("phoneNumber");
        String verificationCode = requestBody.get("verificationCode");

        String responseMessage = authService.verifyRegistration(phoneNumber, verificationCode);
        return ResponseEntity.ok().body(responseMessage);
    }

    @PostMapping("/login")
    public ResponseEntity<String> login(@RequestBody LoginDTO loginDTO) {
        String responseMessage = authService.login(loginDTO);
        return ResponseEntity.ok().body(responseMessage);
    }

}

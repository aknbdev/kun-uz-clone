package uz.isystem.KunUzClone.auth;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;

@RestController
@RequestMapping("/api")
@RequiredArgsConstructor
public class AuthController {
    private final AuthService authService;

    @PostMapping("/register")
    public ResponseEntity<?> register(@Valid @RequestBody RegistrationDto dto){
        authService.register(dto);
        return ResponseEntity.ok("We sent the mail to your email! Please verify your data.");
    }

    @GetMapping("/validation/{token}")
    public ResponseEntity<?> verification(@PathVariable("token") String token){
        authService.verification(token);
        return ResponseEntity.ok("Successfully registered!");
    }

    @GetMapping("/refresh/token")
    public void refreshToken(HttpServletRequest request, HttpServletResponse response){
        authService.refreshToken(request, response);
    }
}

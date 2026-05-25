package com.nihil.clientifyapi.auth;

import com.nihil.clientifyapi.auth.dto.LoginRequest;
import com.nihil.clientifyapi.auth.dto.RegisterRequest;
import com.nihil.clientifyapi.auth.entities.Session;
import com.nihil.clientifyapi.auth.entities.User;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController("auth")
@RequestMapping("/auth")
public class AuthController {
    private final AuthService authService;
    public AuthController(AuthService _authService){
        this.authService = _authService;
    }

    @PostMapping("/register")
    public ResponseEntity<User> register(@RequestBody @Valid RegisterRequest registerRequest) {
        User createdUser = authService.register(registerRequest);

        return  ResponseEntity.status(HttpStatus.CREATED).body(createdUser);
    }

    @PostMapping("/login")
    public ResponseEntity<Session> logIn(@RequestBody @Valid LoginRequest loginRequest) {
        Session session = authService.login(loginRequest);
        return ResponseEntity.status(HttpStatus.CREATED).body(session);
    }

    @DeleteMapping("/logout/{token}")
    public ResponseEntity<?> logout(@PathVariable String token){

        Session sesion = authService.logout(token);

        return ResponseEntity.status(HttpStatus.OK).body(sesion.getId());
    }

}

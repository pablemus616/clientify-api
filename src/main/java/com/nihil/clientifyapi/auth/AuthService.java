package com.nihil.clientifyapi.auth;

import com.nihil.clientifyapi.auth.dto.LoginRequest;
import com.nihil.clientifyapi.auth.dto.RegisterRequest;
import com.nihil.clientifyapi.auth.entities.Session;
import com.nihil.clientifyapi.auth.entities.User;
import com.nihil.clientifyapi.auth.repositories.SessionRepository;
import com.nihil.clientifyapi.auth.repositories.UserRepository;
import jakarta.servlet.http.HttpServletRequest;

import org.springframework.http.HttpStatus;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

@Service
public class AuthService
{
    private final HttpServletRequest request;
    private final UserRepository userRepository;
    private final SessionRepository sessionRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtService jwtService;
    public AuthService(
            UserRepository _userRepository,
            SessionRepository _sessionRepository,
            PasswordEncoder _passwordEncoder,
            HttpServletRequest _request,
            JwtService _jwtService
    ){
        this.userRepository = _userRepository;
        this.sessionRepository = _sessionRepository;
        this.passwordEncoder = _passwordEncoder;
        this.request = _request;
        this.jwtService = _jwtService;
    }

    public User register(RegisterRequest registerRequest) {
        if(userRepository.existsByEmail(registerRequest.email()) ||  userRepository.existsByUsername(registerRequest.username())){
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Email or username already in use");
        }

        User newUser = new User();
        newUser.setEmail(registerRequest.email());
        newUser.setUsername(registerRequest.username());
        String hashedPassword = passwordEncoder.encode(registerRequest.password());
        newUser.setPassword(hashedPassword);

       return userRepository.save(newUser);
    }

    public Session login(LoginRequest loginRequest) {
         if(!userRepository.existsByEmail(loginRequest.email())){
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "User does not exist");
        }
         User user = userRepository.findOneByEmail(loginRequest.email());


        if(!passwordEncoder.matches(loginRequest.password(), user.getPassword())){
            throw new ResponseStatusException(HttpStatus.UNAUTHORIZED, "Wrong password or email");
        }

         String token = jwtService.generate(user.getId(), user.getEmail(), user.getUsername());

         Session session = new Session();
         session.setUser(user);
         session.setSessionToken(token);
         session.setIp(request.getRemoteAddr());

         return sessionRepository.save(session);

    }

    public Session logout (String token){
        if(!sessionRepository.existsBySessionToken(token)){
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Invalid token");
        }

        Session session = sessionRepository.findSessionBySessionToken(token);
        sessionRepository.delete(session);
        return session;
    }

}

package com.hrbp.feedback.controller;


import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import com.hrbp.feedback.auth.JwtUtil;
import com.hrbp.feedback.model.dto.ErrorRes;
import com.hrbp.feedback.model.dto.LoginReq;
import com.hrbp.feedback.model.dto.LoginRes;
import com.hrbp.feedback.model.dto.User;

import lombok.extern.slf4j.Slf4j;

@RestController
@RequestMapping("/auth")
@CrossOrigin("*")
@Slf4j
public class AuthController {

    private final AuthenticationManager authenticationManager;
    private JwtUtil jwtUtil;
    
    public AuthController(AuthenticationManager authenticationManager, JwtUtil jwtUtil) {
        this.authenticationManager = authenticationManager;
        this.jwtUtil = jwtUtil;

    }

    @PostMapping("/generatetoken")
    public ResponseEntity login(@RequestBody LoginReq loginReq)  {
    	log.info("generatetoken(-) started");

        try {
            Authentication authentication =
                    authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(loginReq.getUserId(), loginReq.getPassword()));
            Integer userId = Integer.parseInt(authentication.getName());
            User user = new User(userId,"");
            String token = jwtUtil.createToken(user);
            LoginRes loginRes = new LoginRes(userId,token);
        	log.info("generatetoken(-) completed");
            return ResponseEntity.ok(loginRes);

        }catch (BadCredentialsException e){
            ErrorRes errorResponse = new ErrorRes(HttpStatus.BAD_REQUEST,"Invalid username or password");
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(errorResponse);
        }catch (Exception e){
            ErrorRes errorResponse = new ErrorRes(HttpStatus.BAD_REQUEST, e.getMessage());
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(errorResponse);
        }
    }
}

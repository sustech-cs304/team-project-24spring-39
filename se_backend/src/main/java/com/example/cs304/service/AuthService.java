package com.example.cs304.service;

import com.example.cs304.converter.JwtTokenProvider;
import com.example.cs304.dto.UserDTO;
import com.example.cs304.repository.AdminRepository;
import com.example.cs304.repository.StudentRepository;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.security.authentication.BadCredentialsException;
@Service
public class AuthService{
    private AuthenticationManager authenticationManager;
    private PasswordEncoder passwordEncoder;
    private JwtTokenProvider jwtTokenProvider;
    private StudentRepository studentRepository;
    private AdminRepository adminRepository;

    public AuthService(AuthenticationManager authenticationManager, PasswordEncoder passwordEncoder, JwtTokenProvider jwtTokenProvider, StudentRepository studentRepository, AdminRepository adminRepository) {
        this.authenticationManager = authenticationManager;
        this.passwordEncoder = passwordEncoder;
        this.jwtTokenProvider = jwtTokenProvider;
        this.studentRepository = studentRepository;
        this.adminRepository = adminRepository;
    }




public String login(UserDTO user){
    try {
        Authentication authentication = authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(user.getUsername(), user.getPassword()));
        SecurityContextHolder.getContext().setAuthentication(authentication);
        return jwtTokenProvider.generateToken(authentication);
    } catch (BadCredentialsException e) {
        // 返回一个错误信息
        return "Authentication failed: Invalid username or password.";
    }
}
}

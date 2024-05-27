package com.example.cs304.service;

import com.example.cs304.entity.Admin;
import com.example.cs304.entity.Student;
import com.example.cs304.entity.User;
import com.example.cs304.repository.AdminRepository;
import com.example.cs304.repository.StudentRepository;
import lombok.AllArgsConstructor;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.Set;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor
public class MyUserDetailsService implements UserDetailsService {

//    private UserRepository userRepository;
    private StudentRepository studentRepository;
    private AdminRepository adminRepository;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
//
//        User user = userRepository.findByUsernameOrEmail(usernameOrEmail, usernameOrEmail)
//                .orElseThrow(() -> new UsernameNotFoundException("User not exists by Username or Email"));
//
//        Set<GrantedAuthority> authorities = user.getRoles().stream()
//                .map((role) -> new SimpleGrantedAuthority(role.getName()))
//                .collect(Collectors.toSet());
//
//        return new org.springframework.security.core.userdetails.User(
//                usernameOrEmail,
//                user.getPassword(),
//                authorities
//        );

        User user = new User();
        if (studentRepository.findBySid(username) != null) {
            Student student = studentRepository.findBySid(username);
            user.setUsername(student.getSid());
            user.setPassword(student.getPassword());
            Set<GrantedAuthority> authorities = Collections.singleton(new SimpleGrantedAuthority("STUDENT"));
        } else if (adminRepository.findByAccount(username) != null) {
            Admin admin = adminRepository.findByAccount(username);
            user.setUsername(admin.getAccount());
            user.setPassword(admin.getPassword());
            Set<GrantedAuthority> authorities = Collections.singleton(new SimpleGrantedAuthority("ADMIN"));
        } else {
            throw new UsernameNotFoundException("User not found with username: " + username);
        }
        return new org.springframework.security.core.userdetails.User(
                user.getUsername(),
                user.getPassword(),
                true,
                true,
                true,
                true,
                user.getAuthorities()
        );
    }
}
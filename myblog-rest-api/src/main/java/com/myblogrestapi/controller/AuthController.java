package com.myblogrestapi.controller;

//import com.myblogrestapi.entity.Roles;
//import com.myblogrestapi.entity.Users;
//import com.myblogrestapi.payload.LoginDto;
//import com.myblogrestapi.payload.SignUpDto;
//import com.myblogrestapi.repository.RoleRepository;
//import com.myblogrestapi.repository.UserRepository;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.http.HttpStatus;
//import org.springframework.http.ResponseEntity;
//import org.springframework.security.authentication.AuthenticationManager;
//import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
//import org.springframework.security.core.Authentication;
//
//import org.springframework.security.core.context.SecurityContextHolder;
//import org.springframework.security.crypto.password.PasswordEncoder;
//import org.springframework.web.bind.annotation.PostMapping;
//import org.springframework.web.bind.annotation.RequestBody;
//import org.springframework.web.bind.annotation.RequestMapping;
//import org.springframework.web.bind.annotation.RestController;
//
//import java.util.Collections;
//
//@RestController
//@RequestMapping("/api/auth")
//public class AuthController {
//
//    @Autowired
//    private AuthenticationManager authenticationManager;
//    @Autowired
//    private PasswordEncoder passwordEncoder;
//    @Autowired
//    private UserRepository userRepo;
//    @Autowired
//    private RoleRepository roleRepository;
//    //http://localhost:8080/api/auth/signing
//    @PostMapping("/signing")
//    public ResponseEntity<?> authenticateUser(@RequestBody LoginDto loginDto){
//         Authentication authentication = authenticationManager.authenticate(
//                 new UsernamePasswordAuthenticationToken(loginDto.getUsernameOrEmail(), loginDto.getPassword()));
//
//        SecurityContextHolder.getContext().setAuthentication( authentication);
//        return new ResponseEntity<>("user Signed in successfully ", HttpStatus.OK);
//    }
//   // http://localhost:8080/api/auth/signup
//    @PostMapping("/signup")
//    public ResponseEntity<?> registerUser(@RequestBody SignUpDto signUpDto)
//    {
//        if(userRepo.existsByUsername(signUpDto.getUsername()))
//        {
//        return new ResponseEntity<>("Username is already taken!", HttpStatus.BAD_REQUEST);
//        }
//
//        // add check for email exists in DB
//        if(userRepo.existsByEmail(signUpDto.getEmail()))
//        {
//            return new ResponseEntity<>("Email is already taken!", HttpStatus.BAD_REQUEST);
//        }
//        Users users=new Users();
//        users.setName((signUpDto.getName()));
//        users.setUsername((signUpDto.getUsername()));
//        users.setEmail(signUpDto.getEmail());
//        users.setPassword(passwordEncoder.encode(signUpDto.getPassword()));
//        Roles roles = roleRepository.findByName("ROLE_ADMIN").get();
//        users.setRoless(Collections.singleton(roles));
//
//        userRepo.save( users);
//        return new ResponseEntity<>("user is registered",HttpStatus.CREATED);
//    }
//}
//-----------------------------------------------------------------------------------------------

import com.myblogrestapi.entity.Roles;
import com.myblogrestapi.entity.Users;
import com.myblogrestapi.payload.JWTAuthResponse;
import com.myblogrestapi.payload.LoginDto;
import com.myblogrestapi.payload.SignUpDto;
import com.myblogrestapi.repository.RoleRepository;
import com.myblogrestapi.repository.UserRepository;
import com.myblogrestapi.security.JwtTokenProvider;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Collections;

@RestController
@RequestMapping("/api/auth")
public class AuthController {

    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private RoleRepository roleRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private JwtTokenProvider tokenProvider;

    @PostMapping("/signing")
    public ResponseEntity<JWTAuthResponse> authenticateUser(@RequestBody LoginDto loginDto){
        Authentication authentication = authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(
                loginDto.getUsernameOrEmail(), loginDto.getPassword()));

        SecurityContextHolder.getContext().setAuthentication(authentication);

        // get token form tokenProvider
        String token = tokenProvider.generateToken(authentication);

        return ResponseEntity.ok(new JWTAuthResponse(token));
    }

    @PostMapping("/signup")
    public ResponseEntity<?> registerUser(@RequestBody SignUpDto signUpDto){

        // add check for username exists in a DB
        if(userRepository.existsByUsername(signUpDto.getUsername())){
            return new ResponseEntity<>("Username is already taken!", HttpStatus.BAD_REQUEST);
        }

        // add check for email exists in DB
        if(userRepository.existsByEmail(signUpDto.getEmail())){
            return new ResponseEntity<>("Email is already taken!", HttpStatus.BAD_REQUEST);
        }

        // create user object
        Users user = new Users();
        user.setName(signUpDto.getName());
        user.setUsername(signUpDto.getUsername());
        user.setEmail(signUpDto.getEmail());
        user.setPassword(passwordEncoder.encode(signUpDto.getPassword()));

        Roles roles = roleRepository.findByName("ROLE_ADMIN").get();
        user.setRoless(Collections.singleton(roles));


        userRepository.save(user);

        return new ResponseEntity<>("User registered successfully", HttpStatus.OK);

    }
}



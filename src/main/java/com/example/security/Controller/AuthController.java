package com.example.security.Controller;

import com.example.security.Api.ApiException;
import com.example.security.Api.ApiResponse;
import com.example.security.Model.User;
import com.example.security.Service.AuthService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.Valid;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/auth")
public class AuthController {

    private final AuthService authService;

    @PostMapping("/register")//if i have many users with same names it will be unauthorize!
    public ResponseEntity regirster(@RequestBody @Valid User user){
        authService.register(user);
        return ResponseEntity.ok().body(new ApiResponse("Registered Successfully!"));
    }
    //if the admin do something he has no authorize on it , it will be forbidden


    //add login and logout
    @PostMapping("/login/{username}/{password}")
    public ResponseEntity login(@PathVariable String username, @PathVariable String password){
        authService.login(username, password);
        return ResponseEntity.ok().body("Logged in");
    }

    @PostMapping("/logout")// I HAVE A PROBLEM IN THIS METHOD!
    public ResponseEntity logout(){
        authService.logout();
        return ResponseEntity.ok().body(new ApiResponse("user logged out successfully!"));
    }

    //show all users for only admin
    @GetMapping("/get-users")
    public ResponseEntity getAllUsers(){
        return ResponseEntity.ok().body(authService.getAllUsers());
    }
    //delete user for only admin
    @DeleteMapping("/delete/{username}")
    public ResponseEntity deleteUser(@PathVariable String username){
        authService.deleteUser(username);
        return ResponseEntity.ok().body(new ApiResponse("user deleted succesffully!"));
    }




}

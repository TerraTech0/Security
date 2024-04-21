package com.example.security.Service;

import com.example.security.Api.ApiException;
import com.example.security.Model.User;
import com.example.security.Repository.AuthRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class MyUserDetailsService implements UserDetailsService {

    private final AuthRepository authRepository;
    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
       //i can change in the body of this method only!
        //take username
        // i have create User object to find user by username with help of authRepository!
        User user = authRepository.findUserByUsername(username);

        if (user==null){
            throw new ApiException("wrong username or password!");
        }
        return user;//i should implements the UserDetails in user class the is exist in model!
    }
}

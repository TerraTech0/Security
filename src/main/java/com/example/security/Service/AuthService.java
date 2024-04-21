    package com.example.security.Service;

    import com.example.security.Api.ApiException;
    import com.example.security.Model.User;
    import com.example.security.Repository.AuthRepository;
    import lombok.RequiredArgsConstructor;
    import org.springframework.security.core.context.SecurityContextHolder;
    import org.springframework.security.core.userdetails.UserDetails;
    import org.springframework.security.core.userdetails.UserDetailsService;
    import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
    import org.springframework.stereotype.Service;

    import java.util.List;

    @Service
    @RequiredArgsConstructor
    public class AuthService {

        //register user >> add!
        private final AuthRepository authRepository;
        private final MyUserDetailsService myUserDetailsService;
        public void register(User user){
            user.setRole("CUSTOMER");
            String hashPassword = new BCryptPasswordEncoder().encode(user.getPassword());

            user.setPassword(hashPassword);
            authRepository.save(user);
        }

        //show all users for only admin
        public List<User> getAllUsers(){
            return authRepository.findAll();
        }
        //delete user for only admin
        public void deleteUser(String username){
            User user = authRepository.findUserByUsername(username);
            if (user==null){
                throw new ApiException("username not found!");
            }
            authRepository.delete(user);
        }

        //add login method
        public UserDetails login(String username, String password){
            UserDetails userDetails = myUserDetailsService.loadUserByUsername(username);
            if (new BCryptPasswordEncoder().matches(password, userDetails.getPassword())) {
                // Password matches, returnd the UserDetails
                return userDetails;
            } else {
                // Password does not match, throw an exception or return null
                throw new ApiException("Invalid username or password");
            }
        }
        //add logout method
        public void logout() {
            SecurityContextHolder.getContext().setAuthentication(null);
        }


    }

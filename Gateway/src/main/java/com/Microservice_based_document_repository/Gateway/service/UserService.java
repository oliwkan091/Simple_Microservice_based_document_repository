package com.Microservice_based_document_repository.Gateway.service;
import com.Microservice_based_document_repository.Gateway.model.User;
import com.Microservice_based_document_repository.Gateway.repository.UserRepository;
import java.util.List;
import java.util.Optional;
import com.Microservice_based_document_repository.Gateway.security.UserLogInPrincipal;
import com.Microservice_based_document_repository.Gateway.security.jwt.JwtManagement;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

/**
 * Service logic called by API Controller to handle user data management
 */
@Service
public class UserService {

    private PasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
    private final UserRepository userRepository;
    private final JwtManagement jwtManagement;

    public UserService(UserRepository userRepository, JwtManagement jwtManagement) {
        this.userRepository = userRepository;
        this.jwtManagement = jwtManagement;
    }

    /**
     * Searches database for give username
     * @param name username to be changed in database
     * @return User model load from database
     */
    public Optional<User> findUserByName(String name)
    {
        return userRepository.findUserByName(name);
    }

    /**
     * Save new user to database
     * @param user model to be save
     * @return User model if saving was successful or null if error occurred
     */
    public User saveUser(User user)
    {
        return userRepository.save(new User(user));
    }

    public Optional<User> changeName(User user, UserLogInPrincipal userLogInPrincipal)
    {
        if (userRepository.changeName(user.getName(), user.getId()) == 1)
        {
            Optional<User> newUser = userRepository.findUserByName(user.getName());
            userLogInPrincipal.setName(newUser.get().getName());
            newUser.get().setToken(jwtManagement.generateAuthToken(userLogInPrincipal));
            return newUser;
        }else
        {
            return null;
        }
    }

    /**
     * Changes user password
     * @param passwords old password
     * @param user model with new password
     * @return ResponseEntity with HttpStatus.OK if password was changed successfully, HttpStatus.CONFLICT if
     * password is identical as current one or HttpStatus.NOT_MODIFIED if other error occurred
     */
    public ResponseEntity<?> changePassword(List<String> passwords, UserLogInPrincipal user)
    {
        if(!passwordEncoder.matches(passwords.get(1),userRepository.findUserByName(user.getUsername()).get().getPassword()))
        {
            return new ResponseEntity<>(HttpStatus.CONFLICT);
        }else
        {
            if(userRepository.changePassword(passwordEncoder.encode(passwords.get(0)), user.getId()) == 1)
            {
                return new ResponseEntity<>(HttpStatus.OK);
            }else
            {
                return new ResponseEntity<>(HttpStatus.NOT_MODIFIED);
            }
        }
    }

    /**
     * Gets all users from database
     * @return List of users
     */
    public List<User> getAllUsers()
    {
        return userRepository.findAll();
    }
}

package com.Microservice_based_document_repository.Gateway.controller;
import com.Microservice_based_document_repository.Gateway.model.User;
import com.Microservice_based_document_repository.Gateway.security.UserLogInPrincipal;
import com.Microservice_based_document_repository.Gateway.service.AuthenticationService;
import com.Microservice_based_document_repository.Gateway.service.UserService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;
import java.util.List;

/**
 * API controller to manage user data
 */
@RestController
@RequestMapping("gateway/user")
@CrossOrigin(origins = "http://localhost:4200")
public class UserController {

    private final UserService userService;
    private final AuthenticationService authenticationService;

    public UserController(UserService userService, AuthenticationService authenticationService) {
        this.userService = userService;
        this.authenticationService = authenticationService;
    }

    /**
     * Changes name o currently logged user
     * @param user new name to be saved provided in user model
     * @param userPrincipal authenticates user
     * @return ResponseEntity with HttpStatus.OK if name was chenaged successfully, HttpStatus.CONFLICT if given name
     * alerady exists or HttpStatus.INTERNAL_SERVER_ERROR if other error occurred
     */
    @PostMapping("changeName")
    public ResponseEntity<?> changeName(@RequestBody User user, @AuthenticationPrincipal UserLogInPrincipal userPrincipal)
    {
        if(userService.findUserByName(user.getName()).isPresent())
        {
            return new ResponseEntity<>(HttpStatus.CONFLICT);
        }else
        {
            User userWithChangedName = userService.changeName(user, userPrincipal).get();
            return new ResponseEntity<>(userWithChangedName, HttpStatus.OK);
        }
    }

    /**
     * Changes password of currently logged user
     * @param passwords new password to be changed
     * @param user authenticates user
     * @return ResponseEntity with HttpStatus.OK if password was changed successfully, HttpStatus.CONFLICT if password
     * is identical as previous one or HttpStatus.NOT_MODIFIED if other error occurred
     */
    @PostMapping("changePassword")
    public ResponseEntity<?> changePassword(@RequestBody List<String> passwords, @AuthenticationPrincipal UserLogInPrincipal user)
    {
        return userService.changePassword(passwords, user);
    }

    /**
     * Checks if user is signed in, if not this method will not be accessible and ERROR 401 will be returned*
     * @return HttpStatus.OK
     */
    @PostMapping("/isSignIn")
    public ResponseEntity isSignIn()
    {
        return new ResponseEntity(HttpStatus.OK);
    }

    /**
     * Returns list of all users in database, accessible only for admins
     * @return ResponseEntity with list of all users
     */
    @GetMapping("getAllUsers")
    public ResponseEntity<?> getAllUsers()
    {
        return new ResponseEntity<>(userService.getAllUsers(), HttpStatus.OK);
    }
}

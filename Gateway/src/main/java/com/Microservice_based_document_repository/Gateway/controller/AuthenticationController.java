package com.Microservice_based_document_repository.Gateway.controller;
import com.Microservice_based_document_repository.Gateway.model.User;
import com.Microservice_based_document_repository.Gateway.service.AuthenticationService;
import com.Microservice_based_document_repository.Gateway.service.UserService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

/**
 * API Controller responsible for logIn and registration
 */
@RestController
@RequestMapping("api/authentication")
@CrossOrigin(origins = "http://localhost:4200")
public class AuthenticationController {

    private final AuthenticationService authenticationService;
    private final UserService userService;

    public AuthenticationController(AuthenticationService authenticationService, UserService userService) {
        this.authenticationService = authenticationService;
        this.userService = userService;
    }

    /**
     * Receives user register data, checks if it is correct and returns result
     * @param user model with register data given by user
     * @return ResponseEntity with registered and logged user model attached witch includes JWT token
     */
    @PostMapping("/singUp")
    public ResponseEntity singUp(@RequestBody User user) {
        if (userService.findUserByName(user.getName()).isPresent()) {
            return new ResponseEntity(HttpStatus.CONFLICT);
        }

        return new ResponseEntity(userService.saveUser(user), HttpStatus.CREATED);
    }

    /**
     * Receives user login data, checks if it is correct and returns result
     * @param user model with login data given by user
     * @return ResponseEntity with logged user model attached witch includes JWT token
     */
    @PostMapping("/singIn")
    public ResponseEntity singIn(@RequestBody User user) {
        return new ResponseEntity(authenticationService.singInAndGenerateToken(user), HttpStatus.OK);
    }


    /**
     * Logs user out
     * @return HttpStatus.OK if user was logged out successfully
     */
    @GetMapping("/singOut")
    public ResponseEntity singOut() {
        return new ResponseEntity(HttpStatus.OK);
    }
}

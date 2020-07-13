package com.ucareer.builder.user.web;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.ucareer.builder.core.CoreResponseBody;
import com.ucareer.builder.landing.repos.GalleryRepository;
import com.ucareer.builder.landing.repos.HeadRepository;
import com.ucareer.builder.mail.MailService;
import com.ucareer.builder.user.User;
import com.ucareer.builder.user.UserRepository;
import com.ucareer.builder.user.UserService;
import com.ucareer.builder.user.enums.UserStatus;
import com.ucareer.builder.user.requests.ResetPasswordRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api")
public class UserController {

    @Autowired
    UserService userService;
    @Autowired
    MailService mailService;

    @Autowired
    UserRepository userRepository;

//    @Autowired
//    BuilderRepository builderRepository;

    @Autowired
    GalleryRepository galleryRepository;

    @Autowired
    HeadRepository headRepository;

    //localhost:3000/api/hello
    @GetMapping("/hello")
    @CrossOrigin("http://localhost:3000")
    public ResponseEntity<String> hello() {
        return ResponseEntity.ok("hello world");
    }

    //localhost:3000/api/register
    @PostMapping("/register")
    @CrossOrigin(origins = "http://localhost:3000")
    public ResponseEntity<CoreResponseBody> register(@RequestBody User user) {
        //call user service to insert user
        User savedUser = userService.register(user);
        CoreResponseBody res;
        if (savedUser == null) {
            res = new CoreResponseBody(savedUser, "User already exist.", new Exception("User Already Exist."));
        } else {
            res = new CoreResponseBody(savedUser, "register succeed with state of active", null);
//            try {
//                String token = userService.createToken(savedUser);
//                String body = String.format(
//                        "please click following link to confirm your username. <a href=\"%s/api/user/confirm/%s\">Email Confirmed</a>",
//                        "http://localhost:3000", token);
//                this.mailService.sendSimpleMessage(savedUser.getUsername(), "Please confirm your email!", body);
//            } catch (MailException e) {
//                res = new CoreResponseBody(null, "email send failed", e);
//                return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(res);
//            }
//            res = new CoreResponseBody(savedUser, "register succeed with state of inactive", null);
        }
        return ResponseEntity.ok(res);
    }

    @GetMapping("/user/confirm/{token}")
    @CrossOrigin(origins = "http://localhost:3000")
    public ResponseEntity<CoreResponseBody> confirmMail(@PathVariable String token) {
        // validate token to see if it matches with a user who's currently inactive
        try {
            User user = this.userService.getUserByToken(token);

            // after validate succeed change user active state and stores in db (call service)
            if (user != null) {
                if (this.userService.setUserActive(user)) {
                    CoreResponseBody res = new CoreResponseBody(user, "User set active.", null);
                    return ResponseEntity.ok(res);
                }
            }
            return ResponseEntity.ok(new CoreResponseBody(null, "Token invalid.", new Exception("Invalid Token")));
        } catch (Exception e) {
            return ResponseEntity.ok(new CoreResponseBody(null, "Token invalid.", e));

        }

    }

    //write login api, return token
    @PostMapping("/login")
    //give permission for port 4200 to access this port
    @CrossOrigin(origins = "http://localhost:3000")
    public ResponseEntity<CoreResponseBody> login(@RequestBody User user) {
        CoreResponseBody res;

        // ensure that user is active, if not return false
        if (user.getStatus() == UserStatus.Inactive) {
            res = new CoreResponseBody(null, "User activation needs", new Exception("Activation needed"));
            return ResponseEntity.ok(res);
        }

        //get token use method in service
        String loginToken = userService.login(user);
        System.out.println(loginToken);
        if (loginToken == null) {
            res = new CoreResponseBody(null, "Username or password does not match with the record.", null);
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(res);
        } else {
            res = new CoreResponseBody(loginToken, "get user msg", null);
        }
        return ResponseEntity.ok(res);
    }

    // go to profile page
    @GetMapping("/me")
    @CrossOrigin(origins = "http://localhost:3000")
    public ResponseEntity<CoreResponseBody> me(@RequestHeader("Authorization") String authHeader) {
        String token = this.getJwtTokenFromHeader(authHeader);
        CoreResponseBody res;
        if (token == "") {
            res = new CoreResponseBody(null, "no token", new Exception("no token"));
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(res);
        }

        User user = userService.getUserByToken(token);

        if (user == null) {
            res = new CoreResponseBody(null, "invalid token", new Exception("invalid token"));
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(res);
        }
        res = new CoreResponseBody(user, "get user by username", null);
        return ResponseEntity.status(HttpStatus.OK).body(res);
    }

    // update user profile
    @PostMapping("/me")
    @CrossOrigin(origins = "http://localhost:3000")

    public ResponseEntity<CoreResponseBody> me(
            @RequestHeader("Authorization") String authHeader,
            @RequestBody User user
    ) {
        String token = this.getJwtTokenFromHeader(authHeader);
        CoreResponseBody res;
        if (token == "") {
            res = new CoreResponseBody(null, "no token", new Exception("no token"));
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(res);
        }

        User currUser = userService.getUserByToken(token);
        if (currUser == null) {
            res = new CoreResponseBody(null, "invalid token", new Exception("invalid token"));
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(res);
        }

        User savedUser = userService.updateUser(currUser, user);
        if (savedUser != null) {
            res = new CoreResponseBody(savedUser, "user profile updated", null);
            return ResponseEntity.status(HttpStatus.OK).body(res);
        }

        res = new CoreResponseBody(null, "invalid user information", new Exception("invalid user information"));
        return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(res);
    }

    @PostMapping("/passchange")
    @CrossOrigin(origins = "http://localhost:3000")
    public ResponseEntity<CoreResponseBody> passchange(
            @RequestHeader("Authorization") String authHeader,
            @RequestBody ResetPasswordRequest password) {
        String token = this.getJwtTokenFromHeader(authHeader);
        CoreResponseBody res = new CoreResponseBody(null, "invalid token", new Exception("invalid token"));
        if (token == "") {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(res);
        }
        User user = userService.getUserByToken(token);

        if (user == null) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(res);
        }

        if (password == null) {
            res = new CoreResponseBody(null, "Empty password", new Exception("No pasword given."));
            return ResponseEntity.status(HttpStatus.OK).body(res);
        }

        User savedUser = userService.updateUserPassword(user, password.getOldPassword(), password.getPassword());
        if (savedUser != null) {
            res = new CoreResponseBody(savedUser, "Password changed", null);
        } else {
            res = new CoreResponseBody(null, "Current password is invalid, password change failed.", new Exception("Current password is invalid, password change failed."));
        }
        return ResponseEntity.status(HttpStatus.OK).body(res);
    }

    private String getJwtTokenFromHeader(String authHeader) {
        authHeader = authHeader.replace("Bearer ", "").trim();
        authHeader = authHeader.replace("\"", "").trim();
        return authHeader.replace("Bear ", "").trim();
    }


}

package com.ucareer.builder.user.web;

import com.ucareer.builder.core.CoreResponseBody;
import com.ucareer.builder.mail.MailService;
import com.ucareer.builder.user.User;
import com.ucareer.builder.user.UserService;
import com.ucareer.builder.user.enums.UserStatus;
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
    //localhsot:8080/api/hello
    @GetMapping("/hello")
    @CrossOrigin("http://localhost:4200")
    public ResponseEntity<String> hello() {
        return ResponseEntity.ok("hello world");
    }

    //localhsot:8080/api/register
    @PostMapping("/register")
    @CrossOrigin(origins = "http://localhost:4200")
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
//                        "http://localhost:8080", token);
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
    @CrossOrigin(origins = "http://localhsot:4200")
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
    //give peremission for port 4200 to access this port
    @CrossOrigin(origins = "http://localhost:4200")
    public ResponseEntity<CoreResponseBody> login(@RequestBody User user) {
        CoreResponseBody res;

        // ensure that user is active, if not return false
        if (user.getStatus() == UserStatus.Inactive) {
            res = new CoreResponseBody(null, "User activation needs", new Exception("Activation needed"));
            return ResponseEntity.ok(res);
        }

        //get token use method in service
        String loginToken = userService.login(user);
        if (loginToken == null) {
            res = new CoreResponseBody(null, "Username or password does not match with the record.", null);
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(res);
        } else {
            res = new CoreResponseBody(loginToken, "get user msg", null);
        }
        return ResponseEntity.ok(res);
    }


    private String getJwtTokenFromHeader(String authHeader) {
        return authHeader.replace("Bearer ", "").trim();
    }


}

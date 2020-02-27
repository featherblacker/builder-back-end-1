package com.ucareer.builder.user.web;

import com.ucareer.builder.core.CoreResponseBody;
import com.ucareer.builder.user.User;
import com.ucareer.builder.user.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api")
public class UserController {

    @Autowired
    UserService userService;

    @GetMapping("/hello")
    @CrossOrigin("http://localhost:4200")
    public ResponseEntity<String> hello() {
        return ResponseEntity.ok("hello world");
    }

    //localhsot:8080/api/register
    @PostMapping("/register")
    @CrossOrigin(origins = "http://localhost:4200")
    public ResponseEntity<CoreResponseBody> register(@RequestBody User user) {
        User savedUser = userService.register(user);
        CoreResponseBody res;
        if (savedUser == null) {
            res = new CoreResponseBody(savedUser, "already exist", new Exception("already exist"));
        } else {
            res = new CoreResponseBody(savedUser, "created success", null);
        }
        return ResponseEntity.ok(res);
    }

    @GetMapping("/user/confirm/{token}")
    @CrossOrigin(origins = "http://localhsot:4200")
    public ResponseEntity<CoreResponseBody> confirmMail(@PathVariable String token) {

        return ResponseEntity.ok(null);
    }

    @PostMapping("/login")
    @CrossOrigin(origins = "http://localhost:4200")
    public ResponseEntity<CoreResponseBody> login(@RequestBody User user) {
        String loginStatus = userService.login(user);
        CoreResponseBody token;
        if (loginStatus != null) {
            token = new CoreResponseBody(loginStatus, "login success", null);
        } else {
            token = new CoreResponseBody(loginStatus, "login failed", new Exception("username or password is wrong!"));
        }
        return ResponseEntity.ok(token);
    }


    private String getJwtTokenFromHeader(String authHeader) {
        return authHeader.replace("Bearer ", "").trim();
    }


}

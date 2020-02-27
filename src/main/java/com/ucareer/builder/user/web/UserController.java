package com.ucareer.builder.user.web;

import com.ucareer.builder.core.CoreResponseBody;
import com.ucareer.builder.user.User;
import com.ucareer.builder.user.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api")
public class UserController {


    @Autowired
    UserService userService;

    @GetMapping("/hello")
//    @CrossOrigin("http://localhost:4200")
    public ResponseEntity<String> hello() {
        int number = 9;
        return ResponseEntity.ok("hello world");
    }


    @PostMapping("/login")
    @CrossOrigin(origins = "http://localhost:4200")
    public ResponseEntity<CoreResponseBody> login(@RequestBody User user) {
        if (user.getUsername() == null || user.getPassword() == null) {
            CoreResponseBody body = new CoreResponseBody<User>(null, "", new Exception("missing params"));
            return ResponseEntity.ok(body);
        }
        if (user.getUsername() != null && user.getUsername().isEmpty()) {
            CoreResponseBody body = new CoreResponseBody<User>(null, "", new Exception("missing username"));
            return ResponseEntity.ok(body);
        }
        if (user.getPassword() != null && user.getPassword().isEmpty()) {
            CoreResponseBody body = new CoreResponseBody<User>(null, "", new Exception("missing password"));
            return ResponseEntity.ok(body);
        }

        String token = this.userService.login(user);
        if (token == null) {
            CoreResponseBody body = new CoreResponseBody<User>(null, "", new Exception("user/password not exist"));
            return ResponseEntity.ok(body);
        } else {
            CoreResponseBody body = new CoreResponseBody<String>(token, "", null);
            return ResponseEntity.ok(body);
        }

    }


    @PostMapping("/forgot")
    @CrossOrigin(origins = "http://localhost:4200")
    public ResponseEntity<CoreResponseBody> forgot(@RequestBody User user) {
        if (user.getUsername() == null) {
            CoreResponseBody body = new CoreResponseBody<User>(null, "", new Exception("missing params"));
            return ResponseEntity.ok(body);
        }
        if (user.getUsername() != null && user.getUsername().isEmpty()) {
            CoreResponseBody body = new CoreResponseBody<User>(null, "", new Exception("missing username"));
            return ResponseEntity.ok(body);
        }
        String token = this.userService.forgot(user);
        if (token == null) {
            CoreResponseBody body = new CoreResponseBody<User>(null, "", new Exception("user not exist"));
            return ResponseEntity.ok(body);
        } else {
            CoreResponseBody body = new CoreResponseBody<String>(token, "", null);
            return ResponseEntity.ok(body);
        }

    }


    @GetMapping("/users/me")
    @CrossOrigin(origins = "http://localhost:4200")
    public ResponseEntity<CoreResponseBody> getUserProfile(@RequestHeader("Authorization") String token) {
        User foundUser = this.userService.getMyProfile(token);
        CoreResponseBody body = new CoreResponseBody<User>(foundUser, "", null);
        return ResponseEntity.ok(body);

    }


    @PostMapping("/users/me")
    @CrossOrigin(origins = "http://localhost:4200")
    public ResponseEntity<CoreResponseBody> UpdateUserProfile(@RequestHeader("Authorization") String token, @RequestBody User user) {
        User foundUser = this.userService.getMyProfile(token);
        if (foundUser != null) {
            User newUser = this.userService.updateProfile(foundUser, user);
            CoreResponseBody body = new CoreResponseBody<User>(newUser, "", null);
            return ResponseEntity.ok(body);
        } else {
            CoreResponseBody body = new CoreResponseBody<User>(null, "", new Exception("user not found"));
            return ResponseEntity.ok(body);
        }
    }

    @PostMapping("/reset/{token}")
    @CrossOrigin(origins = "http://localhost:4200")
    public ResponseEntity<CoreResponseBody> reset(@PathVariable String token, @RequestBody User user) {
        if (user.getPassword() == null) {
            CoreResponseBody body = new CoreResponseBody<User>(null, "", new Exception("missing params"));
            return ResponseEntity.ok(body);
        }
        if (user.getPassword() != null && user.getPassword().isEmpty()) {
            CoreResponseBody body = new CoreResponseBody<User>(null, "", new Exception("missing password"));
            return ResponseEntity.ok(body);
        }
        if (userService.resetPassword(token, user.getPassword())) {
            CoreResponseBody body = new CoreResponseBody<String>(null, "reset successfully", null);
            return ResponseEntity.ok(body);
        } else {
            CoreResponseBody body = new CoreResponseBody<String>(token, "", new Exception("not exist"));
            return ResponseEntity.ok(body);
        }

    }

    //localhsot:8080/api/register
    @PostMapping("/register")
    @CrossOrigin(origins = "http://localhost:4200")
    public ResponseEntity<CoreResponseBody> register(@RequestBody User user) {
        if (user.getUsername() == null || user.getPassword() == null) {
            CoreResponseBody body = new CoreResponseBody<User>(null, "", new Exception("missing params"));
            return ResponseEntity.ok(body);
        }
        if (user.getUsername() != null && user.getUsername().isEmpty()) {
            CoreResponseBody body = new CoreResponseBody<User>(null, "", new Exception("missing username"));
            return ResponseEntity.ok(body);
        }
        if (user.getPassword() != null && user.getPassword().isEmpty()) {
            CoreResponseBody body = new CoreResponseBody<User>(null, "", new Exception("missing password"));
            return ResponseEntity.ok(body);
        }
        User savedUser = userService.register(user);
        if (savedUser == null) {
            CoreResponseBody body = new CoreResponseBody<User>(null, "already exist.", null);
            return ResponseEntity.ok(body);
        } else {
            CoreResponseBody body = new CoreResponseBody<User>(savedUser, "", null);
            return ResponseEntity.ok(body);
        }
    }

    @GetMapping("/user/confirm/{token}")
    @CrossOrigin(origins = "http://localhsot:4200")
    public ResponseEntity<CoreResponseBody> confirmMail(@PathVariable String token) {

        return ResponseEntity.ok(null);
    }


    private String getJwtTokenFromHeader(String authHeader) {
        return authHeader.replace("Bearer ", "").trim();
    }


}

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


    //localhsot:8080/api/register
    @PostMapping("/register")
    @CrossOrigin(origins = "http://localhost:4200")
    public ResponseEntity<CoreResponseBody> register(@RequestBody User user) {
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



    @PostMapping("/login")
    @CrossOrigin(origins = "http://localhost:4200")
    public ResponseEntity<CoreResponseBody> login(@RequestBody User user)
    {
        return ResponseEntity.ok(null);
    }


    private String getJwtTokenFromHeader(String authHeader) {
        return authHeader.replace("Bearer ", "").trim();
    }


}

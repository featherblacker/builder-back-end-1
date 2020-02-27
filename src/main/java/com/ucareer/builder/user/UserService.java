package com.ucareer.builder.user;

import com.ucareer.builder.user.enums.UserStatus;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import javax.annotation.PostConstruct;
import java.util.Base64;
import java.util.Date;
import java.util.UUID;
import java.util.concurrent.TimeUnit;

@Service("UserService")
public class UserService {

    //    auto new
    @Autowired
    UserRepository repository;

    @Value("${ucareer.jwt.expire-in-hour}")
    private Long expireHours;

    @Value("${ucareer.jwt.token}")
    private String plainSecret;

    private String encodedSecret;
    private PasswordEncoder encoder;

    @PostConstruct
    protected void init() {
        this.encodedSecret = generateEncodedSecret(this.plainSecret);
        encoder = new BCryptPasswordEncoder();
    }

    private String generateEncodedSecret(String plainSecret) {
        if (StringUtils.isEmpty(plainSecret)) {
            throw new IllegalArgumentException("JWT secret cannot be null or empty.");
        }
        return Base64
                .getEncoder()
                .encodeToString(this.plainSecret.getBytes());
    }

    public User register(User user) {
        // avoid user id duplicate
        User foundUser = repository.findByUsername(user.getUsername()).orElse(null);

        if (foundUser == null) {
            User newUser = new User();
            newUser.setUsername(user.getUsername());
            newUser.setPassword(encoder.encode(user.getPassword()));
            newUser.setStatus(UserStatus.Active);

            User savedUser = repository.save(newUser);
            return savedUser;
        } else {
            return null;
        }
    }

    public String login(User user) {

        User loginUser = (User) repository.findByUsername(user.getUsername()).orElse(null);

        if (loginUser != null) {
            return encoder.matches(user.getPassword(), loginUser.getPassword()) ?
                    createToken(loginUser) : null;
        } else {
            return null;
        }
    }

    public boolean setUserActive(User user) {
        User loginUser = (User) repository.findByUsername(user.getUsername()).orElse(null);

        loginUser.setStatus(UserStatus.Active);
        repository.save(loginUser);
        return true;

    }

    public String createToken(User user) {
        Date now = new Date();
        Long expireInMilis = TimeUnit.HOURS.toMillis(expireHours);
        Date expiredAt = new Date(expireInMilis + now.getTime());

        return Jwts.builder()
                .setId(UUID.randomUUID().toString())
                .setSubject(user.getUsername())
                .setIssuedAt(now)
                .setExpiration(expiredAt)
                .signWith(SignatureAlgorithm.HS512, encodedSecret)
                .compact();
    }

    public User getUserByToken(String token) {
        try {
            Claims claims = Jwts.parser()
                    .setSigningKey(encodedSecret)
                    .parseClaimsJws(token)
                    .getBody();

            String username = claims.getSubject();
            User user = repository.findByUsername(username).orElse(null);
            if (user != null) return user;

        } catch (Exception e) {
            return null;
        }
        return null;
    }

    public User updateUser(User userInDb, User newUser) {


        User savedUser = repository.save(userInDb);

        return savedUser;
    }

    public User updateUserPassword(User userInDb, String oldPass, String newPass) {
        if (userInDb == null) {
            System.out.println("user null");
            return null;
        }
        if (oldPass == null) {
            System.out.println("old pass null");
            return null;
        }
        if (newPass == null) {
            System.out.println("new pass null");
            return null;
        }

        if (!encoder.matches(oldPass, userInDb.getPassword())) {
            System.out.println(userInDb.getUsername());
            System.out.println("unmatch password checked from service");
            return null;
        }

        User savedUser;
        userInDb.setPassword(encoder.encode(newPass));
        savedUser = repository.save(userInDb);

        return savedUser;
    }
}

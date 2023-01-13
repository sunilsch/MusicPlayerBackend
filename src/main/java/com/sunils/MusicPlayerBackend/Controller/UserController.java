package com.sunils.MusicPlayerBackend.Controller;

import com.sunils.MusicPlayerBackend.Entities.UserEntity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.provisioning.JdbcUserDetailsManager;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import javax.sql.DataSource;

@RequestMapping(path="/users")
@RestController
public class UserController {

    @Autowired
    private DataSource dataSource;

    @PostMapping(path="/newUser", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public UserDetails newUser(@RequestBody UserEntity body) {
        String password = new BCryptPasswordEncoder().encode(body.getPassword());
        if(password.length() < 8){
            throw new ResponseStatusException(HttpStatus.NOT_ACCEPTABLE,"Password must have a length of minimum 8");
        }
        UserDetails user = User.builder()
                .username(body.getUsername())
                .password(password)
                .roles(body.getRole())
                .build();
        JdbcUserDetailsManager jdbcUserDetailsManager = new JdbcUserDetailsManager(dataSource);
        jdbcUserDetailsManager.createUser(user);
        return user;
    }

    @GetMapping(path="/user")
    public UserDetails getUser(@RequestParam String username){
        return new JdbcUserDetailsManager(dataSource).loadUserByUsername(username);
    }
}

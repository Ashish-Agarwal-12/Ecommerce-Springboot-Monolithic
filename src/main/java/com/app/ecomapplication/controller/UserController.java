package com.app.ecomapplication.controller;

import com.app.ecomapplication.model.User;
import com.app.ecomapplication.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/users")
public class UserController {

    private final UserService userService;

    @GetMapping
    public ResponseEntity<List<User>> getAllUsers() {
        return new ResponseEntity<>(userService.fetchAllUsers(), HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity<User> getUserById(@PathVariable Long id) {

        return userService.getUserById(id)
                .map(ResponseEntity::ok).
                orElse(ResponseEntity.notFound().build());
    }

    @PostMapping("/createUser")
    public ResponseEntity<String> CreateUser(@RequestBody User user) {
        userService.createUser(user);
        return new ResponseEntity<>("User Added Successfully", HttpStatus.OK);
    }

    @PutMapping("/{id}")
    public ResponseEntity<String> UpdateUser(@PathVariable Long id, @RequestBody User user) {
        boolean updatedStatus = userService.updateUser(id, user);
        if(updatedStatus) {
            return ResponseEntity.ok("User has been updated Successfully");
        }
        else
            return ResponseEntity.notFound().build();
    }
}

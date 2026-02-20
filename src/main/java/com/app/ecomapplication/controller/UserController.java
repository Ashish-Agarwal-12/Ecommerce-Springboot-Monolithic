package com.app.ecomapplication.controller;

import com.app.ecomapplication.dto.UserRequest;
import com.app.ecomapplication.dto.UserResponse;
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
    public ResponseEntity<List<UserResponse>> getAllUsers() {
        return new ResponseEntity<>(userService.fetchAllUsers(), HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity<UserResponse> getUserById(@PathVariable Long id) {
        return userService.getUserById(id)
                .map(ResponseEntity::ok).
                orElse(ResponseEntity.notFound().build());
    }

    @PostMapping("/createUser")
    public ResponseEntity<String> CreateUser(@RequestBody UserRequest userRequest) {
        userService.createUser(userRequest);
        return new ResponseEntity<>("User Added Successfully", HttpStatus.OK);
    }

    @PutMapping("/{id}")
    public ResponseEntity<String> UpdateUser(@PathVariable Long id, @RequestBody UserRequest userRequest) {
        boolean updatedStatus = userService.updateUser(id, userRequest);
        if(updatedStatus) {
            return ResponseEntity.ok("User has been updated Successfully");
        }
        else
            return ResponseEntity.notFound().build();
    }
}

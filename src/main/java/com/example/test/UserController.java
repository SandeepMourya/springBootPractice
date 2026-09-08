package com.example.test;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@RestController
public class UserController {


    private final UserService userService ;

    public UserController(UserService userService) {
        this.userService = userService;
    }


    @GetMapping("/api/getAllUser")
    public ResponseEntity<List<User>> getAllUser(){
        return ResponseEntity.ok(userService.getAllUser());
        //return new ResponseEntity<>(userService.getAllUser(), HttpStatus.OK);
    }

    //@RequestMapping(value = "/api/createUser", method = RequestMethod.POST)
    @PostMapping("/api/createUser")
    public String createUser(@RequestBody User user){
        userService.addUser(user);
        return "User Added Sucess";
    }
}

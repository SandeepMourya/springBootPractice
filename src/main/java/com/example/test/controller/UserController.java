package com.example.test.controller;

import com.example.test.dto.UserRequestDTO;
import com.example.test.dto.UserResponseDTO;
import com.example.test.service.UserService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("api")
public class UserController {


    private final UserService userService ;

    public UserController(UserService userService) {
        this.userService = userService;
    }


    @GetMapping("/getAllUser")
    public ResponseEntity<List<UserResponseDTO>> getAllUser(){
        return ResponseEntity.ok(userService.getAllUser());
        //return new ResponseEntity<>(userService.getAllUser(), HttpStatus.OK);
    }
    @GetMapping("/getUser")
    public ResponseEntity<UserResponseDTO> getUser(@RequestParam Long id){
        return ResponseEntity.ok(userService.getUser(id));
        //return new ResponseEntity<>(userService.getAllUser(), HttpStatus.OK);
    }

    //@RequestMapping(value = "/api/createUser", method = RequestMethod.POST)
    @PostMapping("/createUser")
    public ResponseEntity<String> createUser(@RequestBody UserRequestDTO user){
        UserResponseDTO u = userService.addUser(user);
        return ResponseEntity.ok("User Added Sucess with id:" + u.getId());
    }

    @PutMapping("/updateUser")
    public ResponseEntity<Boolean> updateUser(@RequestBody UserRequestDTO userNew, @RequestParam Long id){
        Boolean result = userService.updateUser(userNew, id);
        if(result)return ResponseEntity.ok(true);
        return ResponseEntity.notFound().build();
    }


}

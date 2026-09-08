package com.example.test;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;


@Service
@RequiredArgsConstructor//this thing is genrating my constructor innjection for repository @RequiredArgsConstructor + final field = constructor injection without manually writing the constructor.
public class UserService {
    //private List<User> userList = new ArrayList<>();
    private final UserRepository userRepo;

    public List<User> getAllUser(){
        return userRepo.findAll();
    }

    public void addUser(User user){
        userRepo.save(user);

    }
}

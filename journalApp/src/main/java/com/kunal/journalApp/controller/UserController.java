package com.kunal.journalApp.controller;

import com.kunal.journalApp.entity.User;
import com.kunal.journalApp.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;


@RestController
@RequestMapping("/user")
public class UserController {
    @Autowired
    private UserService userService;
    @GetMapping
    public ResponseEntity<List<User>> getAll(){
        List<User> all = userService.getAll();
        if(all!=null){
            return new ResponseEntity<>(all, HttpStatus.OK);
        }
        return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }
    @PostMapping
    public ResponseEntity<?> createUser(@RequestBody User user){
        userService.saveEntry(user);
        return new ResponseEntity<>(HttpStatus.CREATED);
    }
    @PutMapping("/{username}")
    public ResponseEntity<?> updateUser(@RequestBody User user, @PathVariable String username){
        User userInDb = userService.findByUserName(username);
        if(userInDb!=null){
            userInDb.setUserName(user.getUserName());
            userInDb.setPassword(user.getPassword());
            userService.saveEntry(userInDb);
            return new ResponseEntity<>(userInDb, HttpStatus.CREATED);
        }
        return new ResponseEntity<>(HttpStatus.NOT_FOUND);

    }
}

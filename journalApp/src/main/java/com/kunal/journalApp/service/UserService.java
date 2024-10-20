package com.kunal.journalApp.service;

import com.kunal.journalApp.entity.User;
import com.kunal.journalApp.repository.UserRepository;
import lombok.extern.slf4j.Slf4j;
import org.bson.types.ObjectId;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Optional;

@Slf4j
@Component
public class UserService {
    @Autowired
    private UserRepository userRepository;

    public void saveEntry(User user) {
        try {
            userRepository.save(user);
        } catch (Exception e) {
            log.error("Exception ", e);
        }
    }

    public List<User> getAll() {
        return userRepository.findAll();
    }

    public Optional<User> findById(ObjectId id) {
        return userRepository.findById(id);
    }
    public User findByUserName(String username) {
        return userRepository.findByUserName(username);
    }

    public void deleteById(ObjectId id) {
        userRepository.deleteById(id);
    }
}

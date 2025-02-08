package org.example.adapter.controller;

import org.example.adapter.UserRepository;
import org.example.entity.UserEntity;
import org.example.services.PasswordBcrypt;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/users")
public class UserController {

    @Autowired
    private UserRepository repository;
    private PasswordBcrypt passwordHash;

    public UserController(UserRepository repository, PasswordBcrypt passwordHash){
        this.repository = repository;
        this.passwordHash = passwordHash;
    }

    @GetMapping("/listall")
    public ResponseEntity<List<UserEntity>> llistAll(){
        return ResponseEntity.ok(repository.findAll());
    }

    @PostMapping("/save")
    public ResponseEntity<UserEntity> save(@RequestBody UserEntity userEntity, String id) throws IllegalAccessException {

        String passwordCrypt = this.passwordHash.hashing(userEntity.getPassword());
        UserEntity user = new UserEntity(userEntity.getLogin(), passwordCrypt);
        return ResponseEntity.ok(repository.save(user));
    }

    @GetMapping("/validation")
    public ResponseEntity<Boolean> passwordValidation(@RequestParam String login, @RequestParam String password) {

        Optional<UserEntity> optionalUser = repository.findByLogin(login);
        if (optionalUser.isEmpty()) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(false);
        }

        UserEntity user = optionalUser.get();
        boolean valid = this.passwordHash.matches(password, user.getPassword());

        HttpStatus status;
        if (valid) {
            status = HttpStatus.OK;
        } else{
            status = HttpStatus.UNAUTHORIZED;
        }

        return ResponseEntity.status(status).body(valid);
    }

}

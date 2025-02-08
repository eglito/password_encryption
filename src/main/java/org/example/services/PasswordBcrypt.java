package org.example.services;

import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class PasswordBcrypt {

    private  ValidPassword validPassword;
    private final PasswordEncoder encoder;

    public PasswordBcrypt(ValidPassword validPassword, PasswordEncoder encoder){
        this.validPassword = validPassword;
        this.encoder = encoder;
    }

    public String hashing(String password) throws IllegalAccessException {

        if(this.validPassword.validPassoword(password)){
            return encoder.encode(password);
        }

        return null;
    }

    public Boolean matches(String password, String userPassword){
        return encoder.matches(password, userPassword);
    }
}


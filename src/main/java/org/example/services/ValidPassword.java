package org.example.services;

import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Service;

import java.util.regex.Pattern;

@Service
public class ValidPassword {

    private String password;

    public ValidPassword(){
        this.password = password;
    }

    public Boolean validPassoword(String password) throws IllegalAccessException {

        this.password = password;

        String regex = "^(?=.*\\d).{8,}$";
        if(Pattern.matches(regex, this.password)){
            return true;
        } throw new IllegalAccessException("A senha deve ser de no mínimo 8 caracteres");
    }
}










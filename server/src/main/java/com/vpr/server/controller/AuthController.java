package com.vpr.server.controller;

import com.vpr.server.data.User;
import com.vpr.server.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

public class AuthController {

    public User getAuthUserFromHeader(String authorizationHeader, UserRepository userRepository){
        String[] splitAuthHeader = authorizationHeader.split("\\s");
        if(splitAuthHeader.length == 2){
            return userRepository.findByToken(splitAuthHeader[1]);
        }
        return null;
    }
}

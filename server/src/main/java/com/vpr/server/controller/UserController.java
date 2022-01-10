package com.vpr.server.controller;

import com.vpr.server.data.User;
import com.vpr.server.repository.UserRepository;
import com.vpr.server.security.Hasher;
import com.vpr.server.security.Token;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.security.NoSuchAlgorithmException;
import java.security.spec.InvalidKeySpecException;
import java.util.Arrays;

@Controller
@RequestMapping(path = "/user")
public class UserController {
    @Autowired
    private UserRepository userRepository;

    /******************
     * POST-ENDPOINTS *
     ******************/

    @PostMapping(path = "/add")
    public @ResponseBody
    String addNewUser(
            @RequestParam String name,
            @RequestParam String forename,
            @RequestParam String login,
            @RequestParam String password,
            @RequestParam String isAdmin
    ) {
        byte[] salt = Hasher.GenerateSalt();
        byte[] hash;
        try {
            hash = Hasher.HashPassword(password, salt);
        } catch (NoSuchAlgorithmException | InvalidKeySpecException e) {
            e.printStackTrace();
            throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, "Fehler beim hashen");
        }

        User user = new User();

        // TODO set correct token and password
        user.setName(name);
        user.setForename(forename);
        user.setLogin(login);
        user.setPassword(hash);
        user.setSalt(salt);
        user.setToken("test");
        user.setAdmin(isAdmin.equals("1"));

        userRepository.save(user);
        return "" + user.getId();
    }

    @PostMapping(path = "/login")
    public @ResponseBody
    String login(
            @RequestParam String login,
            @RequestParam String password
    ) {
        System.out.println("LOGIN");
        User user = userRepository.findByLogin(login);
        if (user == null) {
            throw new ResponseStatusException(HttpStatus.UNAUTHORIZED, "Falscher login");
        }

        byte[] salt = user.getSalt();
        byte[] hash;
        try {
            hash = Hasher.HashPassword(password, salt);
        } catch (NoSuchAlgorithmException | InvalidKeySpecException e) {
            e.printStackTrace();
            throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, "Fehler beim hashen");
        }

        if (Arrays.equals(user.getPassword(), hash)) {
            System.out.println(user.getLogin() + " is now logged in.");
            System.out.println(Token.Generate(user.getLogin()));
            System.out.println(Token.Verify(Token.Generate(user.getLogin()), user.getLogin()));
            return "" + user.getId();
        }
        System.out.println(user.getLogin() + " failed to logged in.");
        System.out.println("entered : " + javax.xml.bind.DatatypeConverter.printHexBinary(hash));
        System.out.println("required: " + javax.xml.bind.DatatypeConverter.printHexBinary(user.getPassword()));

        throw new ResponseStatusException(HttpStatus.UNAUTHORIZED, "Falscher login");
    }

    @PostMapping(path = "/del")
    public @ResponseBody String deleteUser(@RequestParam Integer userId) {
        userRepository.deleteById(Long.valueOf(userId));
        return "Deleted";
    }

    /*****************
     * GET-ENDPOINTS *
     *****************/

    @GetMapping(path = "/all")
    public @ResponseBody
    Object[] getAllUsers() {
        return userRepository.findAllUsernames();
    }
}

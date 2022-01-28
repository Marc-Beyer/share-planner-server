package com.vpr.server.controller;

import com.vpr.server.dao.interfaces.UserDAO;
import com.vpr.server.data.Event;
import com.vpr.server.data.User;
import com.vpr.server.json.JSONMapper;
import com.vpr.server.repository.UserRepository;
import com.vpr.server.security.Hasher;
import com.vpr.server.security.Token;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.security.NoSuchAlgorithmException;
import java.security.spec.InvalidKeySpecException;
import java.util.Arrays;
import java.util.List;

@Controller
@RequestMapping(path = "/user")
public class UserController {
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private UserDAO userDAO;

    private final AuthController authController;

    public UserController() {
        this.authController = new AuthController();
    }

    /******************
     * POST-ENDPOINTS *
     ******************/

    @PostMapping(path = "/add")
    public @ResponseBody
    ResponseEntity<String> addNewUser(
            @RequestHeader("Authorization") String authorizationHeader,
            @RequestParam String name,
            @RequestParam String forename,
            @RequestParam String login,
            @RequestParam String password,
            @RequestParam Boolean isAdmin
    ) {
        User authUser = authController.getAuthUserFromHeader(authorizationHeader, userRepository);
        if (authUser == null || !authUser.isAdmin()) {
            return new ResponseEntity<>("Du hast keine Rechte um einen User an zu legen", HttpStatus.UNAUTHORIZED);
        }

        if (userRepository.findByLogin(login) != null) {
            return new ResponseEntity<>("Login exestiert bereits", HttpStatus.BAD_REQUEST);
        }

        byte[] salt = Hasher.GenerateSalt();
        byte[] hash;
        try {
            hash = Hasher.HashPassword(password, salt);
        } catch (NoSuchAlgorithmException | InvalidKeySpecException e) {
            e.printStackTrace();
            return new ResponseEntity<>("Fehler beim hashen", HttpStatus.INTERNAL_SERVER_ERROR);
        }

        User user = new User();

        user.setName(name);
        user.setForename(forename);
        user.setLogin(login);
        user.setPassword(hash);
        user.setSalt(salt);
        user.setToken("");
        user.setAdmin(isAdmin);

        userRepository.save(user);
        return new ResponseEntity<>("" + user.getId(), HttpStatus.OK);
    }

    @PostMapping(path = "/login")
    public @ResponseBody
    ResponseEntity<String> login(
            @RequestParam String login,
            @RequestParam String password
    ) {
        System.out.println(login + " tries to login.");
        User user = userRepository.findByLogin(login);
        if (user == null) {
            System.out.println("Login for " + login + " failed.");
            return new ResponseEntity<>("Falscher login", HttpStatus.UNAUTHORIZED);
        }

        byte[] salt = user.getSalt();
        byte[] hash;
        try {
            hash = Hasher.HashPassword(password, salt);
        } catch (NoSuchAlgorithmException | InvalidKeySpecException e) {
            e.printStackTrace();
            return new ResponseEntity<>("Fehler beim hashen", HttpStatus.INTERNAL_SERVER_ERROR);
        }

        if (Arrays.equals(user.getPassword(), hash)) {
            String token = Token.Generate(user.getLogin());
            user.setToken(token);
            userRepository.save(user);

            System.out.println(user.getLogin() + " is now logged in.");
            System.out.println(Token.Verify(Token.Generate(user.getLogin()), user.getLogin()));

            return new ResponseEntity<>(token + " " + user.getId(), HttpStatus.OK);
        }
        System.out.println(user.getLogin() + " failed to logged in.");
        System.out.println("entered : " + javax.xml.bind.DatatypeConverter.printHexBinary(hash));
        System.out.println("required: " + javax.xml.bind.DatatypeConverter.printHexBinary(user.getPassword()));

        return new ResponseEntity<>("Falscher login", HttpStatus.UNAUTHORIZED);
    }

    @PostMapping(path = "/login-with-token")
    public @ResponseBody
    ResponseEntity<String> loginWithToken(
            @RequestHeader("Authorization") String authorizationHeader,
            @RequestParam long userId
    ) {
        User authUser = authController.getAuthUserFromHeader(authorizationHeader, userRepository);
        if (authUser == null || authUser.getId() != userId) {
            return new ResponseEntity<>("Falscher auth-token", HttpStatus.UNAUTHORIZED);
        }
        return new ResponseEntity<>("", HttpStatus.OK);
    }

    @PostMapping(path = "/del")
    public @ResponseBody
    ResponseEntity<String> deleteUser(
            @RequestHeader("Authorization") String authorizationHeader,
            @RequestParam long userId
    ) {
        User authUser = authController.getAuthUserFromHeader(authorizationHeader, userRepository);
        if (authUser == null || !authUser.isAdmin()) {
            return new ResponseEntity<>("Du hast keine Rechte um den User zu löschen", HttpStatus.UNAUTHORIZED);
        }
        User user = userRepository.findById(userId);
        if (user == null) {
            return new ResponseEntity<>("User nicht in der Datenbank vorhanden", HttpStatus.BAD_REQUEST);
        }
        userRepository.delete(user);
        return new ResponseEntity<>("", HttpStatus.OK);
    }

    @PostMapping(path = "/edit")
    public @ResponseBody ResponseEntity<String> editUser(
            @RequestHeader("Authorization") String authorizationHeader,
            @RequestParam long userId,
            @RequestParam String name,
            @RequestParam String forename,
            @RequestParam String login,
            @RequestParam String password,
            @RequestParam Boolean isAdmin
    ) {
        User authUser = authController.getAuthUserFromHeader(authorizationHeader, userRepository);
        if (authUser == null || (!authUser.isAdmin() && authUser.getId() != userId)) {
            return new ResponseEntity<>("Du hast keine Rechte um den User zu editieren", HttpStatus.UNAUTHORIZED);
        }
        User user = userRepository.findById(userId);
        if (user == null) {
            return new ResponseEntity<>("User nicht in der Datenbank vorhanden", HttpStatus.BAD_REQUEST);
        }

        User userWithLogin = userRepository.findByLogin(login);
        if (userWithLogin != null && userWithLogin.getId() != userId) {
            return new ResponseEntity<>("Login exestiert bereits", HttpStatus.BAD_REQUEST);
        }

        byte[] salt = Hasher.GenerateSalt();
        byte[] hash;
        try {
            hash = Hasher.HashPassword(password, salt);
        } catch (NoSuchAlgorithmException | InvalidKeySpecException e) {
            e.printStackTrace();
            return new ResponseEntity<>("Fehler beim hashen", HttpStatus.INTERNAL_SERVER_ERROR);
        }

        user.setName(name);
        user.setForename(forename);
        user.setLogin(login);
        user.setPassword(hash);
        user.setSalt(salt);
        user.setToken("");
        user.setAdmin(isAdmin);

        userRepository.save(user);
        return new ResponseEntity<>("", HttpStatus.OK);
    }

    @PostMapping(path = "/all")
    public @ResponseBody
    ResponseEntity<String> getAllUser() {
        List<User> userList = userDAO.getAllUser();

        return new ResponseEntity<>(JSONMapper.userListToJSON(userList), HttpStatus.OK);
    }
}

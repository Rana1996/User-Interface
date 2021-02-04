package io.userinterface.user;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
public class UserController {

    ObjectToResponse ifNull = (object, error, status) -> {
        if(object == null) {
            return new ResponseEntity<String>(error, status);
        }
        return new ResponseEntity<Object>(object, HttpStatus.OK);
    };

    @Autowired
    private UserService service;

    @RequestMapping("/users")
    public List<User> getAllUsers() {
        return service.getAllUsers() ;
    }

    @RequestMapping("/users/{id}")
    public ResponseEntity getById(@PathVariable long id) {
        return ifNull.fun(service.getById(id), "No User was found With this ID: " + id, HttpStatus.NOT_FOUND);
    }

    @RequestMapping("/users/age")
    public ResponseEntity getByAge(@RequestParam int min, @RequestParam int max) {
        return ifNull.fun(service.getByAge(min, max), "No user between the age of " + min + " and " + max + " was found", HttpStatus.NOT_FOUND);
    }

    @RequestMapping("/users/city")
    public ResponseEntity getUsersInCity(@RequestParam String city) {
        return ifNull.fun(service.getByCity(city), "No user was found in living in " + city, HttpStatus.NOT_FOUND);
    }

    @RequestMapping(method=RequestMethod.POST, value="/users")
    public ResponseEntity addUser(@RequestBody User user) {
        return ifNull.fun(service.addUser(user) ? 1 : null, "User with the same license already exists", HttpStatus.BAD_REQUEST);
    }

    @RequestMapping(method=RequestMethod.PUT, value="/users/{id}")
    public ResponseEntity updateUser(@PathVariable long id, @RequestBody User user) {
        return ifNull.fun(service.updateUser(id, user) ? 1 : null,"No User was found With this ID: " + id, HttpStatus.NOT_FOUND);
    }

    @RequestMapping(method=RequestMethod.DELETE, value="/users/{id}")
    public ResponseEntity delete(@PathVariable long id) {
        return ifNull.fun(service.deleteUser(id) ? 1 : null, "No User was found With this ID: " + id, HttpStatus.NOT_FOUND);
    }
}

interface ObjectToResponse {
    public ResponseEntity fun(Object o, String str, HttpStatus s);
}
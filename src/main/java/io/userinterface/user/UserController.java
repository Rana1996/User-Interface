package io.userinterface.user;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Stream;

@RestController
public class UserController {

    @Autowired
    private UserService service;

    @RequestMapping("/users")
    public List<User> getAllUsers() {
        return service.getAllUsers() ;
    }

    @RequestMapping("/users/{id}")
    public ResponseEntity getById(@PathVariable long id) {
        User user = service.getById(id);
        if(user == null) {
            return new ResponseEntity<String>("No User With this ID: " + id , HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<User> (user, HttpStatus.OK);
    }

    @RequestMapping("/users/age")
    public Stream<User> getByAge(@RequestParam int min, @RequestParam int max) {
        return service.getByAge(min, max);
    }

    @RequestMapping("/users/city")
    public Stream<User> getUsersInCity(@RequestParam String city) {
        return service.getByCity(city);
    }

    @RequestMapping(method=RequestMethod.POST, value="/users")
    public void addUser(@RequestBody User user) {
        service.addUser(user);
    }

    @RequestMapping(method=RequestMethod.PUT, value="/users/{id}")
    public void updateUser(@PathVariable long id, @RequestBody User user) {
        service.updateUser(id, user);
    }

    @RequestMapping(method=RequestMethod.DELETE, value="/users/{id}")
    public void delete(@PathVariable long id) {
        service.deleteUser(id);
    }

}
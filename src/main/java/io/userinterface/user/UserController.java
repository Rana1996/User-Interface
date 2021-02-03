package io.userinterface.user;

import org.springframework.beans.factory.annotation.Autowired;
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
    public User getById(@PathVariable long id) {
        return service.getById(id) ;
    }

    @RequestMapping("/users/age/{min}/{max}")
    public Stream<User> getByAge(@PathVariable int min, @PathVariable int max) {
        return service.getByAge(min, max);
    }

    @RequestMapping("/users/city/{city}")
    public Stream<User> getUsersInCity(@PathVariable String city) {
        return service.getByCity(city);
    }

    @RequestMapping(method=RequestMethod.POST, value="/users")
    public void addUser(@RequestBody User user) {
        service.addUser(user);
    }

    @RequestMapping(method=RequestMethod.PUT, value="/users/{id}")
    public void updateUser(@RequestBody User user) {
        service.updateUser(user);
    }

    @RequestMapping(method=RequestMethod.DELETE, value="/users/{id}")
    public void delete(@PathVariable long id) {
        service.deleteUser(id);
    }

}
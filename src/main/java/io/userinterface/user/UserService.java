package io.userinterface.user;

import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.stream.Stream;

@Service
public class UserService {

    private HashMap<Long, User> users = new HashMap<>();
    private HashMap<String, ArrayList<User>> city = new HashMap<>();
    private HashMap<String, ArrayList<User>> age = new HashMap<>();


    PrintMap printMap = stream -> {
        stream.forEach(u -> u.print());
    };

    public List<User> getAllUsers() {
        printAll();
        return new ArrayList<>(users.values());
    }

    public void addUser(User user) {
        System.out.println("In add2Map, User: ");
        user.print();

        Lambda add2Map = (map, item) -> {
            if(map.containsKey(item))
                map.get(item).add(user);
            else {
                ArrayList list = new ArrayList<User>();
                list.add(user);
                map.put(item, list);
            }
        };

        if(users.containsKey(user.getDriverLicence()))
            return;
        add2Map.fun(city, user.getLocation().replaceAll(" ", "-"));
        add2Map.fun(age, user.getAge());
        users.put(user.getDriverLicence(), user);

        System.out.println("...All...");
        printAll();
    }

    private void printAll() {
        System.out.println("!!! users: ");
        printMap.fun(users.values().stream());
        System.out.println("!!! city: ");
        printMap.fun(city.entrySet().stream().flatMap(entry -> entry.getValue().stream()));
        System.out.println("!!! age: ");
        printMap.fun(age.entrySet().stream().flatMap(entry -> entry.getValue().stream()));
    }

    public void updateUser(User user) {
        deleteUser(user.getDriverLicence());
        addUser(user);
    }

    public void deleteUser(long driverLicence) {
        User us = users.get(driverLicence);
        users.remove(driverLicence);
        city.get(us.getLocation()).remove(driverLicence);
        age.get(us.getAge()).remove(driverLicence);
    }

    public Stream<User> getByAge(int min, int max) {
        return age.entrySet().stream()
                .filter(entry -> {
                    int key = Integer.parseInt(entry.getKey());
                    return key  > min && key < max;
                })
                .flatMap(entry -> entry.getValue().stream());
    }
    public List<User> getByCity(String _city) {
        return city.get(_city.replaceAll(" ", "-"));
    }

    public User getById(int driverLicence) {
        return users.get(driverLicence);
    }
}

interface Lambda {
    void fun(HashMap<String, ArrayList<User>> map, String item);
}

interface PrintMap {
    void fun(Stream<User> stream);
}
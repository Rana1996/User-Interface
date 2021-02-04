package io.userinterface.user;

import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.ExceptionHandler;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.stream.Stream;

@Service
public class UserService {

    private HashMap<Long, User> users = new HashMap<>();
    private HashMap<String, HashMap<Long, User>> city = new HashMap<>();
    private HashMap<String, HashMap<Long, User>> age = new HashMap<>();

    Lambda add2Map = (map, item, user) -> {
        if(map.containsKey(item))
            map.get(item).put(user.getDriverLicence(), user);
        else {
            HashMap m = new HashMap();
            m.put(user.getDriverLicence(), user);
            map.put(item, m);
        }
    };

    public List<User> getAllUsers() {
        return new ArrayList<>(users.values());
    }

    public void addUser(User user) {
        if(users.containsKey(user.getDriverLicence()))
            return;
        add2Map.fun(city, user.getLocation(), user);
        add2Map.fun(age, user.getAge(), user);
        users.put(user.getDriverLicence(), user);
    }

    public void updateUser(long id, User user) {
        deleteUser(id);
        addUser(user);
    }

    public Stream<User> getByAge(int min, int max) {
        if(min > max)
            return null;
        return age.entrySet().stream()
                .filter(entry -> {
                    int key = Integer.parseInt(entry.getKey());
                    return key >= min && key <= max;
                })
                .flatMap(entry -> entry.getValue().values().stream());
    }

    public Stream<User> getByCity(String _city) {
        return city.get(_city).values().stream();
    }

    public User getById(long driverLicence) {
        if(!users.containsKey(driverLicence)) return null;
        return users.get(driverLicence);
    }

    public void deleteUser(long driverLicence) {
        if(!users.containsKey(driverLicence)) return;
        User us = users.get(driverLicence);
        users.remove(driverLicence);
        city.get(us.getLocation()).remove(driverLicence);
        age.get(us.getAge()).remove(driverLicence);
    }

    /////////////////////////////////////////////////////////// For testing
    PrintStream printStream = stream -> {
        stream.forEach(u -> u.print());
    };

    PrintMap printMap = map -> {
        city.keySet().stream().forEach(key -> System.out.print(key + " "));
        System.out.println();
        printStream.fun(map.entrySet().stream().flatMap(entry -> entry.getValue().values().stream()));
    };

    private void printAll() {
        System.out.println("...All...");
        System.out.println("!!! users: ");
        printStream.fun(users.values().stream());
        System.out.println("!!! city: ");
        printMap.fun(city);
        System.out.println("!!! age: ");
        printMap.fun(age);
    }
}

interface Lambda {
    void fun(HashMap<String, HashMap<Long, User>> map, String item, User user);
}

interface PrintStream {
    void fun(Stream<User> stream);
}

interface PrintMap {
    void fun(HashMap<String, HashMap<Long, User>> map);
}




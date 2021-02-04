package io.userinterface.user;

import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.ZoneId;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.stream.Stream;

@Service
public class UserService {

    private HashMap<Long, User> users = new HashMap<>();
    private HashMap<String, HashMap<Long, User>> city = new HashMap<>();
    private HashMap<String, HashMap<Long, User>> age = new HashMap<>();

    String2Int toInt = (s) -> Integer.parseInt(s);

    String2Int birthdate2Age = (birthDate) -> {
        String[] bd = birthDate.split("/");
        int d = toInt.fun(bd[0]), m = toInt.fun(bd[1]), y = toInt.fun(bd[2]);

		LocalDate now = new Date().toInstant().atZone(ZoneId.systemDefault()).toLocalDate();
        int sub = 0;
        if(m == now.getMonthValue() && d >= now.getDayOfMonth() || m > now.getMonthValue())
            sub = 1;
        return now.getYear() - y - sub;
    };

    MapOfMaps add2Map = (map, item, user) -> {
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

    public boolean addUser(User user) {
        if(users.containsKey(user.getDriverLicence()))
            return false;
        add2Map.fun(city, user.getLocation(), user);
        add2Map.fun(age, user.getBirthdate(), user);
        users.put(user.getDriverLicence(), user);
        return true;
    }

    public boolean updateUser(long id, User user) {
        if(!users.containsKey(id)) return false;
        deleteUser(id);
        addUser(user);
        return true;
    }

    public Stream<User> getByAge(int min, int max) {
        if(min > max)
            return null;
        return age.entrySet().stream()
                .filter(entry -> {
                    int key = birthdate2Age.fun(entry.getKey());
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

    public boolean deleteUser(long driverLicence) {
        if(!users.containsKey(driverLicence)) return false;
        User us = users.get(driverLicence);
        users.remove(driverLicence);
        city.get(us.getLocation()).remove(driverLicence);
        age.get(us.getBirthdate()).remove(driverLicence);
        return true;
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

interface MapOfMaps {
    void fun(HashMap<String, HashMap<Long, User>> map, String item, User user);
}

interface PrintStream {
    void fun(Stream<User> stream);
}

interface PrintMap {
    void fun(HashMap<String, HashMap<Long, User>> map);
}

interface String2Int {
    int fun(String s);
}



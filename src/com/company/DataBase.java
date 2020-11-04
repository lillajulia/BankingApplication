package com.company;

import java.util.ArrayList;

public class DataBase {
    private ArrayList<User> users = new ArrayList<User>();

    public void registerUser(User user) {
        users.add(user);

    }

    public User getUser(String name) {
        for (User usr : users) {
            if (name.equals(usr.getName())) {
                return usr;
            }
        }
        return null;
    }

    public ArrayList<User> getUsers() {
        return users;
    }
}

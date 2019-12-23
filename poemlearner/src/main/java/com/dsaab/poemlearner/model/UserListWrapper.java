package com.dsaab.poemlearner.model;

import java.io.Serializable;
import java.util.List;

public class UserListWrapper implements Serializable{

    private static final long serialVersionUID = 1L;
    private List<User> users;

    public UserListWrapper() {

    }

    public List<User> getUsers() {
        return users;
    }

    public void setUsers(List<User> users) {
        this.users = users;
    }
}
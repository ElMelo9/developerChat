package com.example.developerchat.models;

public class User {

    private String uid;
    private String name;
    private String lastName;
    private String email;

    private String status;

    public User() {
    }

    public User(String uid, String name, String lastName, String email, String status) {
        this.uid = uid;
        this.name = name;
        this.lastName = lastName;
        this.email = email;
        this.status = status;
    }

    public String getUid() {
        return uid;
    }

    public void setUid(String uid) {
        this.uid = uid;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }
}

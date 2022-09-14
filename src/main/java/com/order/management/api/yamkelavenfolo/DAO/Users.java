package com.order.management.api.yamkelavenfolo.DAO;

public class Users {
    private int id;
    private String firstName;

    private String surname;

    private String username;

    private String password;

    private String email;


    public Users(int id, String firstName, String surname, String username, String password, String email) {
        this.id = id;
        this.firstName = firstName;
        this.surname = surname;
        this.username = username;
        this.password = password;
        this.email = email;
    }

    public Users() {

    }

    public String getFirstName() {
        return firstName;
    }

    public String getSurname() {
        return surname;
    }

    public String getUsername() {
        return username;
    }

    public String getPassword() {
        return password;
    }

    public String getEmail() {
        return email;
    }
}

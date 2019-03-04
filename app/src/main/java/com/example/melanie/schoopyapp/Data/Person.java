package com.example.melanie.schoopyapp.Data;

import java.util.*;

public abstract class Person {

    String firstName, lastName;
    String schoolemail;
    String username; //for teachers its kürzel for schueler its lastname+first letter of firstname
    String password;

    public Person(){

    }

    public Person(String firstName, String lastName, String schoolemail, String username, String password) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.schoolemail = schoolemail;
        this.username = username;
        this.password = password;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getSchoolemail() {
        return schoolemail;
    }

    public void setSchoolemail(String schoolemail) {
        this.schoolemail = schoolemail;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }


}

package com.tejasCB.restful_service.model;

public class Passenger {
    private  String firstName;
    private  String lastName;
    private  int age;
    private String gender;
    private String email;
    private  long id;
    private String seat;

    public Passenger(String firstName, String lastName, int age, String gender, long id, String email)
    {
        this.firstName = firstName;
        this.lastName = lastName;
        this.age = age;
        this.gender = gender;
        this.id = id;
        this.email = email;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getEmail() {
        return email;
    }

    public String getFirstName() {
        return firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public int getAge() {
        return age;
    }

    public String getGender() {
        return gender;
    }

    public long getId() {
        return id;
    }

}

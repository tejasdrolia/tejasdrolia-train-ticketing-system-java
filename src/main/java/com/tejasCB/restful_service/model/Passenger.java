package com.tejasCB.restful_service.model;

/**
 * Represents a passenger in the train ticketing system.
 */
public class Passenger {

    private String firstName;
    private String lastName;
    private int age;
    private String gender;
    private String email;
    private long id;
    private String seat;

    /**
     * Constructs a new Passenger with the specified details.
     *
     * @param firstName the first name of the passenger
     * @param lastName the last name of the passenger
     * @param age the age of the passenger
     * @param gender the gender of the passenger
     * @param id the unique identifier of the passenger
     * @param email the email address of the passenger
     */
    public Passenger(String firstName, String lastName, int age, String gender, long id, String email) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.age = age;
        this.gender = gender;
        this.id = id;
        this.email = email;
    }

    /**
     * Sets the unique identifier of the passenger.
     *
     * @param id the unique identifier of the passenger
     */
    public void setId(long id) {
        this.id = id;
    }

    /**
     * Returns the email address of the passenger.
     *
     * @return the email address of the passenger
     */
    public String getEmail() {
        return email;
    }

    /**
     * Returns the first name of the passenger.
     *
     * @return the first name of the passenger
     */
    public String getFirstName() {
        return firstName;
    }

    /**
     * Returns the last name of the passenger.
     *
     * @return the last name of the passenger
     */
    public String getLastName() {
        return lastName;
    }

    /**
     * Returns the age of the passenger.
     *
     * @return the age of the passenger
     */
    public int getAge() {
        return age;
    }

    /**
     * Returns the gender of the passenger.
     *
     * @return the gender of the passenger
     */
    public String getGender() {
        return gender;
    }

    /**
     * Returns the unique identifier of the passenger.
     *
     * @return the unique identifier of the passenger
     */
    public long getId() {
        return id;
    }
}

package com.example.pocketguard.Bookmarks;

import java.io.Serializable;
/**
 * Model class representing a student.
 * This class is a part of the MVC (Model-View-Controller) design pattern.
 */
public class Sqlite_StudentModel implements Serializable {

    int id ;
    String name ;
    int age ;
    String Address ;

    /**
     * Constructor to create a student model with all attributes.
     * @param id the student's ID
     * @param name the student's name
     * @param age the student's age
     * @param address the student's address
     */
    public Sqlite_StudentModel(int id, String name, int age, String address) {
        this.id = id;
        this.name = name;
        this.age = age;
        Address = address;
    }
    /**
     * Constructor to create a student model without an ID.
     * @param name the student's name
     * @param age the student's age
     * @param address the student's address
     */
    public Sqlite_StudentModel(String name, int age, String address) {
        this.name = name;
        this.age = age;
        Address = address;
    }

    /**
     * Gets the student's ID.
     * @return the student's ID
     */
    public int getId() {
        return id;
    }
    /**
     * Sets the student's ID.
     * @param id the student's ID
     */
    public void setId(int id) {
        this.id = id;
    }
    /**
     * Gets the student's name.
     * @return the student's name
     */
    public String getName() {
        return name;
    }
    /**
     * Sets the student's name.
     * @param name the student's name
     */
    public void setName(String name) {
        this.name = name;
    }
    /**
     * Gets the student's age.
     * @return the student's age
     */
    public int getAge() {
        return age;
    }
    /**
     * Sets the student's age.
     * @param age the student's age
     */
    public void setAge(int age) {
        this.age = age;
    }
    /**
     * Gets the student's address.
     * @return the student's address
     */
    public String getAddress() {
        return Address;
    }

    /**
     * Sets the student's address.
     * @param address the student's address
     */
    public void setAddress(String address) {
        Address = address;
    }
}

package com.app.phonebook.Model;

/**
 * Created by Colinares on 4/6/2018.
 */
public class ContactModel {

    private int id,age;
    private String name,number,gender,date;

    public ContactModel(int id, int age, String name, String number, String gender, String date) {
        this.id = id;
        this.age = age;
        this.name = name;
        this.number = number;
        this.gender = gender;
        this.date = date;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getAge() {
        return age;
    }

    public void setAge(int age) {
        this.age = age;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getNumber() {
        return number;
    }

    public void setNumber(String number) {
        this.number = number;
    }

    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }
}

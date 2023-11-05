package com.example.studentdb.model;

import java.text.DecimalFormat;

public class Student {

    private String surname, firstName;
    private int ID;
    private float GPA;

    DecimalFormat twoDForm = new DecimalFormat("#.##"); // float number formatter

    Student(){
        surname = "";
        firstName = "";
        ID = 0;
        GPA = 0.00F;
    }

    public Student(String surname, String firstName, int ID, float GPA){
        this.surname = surname;
        this.firstName = firstName;
        this.ID = ID;
        this.GPA = Float.parseFloat(twoDForm.format(GPA)); // to prevent extra decimal places when loaded from db
    }

    public String getSurname() {
        return surname;
    }

    public void setSurname(String surname) {
        this.surname = surname;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public int getID() {
        return ID;
    }

    public void setID(int ID) {
        this.ID = ID;
    }

    public float getGPA() {
        return GPA;
    }

    public void setGPA(float GPA) {
        this.GPA = GPA;
    }
}

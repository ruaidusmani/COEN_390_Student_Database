package com.example.studentdb;

public class Student {

    private String surname;
    private String firstName;
    private int ID;
    private float GPA;

    Student(){
        surname = "";
        firstName = "";
        ID = 0;
        GPA = 0;
    }
    Student(String surname, String firstName, int ID, float GPA){
        this.surname = surname;
        this.firstName = firstName;
        this.ID = ID;
        this.GPA = GPA;
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

package org.example.Structs;

public class Student {
    private String name;
    private String roll;
    private String email;

    public Student(String name, String roll, String email) {
        this.name = name;
        this.roll = roll;
        this.email = email;
    }

    public String getEmail() {
        return email;
    }

    public String getName() {
        return name;
    }

    public String getRoll() {
        return roll;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setRoll(String roll) {
        this.roll = roll;
    }
}

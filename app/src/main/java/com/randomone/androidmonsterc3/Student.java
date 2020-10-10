package com.randomone.androidmonsterc3;

public class Student {
    public String FName;
    public String LName;
    public String Phone;
    public String Email;
    public String PhotoURL;

    public Student() {
        //Empty Constructor
    }

    public Student(String FName, String LName, String phone, String email, String photoURL) {
        this.FName = FName;
        this.LName = LName;
        this.Phone = phone;
        this.Email = email;
        this.PhotoURL = photoURL;
    }

    public String getFName() {
        return FName;
    }

    public void setFName(String FName) {
        this.FName = FName;
    }

    public String getLName() {
        return LName;
    }

    public void setLName(String LName) {
        this.LName = LName;
    }

    public String getPhone() {
        return Phone;
    }

    public void setPhone(String phone) {
        this.Phone = phone;
    }

    public String getEmail() {
        return Email;
    }

    public void setEmail(String email) {
        this.Email = email;
    }

    public String getPhotoURL() {
        return PhotoURL;
    }

    public void setPhotoURL(String photoURL) {
        this.PhotoURL = photoURL;
    }
}



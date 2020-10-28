package com.randomone.androidmonsterc3;

import android.os.Parcel;
import android.os.Parcelable;

public class Student implements Parcelable {
    public String fName;
    public String lName;
    public int studentID;
    public String phone;
    public String email;
    public String photoURL;

    public Student() {
        //Empty Constructor
    }

    public Student(String fName, String lName, int studentID, String phone, String email, String photoURL) {
        this.fName = fName;
        this.lName = lName;
        this.studentID = studentID;
        this.phone = phone;
        this.email = email;
        this.photoURL = photoURL;
    }

    protected Student(Parcel in) {
        fName = in.readString();
        lName = in.readString();
        studentID = in.readInt();
        phone = in.readString();
        email = in.readString();
        photoURL = in.readString();
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(fName);
        dest.writeString(lName);
        dest.writeInt(studentID);
        dest.writeString(phone);
        dest.writeString(email);
        dest.writeString(photoURL);
    }

    @Override
    public int describeContents() {
        return 0;
    }

    public static final Creator<Student> CREATOR = new Creator<Student>() {
        @Override
        public Student createFromParcel(Parcel in) {
            return new Student(in);
        }

        @Override
        public Student[] newArray(int size) {
            return new Student[size];
        }
    };

    public String getfName() {
        return fName;
    }

    public void setfName(String fName) {
        this.fName = fName;
    }

    public String getlName() {
        return lName;
    }

    public void setlName(String lName) {
        this.lName = lName;
    }

    public int getStudentID() {
        return studentID;
    }

    public void setStudentID(int studentID) {
        this.studentID = studentID;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPhotoURL() {
        return photoURL;
    }

    public void setPhotoURL(String photoURL) {
        this.photoURL = photoURL;
    }
}



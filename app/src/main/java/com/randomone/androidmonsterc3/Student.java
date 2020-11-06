package com.randomone.androidmonsterc3;

import android.os.Parcel;
import android.os.Parcelable;

public class Student implements Parcelable {
    public String fName;
    public String lName;
    public Long studentID;
    public String phone;
    public String email;
    public String photoURL;
    public String pathway;

    public Student() {

    }

    public Student(String fName, String lName, Long studentID, String phone, String email, String photoURL, String pathway) {
        this.fName = fName;
        this.lName = lName;
        this.studentID = studentID;
        this.phone = phone;
        this.email = email;
        this.photoURL = photoURL;
        this.pathway = pathway;
    }

    protected Student(Parcel in) {
        fName = in.readString();
        lName = in.readString();
        studentID = in.readLong();
        phone = in.readString();
        email = in.readString();
        photoURL = in.readString();
        pathway = in.readString();
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(fName);
        dest.writeString(lName);
        dest.writeLong(studentID);
        dest.writeString(phone);
        dest.writeString(email);
        dest.writeString(photoURL);
        dest.writeString(pathway);
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

    public Long getStudentID() {
        return studentID;
    }

    public void setStudentID(Long studentID) {
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

    public String getPathway() {
        return pathway;
    }

    public void setPathway(String pathway) {
        this.pathway = pathway;
    }
}



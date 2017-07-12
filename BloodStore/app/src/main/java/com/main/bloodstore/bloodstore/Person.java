package com.main.bloodstore.bloodstore;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by Sai Teja on 05-01-2017.
 */

public class Person implements Parcelable{

    private String name;
    private String phone;
    private String gender;
    private String email;
    private String age;
    private String status;

    public Person() {
    }

    protected Person(Parcel in) {
        name = in.readString();
        phone = in.readString();
        gender=in.readString();
        email=in.readString();
        age=in.readString();
        status=in.readString();
    }

    public static final Creator<Person> CREATOR = new Creator<Person>() {
        @Override
        public Person createFromParcel(Parcel in) {
            return new Person(in);
        }

        @Override
        public Person[] newArray(int size) {
            return new Person[size];
        }
    };

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }
    public void setStatus(String status){this.status=status;}
    public String getStatus(){return status;}

    public String getAge(){return age;}
    public String getGender(){return gender;}
    public String getEmail(){return email;}

    public void setAge(String age){this.age=age;}
    public void setGender(String gender){this.gender=gender;}
    public void setEmail(String email){this.email=email;}

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(name);
        dest.writeString(phone);
        dest.writeString(gender);
        dest.writeString(email);
        dest.writeString(age);
        dest.writeString(status);
    }
}

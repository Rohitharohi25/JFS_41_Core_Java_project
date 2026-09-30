package com.Modules;

public class Customer extends User {

    private String mobileNum;
    private String city;

    public Customer(int id, String username, String mailId,String password, String mobileNum, String city) {

        super(id, username, mailId, password);

        this.mobileNum = mobileNum;
        this.city = city;
    }

    public String getMobileNum() {
        return mobileNum;
    }

    public String getCity() {
        return city;
    }

    @Override
    public String toString() {

        return userId + " | " + username + " | " + mailId + " | " + mobileNum + " | " + city;
    }
}
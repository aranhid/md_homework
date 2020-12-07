package com.example.mycustomadapter;

import java.util.Comparator;

public class UsersPhoneComparator implements Comparator<User> {

    @Override
    public int compare(User u1, User u2) {
        return u1.phoneNumber.compareTo(u2.phoneNumber);
    }
}

package com.example.mycustomadapter;

import java.util.Comparator;

public class UsersGenderComparator implements Comparator<User> {

    @Override
    public int compare(User u1, User u2) {
        return u1.gender.compareTo(u2.gender);
    }
}

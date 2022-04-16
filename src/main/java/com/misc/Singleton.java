package com.misc;

import com.entity.UserEntity;

public class Singleton {

    private static Singleton instance = null;
    private final UserEntity user;

    private Singleton(UserEntity user) {
        this.user = user;
    }

    public static Singleton getInstance(UserEntity user) {
        synchronized (Singleton.class) {
            if (instance == null) {
                instance = new Singleton(user);
            }
        }
        return instance;
    }

    public UserEntity getUser() {
        return user;
    }

}

package com.misc;

import com.entity.UserEntity;

public record Singleton(UserEntity user) {

    private static Singleton instance = null;

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

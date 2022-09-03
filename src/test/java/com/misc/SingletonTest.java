package com.misc;

import com.entity.UserEntity;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;


class SingletonTest {

    @Test
    void getInstance() {

        UserEntity user1 = new UserEntity();
        UserEntity user2 = new UserEntity();

        Assertions.assertEquals(Singleton.getInstance(user1), Singleton.getInstance(user2));
    }
}
package com.entity;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class UserEntityTest {

    @Test
    void testEqualsTrue() {
        UserEntity user1 = new UserEntity();
        user1.setUsername("1");
        UserEntity user2 = new UserEntity();
        user2.setUsername("1");
        Assertions.assertEquals(user1, user2);
    }

    @Test
    void testEqualsFalse() {
        UserEntity user1 = new UserEntity();
        user1.setCity("Arad");
        UserEntity user2 = new UserEntity();
        user2.setCity("Bucuresti");
        Assertions.assertNotEquals(user1, user2);
    }
}
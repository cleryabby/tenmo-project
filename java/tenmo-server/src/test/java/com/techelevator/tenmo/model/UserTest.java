package com.techelevator.tenmo.model;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.*;

public class UserTest {

    User user = new User();

    @Before
    public void setUp() throws Exception {

    }

    @After
    public void tearDown() throws Exception {

    }

    @Test
    public void get_id_from_set_id() {

        user.setId(8558L);
        assertEquals(8558L, user.getId());

        user.setId(58L);
        assertEquals(58L, user.getId());

        user.setId(8598L);
        assertEquals(8598L, user.getId());

    }

    @Test
    public void get_username_from_set_username() {

        user.setUsername("name Name");
        assertEquals("name Name", user.getUsername());

        user.setUsername("user2");
        assertEquals("user2", user.getUsername());

        user.setUsername("thisuserName123");
        assertEquals("thisuserName123", user.getUsername());


    }

    @Test
    public void get_password_from_set_password() {

        user.setPassword("firstPassword3");
        assertEquals("firstPassword3", user.getPassword());

        user.setPassword("firstPassword");
        assertEquals("firstPassword", user.getPassword());

        user.setPassword("samplePassword");
        assertEquals("samplePassword", user.getPassword());

    }

    @Test
    public void is_activated() {

        user.setActivated(true);
        assertTrue(user.isActivated());

        user.setActivated(false);
        assertFalse(user.isActivated());

    }


}
package com.techelevator.tenmo.dao;

import com.techelevator.tenmo.model.User;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.List;

public class JdbcUserDaoTests extends TenmoDaoTests{

    private static final User USER_1 = new User (1L, "user1", "passString1");
    private static final User USER_2 = new User (2L, "user2", "passString2");
    private static final User USER_3 = new User (3L, "user3", "passString3");

    private JdbcUserDao sut;
    private User testUser;

    @Before
    public void setup() {
        sut = new JdbcUserDao(dataSource);
        testUser = new User (4L, "user4", "passString4");
    }

    @Test
    public void findAll_returns_all_users() {
        List<User> users = sut.findAll();

        Assert.assertEquals(3, users.size());
    }


    @Test
    public void getAllUsers_returns_all_users() {
        List<User> usersList = sut.getAllUsers();

        Assert.assertEquals(3, usersList.size());

    }

    @Test
    public void getUserByAccountId_returns_correct_user_for_accountId() {
        User user = sut.getUserByAccountId(2);

        assertUsersMatch(USER_2, user);
    }



    private void assertUsersMatch(User expected, User actual) {
        Assert.assertEquals(expected.getId(), actual.getId());
        Assert.assertEquals(expected.getUsername(), actual.getUsername());
        Assert.assertEquals(expected.getPassword(), actual.getPassword());

    }

}

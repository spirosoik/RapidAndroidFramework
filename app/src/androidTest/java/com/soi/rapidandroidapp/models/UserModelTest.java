package com.soi.rapidandroidapp.models;

import android.test.InstrumentationTestCase;

import com.soi.rapidandroidapp.models.common.DBModel;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.robolectric.RobolectricTestRunner;

import java.util.Date;
import java.util.List;
import java.util.Random;

/**
 * Created by Spiros I. Oikonomakis on 11/14/14.
 */
@RunWith(RobolectricTestRunner.class)
public class UserModelTest extends InstrumentationTestCase {

    private User user;


    @Override
    protected void setUp() throws Exception {
        super.setUp();

        Random randomizer = new Random();
        user = new User();
        user.userId = randomizer.nextLong();
        user.authToken = String.valueOf(new Date().getTime()) + "_token";
        user.avatar = String.valueOf(new Date().getTime()) + ".jpg";
        user.email = new Date().getTime() + "@test.com";
        user.lname = String.valueOf(randomizer.nextLong());
        user.fname = String.valueOf(randomizer.nextLong());
    }

    @Test
    public void testSave() {
        user.save();
        assertNotNull(user.getId());
    }

    @Test
    public void testFindOne() throws IllegalAccessException, InstantiationException {
        user.save();
        assertNotNull(user.getId());

        User searchUser = (User) DBModel.getInstance(User.class).findOne(user.getId());
        assertEquals(user.getId(), searchUser.getId());
    }

    @Test
    public void testFindAll() throws IllegalAccessException, InstantiationException {
        user.save();
        assertNotNull(user.getId());

        List<User> users = (List<User>) DBModel.getInstance(User.class).findAll();
        for (DBModel model : users) {
            assertNotNull(model);
        }
    }

    @Test
    public void testDeleteOne() throws IllegalAccessException, InstantiationException {
        user.save();
        assertNotNull(user.getId());

        Long userId = user.getId();
        DBModel.getInstance(User.class).deleteOne(user.getId());

        User searchUser = (User) DBModel.getInstance(User.class).findOne(userId);
        assertNull(searchUser);
    }

    @Test
    public void testDeleteAll() throws IllegalAccessException, InstantiationException {
        user.save();
        assertNotNull(user.getId());

        DBModel.getInstance(User.class).deleteAll();

        List<User> userList = (List<User>) DBModel.getInstance(User.class).findAll();
        assertEquals(0, userList.size());
    }
}

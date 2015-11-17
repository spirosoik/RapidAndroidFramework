package com.soi.rapidandroidapp.models;

import android.os.Build;
import com.soi.rapidandroidapp.BuildConfig;
import com.soi.rapidandroidapp.models.common.DBModel;
import com.soi.rapidandroidapp.test.support.UnitTestSpecification;
import java.util.Date;
import java.util.List;
import java.util.Random;
import org.junit.Before;
import org.junit.Test;
import org.robolectric.annotation.Config;

import static org.fest.assertions.api.Assertions.assertThat;

/**
 * Created by Spiros I. Oikonomakis on 11/14/14.
 */
@Config(sdk = Build.VERSION_CODES.JELLY_BEAN, constants = BuildConfig.class)
public class UserModelTest
    extends UnitTestSpecification {

  private User user;

  @Before
  public void setUp() throws Exception {

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
    assertThat(user.getId()).isNotNull();
  }

  @Test
  public void testFindOne() throws IllegalAccessException, InstantiationException {
    user.save();
    assertThat(user.getId()).isNotNull();

    User searchUser = (User) DBModel.getInstance(User.class).findOne(user.getId());
    assertThat(user.getId()).isEqualTo(searchUser.getId());
  }

  @Test
  public void testFindAll() throws IllegalAccessException, InstantiationException {
    user.save();
    assertThat(user.getId()).isNotNull();

    @SuppressWarnings("unchecked")
    List<User> users = (List<User>) DBModel.getInstance(User.class).findAll();
    for (DBModel model : users) {
      assertThat(model).isNotNull();
    }
  }

  @Test
  public void testDeleteOne() throws IllegalAccessException, InstantiationException {
    user.save();
    assertThat(user.getId()).isNotNull();

    Long userId = user.getId();
    DBModel.getInstance(User.class).deleteOne(user.getId());

    User searchUser = (User) DBModel.getInstance(User.class).findOne(userId);
    assertThat(searchUser).isNull();
  }

  @Test
  public void testDeleteAll() throws IllegalAccessException, InstantiationException {
    user.save();
    assertThat(user.getId()).isNotNull();

    DBModel.getInstance(User.class).deleteAll();

    @SuppressWarnings("unchecked")
    List<User> userList = (List<User>) DBModel.getInstance(User.class).findAll();
    assertThat(0).isEqualTo(userList.size());
  }
}

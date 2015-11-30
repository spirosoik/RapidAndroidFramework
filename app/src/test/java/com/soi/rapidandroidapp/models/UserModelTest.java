package com.soi.rapidandroidapp.models;

import android.os.Build;
import com.soi.rapidandroidapp.BuildConfig;
import com.soi.rapidandroidapp.models.common.DBModel;
import com.soi.rapidandroidapp.test.support.ShadowMultiDex;
import com.soi.rapidandroidapp.test.support.UnitTestSpecification;
import java.lang.reflect.Field;
import java.util.Date;
import java.util.List;
import java.util.Random;
import ollie.Ollie;
import ollie.query.Select;
import org.junit.After;
import org.junit.Test;
import org.robolectric.annotation.Config;

import static org.fest.assertions.api.Assertions.assertThat;

/**
 * Created by Spiros I. Oikonomakis on 11/14/14.
 */
@Config(sdk = Build.VERSION_CODES.JELLY_BEAN, constants = BuildConfig.class, shadows = {
    ShadowMultiDex.class
}) public class UserModelTest extends UnitTestSpecification {

  @After public void tearDown() {
    setPrivateField(Ollie.class, "sInitialized", false);
  }

  @Test public void testSave() {
    User user = createUser();
    assertThat(user.id).isNotNull();
  }

  @Test public void testFindOne() throws IllegalAccessException, InstantiationException {
    User user = createUser();
    assertThat(user.id).isNotNull();

    User searchUser = Select.from(User.class).where("_id = ?", user.id).fetchSingle();
    assertThat(user.id).isEqualTo(searchUser.id);
  }

  @Test public void testFindAll() throws IllegalAccessException, InstantiationException {
  }

  @Test public void testDeleteOne() throws IllegalAccessException, InstantiationException {
    User user = createUser();
    assertThat(user.id).isNotNull();

    Long userId = user.id;
    DBModel.getInstance(User.class).deleteOne(user.id);

    User searchUser = (User) DBModel.getInstance(User.class).findOne(userId);
    assertThat(searchUser).isNull();
  }

  @Test public void testDeleteAll() throws IllegalAccessException, InstantiationException {
    User user = createUser();
    assertThat(user.id).isNotNull();

    DBModel.getInstance(User.class).deleteAll();

    @SuppressWarnings("unchecked") List<User> userList =
        (List<User>) DBModel.getInstance(User.class).findAll();
    assertThat(0).isEqualTo(userList.size());
  }

  private User createUser() {
    Random randomizer = new Random();
    User user = new User();
    user.userId = randomizer.nextLong();
    user.authToken = String.valueOf(new Date().getTime()) + "_token";
    user.avatar = String.valueOf(new Date().getTime()) + ".jpg";
    user.email = new Date().getTime() + "@test.com";
    user.lname = String.valueOf(randomizer.nextLong());
    user.fname = String.valueOf(randomizer.nextLong());
    user.save();
    return user;
  }

  private void setPrivateField(Class clz, String fieldName, boolean value) {
    Field field;
    try {
      field = clz.getDeclaredField(fieldName);
      field.setAccessible(true);
      field.set(null, value);
    } catch (Exception e) {
      e.printStackTrace();
    }
  }
}

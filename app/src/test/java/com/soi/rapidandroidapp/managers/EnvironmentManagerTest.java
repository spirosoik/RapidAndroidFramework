package com.soi.rapidandroidapp.managers;

import android.os.Build;
import android.util.Log;
import com.soi.rapidandroidapp.BuildConfig;
import com.soi.rapidandroidapp.TestBaseApplication;
import com.soi.rapidandroidapp.models.common.Environment;
import com.soi.rapidandroidapp.test.support.UnitTestSpecification;
import com.soi.rapidandroidapp.utilities.Constants;
import javax.inject.Inject;
import org.junit.Before;
import org.junit.Test;
import org.mockito.MockitoAnnotations;
import org.robolectric.annotation.Config;
import retrofit.RestAdapter;

import static org.fest.assertions.api.Assertions.assertThat;

/**
 * Created by Spiros I. Oikonomakis on 11/4/14.
 */
@Config(sdk = Build.VERSION_CODES.JELLY_BEAN, constants = BuildConfig.class)
public class EnvironmentManagerTest
    extends UnitTestSpecification {

  @Inject EnvironmentManager environmentManager;

  @Before
  public void setup() throws Exception {

    MockitoAnnotations.initMocks(this);

    TestBaseApplication.injectMocks(this);
  }

  @Test
  public void testEnviroment() {
    Environment environment = environmentManager.getEnvironment();
    assertThat(environment).isNotNull();
    assertThat(environment.toString()).isEqualTo(BuildConfig.ENVIRONMENT);
  }

  @Test
  public void testApiEnvironment() {
    String apiUrl = environmentManager.getEnviromentApiUrl();
    assertThat(apiUrl);

    switch (environmentManager.getEnvironment()) {
      case LIVE:
        assertThat(apiUrl).matches(Constants.API_LIVE_URL);
        break;
      case STAGING:
        assertThat(apiUrl).matches(Constants.API_STAGING_URL);
        break;
      case UAT:
        assertThat(apiUrl).matches(Constants.API_STAGING_URL);
        break;
    }
  }

  @Test
  public void testApiLogLevel() {
    RestAdapter.LogLevel logLevel = environmentManager.getEnvironmentApiLogLevel();
    assertThat(logLevel);

    switch (environmentManager.getEnvironment()) {
      case LIVE:
        assertThat(logLevel).isEqualTo(RestAdapter.LogLevel.BASIC);
        break;
      case STAGING:
        assertThat(logLevel).isEqualTo(RestAdapter.LogLevel.FULL);
        break;
      case UAT:
        assertThat(logLevel).isEqualTo(RestAdapter.LogLevel.FULL);
        break;
    }
  }

  @Test
  public void testLogLevel() {
    int logLevel = environmentManager.getEnvironmentLogLevel();
    assertThat(logLevel);

    switch (environmentManager.getEnvironment()) {
      case LIVE:
        assertThat(logLevel).isEqualTo(Log.INFO);
        break;
      case STAGING:
        assertThat(logLevel).isEqualTo(Log.DEBUG);
        break;
      case UAT:
        assertThat(logLevel).isEqualTo(Log.VERBOSE);
        break;
    }
  }

  @Test
  public void testCanTrackGA() {
    boolean logLevel = environmentManager.canTrackGA();
    assertThat(logLevel);

    switch (environmentManager.getEnvironment()) {
      case LIVE:
        assertThat(logLevel).isEqualTo(true);
        break;
      case STAGING:
        assertThat(logLevel).isEqualTo(false);
        break;
      case UAT:
        assertThat(logLevel).isEqualTo(true);
        break;
    }
  }
}

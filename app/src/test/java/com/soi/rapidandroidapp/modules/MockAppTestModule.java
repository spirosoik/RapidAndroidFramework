package com.soi.rapidandroidapp.modules;

import android.content.Context;

import com.soi.rapidandroidapp.TestBaseApplication;
import com.soi.rapidandroidapp.api.managers.FoursquareApiManager;
import com.soi.rapidandroidapp.api.managers.FoursquareApiManagerTest;
import com.soi.rapidandroidapp.managers.EnvironmentManagerTest;
import com.soi.rapidandroidapp.managers.EnvironmentManager;

import org.mockito.Mockito;

import dagger.Module;

@Module(
    complete = false,
    includes = {AppModule.class},
    injects = {FoursquareApiManagerTest.class, EnvironmentManagerTest.class, TestBaseApplication.class},
    overrides = true
)
public class MockAppTestModule {

    FoursquareApiManager provideFoursquareApiManager(Context context)
    {
        return Mockito.mock(FoursquareApiManager.class);
    }

    EnvironmentManager provideEnvironmentManager(Context context)
    {
        return Mockito.mock(EnvironmentManager.class);
    }

}
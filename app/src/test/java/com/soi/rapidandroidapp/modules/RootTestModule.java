package com.soi.rapidandroidapp.modules;

import dagger.Module;

/**
 * Created by Spiros I. Oikonomakis on 5/22/15.
 */
@Module
(
    includes = {
            AndroidModule.class,
            AppModule.class,
            MockAppTestModule.class
    }
)
public class RootTestModule {
}
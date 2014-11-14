package com.soi.rapidandroidapp.modules;

import dagger.Module;

/**
 * Created by spirosoikonomakis on 3/9/14.
 */
@Module
(
    includes = {
            AndroidModule.class,
            AppModule.class
    }
)
public class RootModule {
}
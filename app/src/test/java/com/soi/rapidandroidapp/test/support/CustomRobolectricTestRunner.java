package com.soi.rapidandroidapp.test.support;

import com.soi.rapidandroidapp.BuildConfig;
import com.soi.rapidandroidapp.ui.MainActivity;

import org.robolectric.RobolectricTestRunner;
import org.junit.runners.model.InitializationError;

public class CustomRobolectricTestRunner extends RobolectricTestRunner {

    public CustomRobolectricTestRunner(Class<?> testClass) throws InitializationError {
        super(testClass);
        String buildVariant = (BuildConfig.FLAVOR.isEmpty() ? "" : BuildConfig.FLAVOR+ "/") + BuildConfig.BUILD_TYPE;
        String intermediatesPath = MainActivity.class.getResource("").toString().replace("file:", "");
        System.out.println(intermediatesPath);
        intermediatesPath = intermediatesPath.substring(0, intermediatesPath.indexOf("/classes"));

        System.setProperty("android.package", BuildConfig.APPLICATION_ID);
        System.setProperty("android.manifest", intermediatesPath + "/manifests/full/" + buildVariant + "/AndroidManifest.xml");
        System.setProperty("android.resources", intermediatesPath + "/res/" + buildVariant);
        System.setProperty("android.assets", intermediatesPath + "/assets/" + buildVariant);
    }
}
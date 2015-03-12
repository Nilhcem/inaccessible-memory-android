package com.nilhcem.inaccessible.memory;

import org.junit.runners.model.InitializationError;
import org.robolectric.AndroidManifest;
import org.robolectric.RobolectricTestRunner;
import org.robolectric.annotation.Config;
import org.robolectric.res.Fs;

import java.io.File;

/**
 * @see "http://blog.blundell-apps.com/android-gradle-app-with-robolectric-junit-tests/"
 */
public class RobolectricGradleTestRunner extends RobolectricTestRunner {

    private static final int MAX_SDK_SUPPORTED_BY_ROBOLECTRIC = 18;

    public RobolectricGradleTestRunner(Class<?> testClass) throws InitializationError {
        super(testClass);
    }

    @Override
    protected AndroidManifest getAppManifest(Config config) {
        String manifestProperty = "app/src/main/AndroidManifest.xml";
        String resProperty = "app/src/main/res";

        // This patch is for Android studio that searches app directory in ../app instead of ./app using gradle
        File file = new File("../app");
        if (file.exists()) {
            manifestProperty = "../" + manifestProperty;
            resProperty = "../" + resProperty;
        }

        return new AndroidManifest(Fs.fileFromPath(manifestProperty), Fs.fileFromPath(resProperty)) {
            @Override
            public int getTargetSdkVersion() {
                return MAX_SDK_SUPPORTED_BY_ROBOLECTRIC;
            }
        };
    }
}

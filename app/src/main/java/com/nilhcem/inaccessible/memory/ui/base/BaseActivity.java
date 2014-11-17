package com.nilhcem.inaccessible.memory.ui.base;

import android.app.Activity;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.widget.Toast;

import com.nilhcem.inaccessible.memory.BuildConfig;

import icepick.Icepick;

public abstract class BaseActivity extends Activity {

    private static final int DELAY = 300;

    private Handler mHandler = new Handler();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Icepick.restoreInstanceState(this, savedInstanceState);
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        Icepick.saveInstanceState(this, outState);
    }

    protected void announceMessage(String message) {
        announceMessage(message, false);
    }

    protected void announceMessage(final String message, boolean withDelay) {
        if (BuildConfig.DEBUG) {
            Toast.makeText(this, message, Toast.LENGTH_SHORT).show();
        } else {
            mHandler.postDelayed(new Runnable() {
                public void run() {
                    getLayoutToAnnounceMessages().announceForAccessibility(message);
                }
            }, withDelay ? DELAY : 0);
        }
    }

    protected abstract View getLayoutToAnnounceMessages();
}

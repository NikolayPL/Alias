package com.nickrman.alias.services.navigation.factories;

import android.app.Activity;
import android.content.Intent;

import com.nickrman.alias.GamingActivity;
import com.nickrman.alias.SettingsActivity;
import com.nickrman.alias.StartActivity;
import com.nickrman.alias.WinnerActivity;
import com.nickrman.alias.base.App;
import com.nickrman.alias.services.navigation.Screen;


public class ScreenActivityFactory {

    public Intent getActivityByType(Screen screen) {
        return new Intent(App.getInstance(), getActivityClassByType(screen));
    }

    private Class<? extends Activity> getActivityClassByType(Screen screen) {
        switch (screen) {
            case START:
                return StartActivity.class;
            case SETTINGS:
                return SettingsActivity.class;
            case GAMING:
                return GamingActivity.class;
            case WINNER:
                return WinnerActivity.class;


            default:
                return StartActivity.class;
        }
    }

}

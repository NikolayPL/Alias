package com.nickrman.alias;

import android.os.Bundle;
import android.util.Log;
import android.view.View;

import com.nickrman.alias.base.BaseActivity;
import com.nickrman.alias.base.action_bar.ActionBarContract;
import com.nickrman.alias.base.action_bar.ActionBarView;
import com.nickrman.alias.data.models.SettingItem;
import com.nickrman.alias.services.navigation.Screen;
import com.nickrman.alias.services.navigation.ScreenType;
import com.nickrman.alias.utils.Constants;


import timber.log.Timber;

public class GamingActivity extends BaseActivity {
    private ActionBarContract.View actionBarView;
    private View actionBar;
    private Bundle bundle = new Bundle();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_game);
        actionBar = findViewById(R.id.action_bar);
        actionBarView = new ActionBarView(actionBar);

       // bundle = getIntent().getExtras();

        getNavigator().navigateTo(Screen.SCORE, ScreenType.FRAGMENT/*, bundle*/);


    }

    @Override
    public ActionBarContract.View getActionBarView() {
        return actionBarView;
    }
}

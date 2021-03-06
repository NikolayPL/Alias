package com.nickrman.alias.services.navigation.managers;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;

import com.nickrman.alias.R;
import com.nickrman.alias.base.BaseActivity;
import com.nickrman.alias.services.Navigator;
import com.nickrman.alias.services.navigation.Screen;
import com.nickrman.alias.services.navigation.ScreenAnimType;
import com.nickrman.alias.services.navigation.ScreenType;
import com.nickrman.alias.services.navigation.factories.ScreenActivityFactory;
import com.nickrman.alias.services.navigation.factories.ScreenFragmentFactory;


public class ScreenNavigationManager implements Navigator {

    public final static String ACTIVITY_REQUEST_CODE = "ScreenNavigationManager.activityRequestCode";

    public final ScreenActivityFactory activityFactory;
    public final ScreenFragmentFactory fragmentFactory;
    public BaseActivity activity;
    private Screen screen;

    public BaseActivity getActivity() {
        return activity;
    }


    @Override
    public Screen getScreen() {
        return this.screen;
    }


    public ScreenNavigationManager(BaseActivity activity) {
        this.activity = activity;
        activityFactory = new ScreenActivityFactory();
        fragmentFactory = new ScreenFragmentFactory();
    }

    @Override
    public void navigateTo(Screen screen, ScreenType type) {
        navigateTo(screen, type, null);
    }

    @Override
    public void navigateTo(Screen screen, ScreenType type, Bundle args) {
        switch (type) {
            case ACTIVITY:
                navigateToActivity(screen, args);
                this.screen = screen;
                break;
            case FRAGMENT:
                navigateToFragment(screen, args);
                this.screen = screen;
                break;
        }
    }

    private void navigateToActivity(Screen screen, Bundle args) {
        switch (screen) {
            case START:
                navigateToStart(args);
                break;
            case SETTINGS:
                navigateToSettings(args);
                break;
            case GAMING:
                navigateToGaming(args);
                break;
            case WINNER:
                navigateToWinner(args);
                break;
        }
    }

    private void navigateToFragment(Screen screen, Bundle args) {
        switch (screen) {
            case SCORE:
                navigateToScoreFragment(args);
                break;
            case CARD:
                navigateToGameFragment(args);
                break;
            case RESULT:
                navigateToResultFragment(args);
                break;

        }
    }




    //Activity

    private void navigateToStart(Bundle args) {
        switchActivityScreen(Screen.START, args, ScreenAnimType.NONE_TYPE, true);
        activity.hideKeyboard();
        /*activity.finish();
        activity.freeMemory();*/
    }

    private void navigateToSettings(Bundle args) {
        switchActivityScreen(Screen.SETTINGS, args, ScreenAnimType.NONE_TYPE, false);
        activity.hideKeyboard();

    }

    private void navigateToGaming(Bundle args) {
        switchActivityScreen(Screen.GAMING, args, ScreenAnimType.NONE_TYPE, false);
        activity.hideKeyboard();
        /*activity.finish();
        activity.freeMemory();*/
    }

    private void navigateToWinner(Bundle args) {
        switchActivityScreen(Screen.WINNER,args,ScreenAnimType.NONE_TYPE,true);
        activity.hideKeyboard();
        activity.finish();
        activity.freeMemory();
    }



    //Fragment
    private void navigateToScoreFragment(Bundle args) {
        switchFragmentScreen(Screen.SCORE, args, ScreenAnimType.FADE_TYPE, false);
        activity.hideKeyboard();
    }

    private void navigateToGameFragment(Bundle args) {
        switchFragmentScreen(Screen.CARD, args, ScreenAnimType.NONE_TYPE, false);
        activity.hideKeyboard();
    }

    private void navigateToResultFragment(Bundle args) {
        switchFragmentScreen(Screen.RESULT,args,ScreenAnimType.NONE_TYPE,false);
        activity.hideKeyboard();
    }

    private void switchActivityScreen(Screen type, Bundle bundle, ScreenAnimType animate, boolean clearStack) {
        Intent intent = activityFactory.getActivityByType(type);
        if (clearStack) {
            intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
        } else {
            intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        }

        if (bundle != null && !bundle.isEmpty()) {
            intent.putExtras(bundle);
        }

        // logic for enabling handling result in onActivityResult
        int requestCode = 0;
        if (bundle != null) {
            requestCode = bundle.getInt(ACTIVITY_REQUEST_CODE, 0);
        }
        if (requestCode != 0) {
            activity.startActivityForResult(intent, requestCode);
        } else {
            activity.startActivity(intent);
        }


        switch (animate) {
            case NONE_TYPE:
                activity.overridePendingTransition(0, 0);
                break;
            case FADE_TYPE:
                activity.overridePendingTransition(android.R.anim.fade_in, android.R.anim.fade_out);
                break;
            case BOTTOM_TO_TOP_TYPE:
                activity.overridePendingTransition(R.anim.slide_in_up, R.anim.slide_out_up);
                break;
            /*case RIGHT_TO_LEFT_TYPE:
                activity.overridePendingTransition(R.anim.right_to_left_in, R.anim.right_to_left_out);
                break;
            case LEFT_TO_RIGHT_TYPE:
                activity.overridePendingTransition(R.anim.left_to_right_in, R.anim.left_to_right_out);
                break;
            */
        }
    }

    private void switchFragmentScreen(Screen type, Bundle bundle, ScreenAnimType animate, boolean addToBackStack) {
        if (isSameFragmentAlreadyPlaced(type)) {
            return;
        }

        FragmentManager fragmentManager = activity.getSupportFragmentManager();
        FragmentTransaction tran = fragmentManager.beginTransaction();


        setAnimationForFragment(animate, tran);


        Fragment fragment = fragmentFactory.getFragmentByType(type);
        if (bundle != null && !bundle.isEmpty()) {
            fragment.setArguments(bundle);
        }
        if (addToBackStack) {

            tran.replace(R.id.content_frame, fragment, fragment.getClass().getSimpleName());
            tran.addToBackStack(fragment.getClass().getSimpleName());
        } else {

            tran.replace(R.id.content_frame, fragment);
        }
        tran.commit();
    }

    private FragmentTransaction setAnimationForFragment(ScreenAnimType animate, FragmentTransaction tran) {

        switch (animate) {
            case NONE_TYPE:
                tran.setCustomAnimations(0, 0);
                break;
            case FADE_TYPE:
                tran.setCustomAnimations(android.R.anim.fade_in, android.R.anim.fade_out);
                break;
            case BOTTOM_TO_TOP_TYPE:
                tran.setCustomAnimations(R.anim.slide_in_up, R.anim.slide_out_up);
                break;
            case TOP_TO_BOTTOM_TYPE:
                tran.setCustomAnimations(R.anim.slide_out_up, R.anim.slide_in_up);
                break;
            /*case RIGHT_TO_LEFT_TYPE:
                activity.overridePendingTransition(R.anim.right_to_left_in, R.anim.right_to_left_out);
                break;
            case LEFT_TO_RIGHT_TYPE:
                activity.overridePendingTransition(R.anim.left_to_right_in, R.anim.left_to_right_out);
                break;
            */

        }
        return tran;

    }


    private boolean isSameFragmentAlreadyPlaced(Screen type) {
        Fragment existing = activity.getSupportFragmentManager().findFragmentById(R.id.root);
        if (existing != null) {
            Class<? extends Fragment> requested = fragmentFactory.getFragmentClassByType(type);
            if (existing.getClass().equals(requested)) {
                return true;
            }
        }
        return false;
    }
}
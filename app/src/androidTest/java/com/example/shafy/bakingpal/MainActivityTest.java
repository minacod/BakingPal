package com.example.shafy.bakingpal;

import android.support.test.espresso.contrib.RecyclerViewActions;
import android.support.test.rule.ActivityTestRule;
import android.support.test.runner.AndroidJUnit4;

import static android.support.test.espresso.Espresso.onView;
import static android.support.test.espresso.action.ViewActions.click;
import static android.support.test.espresso.assertion.ViewAssertions.matches;
import static android.support.test.espresso.matcher.ViewMatchers.isDisplayed;
import static android.support.test.espresso.matcher.ViewMatchers.isEnabled;
import static android.support.test.espresso.matcher.ViewMatchers.withId;
import static android.support.test.espresso.matcher.ViewMatchers.withText;
import static org.hamcrest.core.IsNot.not;

import com.example.shafy.bakingpal.ui.MainActivity;

import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

/**
 * Created by shafy on 30/11/2017.
 */

@RunWith(AndroidJUnit4.class)
public class MainActivityTest {
    @Rule public ActivityTestRule<MainActivity> mActivityTestRule= new ActivityTestRule<>(MainActivity.class);

    @Test
    public void clickRecipesListItem(){

        onView(withId(R.id.rv_recipes_list)).perform(RecyclerViewActions.scrollToPosition(3));
        onView(withId(R.id.rv_recipes_list)).perform(RecyclerViewActions.actionOnItemAtPosition(3, click()));
        onView(withId(R.id.tv_persons_number)).check(matches(withText("8")));

        onView(withId(R.id.rv_steps_list)).perform(RecyclerViewActions.scrollToPosition(0));
        onView(withId(R.id.rv_steps_list)).perform(RecyclerViewActions.actionOnItemAtPosition(0, click()));
        onView(withId(R.id.btn_back)).check(matches(not(isEnabled())));

        onView(withId(R.id.btn_next)).perform(click());
        onView(withId(R.id.btn_back)).check(matches(isEnabled()));

    }




}

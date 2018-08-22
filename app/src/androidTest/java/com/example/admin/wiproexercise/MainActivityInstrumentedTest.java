package com.example.admin.wiproexercise;

import android.support.test.rule.ActivityTestRule;
import android.support.test.runner.AndroidJUnit4;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;

import com.example.admin.wiproexercise.activity.MainActivity;
import com.example.admin.wiproexercise.utils.Utils;

import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import static android.support.test.espresso.Espresso.onView;
import static android.support.test.espresso.action.ViewActions.click;
import static android.support.test.espresso.assertion.ViewAssertions.matches;
import static android.support.test.espresso.matcher.ViewMatchers.withId;
import static android.support.test.espresso.matcher.ViewMatchers.withText;
import static org.hamcrest.core.IsNull.notNullValue;
import static org.junit.Assert.assertThat;

@RunWith(AndroidJUnit4.class)
public class MainActivityInstrumentedTest {

    @Rule
    public ActivityTestRule<MainActivity> mainActivityTestRule =
            new ActivityTestRule<>(MainActivity.class);

    @Test
    public void buttonClick_refreshMainActivity() {
        onView((withId(R.id.activity_main_iv_refresh))).perform(click());
    }

    @Test
    public void ensureDataIsPresent() throws Exception {
        MainActivity activity = mainActivityTestRule.getActivity();
        View viewById = activity.findViewById(R.id.activity_main_rv);
        assertThat(viewById, notNullValue());

        if (getFeedsCount(viewById) > 0) {
            Log.e("@Test", "feeds loaded successfully");
        }
    }

    @Test
    public void ensureDataIsNotPresent() {
        MainActivity activity = mainActivityTestRule.getActivity();
        View viewById = activity.findViewById(R.id.activity_main_rv);
        assertThat(viewById, notNullValue());

        if (getFeedsCount(viewById) == 0) {

            Log.e("@Test", "feeds not available");

            View errorViewById = activity.findViewById(R.id.activity_main_tv_error);
            assertThat(errorViewById, notNullValue());
            if (Utils.getDataConnection(activity))
                onView(withId(R.id.activity_main_tv_error)).check(matches(withText(R.string.no_feeds_available)));
            else
                onView(withId(R.id.activity_main_tv_error)).check(matches(withText(R.string.no_internet)));
        }
    }


    private int getFeedsCount(View viewById) {
        RecyclerView recyclerView = (RecyclerView) viewById;

        if (recyclerView.getAdapter() == null)
            return 0;
        else
            return recyclerView.getAdapter().getItemCount();
    }
}

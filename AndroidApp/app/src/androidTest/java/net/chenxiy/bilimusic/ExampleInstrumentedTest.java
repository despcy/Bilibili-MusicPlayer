package net.chenxiy.bilimusic;

import android.content.Intent;

import net.chenxiy.bilimusic.view.AlbumDetailActivity;

import androidx.test.rule.ActivityTestRule;
import androidx.test.runner.AndroidJUnit4;

import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

/**
 * Instrumented test, which will execute on an Android device.
 *
 * @see <a href="http://d.android.com/tools/testing">Testing documentation</a>
 */
@RunWith(AndroidJUnit4.class)
public class ExampleInstrumentedTest {


    @Rule
    public ActivityTestRule<AlbumDetailActivity> activityRule
            = new ActivityTestRule<>(
            AlbumDetailActivity.class,
            true,     // initialTouchMode
            false);   // launchActivity. False to customize the intent

    @Test
    public void intent () {
        Intent intent = new Intent();
        intent.putExtra("your_key", "your_value");

        activityRule.launchActivity(intent);
        try {
            Thread.sleep(1000000000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }


        // Continue with your test
    }


}

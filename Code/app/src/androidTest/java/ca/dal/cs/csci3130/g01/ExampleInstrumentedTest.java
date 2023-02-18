package ca.dal.cs.csci3130.g01;

import static org.hamcrest.Matchers.allOf;
import static org.junit.Assert.assertEquals;

import android.content.Context;
import android.view.View;

import androidx.recyclerview.widget.RecyclerView;
import androidx.test.espresso.UiController;
import androidx.test.espresso.ViewAction;
import androidx.test.espresso.intent.Intents;
import androidx.test.ext.junit.rules.ActivityScenarioRule;
import androidx.test.ext.junit.runners.AndroidJUnit4;
import androidx.test.platform.app.InstrumentationRegistry;
import static androidx.test.espresso.Espresso.onView;
import static androidx.test.espresso.assertion.ViewAssertions.matches;
import static androidx.test.espresso.contrib.RecyclerViewActions.actionOnItemAtPosition;
import static androidx.test.espresso.matcher.ViewMatchers.hasMinimumChildCount;
import static androidx.test.espresso.matcher.ViewMatchers.isAssignableFrom;
import static androidx.test.espresso.matcher.ViewMatchers.isDisplayed;
import static androidx.test.espresso.matcher.ViewMatchers.withId;



import org.hamcrest.Matcher;
import org.junit.AfterClass;
import org.junit.BeforeClass;
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
    public ActivityScenarioRule<ProvidersListings> activityScenarioRule = new ActivityScenarioRule<>(ProvidersListings.class);

    @BeforeClass
    public static void setup() {
        Intents.init();
    }

    @AfterClass
    public static void destroy() {
        System.gc();
    }

    @Test
    public void useAppContext() {
        // Context of the app under test.
        Context appContext = InstrumentationRegistry.getInstrumentation().getTargetContext();
        assertEquals("ca.dal.cs.csci3130.g01", appContext.getPackageName());
    }

    /**
     * UAT-7 1
     */

    /**
     * This Tests if the recyclers contents are displayed
     */
    @Test
    public void testRecyclerIsDisplayed(){

        onView(withId(R.id.recyclerView)).check(matches(hasMinimumChildCount(1)));
    }

    /**
     * This tests if the button on recycler view works and transfers to ItemDetails.class
     */
    @Test
    public void testItemDetails(){

        onView(withId(R.id.recyclerView)).perform(actionOnItemAtPosition(0, clickChild(R.id.viewItem)));

        onView(withId(R.id.productTitle)).check(matches(isDisplayed()));
        onView(withId(R.id.productDesp)).check(matches(isDisplayed()));
    }

    // DO NOT TOUCH

    /**
     * This method is for creating a custom click action on recycler view
     * @param id the button ID
     * @return The customized click action
     */
    private static ViewAction clickChild (int id){
        return new ViewAction() {
            @Override
            public String getDescription() {
                return "Click on child at position";
            }

            @Override
            public Matcher<View> getConstraints() {
                return allOf(isAssignableFrom(RecyclerView.class), isDisplayed());
            }

            @Override
            public void perform(UiController uiController, View view) {
                view.findViewById(id).performClick();
            }
        };
    }
}
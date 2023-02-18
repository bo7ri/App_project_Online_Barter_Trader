package ca.dal.cs.csci3130.g01;

import static org.hamcrest.Matchers.allOf;
import static org.hamcrest.Matchers.is;
import static org.junit.Assert.assertEquals;

import android.content.Context;
import android.widget.SearchView;

import androidx.appcompat.view.menu.ActionMenuItemView;
import androidx.test.espresso.intent.Intents;
import androidx.test.ext.junit.rules.ActivityScenarioRule;
import androidx.test.ext.junit.runners.AndroidJUnit4;
import androidx.test.platform.app.InstrumentationRegistry;
import static androidx.test.espresso.Espresso.onView;
import static androidx.test.espresso.action.ViewActions.click;
import static androidx.test.espresso.action.ViewActions.pressImeActionButton;
import static androidx.test.espresso.action.ViewActions.replaceText;
import static androidx.test.espresso.action.ViewActions.typeText;
import static androidx.test.espresso.assertion.ViewAssertions.matches;
import static androidx.test.espresso.contrib.RecyclerViewActions.actionOnItemAtPosition;
import static androidx.test.espresso.matcher.ViewMatchers.hasMinimumChildCount;
import static androidx.test.espresso.matcher.ViewMatchers.isAssignableFrom;
import static androidx.test.espresso.matcher.ViewMatchers.isDisplayed;
import static androidx.test.espresso.matcher.ViewMatchers.isEnabled;
import static androidx.test.espresso.matcher.ViewMatchers.withId;


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
     * AT-6
     */
    @Test
    public void searchGoodByNameIsAvailable(){

        onView(withId(R.id.search)).perform(click());
        onView(withId(androidx.appcompat.R.id.search_src_text))
                .perform(typeText("Chair"), pressImeActionButton());
        onView(withId(R.id.recyclerView)).check(matches(hasMinimumChildCount(1)));
    }

    @Test
    public void searchGoodByNameIsNotAvailable(){
        onView(withId(R.id.search)).perform(click());
        onView(withId(androidx.appcompat.R.id.search_src_text))
                .perform(typeText("Wooden chair"), pressImeActionButton());

        onView(withId(R.id.recyclerView)).check(matches(hasMinimumChildCount(0)));
    }


    /**
     * AT-7
     */
    /**
     * This Tests if the recyclers contents are displayed
     */
    @Test
    public void testContentsAreDisplayed(){

        onView(withId(R.id.recyclerView)).check(matches(hasMinimumChildCount(1)));
    }

    /**
     * This tests if the button on recycler view works and transfers to ItemDetails.class
     */
    @Test
    public void testItemDetails(){

        onView(withId(R.id.recyclerView)).perform(actionOnItemAtPosition(0, click()));

        onView(withId(R.id.productTitle)).check(matches(isDisplayed()));
        onView(withId(R.id.productDesp)).check(matches(isDisplayed()));
    }


}
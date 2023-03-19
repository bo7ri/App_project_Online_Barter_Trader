package ca.dal.cs.csci3130.g01;

import static androidx.test.espresso.Espresso.onView;
import static androidx.test.espresso.action.ViewActions.click;
import static androidx.test.espresso.action.ViewActions.pressImeActionButton;
import static androidx.test.espresso.action.ViewActions.typeText;
import static androidx.test.espresso.assertion.ViewAssertions.matches;
import static androidx.test.espresso.contrib.RecyclerViewActions.actionOnItemAtPosition;
import static androidx.test.espresso.matcher.ViewMatchers.hasChildCount;
import static androidx.test.espresso.matcher.ViewMatchers.hasMinimumChildCount;
import static androidx.test.espresso.matcher.ViewMatchers.isDisplayed;
import static androidx.test.espresso.matcher.ViewMatchers.withId;
import static androidx.test.espresso.matcher.ViewMatchers.withText;
import static androidx.test.platform.app.InstrumentationRegistry.getInstrumentation;
import static org.junit.Assert.assertEquals;

import android.content.Context;

import androidx.test.espresso.intent.Intents;

import androidx.test.ext.junit.rules.ActivityScenarioRule;
import androidx.test.ext.junit.runners.AndroidJUnit4;



import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

@RunWith(AndroidJUnit4.class)
public class RecyclerViewTest {

    @Rule
    public ActivityScenarioRule<ProvidersListings> providersListingsRule = new ActivityScenarioRule<>(ProvidersListings.class);


    @BeforeClass
    public static void setup() {

        Intents.init();
    }

    @AfterClass
    public static void tearDown() {
        System.gc();
    }

    @Test
    public void useAppContext() {
        // Context of the app under test.
        Context appContext = getInstrumentation().getTargetContext();
        assertEquals("ca.dal.cs.csci3130.g01", appContext.getPackageName());
    }


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
                .perform(typeText("Wooden table"), pressImeActionButton());

        onView(withId(R.id.recyclerView)).check(matches(hasChildCount(0)));
    }

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

    @Test
    public void SearchSortItems(){

        onView(withId(R.id.search)).perform(click());
        onView(withId(androidx.appcompat.R.id.search_src_text))
                .perform(typeText("Chair"), pressImeActionButton());
        onView(withId(R.id.sortBtn)).perform(click());
        onView(withId(R.id.recyclerView)).perform(actionOnItemAtPosition(0, click()));
        onView(withId(R.id.productTitle)).check(matches(withText("Chair")));
    }


    @Test
    public void sortItemsAscending(){

        onView(withId(R.id.sortBtn)).perform(click());
        onView(withId(R.id.recyclerView)).perform(actionOnItemAtPosition(0, click()));
        onView(withId(R.id.productTitle)).check(matches(withText("ABC")));
    }

    @Test
    public void sortItemsDescending(){

        onView(withId(R.id.sortBtn)).perform(click());
        onView(withId(R.id.sortBtn)).perform(click());
        onView(withId(R.id.recyclerView)).perform(actionOnItemAtPosition(0, click()));
        onView(withId(R.id.productTitle)).check(matches(withText("XYZ")));
    }
}

package ca.dal.cs.csci3130.g01;

import static androidx.test.espresso.Espresso.onView;
import static androidx.test.espresso.action.ViewActions.click;
import static androidx.test.espresso.action.ViewActions.replaceText;
import static androidx.test.espresso.assertion.ViewAssertions.matches;
import static androidx.test.espresso.intent.Intents.intended;
import static androidx.test.espresso.intent.matcher.IntentMatchers.hasComponent;
import static androidx.test.espresso.matcher.ViewMatchers.withId;
import static androidx.test.espresso.matcher.ViewMatchers.withText;

import android.content.Context;

import androidx.test.espresso.intent.rule.IntentsTestRule;
import androidx.test.ext.junit.rules.ActivityScenarioRule;
import androidx.test.platform.app.InstrumentationRegistry;
import androidx.test.ext.junit.runners.AndroidJUnit4;
import androidx.test.espresso.intent.Intents;

import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import static org.hamcrest.Matchers.not;
import static org.junit.Assert.*;

/**
 * Instrumented test, which will execute on an Android device.
 *
 * @see <a href="http://d.android.com/tools/testing">Testing documentation</a>
 */
@RunWith(AndroidJUnit4.class)
public class ExampleInstrumentedTest {

    @Rule
    public ActivityScenarioRule<LoginPage> myRule = new ActivityScenarioRule<>(LoginPage.class);
    public IntentsTestRule<LoginPage> myIntentRule = new IntentsTestRule<>(LoginPage.class);

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
        Context appContext = InstrumentationRegistry.getInstrumentation().getTargetContext();
        assertEquals("ca.dal.cs.csci3130.g01", appContext.getPackageName());
    }

    //@Test
//    public void checkIfSwitchedToListPage() {
//        onView(withId(R.id.Login)).perform(click());
//        intended(hasComponent(ProvidersListings.class.getName()));
//    }

//    @Test
//    public void checkIfIncorrectLogin(){
//        onView(withId(R.id.Username)).perform(replaceText("admin"));
//        onView(withId(R.id.Password)).perform(replaceText("123"));
//        onView(withId(R.id.Login)).perform(click());
//        onView(withId(R.id.LoginStatusText)).check(matches(withText("Invalid Login!")));
//    }


    // Checks if fields are not empty and submits correctly.
//    @Test
//    public void checkIfFieldsNotEmpty() {
//        onView(withId(R.id.registerPageButton)).perform(click());
//        onView(withId(R.id.registerUsernameField)).perform(replaceText("BLAbla"));
//        onView(withId(R.id.registerPasswordField)).perform(replaceText("password333"));
//        onView(withId(R.id.registerFirstNameField)).perform(replaceText("Blae"));
//        onView(withId(R.id.registerLastNameField)).perform(replaceText("Orange"));
//        onView(withId(R.id.registerEmailField)).perform(replaceText("blabla@orange.com"));
//        onView(withId(R.id.registerUserTypeField)).perform(replaceText("Provider"));
//        onView(withId(R.id.registerSubmitButton)).perform(click());
//        onView(withId(R.id.registerPageButton)).check(matches(isDisplayed()));
//    }

    // Checks if one field is empty and doesn't move to another page.
//    @Test
//    public void checkIfFieldsAreEmpty() {
//        onView(withId(R.id.registerPageButton)).perform(click());
//        onView(withId(R.id.registerUsernameField)).perform(replaceText("BLAbla"));
//        onView(withId(R.id.registerPasswordField)).perform(replaceText("password333"));
//        onView(withId(R.id.registerFirstNameField)).perform(replaceText("Blae"));
//        onView(withId(R.id.registerLastNameField)).perform(replaceText("Orange"));
//        onView(withId(R.id.registerEmailField)).perform(replaceText(" "));
//        onView(withId(R.id.registerUserTypeField)).perform(replaceText("Provider"));
//        onView(withId(R.id.registerSubmitButton)).perform(click());
//        onView(withId(R.id.registerationTestMessage)).check(matches(withText("One or more fields are empty!")));
//    }

    // Checks if switches correctly to register page after button is clicked.
//    @Test
//    public void checkIfSwitchedToRegisterPage() {
//        onView(withId(R.id.registerPageButton)).perform(click());
//        intended(hasComponent(RegisterPage.class.getName()));
//    }

}

package ca.dal.cs.csci3130.g01;

import com.google.firebase.FirebaseApp;


import static org.junit.Assert.assertEquals;
import static androidx.test.espresso.Espresso.onView;
import static androidx.test.espresso.action.ViewActions.clearText;
import static androidx.test.espresso.action.ViewActions.click;
import static androidx.test.espresso.action.ViewActions.closeSoftKeyboard;
import static androidx.test.espresso.action.ViewActions.replaceText;
import static androidx.test.espresso.assertion.ViewAssertions.matches;
import static androidx.test.espresso.intent.Intents.intended;
import static androidx.test.espresso.intent.matcher.IntentMatchers.hasComponent;
import static androidx.test.espresso.matcher.ViewMatchers.isDisplayed;
import static androidx.test.espresso.matcher.ViewMatchers.withId;
import static androidx.test.espresso.matcher.ViewMatchers.withText;

import android.content.Context;


import androidx.test.espresso.action.ViewActions;
import androidx.test.espresso.intent.Intents;
import androidx.test.ext.junit.rules.ActivityScenarioRule;
import androidx.test.ext.junit.runners.AndroidJUnit4;
import androidx.test.filters.LargeTest;
import static androidx.test.espresso.action.ViewActions.pressImeActionButton;
import static androidx.test.espresso.action.ViewActions.typeText;
import static androidx.test.espresso.contrib.RecyclerViewActions.actionOnItemAtPosition;
import static androidx.test.espresso.matcher.ViewMatchers.hasMinimumChildCount;
import static androidx.test.platform.app.InstrumentationRegistry.getInstrumentation;


import org.junit.AfterClass;
import org.junit.BeforeClass;

import androidx.test.espresso.intent.rule.IntentsTestRule;

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
@LargeTest
public class ExampleInstrumentedTest {


    @Rule
    public ActivityScenarioRule<LoginPage> longinRule = new ActivityScenarioRule<>(LoginPage.class);
//    @Rule
//    public ActivityScenarioRule<ProvidersListings> providersListingsRule = new ActivityScenarioRule<>(ProvidersListings.class);
//    @Rule
//    public  ActivityScenarioRule<Profile> profileRule = new ActivityScenarioRule<>(Profile.class);
//    @Rule
//    public ActivityScenarioRule<AddProduct> addProductRule = new ActivityScenarioRule<>(AddProduct.class);
//    @Rule
//    public ActivityScenarioRule<ItemDetails> itemDetailsRule = new ActivityScenarioRule<>(ItemDetails.class);

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
    /**
     * AT-5
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


    /**
     * This will test if the labels information are showed in the profile
     */
    @Test
    public void testProfileLabels() {

        onView(withId(R.id.profile)).perform(click());

//        onView(withId(R.id.last_name_edit_text)).check(matches(isDisplayed()));
//        onView(withId(R.id.first_name_edit_text)).check(matches(isDisplayed()));
//        onView(withId(R.id.email_edit_text)).check(matches(isDisplayed()));
//        onView(withId(R.id.username_edit_text)).check(matches(isDisplayed()));
//        onView(withId(R.id.goods)).check(matches(isDisplayed()));
//        onView(withId(R.id.user_type_edit_text)).check(matches(isDisplayed()));
    }

    @Test
    public void testProfileInputs() {}

    // Checks if fields are not empty and submits correctly.
    @Test
    public void checkIfFieldsNotEmpty() {
        onView(withId(R.id.RegisterButton)).perform(click());
        onView(withId(R.id.registerUsernameField)).perform(replaceText("BLAbla"));
        onView(withId(R.id.registerPasswordField)).perform(replaceText("password333"));
        onView(withId(R.id.registerFirstNameField)).perform(replaceText("Blae"));
        onView(withId(R.id.registerLastNameField)).perform(replaceText("Orange"));
        onView(withId(R.id.registerEmailField)).perform(replaceText("blabla@orange.com"));
        onView(withId(R.id.registerUserTypeField)).perform(replaceText("Provider"));
        onView(withId(R.id.registerSubmitButton)).perform(click());
        onView(withId(R.id.RegisterButton)).check(matches(isDisplayed()));
    }

    // Checks if one field is empty and doesn't move to another page.
    @Test
    public void checkIfFieldsAreEmpty() {
        onView(withId(R.id.RegisterButton)).perform(click());
        onView(withId(R.id.registerUsernameField)).perform(replaceText("BLAbla"));
        onView(withId(R.id.registerPasswordField)).perform(replaceText("password333"));
        onView(withId(R.id.registerFirstNameField)).perform(replaceText("Blae"));
        onView(withId(R.id.registerLastNameField)).perform(replaceText("Orange"));
        onView(withId(R.id.registerEmailField)).perform(replaceText(" "));
        onView(withId(R.id.registerUserTypeField)).perform(replaceText("Provider"));
        onView(withId(R.id.registerSubmitButton)).perform(click());
        onView(withId(R.id.registerationTestMessage)).check(matches(withText("One or more fields are empty!")));
    }

    // Checks if switches correctly to register page after button is clicked.
    @Test
    public void checkIfSwitchedToRegisterPage() {
        onView(withId(R.id.RegisterButton)).perform(click());
        intended(hasComponent(RegisterPage.class.getName()));
    }

    @Test
    public void checkIfSwitchedToListPage() {
        onView(withId(R.id.Login)).perform(click());
        intended(hasComponent(ProvidersListings.class.getName()));
    }

    @Test
    public void checkIfIncorrectLogin() {
        onView(withId(R.id.Username)).perform(typeText("admin"));
        onView(withId(R.id.Password)).perform(typeText("123"));
        onView(withId(R.id.Login)).perform(click());
        onView(withId(R.id.LoginStatusText)).check(matches(withText("Invalid Login!")));
    }
//    @Test
//    public void testSendEmail() {
//        String senderEmail = "test12appproject@gmail.com";
//        String senderPassword = "wlkhkntczgufmqlh";
//
//        // enter any email u wish to check
//        String receiverEmail = "monther.s122@gmail.com";
//        String subject = "Test 2";
//        String message = "This is a massage from the app";
//
//        onView(withId(R.id.et_from_email)).perform(clearText(), typeText(senderEmail), closeSoftKeyboard());
//        onView(withId(R.id.et_password)).perform(clearText(), typeText(senderPassword), closeSoftKeyboard());
//        onView(withId(R.id.et_to_email)).perform(clearText(), typeText(receiverEmail), closeSoftKeyboard());
//        onView(withId(R.id.et_subject)).perform(clearText(), typeText(subject), closeSoftKeyboard());
//        onView(withId(R.id.et_message)).perform(clearText(), typeText(message), closeSoftKeyboard());
//
//        onView(withId(R.id.btn_send)).perform(ViewActions.click());
//    }
}
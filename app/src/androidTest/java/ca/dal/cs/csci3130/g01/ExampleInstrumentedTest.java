package ca.dal.cs.csci3130.g01;



import static org.junit.Assert.assertEquals;
import static androidx.test.espresso.Espresso.onView;
import static androidx.test.espresso.action.ViewActions.clearText;
import static androidx.test.espresso.action.ViewActions.click;
import static androidx.test.espresso.action.ViewActions.closeSoftKeyboard;
import static androidx.test.espresso.action.ViewActions.pressImeActionButton;
import static androidx.test.espresso.action.ViewActions.replaceText;
import static androidx.test.espresso.assertion.ViewAssertions.matches;
import static androidx.test.espresso.contrib.RecyclerViewActions.actionOnItemAtPosition;
import static androidx.test.espresso.intent.Intents.intended;
import static androidx.test.espresso.intent.matcher.IntentMatchers.hasComponent;
import static androidx.test.espresso.matcher.ViewMatchers.hasChildCount;
import static androidx.test.espresso.matcher.ViewMatchers.hasDescendant;
import static androidx.test.espresso.matcher.ViewMatchers.hasMinimumChildCount;
import static androidx.test.espresso.matcher.ViewMatchers.isClickable;
import static androidx.test.espresso.matcher.ViewMatchers.isDisplayed;
import static androidx.test.espresso.matcher.ViewMatchers.isEnabled;
import static androidx.test.espresso.matcher.ViewMatchers.isNotEnabled;
import static androidx.test.espresso.matcher.ViewMatchers.withId;
import static androidx.test.espresso.matcher.ViewMatchers.withText;


import android.content.Context;


import androidx.recyclerview.widget.RecyclerView;
import androidx.test.espresso.action.ViewActions;
import androidx.test.espresso.contrib.RecyclerViewActions;
import androidx.test.espresso.intent.Intents;
import androidx.test.espresso.intent.rule.IntentsRule;

import androidx.test.ext.junit.rules.ActivityScenarioRule;
import androidx.test.ext.junit.runners.AndroidJUnit4;



import static androidx.test.espresso.action.ViewActions.typeText;
import static androidx.test.platform.app.InstrumentationRegistry.getInstrumentation;


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
    public ActivityScenarioRule<LoginPage> longinRule = new ActivityScenarioRule<>(LoginPage.class);
    public IntentsRule intentsRule = new IntentsRule();
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
    public void checkIfIncorrectLogin() {
        onView(withId(R.id.Username)).perform(typeText("admin"));
        onView(withId(R.id.Password)).perform(typeText("123"), closeSoftKeyboard());
        onView(withId(R.id.Login)).perform(click());
        onView(withId(R.id.LoginStatusText)).check(matches(withText(R.string.INVALID_LOGIN)));

    }

    @Test
    public void checkIfSwitchedToListPage() {
        onView(withId(R.id.Username)).perform(typeText("admin"));
        onView(withId(R.id.Password)).perform(typeText("1234"), closeSoftKeyboard());
        onView(withId(R.id.Login)).perform(click());
    }

    @Test
    public void checkIfSwitchedToSavedPage(){
        onView(withId(R.id.Username)).perform(typeText("ProviderAdmin"));
        onView(withId(R.id.Password)).perform(typeText("admin4321"));
        onView(withId(R.id.Login)).perform(click());

        onView(withId(R.id.savedItems)).perform(click());
        intended(hasComponent(SavedItems.class.getName()));
    }



    /**
     * This will test if the labels information are showed in the profile
     */
    @Test
    public void testProfileLabels() {

        onView(withId(R.id.Username)).perform(typeText("admin"));
        onView(withId(R.id.Password)).perform(typeText("1234"), closeSoftKeyboard());
        onView(withId(R.id.Login)).perform(click());

        onView(withId(R.id.profile)).perform(click());

        onView(withId(R.id.last_name_edit_text)).check(matches(isDisplayed()));
        onView(withId(R.id.first_name_edit_text)).check(matches(isDisplayed()));
        onView(withId(R.id.email_edit_text)).check(matches(isDisplayed()));
        onView(withId(R.id.username_edit_text)).check(matches(isDisplayed()));
        onView(withId(R.id.goods)).check(matches(isDisplayed()));
        onView(withId(R.id.user_type_edit_text)).check(matches(isDisplayed()));
    }



    // Checks if switches correctly to register page after button is clicked.
    @Test
    public void checkIfSwitchedToRegisterPage() {
        onView(withId(R.id.RegisterButton)).perform(click());
        intended(hasComponent(RegisterPage.class.getName()));
    }
    

    @Test
    public void switchToAddProduct() {
        onView(withId(R.id.Username)).perform(replaceText("admin"));
        onView(withId(R.id.Password)).perform(replaceText("1234"));
        onView(withId(R.id.Login)).perform(click());
        onView(withId(R.id.productAddBtn)).perform(click());
    }

    @Test
    public void testToolbarIsDisplayed() {
        onView(withId(R.id.Username)).perform(replaceText("admin"));
        onView(withId(R.id.Password)).perform(replaceText("1234"));
        onView(withId(R.id.Login)).perform(click());
        onView(withId(R.id.toolBar)).check(matches(isDisplayed()));
    }

    @Test
    public void testAddProductIsNotEmpty() {
        onView(withId(R.id.Username)).perform(replaceText("providerAdmin"));
        onView(withId(R.id.Password)).perform(replaceText("password123"));
        onView(withId(R.id.Login)).perform(click());
        onView(withId(R.id.productAddBtn)).perform(click());
        onView(withId(R.id.addProductTitle)).perform(replaceText("iPhone 11"));
        onView(withId(R.id.addProductDescription)).perform(replaceText("This is the description for the iPhone 11."));
        onView(withId(R.id.addProductPrice)).perform(replaceText("101"));
        onView(withId(R.id.submitAddProduct)).perform(click());
    }

    @Test
    public void testAddProductIsEmpty() {
        onView(withId(R.id.Username)).perform(replaceText("providerAdmin"));
        onView(withId(R.id.Password)).perform(replaceText("password123"));
        onView(withId(R.id.Login)).perform(click());
        onView(withId(R.id.productAddBtn)).perform(click());
        onView(withId(R.id.addProductTitle)).perform(replaceText("iPhone 11"));
        onView(withId(R.id.addProductDescription)).perform(replaceText(""));
        onView(withId(R.id.addProductPrice)).perform(replaceText("100"));
        onView(withId(R.id.submitAddProduct)).perform(click());
        onView(withId(R.id.submitAddProduct)).check(matches(isDisplayed()));
    }


    @Test
    public void testInboxAndSendEmail() {
        // Login
        onView(withId(R.id.Username)).perform(replaceText("maaar"));
        onView(withId(R.id.Password)).perform(replaceText("nicepassword"));
        closeSoftKeyboard();
        onView(withId(R.id.Login)).perform(click());
        onView(withId(R.id.profile)).perform(click());
        onView(withId(R.id.send_email)).perform(click());
        onView(withId(R.id.recipient_edit_text)).perform(replaceText("blabla@orange.com"));
        onView(withId(R.id.subject_edit_text)).perform(replaceText("Test Subject"));
        onView(withId(R.id.body_edit_text)).perform(replaceText("Test Body"));
        closeSoftKeyboard();
        onView(withId(R.id.send_button)).perform(click());
    }
    @Test
    public void testInbox() {
        // Login
        onView(withId(R.id.Username)).perform(replaceText("maaar"));
        onView(withId(R.id.Password)).perform(replaceText("nicepassword"));
        closeSoftKeyboard();
        onView(withId(R.id.Login)).perform(click());
        onView(withId(R.id.profile)).perform(click());
        onView(withId(R.id.view_inbox)).perform(click());
        onView(withId(R.id.recyclerView)).check(matches(isDisplayed()));
        onView(withId(R.id.recyclerView))
                .perform(RecyclerViewActions.actionOnItemAtPosition(0, click()));
    }

    @Test
    public void providerPageAsReceiverUser(){
        onView(withId(R.id.Username)).perform(typeText("admin"));
        onView(withId(R.id.Password)).perform(typeText("1234"), closeSoftKeyboard());
        onView(withId(R.id.Login)).perform(click());

        onView(withId(R.id.recyclerView)).perform(actionOnItemAtPosition(0, click()));

        onView(withId(R.id.providerProfilePage)).perform(click());

        intended(hasComponent(ProviderPage.class.getName()));
    }

    @Test
    public void providerPageAsProviderUser(){

        onView(withId(R.id.Username)).perform(typeText("providerAdmin"));
        onView(withId(R.id.Password)).perform(typeText("password123"), closeSoftKeyboard());
        onView(withId(R.id.Login)).perform(click());

        onView(withId(R.id.recyclerView)).perform(actionOnItemAtPosition(0, click()));

        onView(withId(R.id.providerProfilePage)).check(matches(isNotEnabled()));

    }

    @Test
    public void testSwitchToRequestPageAsReceiver() {
        onView(withId(R.id.Username)).perform(typeText("admin"));
        onView(withId(R.id.Password)).perform(typeText("1234"), closeSoftKeyboard());
        onView(withId(R.id.Login)).perform(click());
        onView(withId(R.id.recyclerView)).perform(actionOnItemAtPosition(0, click()));
        onView(withId(R.id.sendRequestBtn)).perform(click());
        onView(withId(R.id.sendRequestSubmitBtn)).check(matches(isDisplayed()));
    }

    @Test
    public void testSwitchToRequestPageAsProvider() {
        onView(withId(R.id.Username)).perform(typeText("providerAdmin"));
        onView(withId(R.id.Password)).perform(typeText("password123"), closeSoftKeyboard());
        onView(withId(R.id.Login)).perform(click());
        onView(withId(R.id.recyclerView)).perform(actionOnItemAtPosition(0, click()));
        onView(withId(R.id.sendRequestBtn)).perform(click());
        onView(withId(R.id.toolBar)).check(matches(isDisplayed()));
    }

    @Test
    public void testRequestButtonSend() {
        onView(withId(R.id.Username)).perform(typeText("admin"));
        onView(withId(R.id.Password)).perform(typeText("1234"), closeSoftKeyboard());
        onView(withId(R.id.Login)).perform(click());
        onView(withId(R.id.recyclerView)).perform(actionOnItemAtPosition(0, click()));
        onView(withId(R.id.sendRequestBtn)).perform(click());
        onView(withId(R.id.sendRequestSubmitBtn)).check(matches(isClickable()));
    }

    @Test
    public void testRequestButtonReject() {
        onView(withId(R.id.Username)).perform(typeText("admin"));
        onView(withId(R.id.Password)).perform(typeText("1234"), closeSoftKeyboard());
        onView(withId(R.id.Login)).perform(click());
        onView(withId(R.id.recyclerView)).perform(actionOnItemAtPosition(0, click()));
        onView(withId(R.id.sendRequestBtn)).perform(click());
        onView(withId(R.id.sendRequestCancelBtn)).check(matches(isClickable()));
    }

    @Test
    public void searchGoodByNameIsAvailable(){

        onView(withId(R.id.Username)).perform(typeText("admin"));
        onView(withId(R.id.Password)).perform(typeText("1234"), closeSoftKeyboard());
        onView(withId(R.id.Login)).perform(click());

        onView(withId(R.id.search)).perform(click());
        onView(withId(androidx.appcompat.R.id.search_src_text))
                .perform(typeText("Chair"), pressImeActionButton());
        onView(withId(R.id.recyclerView)).check(matches(hasMinimumChildCount(1)));
    }

    @Test
    public void searchGoodByNameIsNotAvailable(){

        onView(withId(R.id.Username)).perform(typeText("admin"));
        onView(withId(R.id.Password)).perform(typeText("1234"), closeSoftKeyboard());
        onView(withId(R.id.Login)).perform(click());

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

        onView(withId(R.id.Username)).perform(typeText("admin"));
        onView(withId(R.id.Password)).perform(typeText("1234"), closeSoftKeyboard());
        onView(withId(R.id.Login)).perform(click());
        onView(withId(R.id.recyclerView)).check(matches(hasMinimumChildCount(1)));
    }

    /**
     * This tests if the button on recycler view works and transfers to ItemDetails.class
     */
    @Test
    public void testItemDetails(){

        onView(withId(R.id.Username)).perform(typeText("admin"));
        onView(withId(R.id.Password)).perform(typeText("1234"), closeSoftKeyboard());
        onView(withId(R.id.Login)).perform(click());

        onView(withId(R.id.recyclerView)).perform(actionOnItemAtPosition(0, click()));

        onView(withId(R.id.productTitle)).check(matches(isDisplayed()));
        onView(withId(R.id.productDesp)).check(matches(isDisplayed()));
    }

    @Test
    public void SearchSortItems(){

        onView(withId(R.id.Username)).perform(typeText("admin"));
        onView(withId(R.id.Password)).perform(typeText("1234"), closeSoftKeyboard());
        onView(withId(R.id.Login)).perform(click());

        onView(withId(R.id.search)).perform(click());
        onView(withId(androidx.appcompat.R.id.search_src_text))
                .perform(typeText("Chair"), pressImeActionButton());
        onView(withId(R.id.sortBtn)).perform(click());
        onView(withId(R.id.recyclerView)).perform(actionOnItemAtPosition(0, click()));
        onView(withId(R.id.productTitle)).check(matches(withText("Chair")));
    }


    @Test
    public void sortItemsAscending(){

        onView(withId(R.id.Username)).perform(typeText("admin"));
        onView(withId(R.id.Password)).perform(typeText("1234"), closeSoftKeyboard());
        onView(withId(R.id.Login)).perform(click());

        onView(withId(R.id.sortBtn)).perform(click());
        onView(withId(R.id.recyclerView)).perform(actionOnItemAtPosition(0, click()));
        onView(withId(R.id.productTitle)).check(matches(withText("ABC")));
    }

    @Test
    public void sortItemsDescending(){

        onView(withId(R.id.Username)).perform(typeText("admin"));
        onView(withId(R.id.Password)).perform(typeText("1234"), closeSoftKeyboard());
        onView(withId(R.id.Login)).perform(click());

        onView(withId(R.id.sortBtn)).perform(click());
        onView(withId(R.id.sortBtn)).perform(click());
        onView(withId(R.id.recyclerView)).perform(actionOnItemAtPosition(0, click()));
        onView(withId(R.id.productTitle)).check(matches(withText("Z")));
    }

}
package ca.dal.cs.csci3130.g01;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotEquals;
import static org.junit.Assert.assertTrue;

import org.junit.After;
import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.mockito.junit.MockitoJUnitRunner;

/**
 * Example local unit test, which will execute on the development machine (host).
 *
 * @see <a href="http://d.android.com/tools/testing">Testing documentation</a>
 */
@RunWith(MockitoJUnitRunner.Silent.class)
public class ExampleUnitTest {


    static RegisterPage registerPageFirst;

    @BeforeClass
    public static void setup() {

        // This is mocking registerPageFirst.
        registerPageFirst = Mockito.mock(RegisterPage.class);
        Mockito.when(registerPageFirst.getFirstName()).thenReturn("Jackson");
        Mockito.when(registerPageFirst.getLastName()).thenReturn("Thor");
        Mockito.when(registerPageFirst.getPassword()).thenReturn("averylongpassword817");
        Mockito.when(registerPageFirst.getUsername()).thenReturn("JThor111");
        Mockito.when(registerPageFirst.getEmailAddress()).thenReturn("jthor111@gmail.com");
        Mockito.when(registerPageFirst.getUserType()).thenReturn("Provider");
        Mockito.when(registerPageFirst.isValidUserType("Provider")).thenReturn(true);
        Mockito.when(registerPageFirst.isValidEmailAddress("jthor111@gmail.com")).thenReturn(true);

    }

    @Test
    public void addition_isCorrect() {
        assertEquals(4, 2 + 2);
    }

    @Test
    public void testGetProductTitle() {
//        Product product = new Product("iPhone12", "This is a description");
//        assertEquals("iPhone12", product.getTitle());
    }

    @Test
    public void testGetProductDescription() {
//        Product product = new Product("SamsungS48193", "A very nice samsung.");
//        assertEquals("A very nice samsung.", product.getDescription());
    }

    @Test
    public void testSetProductTitle() {
//        Product product = new Product("BadTitle", "A description.");
//        product.setTitle("GoodTitle");
//        assertEquals("GoodTitle", product.getTitle());
    }

    @Test
    public void testSetProductDescription() {
////        Product product = new Product("BigSofa", "BadDescription");
//        product.setDescription("GoodDescription");
//        assertEquals("GoodDescription", product.getDescription());
    }

    @Test
    public void testRegisterFirstName() {
        assertEquals("Jackson", registerPageFirst.getFirstName());
    }

    @Test
    public void testRegisterLastName() {
        assertEquals("Thor", registerPageFirst.getLastName());
    }

    @Test
    public void testRegisterPassword() {
        assertEquals("averylongpassword817", registerPageFirst.getPassword());
    }

    @Test
    public void testRegisterUsername() {
        assertEquals("JThor111", registerPageFirst.getUsername());
    }

    @Test
    public void testRegisterEmailAddress() {
        assertEquals("jthor111@gmail.com", registerPageFirst.getEmailAddress());
    }

    @Test
    public void testRegisterUserType() {
        assertEquals("Provider", registerPageFirst.getUserType());
    }

    @Test
    public void testRegisterValidUserType() {
        assertTrue(registerPageFirst.isValidUserType(registerPageFirst.getUserType()));
    }

    @Test
    public void testRegisterValidEmailAddress() {
        assertTrue(registerPageFirst.isValidEmailAddress(registerPageFirst.getEmailAddress()));
    }

    @Test
    public void testRegisterFieldsNotEmpty() {
        String username = registerPageFirst.getUsername();
        String password = registerPageFirst.getPassword();
        String firstName = registerPageFirst.getFirstName();
        String lastName = registerPageFirst.getLastName();
        String userType = registerPageFirst.getUserType();
        String email = registerPageFirst.getEmailAddress();
        assertFalse(registerPageFirst.isRegisterFieldsEmpty(username, password, firstName, lastName, email, userType));
    }

}
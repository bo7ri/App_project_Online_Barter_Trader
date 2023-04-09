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
import org.mockito.Mock;
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
    static RequestItem requestItemFirst;

    static FavItem favItemFirst;

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

        // This is mocking requestItemFirst.
        requestItemFirst = Mockito.mock(RequestItem.class);
        Mockito.when(requestItemFirst.getReceiverUsername()).thenReturn("Messi");
        Mockito.when(requestItemFirst.getProviderUsername()).thenReturn("Ronaldo");
        Mockito.when(requestItemFirst.getProductTitle()).thenReturn("Golden Boot");
        Mockito.when(requestItemFirst.getProductDescription()).thenReturn("This is a golden boot made out of gold!");
        Mockito.when(requestItemFirst.getRequestMessage()).thenReturn("Messi is a better player than Ronaldo!");

        // This is mocking favItemFirst.
        favItemFirst = Mockito.mock(FavItem.class);
        Mockito.when(favItemFirst.getKey_id()).thenReturn("1AbD42Aa");
        Mockito.when(favItemFirst.getItem_title()).thenReturn("Golden Toothpick");
        Mockito.when(favItemFirst.getItem_image()).thenReturn(1231241);

    }

    @Test
    public void addition_isCorrect() {
        assertEquals(4, 2 + 2);
    }

    @Test
    public void testGetProductTitle() {
        Product product = new Product("iPhone12", "This is a description", "User1");
        assertEquals("iPhone12", product.getTitle());
    }

    @Test
    public void testGetProductDescription() {
        Product product = new Product("SamsungS48193", "A very nice samsung.", "User1");
        assertEquals("A very nice samsung.", product.getDescription());
    }

    @Test
    public void testSetProductTitle() {
        Product product = new Product("BadTitle", "A description.", "User1");
        product.setTitle("GoodTitle");
        assertEquals("GoodTitle", product.getTitle());
    }

    @Test
    public void testSetProductDescription() {
        Product product = new Product("BigSofa", "BadDescription", "User1");
        product.setDescription("GoodDescription");
        assertEquals("GoodDescription", product.getDescription());
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
    @Test
    public void testRequestReceiverUsername() {
        assertEquals("Messi", requestItemFirst.getReceiverUsername());
    }

    @Test
    public void testRequestProviderUsername() {
        assertEquals("Ronaldo", requestItemFirst.getProviderUsername());
    }

    @Test
    public void testRequestProductTitle() {
        assertEquals("Golden Boot", requestItemFirst.getProductTitle());
    }

    @Test
    public void testRequestProductDescription() {
        assertEquals("This is a golden boot made out of gold!", requestItemFirst.getProductDescription());
    }

    @Test
    public void testRequestRequestMessage() {
        assertEquals("Messi is a better player than Ronaldo!", requestItemFirst.getRequestMessage());
    }

    @Test
    public void testFavItemGetID() {
        assertEquals("1AbD42Aa", favItemFirst.getKey_id());
    }

    @Test
    public void testFavItemGetTitle() {
        assertEquals("Golden Toothpick", favItemFirst.getItem_title());
    }

    @Test
    public void testFavItemGetImage() {
        assertEquals(1231241, favItemFirst.getItem_image());
    }

    @Test
    public void testFavItemSetID() {
        FavItem tempFavItem = new FavItem("Cool title", "1111", 939);
        tempFavItem.setKey_id("2222");
        assertEquals("2222", tempFavItem.getKey_id());
    }

    @Test
    public void testFavItemSetTitle() {
        FavItem tempFavItem = new FavItem("Cool title", "1111", 939);
        tempFavItem.setItem_title("Very cool title");
        assertEquals("Very cool title", tempFavItem.getItem_title());
    }

    @Test
    public void testFavItemSetImage() {
        FavItem tempFavItem = new FavItem("Cool title", "1111", 939);
        tempFavItem.setItem_image(727);
        assertEquals(727, tempFavItem.getItem_image());
    }

    @Test
    public void testProductGetPrice() {
        Product tempProduct = new Product("Wooden Table", "Sample text.", "0",
                "0", 1234, "coolAdmin", 100, "Nova Scotia", "Halifax");
        assertEquals(100, tempProduct.getPrice(), 0);
    }

    @Test
    public void testProductSetPrice() {
        Product tempProduct = new Product("Wooden Table", "Sample text.", "0",
                "0", 1234, "coolAdmin", 100, "Nova Scotia", "Halifax");
        tempProduct.setPrice(88.1);
        assertEquals(88.1, tempProduct.getPrice(), 0);
    }

    @Test
    public void testProductGetKeyID() {
        Product tempProduct = new Product("Wooden Table", "Sample text.", "0",
                "0", 1234, "coolAdmin", 100, "Nova Scotia", "Halifax");
        assertEquals("0", tempProduct.getKey_id());
    }

    @Test
    public void testProductSetKeyID() {
        Product tempProduct = new Product("Wooden Table", "Sample text.", "0",
                "0", 1234, "coolAdmin", 100, "Nova Scotia", "Halifax");
        tempProduct.setKey_id("1521");
        assertEquals("1521", tempProduct.getKey_id());
    }

    @Test
    public void testProductGetFav() {
        Product tempProduct = new Product("Wooden Table", "Sample text.", "0",
                "0", 1234, "coolAdmin", 100, "Nova Scotia", "Halifax");
        assertEquals("0", tempProduct.getFavStatus());
    }

    @Test
    public void testProductSetFav() {
        Product tempProduct = new Product("Wooden Table", "Sample text.", "0",
                "0", 1234, "coolAdmin", 100, "Nova Scotia", "Halifax");
        tempProduct.setFavStatus("1");
        assertEquals("1", tempProduct.getFavStatus());
    }

    @Test
    public void testProductGetImage() {
        Product tempProduct = new Product("Wooden Table", "Sample text.", "0",
                "0", 1234, "coolAdmin", 100, "Nova Scotia", "Halifax");
        assertEquals(1234, tempProduct.getImageResource());
    }

    @Test
    public void testProductSetImage() {
        Product tempProduct = new Product("Wooden Table", "Sample text.", "0",
                "0", 1234, "coolAdmin", 100, "Nova Scotia", "Halifax");
        tempProduct.setImageResource(4321);
        assertEquals(4321, tempProduct.getImageResource());
    }

    @Test
    public void testProductGetName() {
        Product tempProduct = new Product("Wooden Table", "Sample text.", "0",
                "0", 1234, "coolAdmin", 100, "Nova Scotia", "Halifax");
        assertEquals("coolAdmin", tempProduct.getUsername());
    }

    @Test
    public void tempProductSetName() {
        Product tempProduct = new Product("Wooden Table", "Sample text.", "0",
                "0", 1234, "coolAdmin", 100, "Nova Scotia", "Halifax");
        tempProduct.setUsername("TheCoolAdmin");
        assertEquals("TheCoolAdmin", tempProduct.getUsername());
    }



}
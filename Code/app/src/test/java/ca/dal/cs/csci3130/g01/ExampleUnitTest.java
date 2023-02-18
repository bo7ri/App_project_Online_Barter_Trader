package ca.dal.cs.csci3130.g01;

import static org.junit.Assert.assertNotEquals;
import static org.junit.Assert.assertTrue;

import org.junit.After;
import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.Test;

/**
 * Example local unit test, which will execute on the development machine (host).
 *
 * @see <a href="http://d.android.com/tools/testing">Testing documentation</a>
 */
public class ExampleUnitTest {


    static ProvidersListings providersListings;

    @BeforeClass
    public static void setup() {
        providersListings = new ProvidersListings();
    }

    @AfterClass
    public static void tearDown() {
        System.gc();
    }
    @Test
    public void TestListNotEmpty(){
        providersListings.setData();
        assertNotEquals(providersListings.productList.size(), 0);
    }




}
package comp3350.CookMate.tests.business;

import static org.junit.Assert.*;

import java.io.File;
import java.io.IOException;
import java.util.LinkedList;
import java.util.List;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.rules.ExpectedException;

import comp3350.CookMate.objects.Cook;
import comp3350.CookMate.objects.DietaryRestriction;
import comp3350.CookMate.objects.NoCook;
import comp3350.CookMate.business.AccessCook;
import comp3350.CookMate.objects.YouShallNotPass;
import comp3350.CookMate.persistence.AccountManagement;
import comp3350.CookMate.persistence.HSQLDB.AccountManagementHSQLDB;
import comp3350.CookMate.persistence.HSQLDB.DuplicateAccountException;
import comp3350.CookMate.persistence.HSQLDB.RecipeManagementHSQLDB;
import comp3350.CookMate.persistence.RecipeManagement;
import comp3350.CookMate.presentation.HomeActivity;
import comp3350.CookMate.tests.CopyDB;

public class AccessCookTest {

    private AccessCook accessCook;
    private AccountManagement accountManagement;
    private RecipeManagement list;
    private File temp_Database;

    @Before
    public void setUp() throws IOException {
        this.temp_Database = CopyDB.copy_real_db();
        HomeActivity.setDBPathName(this.temp_Database.getAbsolutePath().replace(".script" , ""));
        list = new RecipeManagementHSQLDB(HomeActivity.getDBPathName());
        accountManagement = new AccountManagementHSQLDB(HomeActivity.getDBPathName());
        accessCook = new AccessCook(accountManagement);
    }

    @Test
    public void testGetAccounts() {
        List<DietaryRestriction> dietaryRestriction = new LinkedList<>();
        dietaryRestriction.add(new DietaryRestriction("Halal"));
        Cook cook1 = new Cook("user1", "name1", "password1", "anything", dietaryRestriction);
        Cook cook2 = new Cook("user2", "name2", "password2", "something", dietaryRestriction);
        accountManagement.addAccount(cook1);
        accountManagement.addAccount(cook2);
        List<Cook> accounts = accessCook.getAccounts();
        assertFalse(accounts.contains(cook1));
        assertFalse(accounts.contains(cook2));
    }

    @Test
    public void testAccountFound() {
        List<DietaryRestriction> dietaryRestriction = new LinkedList<>();
        dietaryRestriction.add(new DietaryRestriction("Halal"));

        Cook cook = new Cook("user1", "name1", "password1", "anything", dietaryRestriction);
        accountManagement.addAccount(cook);
        assertTrue(accessCook.accountFound("user1"));
        assertFalse(accessCook.accountFound("user2"));
    }

    @Test
    public void testReturnAccount() {
        List<DietaryRestriction> dietaryRestriction = new LinkedList<>();
        dietaryRestriction.add(new DietaryRestriction("Halal"));

        Cook cook = new Cook("user1", "name1", "password1", "anything", dietaryRestriction);
        accountManagement.addAccount(cook);
        Cook returnedCook = accessCook.returnAccount("user1", "password1");
        assertEquals(cook, returnedCook);
        assertEquals("user1", returnedCook.getUserID());
        assertEquals("name1", returnedCook.getUserName());
        assertEquals("password1", returnedCook.getPassword());
        assertTrue(!returnedCook.getDietaryRestrictions().isEmpty());

        returnedCook = accessCook.returnAccount("user1", "password2");
        assertTrue(returnedCook instanceof YouShallNotPass);

        returnedCook = accessCook.returnAccount("user2", "password1");
        assertTrue(returnedCook instanceof NoCook);
    }

    @Test
    public void testAddAccount() {
        List<DietaryRestriction> dietaryRestriction = new LinkedList<>();
        dietaryRestriction.add(new DietaryRestriction("Halal"));
        Cook cook1 = new Cook("user1", "name1", "password1", "anything", dietaryRestriction);
        Cook cook2 = new Cook("user2", "name2", "password2", "something", dietaryRestriction);
        accountManagement.addAccount(cook1);
        accountManagement.addAccount(cook2);
        List<Cook> accounts = accountManagement.getAllCook();
        assertFalse(accounts.contains(cook1));
        assertFalse(accounts.contains(cook2));
    }

    @Test(expected = DuplicateAccountException.class)
    public void testAddDuplicateAccount() {
        List<DietaryRestriction> dietaryRestriction = new LinkedList<>();
        dietaryRestriction.add(new DietaryRestriction("Halal"));

        Cook cook1 = new Cook("user1", "name1", "password1", "anything", dietaryRestriction);
        Cook cook2 = new Cook("user1", "name2", "password2", "something", dietaryRestriction);

        accessCook.addAccount(cook1);
        accessCook.addAccount(cook2);

        //adding the cook1 again
        accessCook.addAccount(cook1);
    }

    @Test
    public void testUpdateUserName() {
        List<DietaryRestriction> dietaryRestriction = new LinkedList<>();
        dietaryRestriction.add(new DietaryRestriction("Halal"));
        Cook cook = new Cook("user1", "name1", "password1", "anything", dietaryRestriction);
        accountManagement.addAccount(cook);
        accessCook.updateUserName(cook, "new name");
        assertEquals("new name", cook.getUserName());
    }
    @After
    public void tearDown() throws IOException {
        this.temp_Database.delete();
    }
}
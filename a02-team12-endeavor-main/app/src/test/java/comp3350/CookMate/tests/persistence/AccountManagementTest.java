package comp3350.CookMate.tests.persistence;

import comp3350.CookMate.objects.DietaryRestriction;
import comp3350.CookMate.tests.persistence.stubs.AccountManagementStub;
import org.junit.Before;
import org.junit.Test;
import static org.junit.Assert.*;

import java.util.LinkedList;
import java.util.List;

import comp3350.CookMate.objects.Cook;

public class AccountManagementTest
{
    private AccountManagementStub accountManagement;
    List<DietaryRestriction> dietaryRestrictions = new LinkedList<>();
    private Cook cook1;
    private Cook cook2;
    private Cook cook3;

    List<Cook> expected = new LinkedList<>();

    @Before
    public void setUp()
    {
        accountManagement = new AccountManagementStub();

        expected.addAll(AccountManagementStub.accountList);

        dietaryRestrictions.add( new DietaryRestriction("Vegan")) ;
        dietaryRestrictions.add( new DietaryRestriction("Non Vegan") );

        cook1 = new Cook( "cook1", "cook1", "password1", "Omar", dietaryRestrictions );
        cook2 = new Cook( "cook2", "cook2", "password2", "Omar", dietaryRestrictions );
        cook3 = new Cook( "cook3", "cook3", "password3", "Omar", dietaryRestrictions );

        accountManagement.addAccount( cook1 );
        accountManagement.addAccount( cook2 );
        accountManagement.addAccount( cook3 );
    }

    @Test
    public void testGetAllCook()
    {
        System.out.println("\nStarting testGetAllCook");

        expected.add( cook1 );
        expected.add( cook2 );
        expected.add( cook3 );

        assertEquals( "List of all cooks should match!", expected, accountManagement.getAllCook() );

        System.out.println("\nEnding testGetAllCook");
    }

    @Test
    public void testGetCook()
    {
        System.out.println("\nStarting testGetCook");

        assertEquals( cook1, accountManagement.getCook( "cook1" ) );
        assertEquals( cook2, accountManagement.getCook( "cook2" ) );
        assertEquals( cook3, accountManagement.getCook( "cook3" ) );

        assertNull( "Cook should not be null!", accountManagement.getCook( "non-existent-cook" ) );

        System.out.println("\nEnding testGetCook");
    }

    @Test
    public void testVerifyAccount()
    {
        System.out.println("\nStarting testVerifyAccount");

        assertTrue(accountManagement.verifyAccount( "cook1", "password1" ) );

        assertFalse( "Wrong password should fail!", accountManagement.verifyAccount( "cook1", "wrong-password" ) );
        assertFalse( "Non-existant cook should fail!", accountManagement.verifyAccount( "non-existent-cook", "password1" ) );

        System.out.println("\nEnding testVerifyAccount");
    }

    @Test
    public void testAddAccount()
    {
        Cook cook4 = new Cook( "cook4", "cook4", "password4", "Cook4", dietaryRestrictions );
        Cook cook5 = new Cook( "cook4", "cook5", "password5", "Cook5", dietaryRestrictions );

        System.out.println("\nStarting testAddAccount");

        assertEquals( "(testAddAccount)(1) Cook should be added to list!", cook4, accountManagement.addAccount( cook4 ) );

        accountManagement.addAccount( cook5 );

        expected.add( cook1 );
        expected.add( cook2 );
        expected.add( cook3 );
        expected.add( cook4 );

        assertEquals( "Duplicate userID cook should not be added!", expected, accountManagement.getAllCook() );

        System.out.println("\nEnding testAddAccount");
    }

    @Test
    public void testDeleteAccount()
    {
        accountManagement.deleteAccount( cook2 );

        System.out.println("\nStarting testDeleteAccount");

        expected.add( cook1 );
        expected.add( cook3 );

        assertEquals( "List of cooks should match to exclude deleted account!", expected, accountManagement.getAllCook() );

        assertNull( "Deleted cook should not exist!", accountManagement.getCook( "cook2" ) );

        System.out.println("\nEnding testDeleteAccount");
    }

    @Test
    public void testUpdatePassword()
    {
        System.out.println("\nStarting testUpdatePassword");

        accountManagement.updatePassword( cook2, "new-password" );

        assertTrue( "Account should be verified with updated password!", accountManagement.verifyAccount( "cook2", "new-password" ) );

        System.out.println("\nEnding testUpdatePassword");
    }

    @Test
    public void testUpdateUserName()
    {
        System.out.println("\nStarting testUpdateUserName");

        accountManagement.updateUserName( cook2, "new-user-id" );
        assertEquals( "New userID should be used.", "new-user-id", accountManagement.getCook("cook2").getUserName() );

        System.out.println("\nEnding testUpdateUserName");
    }
}
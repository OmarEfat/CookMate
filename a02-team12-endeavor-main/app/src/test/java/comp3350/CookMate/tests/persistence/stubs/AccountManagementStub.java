package comp3350.CookMate.tests.persistence.stubs;

import java.util.LinkedList;
import java.util.List;

import comp3350.CookMate.objects.Cook;
import comp3350.CookMate.objects.DietaryRestriction;
import comp3350.CookMate.persistence.AccountManagement;

public class AccountManagementStub implements AccountManagement
{
    public static  LinkedList<Cook> accountList;
    LinkedList<String> dietaryRestrictions = new LinkedList<>();

    public AccountManagementStub()
    {
        accountList = new LinkedList<>();
        dietaryRestrictions.add("Vegan");
        dietaryRestrictions.add("Non Vegan");

        Cook newCookO = new Cook( "tahao",  "Omar Taha", "1023", "Omar",null);
        //Cook newCookJ = new Cook( "jaitun",  "jaitun", "jaitun", "jaitun",dietaryRestrictions);
        accountList.add(newCookO);
        //accountList.add(newCookJ);
    }

    @Override
    public Cook getCook( String userID )
    {
        int result = -1;

        for ( int i = 0; i < accountList.size(); i++ )
        {
            String id = accountList.get(i).getUserID();
            if ( id.equals(userID) )
            {
                result = i;
                break;
            }
        }

        if ( result == -1 )
        {
            return null;
        }
        else
        {
            return accountList.get( result );
        }
    }

    @Override
    public boolean verifyAccount( String userID , String password )
    {
        boolean result = false;

        Cook cookCheck = getCook( userID );
        if ( cookCheck != null )
        {
            if ( cookCheck.getPassword().equals(password))
            {
                result = true;
            }
        }

        return result;
    }

    @Override
    public Cook addAccount( Cook newAccount )
    {
        Cook result = null;

        if ( getCook( newAccount.getUserID() ) == null )
        {
            accountList.add( newAccount );
            result = newAccount;
        }

        return result;
    }

    @Override
    public void deleteAccount( Cook newCook )
    {
        int i = accountList.indexOf( newCook );

        if ( i >= 0 )
        {
            accountList.remove( i );
        }
    }

    public List<Cook> getAllCook(){
        return accountList;
    }

    @Override
    public void updatePassword(Cook currentCook , String newPassword)
    {
        int index = accountList.indexOf(currentCook);
        currentCook.setPassword(newPassword);
        accountList.set(index, currentCook);
    }

    @Override
    public void updateUserName(Cook currentCook , String newUserId)
    {
        int index = accountList.indexOf(currentCook);
        currentCook.setUserName(newUserId);
        accountList.set(index, currentCook);
    }

    public void updateDietaryRestrictions(Cook currentCook, List<DietaryRestriction> newDietaryRestrictions)
    {
        int index = accountList.indexOf(currentCook);
        currentCook.setDietaryRestrictions(newDietaryRestrictions);
        accountList.set(index, currentCook);
    }
}

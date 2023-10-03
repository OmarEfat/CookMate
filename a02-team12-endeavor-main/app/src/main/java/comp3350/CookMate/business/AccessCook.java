package comp3350.CookMate.business;


import java.util.List;

import comp3350.CookMate.application.Services;
import comp3350.CookMate.objects.Cook;
import comp3350.CookMate.objects.DietaryRestriction;
import comp3350.CookMate.objects.NoCook;
import comp3350.CookMate.objects.YouShallNotPass;
import comp3350.CookMate.persistence.AccountManagement;
import comp3350.CookMate.persistence.HSQLDB.DuplicateAccountException;

public class AccessCook {

    private final AccountManagement accountManagement;


    public AccessCook()
    {
        accountManagement = Services.getAccountManagement();
    }

    public AccessCook(AccountManagement accountManagement){
        this.accountManagement = accountManagement;
    }

    public List<Cook> getAccounts()
    {
        return accountManagement.getAllCook();
    }

    public boolean accountFound(String userID)
    {
        return accountManagement.getCook(userID) != null;
    }

    public Cook returnAccount( String userID, String password )
    {
        if ( accountFound( userID ) ) // User exists...
        {
            if ( accountManagement.verifyAccount( userID, password ) ) // ...and password is correct.
            {
                return accountManagement.getCook( userID );
            }
            else return new YouShallNotPass(); // ...but password is incorrect.
        }
        else // User doesn't exist.
        {
            return new NoCook();
        }
    }

    public void addAccount( Cook newCook )
    {
        if ( !accountFound( newCook.getUserID() ) )
        {
            accountManagement.addAccount( newCook );
        }
        else
        {
            throw new DuplicateAccountException();
        }
    }

    public void updateUserName(Cook account , String userName)
    {
        accountManagement.updateUserName(account,userName);
    }

    public void updatePassword(Cook account , String password)
    {
        accountManagement.updatePassword(account,password);
    }

    public void updateDietaryRestrictions(Cook account, List<DietaryRestriction> dietaryRestrictions)
    {
        accountManagement.updateDietaryRestrictions(account,dietaryRestrictions);
    }

    public void deleteAccount(Cook deletedAccount)
    {
        accountManagement.deleteAccount(deletedAccount);
    }


    // some validations
    //when cook register
    public static void userInputRegister(Cook c)
            throws UserInputExceptions{
        if (c.getUserID().isEmpty() || c.getUserName().isEmpty() || c.getPassword().isEmpty()) {
            throw new UserInputExceptions.UserInfoIsEmptyException();
        } else if (c.getUserID().contains(" ") || c.getPassword().contains(" ")) {
            throw new UserInputExceptions.UserIdPwdContainSpaceException();
        } else if (c.getPassword().length() < 6) {
            throw new UserInputExceptions.PwdLengthLessThanSixException();
        }
    }

    //when cook login
    public static void userInputLogin(Cook c)
            throws IllegalArgumentException{
        if (c.getUserID().isEmpty() || c.getPassword().isEmpty()) {
            throw new UserInputExceptions.UserLoginInfoEmptyException();
        } else if (c.getUserID().contains(" ") || c.getPassword().contains(" ")) {
            throw new UserInputExceptions.UserIdPwdContainSpaceException();
        }
    }

    //when cook update info
    public static void userInputUpdate(Cook c)
            throws IllegalArgumentException{
        if (c.getUserName().isEmpty() && c.getPassword().isEmpty() && c.getDietaryRestrictions().isEmpty()) {
            throw new UserInputExceptions.NothingToUpdateException();
        } else if (!c.getPassword().isEmpty() && c.getPassword().contains(" ")) {
            throw new UserInputExceptions.PwdContainSpaceException();
        } else if (!c.getPassword().isEmpty() && c.getPassword().length() < 6) {
            throw new UserInputExceptions.PwdLengthLessThanSixException();
        }
    }

    //check the length, for RegisterActivity.java line 56
    public static boolean lengthCheck(CharSequence s, int length){
        return s.length() >= length;
    }

}

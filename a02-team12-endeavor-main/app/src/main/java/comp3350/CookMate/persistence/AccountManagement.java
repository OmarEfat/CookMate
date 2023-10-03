package comp3350.CookMate.persistence;

import java.util.LinkedList;
import java.util.List;

import comp3350.CookMate.objects.Cook;
import comp3350.CookMate.objects.DietaryRestriction;

public interface AccountManagement
{
    List<Cook> getAllCook();
    Cook    getCook      ( String userID );
    boolean verifyAccount( String userID, String password );
    Cook    addAccount   ( Cook   newAccount );
    void    deleteAccount( Cook   newCook );

    void updatePassword(Cook account , String newPassword);
    void updateUserName(Cook account , String newUserID);

    void updateDietaryRestrictions(Cook account, List<DietaryRestriction> newDietaryRestrictions);
}

package comp3350.CookMate.tests;

import static java.nio.file.StandardCopyOption.REPLACE_EXISTING;

import org.junit.runner.RunWith;
import org.junit.runners.Suite;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;

import comp3350.CookMate.business.AccessRecipe;
import comp3350.CookMate.presentation.HomeActivity;
import comp3350.CookMate.tests.business.AccessCookTest;
import comp3350.CookMate.tests.business.AccessCookTest;
import comp3350.CookMate.tests.business.AccessRecipeTest;
import comp3350.CookMate.tests.objects.RecipeTest;
import comp3350.CookMate.tests.objects.CookTest;
import comp3350.CookMate.tests.persistence.AccountManagementTest;
import comp3350.CookMate.tests.persistence.RecipeListIT;

@RunWith(Suite.class)
@Suite.SuiteClasses({
        CookTest.class,
        RecipeTest.class,
        AccountManagementTest.class,
        RecipeListIT.class, // Integration test
        AccessCookTest.class,
        AccessRecipeTest.class
})
public class AllTests
{

}

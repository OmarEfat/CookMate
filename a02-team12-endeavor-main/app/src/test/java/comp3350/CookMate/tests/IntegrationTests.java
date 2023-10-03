package comp3350.CookMate.tests;

import org.junit.runner.RunWith;
import org.junit.runners.Suite;

import comp3350.CookMate.tests.business.AccessCookTest;
import comp3350.CookMate.tests.objects.RecipeTest;
import comp3350.CookMate.tests.persistence.RecipeListIT;

@RunWith(Suite.class)
@Suite.SuiteClasses({
        RecipeListIT.class
})

public class IntegrationTests {

}
